package in.turp.persistancetp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import in.turp.persistancetp.R;
import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.data.ReleveProduit;
import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.view.ReleveProduitListAdapter;
import in.turp.persistancetp.view.VisiteListAdapter;


public class ReleveProduitListActivity extends ListActivity {
    public static final String EXTRA_VISITE_ID = "in.turp.pertistancetp.activity.releveProduitList.VISITE_ID";
    private static final int NO_DATA_MSG = R.string.no_releve_produit;
    private static final int ROW_LAYOUT = R.layout.visite_row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DAO<ReleveProduit> dao = new DAO<>(getApplicationContext(), ReleveProduit.class);
        int visiteId = getIntent().getIntExtra(EXTRA_VISITE_ID, 0);
        List<ReleveProduit> releves = dao.get("visite", visiteId);
        if(releves.size() == 0) {
            Toast toast = Toast.makeText(this, NO_DATA_MSG, Toast.LENGTH_LONG);
            toast.show();
        }

        dao.loadAssociation(releves, "produit");

        ArrayAdapter adapter = new ReleveProduitListAdapter(getApplicationContext(),
                ROW_LAYOUT, releves);
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
        Visite item = (Visite) getListAdapter().getItem(position);

        Intent intent = new Intent(this, VisiteActivity.class);
        intent.putExtra(VisiteActivity.EXTRA_VISITE_ID, item.getId());
        startActivity(intent);
    }
}