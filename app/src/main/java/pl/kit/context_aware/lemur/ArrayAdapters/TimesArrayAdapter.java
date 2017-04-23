package pl.kit.context_aware.lemur.ArrayAdapters;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.TimePickerFragment;
import pl.kit.context_aware.lemur.TmpTests.ListItem2;

/**
 * Created by Tomek on 2017-04-22.
 */

public class TimesArrayAdapter extends ArrayAdapter<ListItem2>  {
    public ArrayList<ListItem2> list;
    private Context mContext;
    /**
     * Needed constructor
     */
    public TimesArrayAdapter(Context context, ArrayList<ListItem2> Texts) {
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
                ((TimePickerFragment) newFragment).setHour(5);
                ((TimePickerFragment) newFragment).setMinute(position);
                newFragment.show(((Activity)mContext).getFragmentManager(), "Time Picker");
                notifyDataSetChanged();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
