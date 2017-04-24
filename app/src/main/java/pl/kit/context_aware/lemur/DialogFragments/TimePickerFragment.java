package pl.kit.context_aware.lemur.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import pl.kit.context_aware.lemur.R;

/**
 * Created by Tomek on 09.01.2017.
 * Dialog fragment used to pick time in EditScript Activity
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int minute; //picked minute
    private int hour; //picked hour
    private NoticeDialogTPFListener mListener; //Object of inner inference used to communicate between dialog and activity
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {

        return position;
    }

    /**
     * Getter returning picked minutes
     * @return picked minutes
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Getter returning picked hours
     * @return picked mhours
     */
    public int getHour() {
        return hour;
    }

    /**
     *
     * @param minute
     */
    public void setMinute(int minute) {
        if (minute >=0) this.minute = minute;
    }

    /**
     *
     * @param hour
     */
    public void setHour(int hour) {

        if(hour>=0) this.hour = hour;
    }

    /**
     * Inference used to communication between this dialog and activity in which it was called
     */
    public interface NoticeDialogTPFListener {
        public void onDialogTPFPositiveClick(DialogFragment dialog);
        public void onDialogTPFNegativeClick(DialogFragment dialog);
    }

    /**
     * Method needed for communication between activity and dialog
     */
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
                    + " must implement NoticeDialogTPFListener");
        }
    }

    /**
     * Method building dialog window
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
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

