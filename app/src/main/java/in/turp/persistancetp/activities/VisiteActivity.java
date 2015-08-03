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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.turp.persistancetp.R;
import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.data.Client;
import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.view.MagasinListAdapter;

public class VisiteActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_VISITE_ID = "in.turp.pertistancetp.activity.visite.VISITE_ID";

    private DAO<Visite> dao;
    private Visite visite;
    private Spinner magasinSpinner;
    private Spinner clientSpinner;
    private EditText dateField;
    private CheckBox realiseeCheckBox;
    private DateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_visite);

        dao = new DAO<>(getApplicationContext(), Visite.class);
        int visiteId = getIntent().getIntExtra(EXTRA_VISITE_ID, 0);
        if(visiteId == 0) {
            visite = new Visite();
            visite.setDateCreation(new Date());
        }
        else {
            visite = dao.get(visiteId);
        }

        DAO<Magasin> daoMagasin = new DAO<>(getApplicationContext(), Magasin.class);
        DAO<Client> daoClient = new DAO<>(getApplicationContext(), Client.class);

        List<Magasin> magasins = daoMagasin.getAll();
        List<Client> clients = daoClient.getAll();

        daoMagasin.loadAssociation(magasins, "enseigne");

        magasinSpinner = (Spinner) findViewById(R.id.magasin_spinner);
        clientSpinner = (Spinner) findViewById(R.id.client_spinner);

        int magasinIndex = -1;
        for (int i = 0, magasinsSize = magasins.size(); i < magasinsSize; ++i) {
            Magasin magasin = magasins.get(i);
            if (magasin.getId() == visite.getMagasin()) {
                magasinIndex = i;
                break;
            }
        }

        MagasinListAdapter magasinAdapter = new MagasinListAdapter(getApplicationContext(), R.layout.magasin_row, magasins);
        magasinAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        magasinSpinner.setAdapter(magasinAdapter);
        if(magasinIndex >= 0) {
            magasinSpinner.setSelection(magasinIndex);
        }

        int clientIndex = -1;
        for (int i = 0, clientSize = clients.size(); i < clientSize; ++i) {
            Client client = clients.get(i);
            if (client.getId() == visite.getClient()) {
                clientIndex = i;
                break;
            }
        }

        ArrayAdapter<Client> clientAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_dropdown_item, R.id.spinner_dropdown_item, clients);
        clientAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        clientSpinner.setAdapter(clientAdapter);
        if(clientIndex >= 0) {
            clientSpinner.setSelection(clientIndex);
        }

        dateField = (EditText) findViewById(R.id.date_visite_text);
        format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        if(visite.getDateVisite() != null) {
            dateField.setText(format.format(visite.getDateVisite()));
        }

        realiseeCheckBox = (CheckBox) findViewById(R.id.realisee_check_box);
        realiseeCheckBox.setChecked(visite.getIsRealisee());

        Button button = (Button) findViewById(R.id.send_visite_button);
        button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_visite, menu);
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
        Magasin magasin = null;
        Client client = null;
        String dateStr = dateField.getText().toString();
        Date date = null;
        boolean realisee = realiseeCheckBox.isChecked();

        if(magasinSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            magasin = (Magasin) magasinSpinner.getSelectedItem();
        }
        else {
            toast(R.string.visite_no_magasin);
        }

        if(clientSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            client = (Client) clientSpinner.getSelectedItem();
        }
        else {
            toast(R.string.error_visite_no_client);
        }

        if(dateStr.isEmpty()) {
            dateField.setError(getString(R.string.error_field_required));
        }
        else {
            try {
                date = format.parse(dateStr);
            } catch (ParseException e) {
                dateField.setError(getString(R.string.error_invalid_date));
            }
        }

        if(magasin != null && client != null && date != null) {
            visite.setMagasin(magasin.getId());
            visite.setClientObject(client);
            visite.setDateVisite(date);
            visite.setIsRealisee(realisee);
            visite.setDateModification(new Date());

            dao.save(visite);
            finish();
        }
    }

    private void toast(@StringRes int resId) {
        Toast toast = Toast.makeText(this, getString(resId), Toast.LENGTH_LONG);
        toast.show();
    }
}
