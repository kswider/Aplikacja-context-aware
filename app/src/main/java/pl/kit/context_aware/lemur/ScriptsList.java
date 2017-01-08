package pl.kit.context_aware.lemur;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pl.kit.context_aware.lemur.Readers.ReadTime;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScriptsList extends Fragment {


    public ScriptsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FloatingActionButton fab =
                (FloatingActionButton) inflater.inflate(R.layout.fragment_scripts_list, container, false)
                        .findViewById(R.id.floatingButton);
        return inflater.inflate(R.layout.fragment_scripts_list, container, false);
    }

}
