package pl.kit.context_aware.lemur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.TmpTests.ListItem2;

/**
 * Created by Tomek on 2017-04-22.
 */

public class EditScriptsArrayAdapter extends ArrayAdapter<ListItem2> {
    /**
     * Needed constructor
     */
    public EditScriptsArrayAdapter(Context context, ArrayList<ListItem2> Texts) {
        super(context,0, Texts);
    }

    /**
     * Method creating view of single list element
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ListItem2 item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_script_edit, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_es_main);
        TextView sub = (TextView) convertView.findViewById(R.id.le_es_sub);
        // Populate the data into the template view using the data object
        main.setText(item.mainText);
        sub.setText(item.subText);
        // Return the completed view to render on screen
        return convertView;
    }
}
