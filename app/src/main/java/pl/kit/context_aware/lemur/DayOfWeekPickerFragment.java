package pl.kit.context_aware.lemur;

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

/**
 * Created by Tomek on 10.01.2017.
 */

public class DayOfWeekPickerFragment extends DialogFragment {
    private LinkedList<Integer> daysOfWeek = new LinkedList<>();

    public LinkedList<Integer> getDays() {
        return daysOfWeek;
    }

    public interface NoticeDialogDOWPFListener {
        public void onDialogDOWPFPositiveClick(DialogFragment dialog);
        public void onDialogDOWPFNegativeClick(DialogFragment dialog);
    }

    NoticeDialogDOWPFListener mListener;

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



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Integer> mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMultiChoiceItems(R.array.days, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            mSelectedItems.add(which);
                        }
                        else if(mSelectedItems.contains(which)){
                            mSelectedItems.remove(Integer.valueOf(which));
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
                            daysOfWeek.add(mSelectedItems.get(i));
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
