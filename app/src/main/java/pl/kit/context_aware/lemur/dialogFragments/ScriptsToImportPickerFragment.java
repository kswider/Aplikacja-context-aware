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
 * Created by Tomek on 2017-01-17.
 * Dialog fragment used to pick scripts to import in ImportExportFragment
 */

public class ScriptsToImportPickerFragment extends DialogFragment {
    private LinkedList<String> selectedScripts = new LinkedList<>(); //List containing int numbers of picked scripts
    ScriptsToImportPickerFragment.NoticeDialogSTIFListener mListener; //Object of inner inference used to communicate between dialog and activity

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
    public interface NoticeDialogSTIFListener {
        public void onDialogSTIFPositiveClick(DialogFragment dialog);
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
            mListener = (ScriptsToImportPickerFragment.NoticeDialogSTIFListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogDOWPFListener");
        }
    }

    /**
     * Method building dialog window
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<String> mSelectedItems = new ArrayList();

        final String[] listItems = FilesOperations.getAllModelNamesFromExternalStorage(this.getActivity())
                .toArray(new String[FilesOperations.getAllModelNamesFromExternalStorage(this.getActivity()).size()]);

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
                        mListener.onDialogSTIFPositiveClick(ScriptsToImportPickerFragment.this);

                    }
                });
        return builder.create();
    }
}
