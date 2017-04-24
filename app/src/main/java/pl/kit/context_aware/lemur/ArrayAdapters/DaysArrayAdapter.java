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
import pl.kit.context_aware.lemur.DialogFragments.DayOfWeekPickerFragment;
import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.ListItems.DayItem;

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
        DayItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_script_edit_1, parent, false);
        }
        // Lookup view for data population
        TextView main = (TextView) convertView.findViewById(R.id.le_es_main);

        main.setText("");
        if(item.getType() == 1){
            final String[] daysOfWeekArray = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
            for(int i=0; i <list.size(); i++){
                main.setText(main.getText() + "," + item.getDayOfWeek().get(i));
            }
        }else{
            main.setText(item.getDay() + "." + item.getMonth() + "." + item.getYear());
        }

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
                DialogFragment newFragment = new DayOfWeekPickerFragment();
                //((DayOfWeekPickerFragment) newFragment).setDaysOfWeek(days);
                newFragment.show(((Activity)mContext).getFragmentManager(), "DayOfWeek Picker");
                notifyDataSetChanged();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
