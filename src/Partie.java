import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
@SuppressWarnings("ConvertToTryWithResources")

public class Partie{

    public static final int NB_POKEMON_PAR_EQUIPE = 6;
    private Joueur joueur1;
    private Joueur joueur2;
    private String nomJ;
    Pokemon[] equipeJ;
    Random rand = new Random();
    AttaqueDAO aDAO = new AttaqueDAO();;
    Scanner sc = new Scanner(System.in);

    //Méthode pour le début de la partie, elle gère la création des joueurs et de leurs équipes
    public Joueur[] debutPartie(DatabaseManager dbm){

        Joueur[] tabJoueur = new Joueur[2];
        System.out.println("Début de la partie");
        int choix;
        do{
            System.out.println("Choisissez le nombre de joueurs : ");
            System.out.println("/1/  1 Joueur (contre l'IA)");
            System.out.println("/2/  2 Joueurs");
            choix = sc.nextInt();
            sc.nextLine();
            if (choix == 1){
                System.out.println("Vous avez choisi de jouer contre l'IA");
                pause(500);


                //creation de l'IA
                nomJ = "Professeur Axeman";
                equipeJ = new Pokemon[NB_POKEMON_PAR_EQUIPE];
                for (int i = 0; i < equipeJ.length; i++) {
                    int idPokeIA = rand.nextInt(151) + 1;
                    Attaque[] attaquesPokeIA = aDAO.recupAttaquesPokemon(idPokeIA);
                    PokemonDAO pokeDAO = new PokemonDAO();
                    equipeJ[i] = pokeDAO.chargerParId(idPokeIA, attaquesPokeIA);
                }
                JoueurIA joueurIA = new JoueurIA(nomJ, equipeJ);
                System.out.println("\n Voici votre adversaire : \n ");
                pause(500);
                joueurIA.afficherJoueur();
                

                //creation du joueur humain
                joueur1 = creationJH(dbm);
                System.out.println("Voici votre dresseur : ");
                pause(500);
                joueur1.afficherJoueur();

                tabJoueur[0] = joueurIA;
                tabJoueur[1] = joueur1; 

            } else if (choix == 2){
            System.out.println("Vous avez choisi de jouer contre un autre joueur");
                //creation du joueur 1
                System.out.println("Création du Joueur 1 : ");
                joueur1 = creationJH(dbm);

                //creation du joueur 2
                System.out.println("Création du Joueur 2 : ");
                joueur2 = creationJH(dbm);

                System.out.println("Ce combat opposera " + joueur1.getNom() + " à " + joueur2.getNom());
                System.out.println("Voici vos equipes : \n");
                pause(500);
                joueur1.afficherJoueur();
                pause(1000);
                System.out.println("\n VS \n");
                pause(500);
                joueur2.afficherJoueur();
                pause(1000);

                tabJoueur[0] = joueur1;
                tabJoueur[1] = joueur2;
        
            }

            if (choix != 1 && choix != 2){
                System.out.println("Choix invalide, veuillez réessayer, Choix Valide : 1 ou 2");        
            }
            
        } while (choix != 1 && choix != 2);
        

        return tabJoueur;
    }


    //Methode du combat qui gère le déroulement du combat, les tours, les attaques, les changements de pokémon, etc.
    public void combat(Joueur[] tabJoueur){
        pause(7000);
        System.out.println("Le combat commence !");
        pause(1500);
        System.out.println("Voici les règles du combat :\n - Chaque joueur choisit un Pokémon pour combattre\n - Le joueur qui commence est choisi aléatoirement\n - Chaque joueur peut choisir d'attaquer, de changer de Pokémon ou de fuire \n - Le combat se termine lorsque tous les Pokémon d'un joueur sont K.O. ou que l'un des joueurs fuit \n");
        pause(3000);

        Joueur j1 = tabJoueur[0];
        Joueur j2 = tabJoueur[1];

        choisirPokemonCombat(j1);
        choisirPokemonCombat(j2);

        while (tourCombat(j1, j2)) {
        }
        pause(1000);
        System.out.println("=== FIN DU COMBAT ===");
    }



    private JoueurHumain creationJH(DatabaseManager dbm){

        System.out.println("Entrez votre nom : ");
        nomJ = sc.nextLine();
        equipeJ = choisirPokemonJH(dbm);
        JoueurHumain joueur = new JoueurHumain(nomJ, equipeJ);
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
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
        System.out.println("Voici la liste des Pokémon disponibles : ");
        pause(1000);
        PokemonDAO pokeDAO = new PokemonDAO();
        pokeDAO.chargerTous();

        int idPokeJ1;
        equipeJ = new Pokemon[NB_POKEMON_PAR_EQUIPE];
        for (int i = 0; i < equipeJ.length; i++) {
            do{
                System.out.println("Entrez le numéro de votre Pokémon n°" + (i+1) + " : ");
                idPokeJ1 = sc.nextInt();
            }while(idPokeJ1 < 1 || idPokeJ1 > PokemonDAO.GEN1);
            Attaque[] attaquesPokeJ1 = new AttaqueDAO().recupAttaquesPokemon(idPokeJ1);
            equipeJ[i] = pokeDAO.chargerParId(idPokeJ1, attaquesPokeJ1);
            sc.nextLine();
        }
        try {
            dbm.disconnect();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la déconnexion ! Code Erreur : " + e.getErrorCode());
        }
        return equipeJ;
    }

    public Pokemon choisirPokemonCombat(Joueur joueur){
        if (joueur == null) {
            System.out.println("Erreur : le joueur est null");
            return null;
        }
        int choixPoke;
        Pokemon pokeChoisi;
        if (joueur instanceof JoueurIA) {
            Pokemon[] equipe = joueur.getEquipe();
            List<Pokemon> vivants = new ArrayList<>();
            for (Pokemon p : equipe) {
                if (p.getPv() > 0) {
                    vivants.add(p);
                }
            }

            if (vivants.isEmpty()) {
                System.out.println(joueur.getNom() + " n'a plus de Pokémon en état de combattre.");
                return null;
            }
            int index = rand.nextInt(vivants.size());
            pokeChoisi = vivants.get(index);
            System.out.println(joueur.getNom() + " a choisi " + pokeChoisi.getNom() + " pour le combat !");
            pause(2000);
            joueur.setPokemonActif(pokeChoisi);

            return pokeChoisi;

        } else if (joueur instanceof JoueurHumain) {
            boolean tousKO = true;
            for (Pokemon p : joueur.getEquipe()) {
                if (p.getPv() > 0) {
                    tousKO = false;
                    break;
                }
            }
            if (tousKO) {
                System.out.println(joueur.getNom() + " n'a plus de Pokémon en état de combattre.");
                return null;
            }
            do {
                System.out.println("\n" + joueur.getNom() + ", choisissez votre Pokémon pour le combat : ");
                for (int i = 0; i < joueur.getEquipe().length; i++) {
                    System.out.println("/" + (i+1) + "/ " + joueur.getEquipe()[i].getNom() +
                    " (PV : " + joueur.getEquipe()[i].getPv() + "/" + joueur.getEquipe()[i].getPvMax() + ")");
                    pause(500);
                }
                while (!sc.hasNextInt()) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                    sc.next();
                }
                choixPoke = sc.nextInt();

                } while (choixPoke < 1 || choixPoke > joueur.getEquipe().length || joueur.getEquipe()[choixPoke - 1].getPv() <= 0);
                pokeChoisi = joueur.getEquipe()[choixPoke - 1];
                sc.nextLine();
                System.out.println(joueur.getNom() + " a choisi " + pokeChoisi.getNom() + " pour le combat !");
                pause(2000);
                joueur.setPokemonActif(pokeChoisi);

            return pokeChoisi;
            }
        System.out.println("Erreur : type de joueur inconnu");
        return null;
    }
    

    // Affiche l'état actuel du combat (PV des deux pokémons en jeu)
    private void afficherEtatCombat(Joueur j1, Joueur j2) {
        Pokemon p1 = j1.getPokemonActif();
        Pokemon p2 = j2.getPokemonActif();
        System.out.println("\n==========================================");
        System.out.println("  " + j1.getNom() + " : " + p1.getNom() +
                           "  [PV : " + p1.getPv() + "/" + p1.getPvMax() + "]");
        System.out.println("       VS");
        System.out.println("  " + j2.getNom() + " : " + p2.getNom() +
                           "  [PV : " + p2.getPv() + "/" + p2.getPvMax() + "]");
        System.out.println("==========================================\n");
    }

    private int choisirAction(Joueur joueur) {
        System.out.println("\n" + joueur.getNom() + ", que voulez-vous faire ?");
        System.out.println("/1/ Attaquer");
        System.out.println("/2/ Changer de Pokémon");
        System.out.println("/3/ Fuir");
        int action;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Entrée invalide. Veuillez entrer 1, 2 ou 3.");
                sc.next();
            }
            action = sc.nextInt();
            sc.nextLine();
        } while (action < 1 || action > 3);
        return action;
    }

    private int choisirAttaque(Joueur joueur) {
        Pokemon poke = joueur.getPokemonActif();
        System.out.println("\n" + joueur.getNom() + ", choisissez votre attaque :");
        for (int i = 0; i < 4; i++) {
            Attaque a = poke.attaqueByIndex(i);
            System.out.println("/" + (i + 1) + "/ " + a.getLibelle() +" (Puissance : " + a.getPuissance() + ")");
        }
        int choix;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Entrée invalide. Entrez un nombre entre 1 et 4.");
                sc.next();
            }
            choix = sc.nextInt();
            sc.nextLine();
        } while (choix < 1 || choix > 4);
        return choix - 1; 
    }

    private boolean tousKO(Joueur joueur) {
        for (Pokemon p : joueur.getEquipe()) {
            if (p.getPv() > 0) return false;
        }
        return true;
    }

    private boolean resoudreAttaque(Joueur attaquant, Joueur defenseur, int indexAttaque) {
        Pokemon pokeAtt = attaquant.getPokemonActif();
        Pokemon pokeDef = defenseur.getPokemonActif();
        Attaque attaque = pokeAtt.attaqueByIndex(indexAttaque);
        int degats = pokeAtt.attaquer(attaque, pokeDef);

        System.out.println("\n" + pokeAtt.getNom() + " utilise " + attaque.getLibelle() +" et inflige " + degats + " dégâts à " + pokeDef.getNom() + " !");
        pause(1000);
        if (pokeDef.getPv() <= 0) {
            System.out.println(pokeDef.getNom() + " est K.O. !");
            pause(1000);
            return true; 
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // TOUR DE COMBAT PRINCIPAL
    // Retourne true  → le combat continue
    // Retourne false → le combat est terminé
    // -------------------------------------------------------------------------
    public boolean tourCombat(Joueur j1, Joueur j2) {

        if (j1 == null || j2 == null) {
            System.out.println("Erreur : un joueur est null !");
            return false;
        }

        Pokemon pokeJ1 = j1.getPokemonActif();
        Pokemon pokeJ2 = j2.getPokemonActif();

        if (pokeJ1 == null || pokeJ2 == null) {
            System.out.println("Erreur : un joueur n'a pas de Pokémon actif !");
            return false;
        }

        afficherEtatCombat(j1, j2);
        pause(500);

        int actionJ1;
        int attaqueJ1 = 0;
        if (j1 instanceof JoueurIA) {
            actionJ1 = rand.nextInt(2) + 1;
            if (actionJ1 == 1) attaqueJ1 = rand.nextInt(4);
        } else {
            actionJ1 = choisirAction(j1);
            if (actionJ1 == 3) {
                System.out.println(j1.getNom() + " a fui le combat !");
                return false; 
            }
            if (actionJ1 == 1) attaqueJ1 = choisirAttaque(j1);
            if (actionJ1 == 2) choisirPokemonCombat(j1); 
        }

        int actionJ2;
        int attaqueJ2 = 0;
        if (j2 instanceof JoueurIA) {
            actionJ2 = rand.nextInt(2) + 1;
            if (actionJ2 == 1) attaqueJ2 = rand.nextInt(4);
        } else {
            actionJ2 = choisirAction(j2);
            if (actionJ2 == 3) {
                System.out.println(j2.getNom() + " a fui le combat !");
                return false; 
            }
            if (actionJ2 == 1) attaqueJ2 = choisirAttaque(j2);
            if (actionJ2 == 2) choisirPokemonCombat(j2);
        }

        int vitJ1 = pokeJ1.getVitesse();
        int vitJ2 = pokeJ2.getVitesse();

        Joueur premier  = (vitJ1 >= vitJ2) ? j1 : j2;
        Joueur deuxieme = (vitJ1 >= vitJ2) ? j2 : j1;
        int attPremier  = (vitJ1 >= vitJ2) ? attaqueJ1 : attaqueJ2;
        int attDeuxieme = (vitJ1 >= vitJ2) ? attaqueJ2 : attaqueJ1;
        int actionPremier  = (vitJ1 >= vitJ2) ? actionJ1 : actionJ2;
        int actionDeuxieme = (vitJ1 >= vitJ2) ? actionJ2 : actionJ1;

        if (actionPremier == 1) {
            boolean ko = resoudreAttaque(premier, deuxieme, attPremier);
            if (ko) {
                if (tousKO(deuxieme)) {
                    System.out.println(deuxieme.getNom() + " n'a plus de Pokémon !");
                    System.out.println(premier.getNom() + " remporte le combat !");
                    return false; 
                } else {
                    System.out.println(deuxieme.getNom() + ", choisissez votre prochain Pokémon !");
                    choisirPokemonCombat(deuxieme);
                    return true;
                }
            }
        }

        
        if (actionDeuxieme == 1 && deuxieme.getPokemonActif().getPv() > 0) {
            boolean ko = resoudreAttaque(deuxieme, premier, attDeuxieme);
            if (ko) {
                if (tousKO(premier)) {
                    System.out.println(premier.getNom() + " n'a plus de Pokémon !");
                    System.out.println(deuxieme.getNom() + " remporte le combat !");
                    return false; 
                } else {
                    System.out.println(premier.getNom() + ", choisissez votre prochain Pokémon !");
                    choisirPokemonCombat(premier);
                }
            }
        }
        return true;
    }
        
    
    
    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    

}