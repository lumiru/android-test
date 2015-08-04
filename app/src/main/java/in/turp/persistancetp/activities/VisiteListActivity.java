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
import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.view.VisiteListAdapter;


public class VisiteListActivity extends ListActivity {
    public static final String EXTRA_MAGASIN_ID = "in.turp.pertistancetp.activity.visiteList.MAGASIN_ID";
    private static final int NO_DATA_MSG = R.string.no_visite;
    private static final int ROW_LAYOUT = R.layout.visite_row;

    private int magasinId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        magasinId = getIntent().getIntExtra(EXTRA_MAGASIN_ID, 0);
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();

        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_visite_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, MagasinActivity.class);
            intent.putExtra(MagasinActivity.EXTRA_MAGASIN_ID, magasinId);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_add) {
            Intent intent = new Intent(this, VisiteActivity.class);
            intent.putExtra(VisiteActivity.EXTRA_MAGASIN_ID, magasinId);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Visite item = (Visite) getListAdapter().getItem(position);

        Intent intent = new Intent(this, ReleveProduitListActivity.class);
        intent.putExtra(ReleveProduitListActivity.EXTRA_VISITE_ID, item.getId());
        startActivity(intent);
    }

    private void update() {
        DAO<Visite> dao = new DAO<>(getApplicationContext(), Visite.class);
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
}
