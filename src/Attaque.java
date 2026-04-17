public class Attaque {
    private String libelle;
    private int puissance;
    private int typeId;

    public Attaque(String libelle, int puissance, int typeId) {
        this.libelle = libelle;
        this.puissance = puissance;
        this.typeId = typeId;
    }

    public String getLibelle()  { return libelle; }
    public int getPuissance()   { return puissance; }
    public int getTypeId()      { return typeId; }

    public void setLibelle(String libelle)      { this.libelle = libelle; }
    public void setPuissance(int puissance)     { this.puissance = puissance; }
    public void setTypeId(int typeId)           { this.typeId = typeId; }

    @Override
    public String toString() {
        return "Attaque : " + libelle + ", Puissance=" + puissance + ", TypeID=" + typeId + "\n";
    }
}