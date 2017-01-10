package pl.kit.context_aware.lemur;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tomek on 10.01.2017.
 */

public class DayOfWeekPickerFragment extends DialogFragment {
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
                        }

                    }
                })
                .setNegativeButton(R.string.tp_cancel, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
