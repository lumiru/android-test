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
    private int magasinId;
    private Magasin magasin;
    private int clientId;
    private Client client;

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

    public boolean isRealisee() {
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

    public int getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(int magasinId) {
        this.magasinId = magasinId;
        magasin = null;
    }

    public Magasin getMagasin() {
        //if(magasin == null && magasinId > 0) {
        //    magasin = ;
        //}
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
        magasinId = magasin.getId();
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
        client = null;
    }

    public Client getClient() {
        //if(client == null && clientId > 0) {
        //    client = ;
        //}
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        clientId = client.getId();
    }

    public boolean isSupprime() {
        return supprime;
    }

    public void setSupprime(boolean supprime) {
        this.supprime = supprime;
    }
}
