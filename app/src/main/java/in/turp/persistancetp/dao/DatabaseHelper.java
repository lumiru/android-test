package in.turp.persistancetp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lumiru on 30/06/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String dbName = "persistance_tp";

    public DatabaseHelper(Context context) {
        super(context, dbName, null, 33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS magasin (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "adresse TEXT," +
                "code_postal TEXT," +
                "ville TEXT," +
                "numero_telephone TEXT," +
                "numero_fax TEXT," +
                "enseigne INTEGER," +
                "date_modification TEXT" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS visite (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "wid INTEGER," +
                "supprime INTEGER," +
                "date_creation TEXT," +
                "date_modification TEXT," +
                "date_visite TEXT," +
                "is_realisee INTEGER," +
                "date_cloture TEXT," +
                "magasin INTEGER," +
                "client INTEGER" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS releveproduit (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "wid INTEGER," +
                "supprime INTEGER," +
                "prix INTEGER," +
                "prix_releve INTEGER," +
                "facing INTEGER," +
                "approvisionnement TEXT," +
                "is_valide INTEGER," +
                "produit_id INTEGER," +
                "visite_id INTEGER," +
                "date_modification TEXT" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS enseigne (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "groupe_id INTEGER" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS groupe (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "libelle TEXT" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS frontvente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "magasin_id INTEGER," +
                "utilisateur_id INTEGER," +
                "client_id INTEGER" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS assortiment (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "produit INTEGER," +
                "frontvente INTEGER," +
                "date TEXT," +
                "date_dereferencement TEXT" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS produit (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "ean13 TEXT," +
                "prix INTEGER," +
                "famille_id INTEGER" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS gamme (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "libelle TEXT," +
                "client_id INTEGER" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS famille (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "libelle TEXT," +
                "gamme_id INTEGER," +
                "client_id INTEGER" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS client (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        // comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE IF EXISTS magasin;");
        db.execSQL("DROP TABLE IF EXISTS visite;");
        db.execSQL("DROP TABLE IF EXISTS utilisateur;");
        db.execSQL("DROP TABLE IF EXISTS assortiment;");
        db.execSQL("DROP TABLE IF EXISTS client;");
        db.execSQL("DROP TABLE IF EXISTS famille;");
        db.execSQL("DROP TABLE IF EXISTS gamme;");
        db.execSQL("DROP TABLE IF EXISTS groupe;");
        db.execSQL("DROP TABLE IF EXISTS produit;");
        db.execSQL("DROP TABLE IF EXISTS releveproduit;");
        db.execSQL("DROP TABLE IF EXISTS enseigne;");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if(!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
