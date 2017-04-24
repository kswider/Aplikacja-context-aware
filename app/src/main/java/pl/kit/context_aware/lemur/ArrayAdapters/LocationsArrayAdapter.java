package pl.kit.context_aware.lemur.ArrayAdapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.ListItems.LocationItem;

/**
 * Created by Tomek on 2017-04-22.
 */

public class LocationsArrayAdapter extends ArrayAdapter<LocationItem>  {
    public ArrayList<LocationItem> list;
    private Context mContext;
    /**
     * Needed constructor
     */
    public LocationsArrayAdapter(Context context, ArrayList<LocationItem> Texts) {
        super(context,0, Texts);
        list = Texts;
        mContext = context;
    }

    /**
     * Method creating view of single list element
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LocationItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_script_edit, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_es_main);
        TextView sub = (TextView) convertView.findViewById(R.id.le_es_sub);
        // Populate the data into the template view using the data object
        main.setText(item.getLatitude() + "  " + item.getLongitude());

        ImageButton editButton = (ImageButton)convertView.findViewById(R.id.le_es_edit);
        ImageButton deleteButton = (ImageButton)convertView.findViewById(R.id.le_es_delete);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    int PLACE_PICKER_REQUEST = 1;
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        ((Activity)mContext).startActivityForResult(builder.build((Activity)mContext), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(mContext,mContext.getResources().getString(R.string.pl_fine_location),Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
