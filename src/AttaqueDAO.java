import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttaqueDAO {
    public final static int MAX_ATTAQUES = 4;
    public final static int MAX_ATTAQUE_OVERSIZE = 165;
    private DatabaseManager dbm;

    public AttaqueDAO() {
        dbm = new DatabaseManager();
        try {
            dbm.connect();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public Attaque[] chargeAttaque() {
        String sql = "SELECT * FROM attaques";
        Attaque[] tabAttaque = new Attaque[MAX_ATTAQUE_OVERSIZE];
        try {
            PreparedStatement requete = this.dbm.getConnection().prepareStatement(sql);
            ResultSet donnee = requete.executeQuery();
            int i = 0;
            while (donnee.next()) {
                String libelle  = donnee.getString("libelle");
                int puissance   = donnee.getInt("puissance");
                int typeId      = donnee.getInt("type_id");
                tabAttaque[i]   = new Attaque(libelle, puissance, typeId);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERREUR DU CHARGEMENT DES ATTAQUES : " + e.getErrorCode());
        }
        return tabAttaque;
    }

    public Attaque[] recupAttaquesPokemon(int fkPokemon) {
        String sql = "SELECT a.libelle, a.puissance, a.type_id " +
                     "FROM attaques a " +
                     "JOIN pokemon_attaque pa ON pa.fkAttaque = a.id " +
                     "WHERE pa.fkPokemon = ? LIMIT " + MAX_ATTAQUES + ";";
        Attaque[] tabAttaque = new Attaque[MAX_ATTAQUES];
        try {
            PreparedStatement pstmt = this.dbm.getConnection().prepareStatement(sql);
            pstmt.setInt(1, fkPokemon);
            ResultSet donnee = pstmt.executeQuery(); 
            int i = 0;
            while (donnee.next() && i < MAX_ATTAQUES) {
                String libelle  = donnee.getString("libelle");
                int puissance   = donnee.getInt("puissance");
                int typeId      = donnee.getInt("type_id");
                tabAttaque[i]   = new Attaque(libelle, puissance, typeId);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERREUR ATTAQUES"+ e.getSQLState()+" POKEMON : " + e.getErrorCode());
        }
        return tabAttaque;
    }
}