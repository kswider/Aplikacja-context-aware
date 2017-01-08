package pl.kit.context_aware.lemur;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherTests extends Fragment {
    Button button1;
    Button button2;
    Button button3;

    public OtherTests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        button1 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.startServiceButton);
        button2 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.stopServiceButton);
        button3 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.runSimModelButton);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_tests, container, false);
    }

}
