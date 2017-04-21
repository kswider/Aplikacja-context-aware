package pl.kit.context_aware.lemur;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import pl.kit.context_aware.lemur.R;

/**
 * Created by Tomek on 2017-04-21.
 */

public class SMSMessageDetailsFragment extends DialogFragment {

    private String phoneNo;
    private String message;

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getMessage() {
        return message;
    }

    /* The activity that creates an instance of this dialog fragment must
            * implement this interface in order to receive event callbacks.
            * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeSMSMessageDetailsFragmentListener {
        public void onSMSMessageDetailsFragmentPositiveClick(DialogFragment dialog);
        public void onSMSMessageDetailsFragmentNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeSMSMessageDetailsFragmentListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeSMSMessageDetailsFragmentListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_sms_details, null);
        builder.setView(dialogView);

        final EditText et_phoneNo = (EditText) dialogView.findViewById(R.id.et_sms_phoneNo);
        final EditText et_message = (EditText) dialogView.findViewById(R.id.et_sms_message);


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
            builder.setTitle(R.string.sms_fragmentTitle)
                    // Add action buttons
                    .setPositiveButton(R.string.tp_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                phoneNo = et_phoneNo.getText().toString();
                                message = et_message.getText().toString();
                                mListener.onSMSMessageDetailsFragmentPositiveClick(SMSMessageDetailsFragment.this);
                            }
                        })
                        .setNegativeButton(R.string.tp_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mListener.onSMSMessageDetailsFragmentNegativeClick(SMSMessageDetailsFragment.this);
                                SMSMessageDetailsFragment.this.getDialog().cancel();
                            }
                        });
        return builder.create();
    }

}
