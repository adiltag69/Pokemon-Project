
public class Etat {
    private enum EtatEnum {
        NORMAL       (null,   false, "Le Pokémon est en pleine forme !"),
        POISON       ("PSN",  true,  "Le Pokémon est empoisonné..."),
        TOXIC        ("PSN",  true,  "Le Pokémon est empoisonné..."),
        BRULURE      ("BRN",  true,  "Le Pokémon est brûlé !"),
        PARALYSIE    ("PAR",  true,  "Le Pokémon est paralysé !"),
        SOMMEIL      ("SLP",  false, "Le Pokémon est endormi..."),
        CONGELATION  ("FRZ",  false, "Le Pokémon est congelé !"),
        KO           (null,   false, "Le Pokémon est K.O. !");
        

        private final String abrevation;
        private final boolean degats;
        private final String txt;

        EtatEnum(String abrevation, boolean degats, String txt) {
            this.abrevation = abrevation;
            this.degats = degats;
            this.txt = txt;
        }
    } 

    private EtatEnum etat;
    private int compteurToxic  = 0; // tours écoulés sous Toxic
    private int compteurSommeil = 0; 

    public Etat(){
        this.etat = EtatEnum.NORMAL;
    }

    public EtatEnum getEtat(){
        return this.etat;
    }

    public void setEtat(String etat){
        if (etat == null) {
            this.etat = EtatEnum.NORMAL;
            return;
        }
    
        switch(etat.toLowerCase()){
            case "poison":
                this.etat = EtatEnum.POISON;
                break;
            case "toxic":
                this.etat = EtatEnum.TOXIC;
                break;
            case "brulure":
                this.etat = EtatEnum.BRULURE;
                break;
            case "paralysie":
                this.etat = EtatEnum.PARALYSIE;
                break;
            case "sommeil":
                this.etat = EtatEnum.SOMMEIL;
                break;
            case "congelation":
                this.etat = EtatEnum.CONGELATION;
                break;
            case "ko":
                this.etat = EtatEnum.KO;
                break;
            default:
                this.etat = EtatEnum.NORMAL;
                break;
        }
    }

}
