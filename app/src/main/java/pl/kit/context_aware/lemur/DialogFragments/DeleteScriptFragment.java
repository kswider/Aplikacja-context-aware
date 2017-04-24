package pl.kit.context_aware.lemur.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;


import pl.kit.context_aware.lemur.Editor.ModelCreator;
import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.R;

/**
 * Created by Tomek on 2017-01-13.
 * Dialog fragment used to deleting scripts in ScriptsList Fragment;
 */

public class DeleteScriptFragment extends DialogFragment {
    private String fileName; //name of selected item
    NoticeDialogDSFListener mListener;  //Object of inner inference used to communicate between dialog and activity

    /**
     * Setter used to setting values checked on dialog window opened
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Inference used to communication between this dialog and activity in which it was called
     */
    public interface NoticeDialogDSFListener {
        public void onDialogDSFPositiveClick(DialogFragment dialog);
        public void onDialogDSFNegativeClick(DialogFragment dialog);
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
            mListener = (DeleteScriptFragment.NoticeDialogDSFListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogDSFListener");
        }
    }

    /**
     * Method building dialog window
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.ds_delete_1) + " " + fileName + " " + getString(R.string.ds_delete_2))
                .setPositiveButton(R.string.tp_ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ModelCreator loadedModel = ModelCreator.loadModel(getActivity().getFilesDir() + "/" + fileName +".ser");

                        LinkedList<String> list;
                        list = loadedModel.getAttribute("notificationNumber").getValues(); // checking if there is a need to delete notification
                        if(!list.isEmpty()){
                            FilesOperations.deleteNotification(getActivity(),list.pop());
                        }
                        list = loadedModel.getAttribute("smsNumber").getValues(); // checking if there is a need to delete sms
                        if(!list.isEmpty()){
                            FilesOperations.deleteSMS(getActivity(),list.pop());
                        }

                        File fileSer = new File(getActivity().getFilesDir() + "/" + fileName +".ser");
                        File fileHmr = new File(getActivity().getFilesDir() + "/" + fileName +".hmr");
                        fileSer.delete();
                        fileHmr.delete();
                        mListener.onDialogDSFPositiveClick(DeleteScriptFragment.this);
                    }
                })
                .setNegativeButton(R.string.tp_cancel, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogDSFNegativeClick(DeleteScriptFragment.this);
                    }
                });
        return builder.create();
    }
}
