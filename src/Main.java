import java.sql.SQLException;
public class Main{

    public static void main(String[] args) {

        DatabaseManager dbm = new DatabaseManager();

        try{
            dbm.connect();
        }catch(SQLException e){
            System.out.println("Erreur : " + e.getMessage());
        }
        PokemonDAO pdao = new PokemonDAO();
        Pokemon[] tabpoke;
        tabpoke = pdao.chargerTous(dbm);
        for (int i = 0; i < 151; i++) {
            System.out.println(i+1 + " : " + tabpoke[i].getNom()+ " PV : " + tabpoke[i].getPv() + " PV Max : " + tabpoke[i].getPvMax() + " Attaque : " + tabpoke[i].getAttaque() + " Defense : " + tabpoke[i].getDefense() + " Vitesse : " + tabpoke[i].getVitesse());    
            
        }

    }

}