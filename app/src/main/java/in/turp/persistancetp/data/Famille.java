package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Famille implements Data {
    private int id;
    private String libelle;
    private int gamme;
    // private Gamme gamme;
    private int client;
    // private Client client;

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

    public int getGamme() {
        return gamme;
    }

    public void setGamme(int gamme) {
        this.gamme = gamme;
        // gamme = null;
    }

//    public Gamme getGamme() {
//        return gamme;
//    }
//
//    public void setGamme(Gamme gamme) {
//        this.gamme = gamme;
//        gamme = gamme.getId();
//    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
        // client = null;
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//        client = client.getId();
//    }
}
