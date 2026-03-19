import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        DatabaseManager dbm = new DatabaseManager();
        try {
            dbm.connect();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        // ── Test TypeDAO ──────────────────────────────────────
        TypeDAO td = new TypeDAO();
        Type[] tabTypes;
        tabTypes = td.chargeType(dbm);
        for (int i = 0; i < 15; i++) {
            System.out.println(i + 1 + " : " + tabTypes[i].getNom());
        }

        // ── Test attaqueDAO ───────────────────────────────────
        attaqueDAO attaqueDAO = new attaqueDAO();
        attaque[] attaques = attaqueDAO.chargeAttaque(dbm);
        System.out.println("\n=== Attaques ===");
        for (attaque a : attaques) {
            if (a != null) {
                System.out.println("- " + a.getLibelle() + " | puissance : " + a.getPuissance() + " | type : " + a.getTypeId());
            }
        }
    }
}