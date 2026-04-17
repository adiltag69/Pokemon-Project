

public class Main {
    public static void main(String[] args) {

        DatabaseManager dbm = new DatabaseManager();    
        Partie partie = new Partie();
        
        Joueur[] joueurs = partie.debutPartie(dbm);
        partie.combat(joueurs);

        /* //Test methode AttaqueDAO
        AttaqueDAO aDAO = new AttaqueDAO();
        Attaque[] tabAttaque = aDAO.chargeAttaque();
            for (Attaque attaque : tabAttaque) {               
                if (attaque != null) {
                    System.out.println(attaque.toString());
                }
            }
    
            //Test methode recupAttaquesPokemon
        int idPoke = 1; // Remplacez par l'ID du Pokémon que vous souhaitez tester
        Attaque[] attaquesPoke = aDAO.recupAttaquesPokemon(idPoke);
        System.out.println("Attaques du Pokémon avec ID " + idPoke + " :");
        for (Attaque attaque : attaquesPoke) {
            if (attaque != null) {
                System.out.println(attaque.toString());
            }
        }
 */


    }
}