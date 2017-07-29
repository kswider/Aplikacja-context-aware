package pl.kit.context_aware.lemur.dialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.LinkedList;

import pl.kit.context_aware.lemur.filesOperations.FilesOperations;
import pl.kit.context_aware.lemur.R;

/**
 * Created by Tomek on 2017-01-16.
 * Dialog fragment used to pick scripts to export in ImportExportFragment
 */

public class ScriptsToExportPickerFragment extends DialogFragment {
    private LinkedList<String> selectedScripts = new LinkedList<>();//List containing int numbers of picked scripts
    NoticeDialogSTEFListener mListener; //Object of inner inference used to communicate between dialog and activity

    /**
     * Getter returning list of picked scripts
     * @return Linked list with picked scripts
     */
    public LinkedList<String> getSelectedScripts() {
        return selectedScripts;
    }

    /**
     * Inference used to communication between this dialog and activity in which it was called
     */
    public interface NoticeDialogSTEFListener {
        public void onDialogSTEFPositiveClick(DialogFragment dialog);
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
            mListener = (NoticeDialogSTEFListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogSTEFListener");
        }
    }


    /**
     * Method building dialog window
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<String> mSelectedItems = new ArrayList();

        final String[] listItems = FilesOperations.getAllModelNames(this.getActivity()).toArray(new String[FilesOperations.getAllModelNames(this.getActivity()).size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(getString(R.string.choose_scripts))
                .setMultiChoiceItems(listItems, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            mSelectedItems.add(listItems[which]);
                        }
                        else if(mSelectedItems.contains(which)){
                            mSelectedItems.remove(listItems[which]);
                        }
                    }
                })
                .setPositiveButton(R.string.tp_ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<mSelectedItems.size(); i++){
                            selectedScripts.add(mSelectedItems.get(i));
                        }
                        mListener.onDialogSTEFPositiveClick(ScriptsToExportPickerFragment.this);

                    }
                });
        return builder.create();
    }
}
