package in.turp.persistancetp.data;

import java.util.Date;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class ReleveProduit implements Data {
    public static final int WID_NEW = -1;
    public static final int WID_SYNC_ONCE = 0;

    private int id;

    /**
     * ID local
     * WID_NEW = ID non encore attribué
     * 0 = Existant sur le serveur
     * Autre = Jamais synchronisé (existe uniquement en local)
     */
    private int wid;

    private boolean deleted;
    private int prix;
    private int prixReleve;
    private int facing;
    private String approvisionnement;
    private boolean isValide;
    private int produit;
    private Produit produitObject;
    private int visite;
    // private VisiteActivity visite;
    private Date dateModification;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixReleve() {
        return prixReleve;
    }

    public void setPrixReleve(int prixReleve) {
        this.prixReleve = prixReleve;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public String getApprovisionnement() {
        return approvisionnement;
    }

    public void setApprovisionnement(String approvisionnement) {
        this.approvisionnement = approvisionnement;
    }

    public boolean getIsValide() {
        return isValide;
    }

    public boolean isValide() {
        return isValide;
    }

    public void setIsValide(boolean isValide) {
        this.isValide = isValide;
    }

    public int getProduit() {
        return produit;
    }

    public void setProduit(int produit) {
        produitObject = null;
        this.produit = produit;
    }

    public Produit getProduitObject() {
        return produitObject;
    }

    public void setProduitObject(Produit produit) {
        this.produit = produit.getId();
        produitObject = produit;
    }

    public int getVisite() {
        return visite;
    }

    public void setVisite(int visite) {
        this.visite = visite;
        // visite = null;
    }

//    public VisiteActivity getVisite() {
//        return visite;
//    }
//
//    public void setVisite(VisiteActivity visite) {
//        this.visite = visite;
//        visite = visite.getId();
//    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
}
