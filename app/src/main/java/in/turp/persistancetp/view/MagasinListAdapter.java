package in.turp.persistancetp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import in.turp.persistancetp.R;
import in.turp.persistancetp.data.Magasin;

/**
 * Created by lumiru on 30/06/15.
 */
public class MagasinListAdapter extends ArrayAdapter<Magasin> {
    private final LayoutInflater inflater;
    private final int resource;

    public MagasinListAdapter(Context context, int resource, List<Magasin> objects) {
        super(context, resource, objects);
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* create a new view of my layout and inflate it in the row */
        convertView = inflater.inflate( resource, null );

        /* Extract the city's object to show */
        Magasin magasin = getItem( position );

        /* Take the TextView from layout and set the city's name */
        TextView label = (TextView) convertView.findViewById(R.id.magasin_item_label);
        label.setText(magasin.getNom());

        /* Take the TextView from layout and set the city's wiki link */
        TextView description = (TextView) convertView.findViewById(R.id.magasin_item_ville);
        description.setText(magasin.getVille());

        /* Take the ImageView from layout and set the city's image */
        //ImageView imageCity = (ImageView) convertView.findViewById(R.id.magasin_item_image);
        //String uri = "drawable/" + magasin.getEnseigne().getImage();
        //int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        //Drawable image = context.getResources().getDrawable(imageResource);
        //imageCity.setImageDrawable(image);

        //TextView magasinGroupe = (ImageView) convertView.findViewById(R.id.magasin_item_groupe);
        // magasinGroupe.setText(magasin.getEnseigne().getNom());

        return convertView;
    }
}
