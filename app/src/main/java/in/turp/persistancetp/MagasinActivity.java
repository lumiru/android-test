package in.turp.persistancetp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.dao.DatabaseHelper;
import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.view.MagasinListAdapter;


public class MagasinActivity extends ListActivity {
    private static final int ROW_LAYOUT = R.layout.magasin_row;
    private static final int NO_DATA_MSG = R.string.no_magasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.onUpgrade(db, 0, 0);

        DAO<Magasin> dao = new DAO<>(getApplicationContext(), Magasin.class);

        Magasin magasin = new Magasin();
        magasin.setNom("E. Leclerc");
        magasin.setAdresse("Là-bas");
        magasin.setCodePostal("53000");
        magasin.setVille("Laval");
        magasin.setTelephone("0202020202");
        magasin.setFax("0303030303");
        magasin.setEnseigneId(1);
        dao.save(magasin);

        List<Magasin> magasins = dao.getAll();
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

        Intent intent = new Intent(this, VisiteActivity.class);
        intent.putExtra(VisiteActivity.EXTRA_MAGASIN_ID, item.getId());
        startActivity(intent);
    }
}
