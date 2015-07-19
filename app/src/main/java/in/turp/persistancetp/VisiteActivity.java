package in.turp.persistancetp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.data.Client;
import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.view.VisiteListAdapter;


public class VisiteActivity extends ListActivity {
    public static final String EXTRA_MAGASIN_ID = "in.turp.pertistancetp.activity.visite.MAGASIN_ID";
    private static final int NO_DATA_MSG = R.string.no_visite;
    private static final int ROW_LAYOUT = R.layout.visite_row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DAO<Visite> dao = new DAO<Visite>(getApplicationContext(), Visite.class);
        int magasinId = getIntent().getIntExtra(EXTRA_MAGASIN_ID, 0);
        List<Visite> visites = dao.get("magasin", magasinId);
        if(visites.size() == 0) {
            Toast toast = Toast.makeText(this, NO_DATA_MSG, Toast.LENGTH_LONG);
            toast.show();
        }

        dao.loadAssociation(visites, "client");

        ArrayAdapter adapter = new VisiteListAdapter(getApplicationContext(),
                ROW_LAYOUT, visites);
        setListAdapter(adapter);
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
