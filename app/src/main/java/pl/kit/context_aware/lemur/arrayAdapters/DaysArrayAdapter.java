package pl.kit.context_aware.lemur.arrayAdapters;

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

import pl.kit.context_aware.lemur.activities.EditScript;
import pl.kit.context_aware.lemur.dialogFragments.DatePickerFragment;
import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.listItems.DayItem;

/**
 * Created by Tomek on 2017-04-22.
 */

public class DaysArrayAdapter extends ArrayAdapter<DayItem>  {
    public ArrayList<DayItem> list;
    private Context mContext;
    /**
     * Needed constructor
     */
    public DaysArrayAdapter(Context context, ArrayList<DayItem> Texts) {
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
        final DayItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_script_edit_1, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_es_main);
        TextView sub = (TextView) convertView.findViewById(R.id.le_es_sub);

        main.setText("");
        main.setText(item.getDay() + "." + item.getMonth() + "." + item.getYear());

        ImageButton editButton = (ImageButton)convertView.findViewById(R.id.le_es_edit);
        ImageButton deleteButton = (ImageButton)convertView.findViewById(R.id.le_es_delete);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
                EditScript.ListUtils.setDynamicHeight((ListView)((Activity)mContext).findViewById(R.id.es_lv_days));
            }
        });
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                ((DatePickerFragment)newFragment).setPosition(position);
                ((DatePickerFragment)newFragment).setDay(item.getDay());
                ((DatePickerFragment)newFragment).setMonth(item.getMonth());
                ((DatePickerFragment)newFragment).setYear(item.getYear());
                newFragment.show(((Activity)mContext).getFragmentManager(), "Date Picker");
                notifyDataSetChanged();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
