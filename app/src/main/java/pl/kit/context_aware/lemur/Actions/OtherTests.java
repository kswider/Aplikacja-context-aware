package pl.kit.context_aware.lemur.Actions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.kit.context_aware.lemur.R;


/**
 * A simple {@link Fragment} subclass.
 * Fragment contains buttons used to test other actions like running services or scheduling alarms
 */
public class OtherTests extends Fragment {

    public OtherTests() {
        // Required empty public constructor
    }

    /**
     * method called always when fragment is being drawn
     * creates objects which this fragment contains (buttons, textviews etc.)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button button1 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.startServiceButton);
        Button button2 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.stopServiceButton);
        Button button3 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.runSimModelButton);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_tests, container, false);
    }

}
