package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Gamme implements Data {
    private int id;
    private String libelle;
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
