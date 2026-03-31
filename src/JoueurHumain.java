public class JoueurHumain extends Joueur {
    
    private int nbBadges;
    private int nbVictoires;
    private boolean badges;
    
    
    public JoueurHumain(Pokemon[] equipe,String nom, int nbBadges, int nbVictoires, boolean badges) {
        super(nom,equipe);
        this.nbBadges = nbBadges;
        this.nbVictoires = nbVictoires;
        this.badges = badges;
        ;
    }


    public int getNbBadges() {return nbBadges;}
    public void setNbBadges(int nbBadges) {this.nbBadges = nbBadges;}

    public int getNbVictoires() {return nbVictoires;}
    public void setNbVictoires(int nbVictoires) {this.nbVictoires = nbVictoires;}

    public boolean isBadges() {return badges;}
    public void setBadges(boolean badges) {this.badges = badges;}
}