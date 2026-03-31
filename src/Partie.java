import java.util.Scanner;

public class Partie{

    public static final int NB_POKEMON_PAR_EQUIPE = 6;
    private Joueur joueur1;
    private Joueur joueur2;
    private String nomJ;
    Pokemon[] equipeJ;
    
   


    public void debutPartie(DatabaseManager dbm){

        System.out.println("Début de la partie");
        Scanner nbJoueur = new Scanner(System.in);
        int choix = 0;
        Pokemon[] equipeJ1 = new Pokemon[NB_POKEMON_PAR_EQUIPE];
        do{
            System.out.println("Choisissez le nombre de joueurs : ");
            System.out.println("/1/  1 Joueur (contre l'IA)");
            System.out.println("/2/  2 Joueurs");
            choix = nbJoueur.nextInt();
            if (choix == 1){
                System.out.println("Vous avez choisi de jouer contre l'IA");
                Scanner scJ1 = new Scanner(System.in);
                System.out.println("Entrez votre nom : ");
                nomJ = scJ1.nextLine();
                scJ1.close();
                equipeJ1 = choisirPokemon(dbm);
                joueur1 = new Joueur(nomJ, equipeJ1);               

            } else if (choix == 2){
            System.out.println("Vous avez choisi de jouer contre un autre joueur");
        
            }
            
        } while (choix != 1 && choix != 2);
        System.out.println("Choix invalide, veuillez réessayer, Choix Valide : 1 ou 2");
        nbJoueur.close();   
    }
 
    private Pokemon[] choisirPokemon(DatabaseManager dbm) {
        try {
            dbm.connect();
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
        Scanner scJ = new Scanner(System.in);
        System.out.println("Voici la liste des Pokémon disponibles : ");
        PokemonDAO pokeDAO = new PokemonDAO();
        pokeDAO.chargerTous();

        equipeJ = new Pokemon[NB_POKEMON_PAR_EQUIPE];
        for (int i = 0; i < equipeJ.length; i++) {
            System.out.println("Entrez le numéro de votre Pokémon n°" + (i+1) + " : ");
            int idPokeJ1 = scJ.nextInt();
            if (idPokeJ1 < 1 || idPokeJ1 > PokemonDAO.GEN1) {
                System.out.println("Numéro de Pokémon invalide, veuillez réessayer.");
                i--; 
                continue;
            }
            Attaque[] attaquesPokeJ1 = new AttaqueDAO().recupAttaquesPokemon(idPokeJ1, dbm);
            equipeJ[i] = pokeDAO.chargerParId(idPokeJ1, attaquesPokeJ1);
            scJ.nextLine();
        }
        scJ.close();
        return equipeJ;
    }
}