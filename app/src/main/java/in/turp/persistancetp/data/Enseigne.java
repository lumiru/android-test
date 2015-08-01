package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 30/06/15.
 */
public class Enseigne implements Data {
    private int id;
    private String nom;
    private int groupe;
    // private Groupe groupe;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getGroupe() {
        return groupe;
    }

    public void setGroupe(int groupe) {
        this.groupe = groupe;
        // groupe = null;
    }

//    public Groupe getGroupe() {
//        return groupe;
//    }
//
//    public void setGroupe(Groupe groupe) {
//        this.groupe = groupe;
//        groupe = groupe.getId();
//    }


    @Override
    public String toString() {
        return nom;
    }
}
