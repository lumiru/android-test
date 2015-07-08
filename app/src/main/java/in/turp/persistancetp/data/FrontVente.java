package in.turp.persistancetp.data;

import in.turp.persistancetp.dao.Data;

/**
 * Created by lumiru on 02/07/15.
 */
public class FrontVente implements Data {
    private int id;
    private int magasinId;
    private Magasin magasin;
    private int clientId;
    private Client client;
    private int utilisateurId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(int magasinId) {
        this.magasinId = magasinId;
        magasin = null;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
        magasinId = magasin.getId();
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

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}
