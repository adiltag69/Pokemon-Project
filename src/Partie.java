import java.sql.SQLException;
import java.util.Scanner;
import java.util.Random;

public class Partie{

    public static final int NB_POKEMON_PAR_EQUIPE = 6;
    private Joueur joueur1;
    private Joueur joueur2;
    private String nomJ;
    Pokemon[] equipeJ;
    Random rand = new Random();
    AttaqueDAO aDAO = new AttaqueDAO();

    
    public void debutPartie(DatabaseManager dbm){

        System.out.println("Début de la partie");
        Scanner nbJoueur = new Scanner(System.in);
        int choix = 0;
        do{
            System.out.println("Choisissez le nombre de joueurs : ");
            System.out.println("/1/  1 Joueur (contre l'IA)");
            System.out.println("/2/  2 Joueurs");
            choix = nbJoueur.nextInt();
            if (choix == 1){
                System.out.println("Vous avez choisi de jouer contre l'IA");


                //creation de l'IA
                nomJ = "Professeur Axeman";
                equipeJ = new Pokemon[NB_POKEMON_PAR_EQUIPE];
                for (int i = 0; i < equipeJ.length; i++) {
                    int idPokeIA = rand.nextInt(151) + 1;
                    Attaque[] attaquesPokeIA = aDAO.recupAttaquesPokemon(idPokeIA, dbm);
                    PokemonDAO pokeDAO = new PokemonDAO();
                    equipeJ[i] = pokeDAO.chargerParId(idPokeIA, attaquesPokeIA);
                }
                JoueurIA joueurIA = new JoueurIA(nomJ, equipeJ);
                System.out.println("Voici votre adversaire :"+ joueurIA.toString());

                //creation du joueur humain
                joueur1 = creationJH(dbm);
                 

            } else if (choix == 2){
            System.out.println("Vous avez choisi de jouer contre un autre joueur");
                //creation du joueur 1
                System.out.println("Création du Joueur 1 : ");
                joueur1 = creationJH(dbm);

                //creation du joueur 2
                System.out.println("Création du Joueur 2 : ");
                joueur2 = creationJH(dbm);

                System.out.println("Ce combat opposera " + joueur1.getNom() + " à " + joueur2.getNom());
                System.out.println("Voici vos equipes : "+ joueur1.toString() + "\n" + joueur2.toString());
        
            }
            
        } while (choix != 1 && choix != 2);
        System.out.println("Choix invalide, veuillez réessayer, Choix Valide : 1 ou 2");
        nbJoueur.close();   
    }
    private JoueurHumain creationJH(DatabaseManager dbm){

        Scanner scJ1 = new Scanner(System.in);
        System.out.println("Entrez votre nom : ");
        nomJ = scJ1.nextLine();
        equipeJ = choisirPokemonJH(dbm);
        JoueurHumain joueur = new JoueurHumain(nomJ, equipeJ);
        System.out.println("Voici votre dresseur : "+ joueur.toString());
        try {
            dbm.disconnect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la déconnexion ! Code Erreur : " + e.getErrorCode());
        }
        return joueur;        
    
    }
    private Pokemon[] choisirPokemonJH(DatabaseManager dbm) {
        try {
            dbm.connect();
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
        Scanner scJ = new Scanner(System.in);
        System.out.println("Voici la liste des Pokémon disponibles : ");
        PokemonDAO pokeDAO = new PokemonDAO();
        pokeDAO.chargerTous();

        int idPokeJ1;
        equipeJ = new Pokemon[NB_POKEMON_PAR_EQUIPE];
        for (int i = 0; i < equipeJ.length; i++) {
            do{
                
                System.out.println("Entrez le numéro de votre Pokémon n°" + (i+1) + " : ");
                idPokeJ1 = scJ.nextInt();
            }while(idPokeJ1 < 1 || idPokeJ1 > PokemonDAO.GEN1);
            Attaque[] attaquesPokeJ1 = new AttaqueDAO().recupAttaquesPokemon(idPokeJ1, dbm);
            equipeJ[i] = pokeDAO.chargerParId(idPokeJ1, attaquesPokeJ1);
            scJ.nextLine();
        }
        scJ.close();
        try {
            dbm.disconnect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la déconnexion ! Code Erreur : " + e.getErrorCode());
        }
        return equipeJ;
    }
}