package pl.kit.context_aware.lemur.DialogFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.kit.context_aware.lemur.R;


/**
 * A simple {@link Fragment} subclass.
 * Fragment containing everything needed to import or export scripts
 */
public class ImportExportFragment extends Fragment {
    Button button1;
    Button button2;


    /**
     * Required empty public constructor
     */
    public ImportExportFragment() {
        // Required empty public constructor
    }

    /**
     * method called always when fragment is being drawn
     * creates objects which this fragment contains (buttons, textviews etc.)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        button1 = (Button) inflater.inflate(R.layout.fragment_import_export, container, false)
                .findViewById(R.id.ImportScriptButton);
        button2 = (Button) inflater.inflate(R.layout.fragment_import_export, container, false)
                .findViewById(R.id.ExportScriptButton);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_import_export, container, false);
    }

}
