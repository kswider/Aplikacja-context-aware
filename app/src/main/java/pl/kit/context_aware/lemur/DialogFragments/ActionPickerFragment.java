package pl.kit.context_aware.lemur.DialogFragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import pl.kit.context_aware.lemur.ListItems.ActionItem;
import pl.kit.context_aware.lemur.R;

/**
 * Created by Tomek on 10.01.2017.
 * Dialog fragment used to pick actions in script editor;
 */

public class ActionPickerFragment extends DialogFragment {
    private int actions; //List containing int numbers of picked actions
    private int position;
    NoticeDialogAPFListener mListener; //Object of inner inference used to communicate between dialog and activity

    public void setPosition(int position) {
        this.position = position;
    }


    public int getPosition() {

        return position;
    }

    /**
     * Setter used to setting values checked on dialog window opened
     */
    public void setActions(int actions) {
        this.actions = actions;
    }

    /**
     * Getter returning list of picked actions
     * @return Linked list with picked actions
     */
    public int getActions() {
        return actions;
    }

    /**
     * Inference used to communication between this dialog and activity in which it was called
     */
    public interface NoticeDialogAPFListener {
        public void onDialogAPFPositiveClick(DialogFragment dialog);
        public void onDialogAPFNegativeClick(DialogFragment dialog);
    }

    /**
     * Method needed for communication between activity and dialog
     * @param activity
     */
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
                    + " must implementNoticeDialogAPFListener");
        }
    }


    /**
     * Method building dialog window
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.es_SetAction))
                .setItems(R.array.actions, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actions = which;
                        if(actions == ActionItem.ACTION_SEND_SMS){
                                if (ContextCompat.checkSelfPermission(getActivity().getBaseContext(),
                                        Manifest.permission.SEND_SMS)
                                        == PackageManager.PERMISSION_GRANTED) {
                                    DialogFragment newFragment = new SMSMessageDetailsFragment();
                                    newFragment.show(getActivity().getFragmentManager(), "SendSMSExtended");
                                } else {
                                    Toast.makeText(getActivity().getBaseContext(), getActivity().getBaseContext().getResources().getString(R.string.pl_send_sms), Toast.LENGTH_SHORT).show();
                                }
                        }else if(actions == ActionItem.ACTION_SEND_NOTIFICATION){
                            DialogFragment newFragment = new NotificationMessageDetailsFragment();
                            newFragment.show(getFragmentManager(), "NotificationExtended");
                        }
                        mListener.onDialogAPFPositiveClick(ActionPickerFragment.this);
                    }
                });
        return builder.create();
    }
}
