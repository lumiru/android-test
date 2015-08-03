package in.turp.persistancetp.activities;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import in.turp.persistancetp.R;
import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.data.Enseigne;
import in.turp.persistancetp.data.Magasin;

public class MagasinActivity extends Activity implements View.OnClickListener {
    public static final String EXTRA_MAGASIN_ID = "in.turp.pertistancetp.activity.magasin.MAGASIN_ID";

    private DAO<Magasin> dao;
    private Magasin magasin;
    private Spinner enseigneSpinner;
    private EditText nomField, adresseField, zipField, villeField, telephoneField, faxField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_magasin);

        dao = new DAO<>(getApplicationContext(), Magasin.class);
        int visiteId = getIntent().getIntExtra(EXTRA_MAGASIN_ID, 0);
        if(visiteId == 0) {
            magasin = new Magasin();
        }
        else {
            magasin = dao.get(visiteId);
        }

        DAO<Enseigne> daoEnseignes = new DAO<>(getApplicationContext(), Enseigne.class);
        List<Enseigne> enseignes = daoEnseignes.getAll();

        enseigneSpinner = (Spinner) findViewById(R.id.magasin_enseigne_spinner);
        nomField = (EditText) findViewById(R.id.magasin_nom_field);
        adresseField = (EditText) findViewById(R.id.magasin_adresse_field);
        zipField = (EditText) findViewById(R.id.magasin_zip_field);
        villeField = (EditText) findViewById(R.id.magasin_ville_field);
        telephoneField = (EditText) findViewById(R.id.magasin_telephone_field);
        faxField = (EditText) findViewById(R.id.magasin_fax_field);

        int enseigneIndex = -1;
        for (int i = 0, enseignesSize = enseignes.size(); i < enseignesSize; ++i) {
            Enseigne enseigne = enseignes.get(i);
            if (enseigne.getId() == magasin.getEnseigne()) {
                enseigneIndex = i;
                break;
            }
        }
        ArrayAdapter<Enseigne> enseigneAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_dropdown_item, R.id.spinner_dropdown_item, enseignes);
        enseigneAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        enseigneSpinner.setAdapter(enseigneAdapter);
        if(enseigneIndex >= 0) {
            enseigneSpinner.setSelection(enseigneIndex);
        }

        nomField.setText(magasin.getNom());
        adresseField.setText(magasin.getAdresse());
        zipField.setText(magasin.getCodePostal());
        villeField.setText(magasin.getVille());
        telephoneField.setText(magasin.getNumeroTelephone());
        faxField.setText(magasin.getNumeroFax());

        Button button = (Button) findViewById(R.id.send_magasin_button);
        button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_magasin, menu);
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
        Enseigne enseigne = null;
        String nom = nomField.getText().toString();
        String adresse = adresseField.getText().toString();
        String zip = zipField.getText().toString();
        String ville = villeField.getText().toString();
        String telephone = telephoneField.getText().toString();
        String fax = faxField.getText().toString();

        if(enseigneSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            enseigne = (Enseigne) enseigneSpinner.getSelectedItem();
        }
        else {
            toast(R.string.magasin_no_enseigne);
            hasError = true;
        }

        if(nom.isEmpty()) {
            nomField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(adresse.isEmpty()) {
            adresseField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(zip.isEmpty()) {
            zipField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(ville.isEmpty()) {
            villeField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(telephone.isEmpty()) {
            telephoneField.setError(getString(R.string.error_field_required));
            hasError = true;
        }
        if(fax.isEmpty()) {
            faxField.setError(getString(R.string.error_field_required));
            hasError = true;
        }

        if(!hasError) {
            magasin.setEnseigneObject(enseigne);
            magasin.setNom(nom);
            magasin.setAdresse(adresse);
            magasin.setCodePostal(zip);
            magasin.setVille(ville);
            magasin.setNumeroTelephone(telephone);
            magasin.setNumeroFax(fax);
            magasin.setDateModification(new Date());

            dao.save(magasin);
            finish();
        }
    }

    private void toast(@StringRes int resId) {
        Toast toast = Toast.makeText(this, getString(resId), Toast.LENGTH_LONG);
        toast.show();
    }
}
