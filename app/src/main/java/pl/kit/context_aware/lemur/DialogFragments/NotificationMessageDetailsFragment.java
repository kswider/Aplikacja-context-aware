package pl.kit.context_aware.lemur.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import pl.kit.context_aware.lemur.R;

/**
 * Created by Tomek on 2017-04-21.
 */

public class NotificationMessageDetailsFragment extends DialogFragment {

    private String notiTitle;
    private String message;
    private static final int REQUEST_CONTACT_NUMBER = 1;

    private EditText et_notiTitle;
    private EditText et_message;

    public void setnotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getnotiTitle() {
        return notiTitle;
    }

    public String getMessage() {
        return message;
    }

    /* The activity that creates an instance of this dialog fragment must
            * implement this interface in order to receive event callbacks.
            * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeNotificationMessageDetailsFragmentListener {
        public void onNotificationMessageDetailsFragmentPositiveClick(DialogFragment dialog);
        public void onNotificationMessageDetailsFragmentNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeNotificationMessageDetailsFragmentListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeNotificationMessageDetailsFragmentListener) activity;
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
        View dialogView = inflater.inflate(R.layout.fragment_notification_details, null);
        builder.setView(dialogView);

        et_notiTitle = (EditText) dialogView.findViewById(R.id.noti_et_title);
        et_message = (EditText) dialogView.findViewById(R.id.noti_et_message);


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(R.string.noti_title)
                // Add action buttons
                .setPositiveButton(R.string.tp_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        notiTitle = et_notiTitle.getText().toString();
                        message = et_message.getText().toString();
                        mListener.onNotificationMessageDetailsFragmentPositiveClick(NotificationMessageDetailsFragment.this);
                    }
                })
                .setNegativeButton(R.string.tp_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onNotificationMessageDetailsFragmentNegativeClick(NotificationMessageDetailsFragment.this);
                        NotificationMessageDetailsFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}