package in.turp.persistancetp.service;

import java.util.List;

import in.turp.persistancetp.data.Assortiment;
import in.turp.persistancetp.data.Client;
import in.turp.persistancetp.data.Enseigne;
import in.turp.persistancetp.data.Famille;
import in.turp.persistancetp.data.Gamme;
import in.turp.persistancetp.data.Groupe;
import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.data.Produit;
import in.turp.persistancetp.data.ReleveProduit;
import in.turp.persistancetp.data.Visite;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by lumiru on 08/07/15.
 */
public interface ImportService {
    @GET("/commercial/magasins/modified_after/{date}/")
    List<Magasin> getLastMagasins(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/visites/modified_after/{date}/")
    List<Visite> getLastVisites(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/assortiments/modified_after/{date}/")
    List<Assortiment> getLastAssortiments(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/clients/modified_after/{date}/")
    List<Client> getLastClients(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/enseignes/modified_after/{date}/")
    List<Enseigne> getLastEnseignes(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/familles/modified_after/{date}/")
    List<Famille> getLastFamilles(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/gammes/modified_after/{date}/")
    List<Gamme> getLastGammes(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/groupes/modified_after/{date}/")
    List<Groupe> getLastGroupes(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/produits/modified_after/{date}/")
    List<Produit> getLastProduits(@Header("Authorization") String token, @Path("date") String date);

    @GET("/commercial/releves_produit/modified_after/{date}/")
    List<ReleveProduit> getLastRelevesProduit(@Header("Authorization") String token, @Path("date") String date);
}
