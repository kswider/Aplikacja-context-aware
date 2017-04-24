package pl.kit.context_aware.lemur.ArrayAdapters;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.Activities.EditScript;
import pl.kit.context_aware.lemur.DialogFragments.ActionPickerFragment;
import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.ListItems.ActionItem;

/**
 * Created by Tomek on 2017-04-22.
 */

public class ActionsArrayAdapter extends ArrayAdapter<ActionItem>  {
    public ArrayList<ActionItem> list;
    private Context mContext;
    /**
     * Needed constructor
     */
    public ActionsArrayAdapter(Context context, ArrayList<ActionItem> Texts) {
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
        ActionItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_script_edit, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_es_main);
        TextView sub = (TextView) convertView.findViewById(R.id.le_es_sub);
        // Populate the data into the template view using the data object
        main.setText(item.getMainText());
        sub.setText(item.getSubText());

        ImageButton editButton = (ImageButton)convertView.findViewById(R.id.le_es_edit);
        ImageButton deleteButton = (ImageButton)convertView.findViewById(R.id.le_es_delete);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
                EditScript.ListUtils.setDynamicHeight((ListView)((Activity)mContext).findViewById(R.id.es_lv_actions));
            }
        });
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new ActionPickerFragment();
                //((ActionPickerFragment)newFragment).setActions(this.actions);
                newFragment.show(((Activity)mContext).getFragmentManager(), "Action Picker");
                notifyDataSetChanged();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
