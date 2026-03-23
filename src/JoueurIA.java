public class JoueurIA extends Joueur {
    
    private int arene;
    private String nomArene;
    private String nomVille;
    
    
    public JoueurIA(Pokemon[] equipe,String nom, int arene, String nomArene, String nomVille) {
        super(nom,equipe, equipe[0]);
        this.arene = arene;
        this.nomArene = nomArene;
        this.nomVille = nomVille
        ;
    }

    public int getArene() {return arene;}
    public void setArene(int arene) {this.arene = arene;}

    public String getNomArene() {return nomArene;}
    public void setNomArene(String nomArene) {this.nomArene = nomArene;}

    public String getNomVille() {return nomVille;}
    public void setNomVille(String nomVille) {this.nomVille = nomVille;}

}