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

public class ActionPickerFragment extends DialogFragment {
    private LinkedList<Integer> actions = new LinkedList<Integer>();

    public void setActions(LinkedList<Integer> actions) {
        this.actions = actions;
    }

    public LinkedList<Integer> getActions() {
        return actions;
    }

    public interface NoticeDialogAPFListener {
        public void onDialogAPFPositiveClick(DialogFragment dialog);
        public void onDialogAPFNegativeClick(DialogFragment dialog);
    }

    NoticeDialogAPFListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogAPFListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Integer> mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TMP")
                .setMultiChoiceItems(R.array.actions, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                        TextView action = (TextView) getActivity().findViewById(R.id.es_set_action_sub);
                        action.setText("");
                        for(int i=0; i<mSelectedItems.size(); i++){
                            Resources res = getActivity().getResources();
                            action.setText(action.getText().toString() + res.getStringArray(R.array.actions)[mSelectedItems.get(i)] + ",");
                            actions.add(mSelectedItems.get(i));
                        }
                        mListener.onDialogAPFPositiveClick(ActionPickerFragment.this);
                    }
                })
                .setNegativeButton(R.string.tp_ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogAPFNegativeClick(ActionPickerFragment.this);
                    }
                });
        return builder.create();
    }
}
