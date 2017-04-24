package pl.kit.context_aware.lemur.ArrayAdapters;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.DialogFragments.TimePickerFragment;
import pl.kit.context_aware.lemur.ListItems.TimeItem;

/**
 * Created by Tomek on 2017-04-22.
 */

public class TimesArrayAdapter extends ArrayAdapter<TimeItem>  {
    public ArrayList<TimeItem> list;
    private Context mContext;
    /**
     * Needed constructor
     */
    public TimesArrayAdapter(Context context, ArrayList<TimeItem> Texts) {
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
        TimeItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_script_edit_1, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_es_main);
        TextView sub = (TextView) convertView.findViewById(R.id.le_es_sub);
        // Populate the data into the template view using the data object
        main.setText(item.getHours() + ":" + item.getMinutes());

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
                DialogFragment newFragment = new TimePickerFragment();
                ((TimePickerFragment) newFragment).setHour(list.get(position).getHours());
                ((TimePickerFragment) newFragment).setMinute(list.get(position).getMinutes());
                ((TimePickerFragment) newFragment).setPosition(position);
                newFragment.show(((Activity)mContext).getFragmentManager(), "Time Picker1");
                notifyDataSetChanged();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
