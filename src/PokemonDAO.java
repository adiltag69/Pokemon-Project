import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PokemonDAO{

    public  final static int GEN1 = 151;
    private DatabaseManager dbm;

    public void chargerTous(){
        dbm = new DatabaseManager();
        try {
            dbm.connect();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        String sql = "SELECT * FROM pokemons";
        try {
            PreparedStatement requete = dbm.getConnection().prepareStatement(sql);
            ResultSet donnees = requete.executeQuery();
            while (donnees.next()) {
                int id = donnees.getInt("id");
                String nom = donnees.getString("nom");
                int pv = donnees.getInt("pv");
                int pvMax = donnees.getInt("pvMax");
                int attaque = donnees.getInt("attaque");
                int defense = donnees.getInt("defense");
                int vitesse = donnees.getInt("vitesse");
                System.out.println(" id : "+ id  + " | nom : " + nom + " | pv : " + pv + " | pvMax : " + pvMax + " | attaque : " + attaque + " | defense : " + defense + " | vitesse : " + vitesse);
            }    

        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des Pokémon ! Code Erreur : " + e.getErrorCode());
        }
        try {
            dbm.disconnect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la déconnexion ! Code Erreur : " + e.getErrorCode());
        }
        return;
    }

    public Pokemon chargerParId(int id, Attaque[] tabAttaque){
        Pokemon pokemon = null;
        dbm = new DatabaseManager();
        try {
            dbm.connect();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        String sql = "SELECT * FROM pokemons WHERE id = ?";
        try {
            PreparedStatement requete = dbm.getConnection().prepareStatement(sql);
            requete.setInt(1, id);
            ResultSet donnees = requete.executeQuery();
            if (donnees.next()) {
                
                String nom = donnees.getString("nom");
                int pv = donnees.getInt("pv");
                int pvMax = donnees.getInt("pvMax");
                int attaque = donnees.getInt("attaque");
                int defense = donnees.getInt("defense");
                int vitesse = donnees.getInt("vitesse");
                pokemon = new Pokemon(id, nom, pv, pvMax, attaque, defense, vitesse, tabAttaque);
            }    

        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement du Pokémon ! Code Erreur : " + e.getErrorCode());
        }
        try {
            dbm.disconnect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la déconnexion ! Code Erreur : " + e.getErrorCode());
        }
        return pokemon;
    } 
}