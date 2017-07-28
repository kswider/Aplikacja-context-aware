package pl.kit.context_aware.lemur.DialogFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.kit.context_aware.lemur.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Tomek on 2017-04-21.
 */

public class SMSMessageDetailsFragment extends DialogFragment {

    private String phoneNo;
    private String message;
    private static final int REQUEST_CONTACT_NUMBER = 1;
    private int position; //position on the list in EditScript activity
    private EditText et_phoneNo;
    private EditText et_message;
    private Button clickButton;

    public SMSMessageDetailsFragment() {
        this.position = -1;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }
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

    /**
     * Inference used to communication between this dialog and activity in which it was called
     */
    public interface NoticeSMSMessageDetailsFragmentListener {
        public void onSMSMessageDetailsFragmentPositiveClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeSMSMessageDetailsFragmentListener mListener;

    /**
     * Method needed for communication between activity and dialog
     */
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


    /**
     * Method building dialog window
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_sms_details, null);
        builder.setView(dialogView);

        et_phoneNo = (EditText) dialogView.findViewById(R.id.et_sms_phoneNo);
        et_message = (EditText) dialogView.findViewById(R.id.et_sms_message);

        et_message.setText(message);
        et_phoneNo.setText(phoneNo);

        clickButton = (Button) dialogView.findViewById(R.id.sms_pb_button);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity().getBaseContext(),
                        Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(contactPickerIntent, REQUEST_CONTACT_NUMBER);
                }else{
                    Toast.makeText(getActivity().getBaseContext(),getActivity().getBaseContext().getResources().getString(R.string.pl_read_contacts),Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                                1);
                    }
                }
            }
        });


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
                        });
        return builder.create();
    }

    /**
     *Method used to call phone book
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CONTACT_NUMBER ) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Uri uriOfPhoneNumberRecord = data.getData();
                String idOfPhoneRecord = uriOfPhoneNumberRecord.getLastPathSegment();
                Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone._ID + "=?", new String[]{idOfPhoneRecord}, null);
                if(cursor != null) {
                    if(cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        phoneNo = cursor.getString( cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) );
                        et_phoneNo.setText(phoneNo);
                    }
                    cursor.close();
                }

            }
        }
    }

}
