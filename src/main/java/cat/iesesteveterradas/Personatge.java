package cat.iesesteveterradas;

public class Personatge {
    private int id;
    private String nom;
    private float atac;
    private float defensa;
    private int idFaccio;

    // Constructor sin parámetros
    public Personatge() {
    }

    // Constructor con parámetros
    public Personatge(int id, String nom, float atac, float defensa, int idFaccio) {
        this.id = id;
        this.nom = nom;
        this.atac = atac;
        this.defensa = defensa;
        this.idFaccio = idFaccio;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getAtac() {
        return atac;
    }

    public void setAtac(float atac) {
        this.atac = atac;
    }

    public float getDefensa() {
        return defensa;
    }

    public void setDefensa(float defensa) {
        this.defensa = defensa;
    }

    public int getIdFaccio() {
        return idFaccio;
    }

    public void setIdFaccio(int idFaccio) {
        this.idFaccio = idFaccio;
    }
}
