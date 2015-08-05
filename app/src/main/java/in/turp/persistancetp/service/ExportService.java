package in.turp.persistancetp.service;

import java.util.List;

import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.data.ReleveProduit;
import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.service.data.VisiteSyncObject;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.PUT;

/**
 * Created by lumiru on 08/07/15.
 */
public interface ExportService {
    /**
     * Mettre l'ID à zéro pour ajouter un élément
     * @param token Authorization token
     * @param visite VisiteActivity modifiée
     * @return La visite modifiée
     */
    @PUT("/commercial/visites/sync/")
    Visite save(@Header("Authorization") String token, @Body VisiteSyncObject visite);

    /**
     * Mettre l'ID à zéro pour ajouter un élément
     * @param token Authorization token
     * @param releveProduit Relevé produit modifiée
     * @return Le relevé modifiée
     */
    @PUT("/commercial/visite_releve_produit/sync/")
    ReleveProduit save(@Header("Authorization") String token, @Body ReleveProduit releveProduit);

    @PUT("/commercial/magasins/sync/")
    Magasin save(@Header("Authorization") String token, @Body Magasin magasin);

    @PUT("/commercial/magasins/sync/")
    List<Magasin> save(@Header("Authorization") String token, @Body List<Magasin> magasin);
}
