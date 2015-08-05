package in.turp.persistancetp.service.data;

import java.util.Date;

import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.util.SimpleDate;

/**
 * Created by lumiru on 04/08/15.
 */
public class VisiteSyncObject {
    private int id;
    private boolean deleted;
    private SimpleDate dateCreation;
    private Date dateModification;
    private SimpleDate date;
    private boolean isRealisee;
    private SimpleDate dateCloture;
    private int magasin;
    //private Magasin magasin;
    private int client;

    public VisiteSyncObject(Visite visite) {
        id = visite.getId();
        deleted = visite.getDeleted();
        dateCreation = new SimpleDate(visite.getDateCreation());
        dateModification = visite.getDateModification();
        date = new SimpleDate(visite.getDateVisite());
        isRealisee = visite.getIsRealisee();
        dateCloture = new SimpleDate(visite.getDateCloture());
        magasin = visite.getMagasin();
        client = visite.getClient();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public SimpleDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(SimpleDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public SimpleDate getDate() {
        return date;
    }

    public void setDate(SimpleDate date) {
        this.date = date;
    }

    public boolean isRealisee() {
        return isRealisee;
    }

    public void setIsRealisee(boolean isRealisee) {
        this.isRealisee = isRealisee;
    }

    public SimpleDate getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(SimpleDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public int getMagasin() {
        return magasin;
    }

    public void setMagasin(int magasin) {
        this.magasin = magasin;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }
}
