package in.turp.persistancetp.data;

import java.util.Date;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Assortiment implements Data {
    private int id;
    private int produitId;
    private Produit produit;
    private int frontventeId;
    private FrontVente frontvente;
    private Date date;
    private Data dateDereferencement;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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

    public int getFrontventeId() {
        return frontventeId;
    }

    public void setFrontventeId(int frontventeId) {
        this.frontventeId = frontventeId;
        frontvente = null;
    }

    public FrontVente getFrontvente() {
        return frontvente;
    }

    public void setFrontvente(FrontVente frontvente) {
        this.frontvente = frontvente;
        frontventeId = frontvente.getId();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Data getDateDereferencement() {
        return dateDereferencement;
    }

    public void setDateDereferencement(Data dateDereferencement) {
        this.dateDereferencement = dateDereferencement;
    }
}
