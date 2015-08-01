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
import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.data.Visite;

/**
 * Created by lumiru on 02/07/15.
 */
public class VisiteListAdapter extends ArrayAdapter<Visite> {
    private final LayoutInflater inflater;
    private final int resource;
    private final SimpleDateFormat format;

    public VisiteListAdapter(Context context, int resource, List<Visite> objects) {
        super(context, resource, objects);
        this.resource = resource;
        inflater = LayoutInflater.from(context);
        format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* create a new view of my layout and inflate it in the row */
        convertView = inflater.inflate( resource, null );

        /* Extract the city's object to show */
        Visite visite = getItem( position );

        TextView label = (TextView) convertView.findViewById(R.id.visite_item_label);
        if(visite.getClientObject() != null) {
            label.setText(visite.getClientObject().getNom());
        }

        TextView description = (TextView) convertView.findViewById(R.id.visite_item_ville);
        if(visite.getDateVisite() != null) {
            description.setText(format.format(visite.getDateVisite()));
        }

        return convertView;
    }
}
