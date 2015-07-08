package in.turp.persistancetp.data;

import java.util.Date;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 30/06/15.
 */
public class Magasin implements Data {
    private int id;
    private int enseigne;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String numeroTelephone;
    private String numeroFax;
    private Date dateModification;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnseigne() {
        return enseigne;
    }

    public void setEnseigne(int enseigneId) {
        // enseigne = null;
        this.enseigne = enseigneId;
    }

    /*public Enseigne getEnseigne() {
        //if(enseigne == null && enseigne > 0) {
        //    enseigne = ;
        //}
        return enseigne;
    }

    public void setEnseigne(Enseigne enseigne) {
        enseigne = enseigne.getId();
        this.enseigne = enseigne;
    }*/

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getNumeroFax() {
        return numeroFax;
    }

    public void setNumeroFax(String numeroFax) {
        this.numeroFax = numeroFax;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
}
