package in.turp.persistancetp.data;

import java.util.Date;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Assortiment implements Data {
    private int id;
    private int produit;
    //private Produit produit;
    private int frontvente;
    //private FrontVente frontvente;
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

    public int getProduit() {
        return produit;
    }

    public void setProduit(int produit) {
        this.produit = produit;
    }

    public int getFrontvente() {
        return frontvente;
    }

    public void setFrontvente(int frontvente) {
        this.frontvente = frontvente;
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
