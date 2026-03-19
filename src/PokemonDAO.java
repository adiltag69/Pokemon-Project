import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PokemonDAO{

    public  final static int GEN1 = 151;

    public Pokemon[] chargerTous(DatabaseManager dbm){
        String sql = "SELECT * FROM pokemons";
        Pokemon[] tousPokemon  = new Pokemon[GEN1];
        try {
            PreparedStatement requete = dbm.getConnection().prepareStatement(sql);
            ResultSet donnees = requete.executeQuery();
            int i =0;
            while (donnees.next()) {
                String nom = donnees.getString("nom");
                int pv = donnees.getInt("pv");
                int pvMax = donnees.getInt("pvMax");
                int attaque = donnees.getInt("attaque");
                int defense = donnees.getInt("defense");
                int vitesse = donnees.getInt("vitesse");
                tousPokemon[i] = new Pokemon(nom, pv, pvMax, attaque, defense, vitesse, null);                
                i++;           
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des Pokémon ! Code Erreur : " + e.getErrorCode());
        }
        return tousPokemon;
    }
}