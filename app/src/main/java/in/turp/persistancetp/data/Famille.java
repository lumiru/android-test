package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class Famille implements Data {
    private int id;
    private String libelle;
    private int gammeId;
    private Gamme gamme;
    private int clientId;
    private Client client;

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

    public int getGammeId() {
        return gammeId;
    }

    public void setGammeId(int gammeId) {
        this.gammeId = gammeId;
        gamme = null;
    }

    public Gamme getGamme() {
        return gamme;
    }

    public void setGamme(Gamme gamme) {
        this.gamme = gamme;
        gammeId = gamme.getId();
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
        client = null;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        clientId = client.getId();
    }
}
