import java.util.Scanner;

public class Partie{

    public static final int NB_POKEMON_PAR_EQUIPE = 6;
    private Joueur joueur1;
    private Joueur joueur2;
    
   


    public void debutPartie(DatabaseManager dbm){

        System.out.println("Début de la partie");
        Scanner nbJoueur = new Scanner(System.in);
        int choix = 0;
        String nomJ1;
        do{
            System.out.println("Choisissez le nombre de joueurs : ");
            System.out.println("/1/  1 Joueur (contre l'IA)");
            System.out.println("/2/  2 Joueurs");
            choix = nbJoueur.nextInt();
            if (choix == 1){
                System.out.println("Vous avez choisi de jouer contre l'IA");
                Scanner scJ1 = new Scanner(System.in);
                System.out.println("Entrez votre nom : ");
                nomJ1 = scJ1.nextLine();

                System.out.println("Voici la liste des Pokémon disponibles : ");
                PokemonDAO pokeDAO = new PokemonDAO();
                pokeDAO.chargerTous();

                Pokemon[] equipeJ1 = new Pokemon[NB_POKEMON_PAR_EQUIPE];
                for (int i = 0; i < equipeJ1.length; i++) {
                    System.out.println("Entrez le numéro de votre Pokémon n°" + (i+1) + " : ");
                    int idPokeJ1 = scJ1.nextInt();
                    equipeJ1[i] = pokeDAO.chargerParId(idPokeJ1);
                    scJ1.nextLine();

                }
                scJ1.close();
                

               
               
                

            } else if (choix == 2){
            System.out.println("Vous avez choisi de jouer contre un autre joueur");
        
            }
            
        } while (choix != 1 && choix != 2);
        System.out.println("Choix invalide, veuillez réessayer, Choix Valide : 1 ou 2");
        nbJoueur.close();   
    }
        
}