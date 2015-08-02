package in.turp.persistancetp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import in.turp.persistancetp.R;
import in.turp.persistancetp.data.ReleveProduit;
import in.turp.persistancetp.data.Visite;

/**
 * Created by lumiru on 02/07/15.
 */
public class ReleveProduitListAdapter extends ArrayAdapter<ReleveProduit> {
    private final LayoutInflater inflater;
    private final int resource;

    public ReleveProduitListAdapter(Context context, int resource, List<ReleveProduit> objects) {
        super(context, resource, objects);
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* create a new view of my layout and inflate it in the row */
        convertView = inflater.inflate( resource, null );

        /* Extract the city's object to show */
        ReleveProduit releve = getItem( position );

        TextView label = (TextView) convertView.findViewById(R.id.releve_item_label);
        if(releve.getProduitObject() != null) {
            label.setText(releve.getProduitObject().getNom());
        }

        TextView description = (TextView) convertView.findViewById(R.id.releve_item_prix);
        description.setText(releve.getPrix());

        return convertView;
    }
}
