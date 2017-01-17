package pl.kit.context_aware.lemur;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Tomek on 2017-01-13.
 */

public class DeleteScriptFragment extends DialogFragment {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public interface NoticeDialogDSFListener {
        public void onDialogDSFPositiveClick(DialogFragment dialog);
        public void onDialogDSFNegativeClick(DialogFragment dialog);
    }

    NoticeDialogDSFListener mListener;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.ds_delete_1) + " " + fileName + " " + getString(R.string.ds_delete_2))
                .setPositiveButton(R.string.tp_ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
