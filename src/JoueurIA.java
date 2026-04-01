public class JoueurIA extends Joueur {
    
    private int arene;
    private String nomArene;
    private String nomVille;
    
    
    public JoueurIA(String nom,Pokemon[] equipe) {
        super(nom,equipe);
    }

    public int getArene() {return arene;}
    public void setArene(int arene) {this.arene = arene;}

    public String getNomArene() {return nomArene;}
    public void setNomArene(String nomArene) {this.nomArene = nomArene;}

    public String getNomVille() {return nomVille;}
    public void setNomVille(String nomVille) {this.nomVille = nomVille;}

}