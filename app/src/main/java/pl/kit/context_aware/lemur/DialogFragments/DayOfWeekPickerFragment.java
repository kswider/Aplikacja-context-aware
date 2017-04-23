package pl.kit.context_aware.lemur.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import pl.kit.context_aware.lemur.R;

/**
 * Created by Tomek on 10.01.2017.
 * Dialog fragment used to pick days of week in script editor;
 */

public class DayOfWeekPickerFragment extends DialogFragment {
    private LinkedList<Integer> daysOfWeek = new LinkedList<>(); //List containing int numbers of picked days
    boolean[] checkedValues; //List containing list of all actions. If true action which number is array number is checked
    NoticeDialogDOWPFListener mListener; //Object of inner inference used to communicate between dialog and activity


    /**
     * Setter used to setting values checked on dialog window opened
     */
    public void setDaysOfWeek(LinkedList<Integer> daysOfWeek) {
        this.daysOfWeek = (LinkedList)daysOfWeek.clone();
    }

    /**
     * Getter returning list of picked days
     * @return Linked list with picked days
     */
    public LinkedList<Integer> getDays() {
        return daysOfWeek;
    }


    /**
     * Inference used to communication between this dialog and activity in which it was called
     */
    public interface NoticeDialogDOWPFListener {
        public void onDialogDOWPFPositiveClick(DialogFragment dialog);
        public void onDialogDOWPFNegativeClick(DialogFragment dialog);
    }

    /**
     * Method needed for communication between activity and dialog
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogDOWPFListener so we can send events to the host
            mListener = (NoticeDialogDOWPFListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogDOWPFListener");
        }
    }

    /**
     * Method building dialog window
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Integer> mSelectedItems = new ArrayList();

        for(int i=0; i<daysOfWeek.size();i++) {
            mSelectedItems.add(daysOfWeek.get(i));
        }

        checkedValues = new boolean[getActivity().getResources().getStringArray(R.array.days).length];
        for (int i=0; i< daysOfWeek.size(); i++){
            checkedValues[daysOfWeek.get(i)] = true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.es_SetDay)
                .setMultiChoiceItems(R.array.days, checkedValues, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            mSelectedItems.add(which);
                            daysOfWeek.add(which);
                        }
                        else if(mSelectedItems.contains(which)){
                            mSelectedItems.remove(Integer.valueOf(which));
                            daysOfWeek.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton(R.string.tp_ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView days = (TextView) getActivity().findViewById(R.id.es_set_day_sub);
                        days.setText("");
                        for(int i=0; i<mSelectedItems.size(); i++){
                            Resources res = getActivity().getResources();
                            days.setText(days.getText().toString() + res.getStringArray(R.array.days)[mSelectedItems.get(i)] + ",");
                        }
                        mListener.onDialogDOWPFPositiveClick(DayOfWeekPickerFragment.this);

                    }
                })
                .setNegativeButton(R.string.tp_cancel, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogDOWPFNegativeClick(DayOfWeekPickerFragment.this);
                    }
                });
        return builder.create();
    }
}