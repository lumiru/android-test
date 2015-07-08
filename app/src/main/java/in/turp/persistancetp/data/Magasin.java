package in.turp.persistancetp.data;

import java.util.Date;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 30/06/15.
 */
public class Magasin implements Data {
    private int id;
    private int enseigneId;
    private Enseigne enseigne;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private String fax;
    private Date dateModification;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnseigneId() {
        return enseigneId;
    }

    public void setEnseigneId(int enseigneId) {
        enseigne = null;
        this.enseigneId = enseigneId;
    }

    public Enseigne getEnseigne() {
        //if(enseigne == null && enseigneId > 0) {
        //    enseigne = ;
        //}
        return enseigne;
    }

    public void setEnseigne(Enseigne enseigne) {
        enseigneId = enseigne.getId();
        this.enseigne = enseigne;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
}
