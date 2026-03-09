public class TypeDAO {
    // Attributs
    private DatabaseManager db;

    // Constructeur
    public TypeDAO() {
        this.db = DatabaseManager.getInstance();
    }

    // Méthodes
    public List<Type> chargerTous() {
        List<Type> types = new ArrayList<>();
        String sql = "SELECT * FROM type";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Type type = new Type(rs.getInt("id"), rs.getString("nom"));
                types.add(type);
            }

            // Charger les efficacités
            Map<Integer, Type> typeMap = new HashMap<>();
            for (Type t : types) typeMap.put(t.getId(), t);

            chargerEfficacites(conn, typeMap);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return types;
    }

    public Map<Type, Double> chargerEfficacites() {
        Map<Type, Double> efficacites = new HashMap<>();
        List<Type> types = chargerTous();
        String sql = "SELECT type_attaquant_id, type_defenseur_id, multiplicateur FROM efficacite";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Map<Integer, Type> typeMap = new HashMap<>();
            for (Type t : types) typeMap.put(t.getId(), t);

            while (rs.next()) {
                Type attaquant = typeMap.get(rs.getInt("type_attaquant_id"));
                Type defenseur = typeMap.get(rs.getInt("type_defenseur_id"));
                double multiplicateur = rs.getDouble("multiplicateur");

                if (attaquant != null && defenseur != null) {
                    efficacites.put(defenseur, multiplicateur);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return efficacites;
    }

    private void chargerEfficacites(Connection conn, Map<Integer, Type> typeMap) throws SQLException {
        String sql = "SELECT type_attaquant_id, type_defenseur_id, multiplicateur FROM efficacite";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Type attaquant = typeMap.get(rs.getInt("type_attaquant_id"));
                Type defenseur = typeMap.get(rs.getInt("type_defenseur_id"));
                double multiplicateur = rs.getDouble("multiplicateur");

                if (attaquant != null) {
                    attaquant.getEfficacites().put(defenseur, multiplicateur);
                }
            }
        }
    }
}