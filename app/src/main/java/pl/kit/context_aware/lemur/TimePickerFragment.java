package pl.kit.context_aware.lemur;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Tomek on 09.01.2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the time picker
        //final Calendar c = Calendar.getInstance();
        //int hour = c.get(Calendar.HOUR_OF_DAY);
        //int minute = c.get(Calendar.MINUTE);
        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), this, 12, 33,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(view.getContext(),Integer.toString(hourOfDay) + ":" + Integer.toString(minute),Toast.LENGTH_SHORT).show();
        TextView time = (TextView) getActivity().findViewById(R.id.es_set_time_sub);
        time.setText(hourOfDay +":" + minute);
    }
}

