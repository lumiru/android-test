package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Groupe implements Data {
    private int id;
    private String libelle;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
