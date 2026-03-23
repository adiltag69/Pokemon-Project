public class Combat{
    private Joueur joueur1;
    private Joueur joueur2;

    public Combat(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
    }

    public Joueur victoire(Joueur joueur1, Joueur joueur2) {
        int pokeDown1 = 0;
        int pokeDown2 = 0;
        for (int i = 0; i < Joueur.getTailleEquipe(); i++) {
            if (joueur1.getEquipe()[i].getPv() == 0) {
                pokeDown1 = pokeDown1 + 1;
            }
            if (joueur2.getEquipe()[i].getPv() == 0) {
                pokeDown2 = pokeDown2 + 1;
            }
                if (pokeDown1 == Joueur.getTailleEquipe()) {
                return joueur2;
            }
            if (pokeDown2 == Joueur.getTailleEquipe()) {
                return joueur1;
            }

        }
        return null;
    }
    public Joueur getJoueur1() { return joueur1;}
    public void setJoueur1(Joueur joueur1) { this.joueur1 = joueur1;}
    
    public Joueur getJoueur2() { return joueur2;}
    public void setJoueur2(Joueur joueur2) { this.joueur2 = joueur2;}
}