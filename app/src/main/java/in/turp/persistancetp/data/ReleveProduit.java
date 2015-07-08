package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class ReleveProduit implements Data {
    private int id;
    private int wid;
    private boolean supprime;
    private int prix;
    private int prixReleve;
    private int facing;
    private String approvisionnement;
    private boolean isValide;
    private int produitId;
    private Produit produit;
    private int visiteId;
    private Visite visite;

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

    public boolean isValide() {
        return isValide;
    }

    public void setIsValide(boolean isValide) {
        this.isValide = isValide;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
        produit = null;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
        produitId = produit.getId();
    }

    public int getVisiteId() {
        return visiteId;
    }

    public void setVisiteId(int visiteId) {
        this.visiteId = visiteId;
        visite = null;
    }

    public Visite getVisite() {
        return visite;
    }

    public void setVisite(Visite visite) {
        this.visite = visite;
        visiteId = visite.getId();
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public boolean getSupprime() {
        return supprime;
    }

    public void setSupprime(boolean supprime) {
        this.supprime = supprime;
    }
}
