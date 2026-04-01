public class Pokemon {
    private int id;
    private String nom;
    private int pv; 
    private int pvMax; 
    private int attaque; 
    private int defense; 
    private int vitesse;
    private Type[] types;  
    private Attaque[] attaques; 
    private Etat etat;

   

    public Pokemon(int id, String nom, int pv, int pvMax, int attaque, int defense, int vitesse, Attaque[] attaques) {
        this.id = id;
        this.nom = nom;
        this.pv = pv;
        this.pvMax = pvMax;
        this.attaque = attaque;
        this.defense = defense;
        this.vitesse = vitesse;
        this.attaques = attaques;
        
    }

    
    public int attaquer(Attaque attaque, Pokemon cible) {
        int degats = attaque.getPuissance() + this.attaque - cible.getDefense();
        if (degats < 0) {
            degats = 0;
        }
        cible.setPv(cible.getPv() - degats);
        if (cible.getPv() < 0) {
            cible.setPv(0); 
        }
        return degats;
    }

    public Attaque attaqueByIndex(int index) { return this.attaques[index];}
    
    public Type[] getTypes() {return types;}
    public void setTypes(Type[] types) {this.types = types;}

    public String getNom() {return nom;}
    

    public int getPv() {return pv;}
    public void setPv(int pv) {this.pv = pv;}

    public int getPvMax() {return pvMax;}    
    public int getAttaque() {return attaque;}
    public int getDefense() {return defense;}
    public int getVitesse() {return vitesse;}
    public int getId() {return id;}
    

    

}

