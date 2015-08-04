package in.turp.persistancetp.activities;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import in.turp.persistancetp.R;
import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.data.Famille;
import in.turp.persistancetp.data.Produit;
import in.turp.persistancetp.data.ReleveProduit;
import in.turp.persistancetp.data.Visite;

public class ReleveProduitActivity extends Activity implements View.OnClickListener {
    public static final String EXTRA_VISITE_ID = "in.turp.pertistancetp.activity.releve.VISITE_ID";
    public static final String EXTRA_RELEVE_ID = "in.turp.pertistancetp.activity.releve.RELEVE_ID";

    private DAO<ReleveProduit> dao;
    private ReleveProduit releve;
    private Spinner produitSpinner;
    private EditText prixField, prixReleveField, facingField, approvisionnementField;
    private CheckBox valideField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_releve_produit);

        dao = new DAO<>(getApplicationContext(), ReleveProduit.class);
        int releveId = getIntent().getIntExtra(EXTRA_RELEVE_ID, 0);
        if(releveId == 0) {
            releve = new ReleveProduit();
            releve.setWid(ReleveProduit.WID_NEW);
            int visiteId = getIntent().getIntExtra(EXTRA_VISITE_ID, 0);
            releve.setVisite(visiteId);
        }
        else {
            releve = dao.get(releveId);
        }

        // Charger uniquement les produits du client
        // On va chercher le client dans la visite

        Famille[] familles = new Famille[0];
        if(releve.getVisite() != 0) {
            int clientId = 0;
            DAO<Visite> daoVisite = new DAO<>(getApplicationContext(), Visite.class);
            clientId = daoVisite.get(releve.getVisite()).getClient();

            DAO<Famille> daoFamille = new DAO<>(getApplicationContext(), Famille.class);
            familles = daoFamille.get("client", clientId).toArray(familles);
        }

        DAO<Produit> daoProduit = new DAO<>(getApplicationContext(), Produit.class);
        List<Produit> produits = daoProduit.getIn("famille", familles);
        daoProduit.loadAssociation(produits, "famille", Arrays.asList(familles));

        // Obtention des champs
        produitSpinner = (Spinner) findViewById(R.id.releve_produit_spinner);
        prixField = (EditText) findViewById(R.id.releve_prix_field);
        prixReleveField = (EditText) findViewById(R.id.releve_prix_releve_field);
        facingField = (EditText) findViewById(R.id.releve_facing_field);
        approvisionnementField = (EditText) findViewById(R.id.releve_approvisionnement_field);
        valideField = (CheckBox) findViewById(R.id.releve_valide_field);

        // Définition des valeurs
        int produitIndex = -1;
        for (int i = 0, enseignesSize = produits.size(); i < enseignesSize; ++i) {
            Produit produit = produits.get(i);
            if (produit.getId() == releve.getProduit()) {
                produitIndex = i;
                break;
            }
        }
        ArrayAdapter<Produit> produitAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_dropdown_item, R.id.spinner_dropdown_item, produits);
        produitAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        produitSpinner.setAdapter(produitAdapter);
        if(produitIndex >= 0) {
            produitSpinner.setSelection(produitIndex);
        }

        prixField.setText(String.valueOf(releve.getPrix() / 100.));
        prixReleveField.setText(String.valueOf(releve.getPrixReleve() / 100.));
        facingField.setText(String.valueOf(releve.getFacing()));
        approvisionnementField.setText(releve.getApprovisionnement());
        valideField.setChecked(releve.getIsValide());

        Button button = (Button) findViewById(R.id.send_releve_button);
        button.setOnClickListener(this);

        Button deleteBtn = (Button) findViewById(R.id.del_releve_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releve.setSupprime(true);
                dao.save(releve);
                goBack();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_releve_produit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        boolean hasError = false;
        Produit produit = null;
        String prix = prixField.getText().toString();
        String prixReleve = prixReleveField.getText().toString();
        String facing = facingField.getText().toString();
        String approvisionnement = approvisionnementField.getText().toString();
        boolean valide = valideField.isChecked();

        if(produitSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            produit = (Produit) produitSpinner.getSelectedItem();
        }
        else {
            toast(R.string.releve_no_produit);
            hasError = true;
        }

        if(prix.isEmpty()) {
            prixField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(prixReleve.isEmpty()) {
            prixReleveField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(facing.isEmpty()) {
            facingField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(approvisionnement.isEmpty()) {
            approvisionnementField.setError(getString(R.string.error_field_required));
            hasError = true;
        }

        if(!hasError) {
            releve.setProduitObject(produit);
            releve.setPrix((int) (Float.parseFloat(prix) * 100));
            releve.setPrixReleve((int) (Float.parseFloat(prixReleve) * 100));
            releve.setFacing(Integer.parseInt(facing));
            releve.setApprovisionnement(approvisionnement);
            releve.setIsValide(valideField.isChecked());
            releve.setDateModification(new Date());

            dao.save(releve);
            goBack();
        }
    }

    private void goBack() {
        finish();
    }

    private void toast(@StringRes int resId) {
        Toast toast = Toast.makeText(this, getString(resId), Toast.LENGTH_LONG);
        toast.show();
    }
}
