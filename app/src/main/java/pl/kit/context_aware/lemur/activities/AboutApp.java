package pl.kit.context_aware.lemur.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.kit.context_aware.lemur.R;


/**
 * A simple {@link Fragment} subclass
 * Fragment Shows basic info about app
 */
public class AboutApp extends Fragment {

    /**
     * Required empty public constructor
     */
    public AboutApp() {}

    /**
     * method called always when fragment is being drawn
     * creates objects which this fragment contains (buttons, textviews etc.)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_app, container, false);
    }

}
