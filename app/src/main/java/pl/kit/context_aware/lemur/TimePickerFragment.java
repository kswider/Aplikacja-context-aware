package pl.kit.context_aware.lemur;

import android.app.Activity;
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
    private int minute;
    private int hour;

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public interface NoticeDialogTPFListener {
        public void onDialogTPFPositiveClick(DialogFragment dialog);
        public void onDialogTPFNegativeClick(DialogFragment dialog);
    }

    NoticeDialogTPFListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogTPFListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

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

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.minute = minute;

        this.hour = hourOfDay;

        TextView time = (TextView) getActivity().findViewById(R.id.es_set_time_sub);
        time.setText(hourOfDay +":" + minute);

        mListener.onDialogTPFPositiveClick(TimePickerFragment.this);
    }
}

