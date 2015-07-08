package in.turp.persistancetp.data;

import java.util.Date;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Visite implements Data {
    private int id;
    private int wid;
    private boolean supprime;
    private Date dateCreation;
    private Date dateModification;
    private Date dateVisite;
    private boolean isRealisee;
    private Date dateCloture;
    private int magasin;
    //private Magasin magasin;
    private int client;
    private Client clientObject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }

    public boolean getIsRealisee() {
        return isRealisee;
    }

    public void setIsRealisee(boolean isRealisee) {
        this.isRealisee = isRealisee;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }

    public int getMagasin() {
        return magasin;
    }

    public void setMagasin(int magasinId) {
        this.magasin = magasinId;
        //magasin = null;
    }

    /*public Magasin getMagasin() {
        //if(magasin == null && magasinId > 0) {
        //    magasin = ;
        //}
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
        magasinId = magasin.getId();
    }*/

    public int getClient() {
        return client;
    }

    public void setClient(int clientId) {
        this.client = clientId;
        //client = null;
    }

    public Client getClientObject() {
        //if(client == null && clientId > 0) {
        //    client = ;
        //}
        return clientObject;
    }

    public void setClientObject(Client client) {
        this.clientObject = client;
        this.client = client.getId();
    }

    public boolean getSupprime() {
        return supprime;
    }

    public void setSupprime(boolean supprime) {
        this.supprime = supprime;
    }
}
