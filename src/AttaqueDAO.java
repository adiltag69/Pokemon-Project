import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class attaqueDAO {
    public final static int MAX_ATTAQUES = 200;
    private DatabaseManager dbm;

    public attaqueDAO() {
        dbm = new DatabaseManager();
        try {
            dbm.connect();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public attaque[] chargeAttaque(DatabaseManager dbm) {
        String sql = "SELECT * FROM attaques";
        attaque[] tabAttaque = new attaque[MAX_ATTAQUES];
        try {
            PreparedStatement requete = dbm.getConnection().prepareStatement(sql);
            ResultSet donnee = requete.executeQuery();
            int i = 0;
            while (donnee.next() && i < MAX_ATTAQUES) {
                String libelle  = donnee.getString("libelle");
                int puissance   = donnee.getInt("puissance");
                int typeId      = donnee.getInt("type_id");
                tabAttaque[i]   = new attaque(libelle, puissance, typeId);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERREUR DU CHARGEMENT DES ATTAQUES : " + e.getErrorCode());
        }
        return tabAttaque;
    }

    public attaque[] recupAttaquesPokemon(int fkPokemon, DatabaseManager dbm) {
        String sql = "SELECT a.libelle, a.puissance, a.type_id " +
                     "FROM attaques a " +
                     "JOIN pokemon_attaque pa ON pa.fkAttaque = a.id " +
                     "WHERE pa.fkPokemon = ? LIMIT " + MAX_ATTAQUES + ";";
        attaque[] tabAttaque = new attaque[MAX_ATTAQUES];
        try {
            PreparedStatement pstmt = dbm.getConnection().prepareStatement(sql);
            pstmt.setInt(1, fkPokemon);
            ResultSet donnee = pstmt.executeQuery();
            int i = 0;
            while (donnee.next() && i < MAX_ATTAQUES) {
                String libelle  = donnee.getString("libelle");
                int puissance   = donnee.getInt("puissance");
                int typeId      = donnee.getInt("type_id");
                tabAttaque[i]   = new attaque(libelle, puissance, typeId);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERREUR ATTAQUES POKEMON : " + e.getErrorCode());
        }
        return tabAttaque;
    }
}