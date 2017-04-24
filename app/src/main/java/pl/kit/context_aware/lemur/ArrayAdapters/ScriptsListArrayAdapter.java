package pl.kit.context_aware.lemur.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.ListItems.ListItem;

/**
 * Created by Tomek on 09.01.2017.
 * Adapter class used to drawing single elements of list in ScriptsList Fragment
 */

public class ScriptsListArrayAdapter extends ArrayAdapter<ListItem> {

    /**
     * Needed constructor
     */
    public ScriptsListArrayAdapter(Context context, ArrayList<ListItem> Texts) {
        super(context,0, Texts);
    }

    /**
     * Method creating view of single list element
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ListItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_main);
        TextView sub = (TextView) convertView.findViewById(R.id.le_sub);
        // Populate the data into the template view using the data object
        main.setText(item.mainText);
        sub.setText(item.subText);
        // Return the completed view to render on screen
        return convertView;
    }
}
