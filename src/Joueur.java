public class Joueur{
    public static final int TAILLE_EQUIPE = 6;
    protected String Nom;
    protected final Pokemon[] equipe;
    protected Pokemon pokemonActif;
    protected int pokemonKO;

    public Joueur(String nom, Pokemon[] equipe, Pokemon pokemonActif) {
        this.Nom = nom;
        this.equipe = equipe;
        this.pokemonActif = equipe[0];
    }

    public Pokemon getPokemonActif() { return pokemonActif;}

    public static int getTailleEquipe() { return TAILLE_EQUIPE;}

    public String getNom() { return Nom; }

    public void setNom(String nom) { Nom = nom;}

    public Pokemon[] getEquipe() {return equipe;}

    public void setPokemonActif(Pokemon pokemonActif) {this.pokemonActif = pokemonActif;}




    
}