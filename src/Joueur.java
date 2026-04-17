public class Joueur{
    public static final int TAILLE_EQUIPE = 6;
    protected String nom;
    protected final Pokemon[] equipe;
    protected Pokemon pokemonActif;
    protected int pokemonKO;

    public Joueur(String nom, Pokemon[] equipe) {
        this.nom = nom;
        this.equipe = equipe;
        this.pokemonActif = equipe[0];
    }

    public Pokemon getPokemonActif() { return pokemonActif;}
    public void setPokemonActif(Pokemon pokemonActif) {this.pokemonActif = pokemonActif;}

    public static int getTailleEquipe() { return TAILLE_EQUIPE;}

    public String getNom() { return nom; }

    public Pokemon[] getEquipe() {return equipe;}

    public int getPokemonKO() {return pokemonKO;}
    public void setPokemonKO(int pokemonKO) {this.pokemonKO = pokemonKO;}
    
    public void afficherJoueur() {
        System.out.println("Joueur: " + nom);
        pause(1000);
        System.out.println("Equipe:");
        for (Pokemon p : equipe) {
            System.out.println("- " + p.getNom());
            System.out.println(" PV: " + p.getPv() + " " + p.getAttaques());
            pause(500);
        }
    }   

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}    




    
