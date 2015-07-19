package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Produit implements Data {
    private int id;
    private String nom;
    private String ean13;
    private int prix;
    private int famille;
    // private Famille famille;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getFamille() {
        return famille;
    }

    public void setFamille(int famille) {
        this.famille = famille;
        // famille = null;
    }

//    public Famille getFamille() {
//        return famille;
//    }
//
//    public void setFamille(Famille famille) {
//        this.famille = famille;
//        famille = famille.getId();
//    }
}
