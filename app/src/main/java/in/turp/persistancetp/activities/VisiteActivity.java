package in.turp.persistancetp.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import in.turp.persistancetp.R;
import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.data.Client;
import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.view.MagasinListAdapter;

public class VisiteActivity extends ActionBarActivity {

    public static final String EXTRA_VISITE_ID = "in.turp.pertistancetp.activity.visite.VISITE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_visite);
        setTitle(R.string.view_visite_title);

        DAO<Visite> dao = new DAO<>(getApplicationContext(), Visite.class);
        int visiteId = getIntent().getIntExtra(EXTRA_VISITE_ID, 0);
        Visite visite = dao.get(visiteId);

        List<Magasin> magasins = DAO.getDAO(getApplicationContext(), Magasin.class).getAll();
        List<Client> clients = DAO.getDAO(getApplicationContext(), Client.class).getAll();

        Spinner magasinSpinner = (Spinner) findViewById(R.id.magasin_spinner);
        Spinner clientSpinner = (Spinner) findViewById(R.id.client_spinner);

        int magasinIndex = -1;
        for (int i = 0, magasinsSize = magasins.size(); i < magasinsSize; ++i) {
            Magasin magasin = magasins.get(i);
            if (magasin.getId() == visite.getMagasin()) {
                magasinIndex = i;
                break;
            }
        }

        magasinSpinner.setAdapter(new MagasinListAdapter(getApplicationContext(), R.layout.magasin_row, magasins));
        if(magasinIndex >= 0) {
            magasinSpinner.setSelection(magasinIndex);
        }

        if(visite.getDateVisite() != null) {
            EditText date = (EditText) findViewById(R.id.date_visite_text);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            date.setText(format.format(visite.getDateVisite()));
        }

        CheckBox realiseeCheckBox = (CheckBox) findViewById(R.id.realisee_check_box);
        realiseeCheckBox.setChecked(visite.getIsRealisee());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_visite, menu);
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
}
