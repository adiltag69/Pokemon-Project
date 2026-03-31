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
    public void setNom(String nom) {this.nom = nom;}

    public Pokemon[] getEquipe() {return equipe;}

    public int getPokemonKO() {return pokemonKO;}
    public void setPokemonKO(int pokemonKO) {this.pokemonKO = pokemonKO;}
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Joueur: ").append(nom).append("\n");
        sb.append("Equipe:\n");
        for (Pokemon p : equipe) {
            sb.append("- ").append(p.getNom()).append(" (PV: ").append(p.getPv()).append(")\n");
        }
        return sb.toString();
    }

}    




    
