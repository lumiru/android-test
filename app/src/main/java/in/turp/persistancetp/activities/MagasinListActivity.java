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
import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.view.MagasinListAdapter;


public class MagasinListActivity extends ListActivity {
    private static final int ROW_LAYOUT = R.layout.magasin_row;
    private static final int NO_DATA_MSG = R.string.no_magasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DAO<Magasin> dao = new DAO<>(getApplicationContext(), Magasin.class);
        List<Magasin> magasins = dao.getAll();
        dao.loadAssociation(magasins, "enseigne");
        if(magasins.size() == 0) {
            Toast toast = Toast.makeText(this, NO_DATA_MSG, Toast.LENGTH_LONG);
            toast.show();
        }

        ArrayAdapter adapter = new MagasinListAdapter(getApplicationContext(),
                ROW_LAYOUT, magasins);
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
        Magasin item = (Magasin) getListAdapter().getItem(position);

        Intent intent = new Intent(this, VisiteListActivity.class);
        intent.putExtra(VisiteListActivity.EXTRA_MAGASIN_ID, item.getId());
        startActivity(intent);
    }
}
