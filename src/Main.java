

public class Main {
    public static void main(String[] args) {

        DatabaseManager dbm = new DatabaseManager();
        /*Partie partie = new Partie();
        
        partie.debutPartie(dbm);*/

        //Test methode AttaqueDAO
        AttaqueDAO aDAO = new AttaqueDAO();
        int i=0;
        Attaque[] tabAttaque = aDAO.chargeAttaque();
            for (Attaque attaque : tabAttaque) {

                System.err.println(i);
                i++;
                if (attaque != null) {
                    System.out.println(attaque);
                }
            }
    
            //Test methode recupAttaquesPokemon
        int idPoke = 1; // Remplacez par l'ID du Pokémon que vous souhaitez tester
        Attaque[] attaquesPoke = aDAO.recupAttaquesPokemon(idPoke);
        System.out.println("Attaques du Pokémon avec ID " + idPoke + " :");
        for (Attaque attaque : attaquesPoke) {
            if (attaque != null) {
                System.out.println(attaque);
            }
        }



    }
}