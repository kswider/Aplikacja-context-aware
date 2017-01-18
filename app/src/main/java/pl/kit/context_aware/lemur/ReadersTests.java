package pl.kit.context_aware.lemur;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Fragment contains buttons used to test if readers are working properly
 */
public class ReadersTests extends Fragment {
    public ReadersTests() {
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
                .findViewById(R.id.ReadTimeButton);
        Button button2 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.ReadLocationButton);
        return inflater.inflate(R.layout.fragment_readers_tests, container, false);
    }

}
