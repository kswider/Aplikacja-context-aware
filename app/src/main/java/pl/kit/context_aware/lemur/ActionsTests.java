package pl.kit.context_aware.lemur;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pl.kit.context_aware.lemur.PhoneActions.SendNotification;


/**
 * A simple {@link Fragment} subclass.
 * Fragment contains buttons used to chech if Result Actions are working properly
 */
public class ActionsTests extends Fragment {

    public ActionsTests() {
        // Required empty public constructor
    }

    /**
     * method called always when fragment is being drawn
     * creates objects which this fragment contains (buttons, textviews etc.)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Button button1 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.SilentButton);
        Button button2 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.VibrationButton);
        Button button3 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.NormalRingButton);
        Button button4 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.NotificationButton);
        Button button5= (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.TurnOnWiFiButton);
        Button button6 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.TurnOffWiFiButton);

        return inflater.inflate(R.layout.fragment_actions_tests, container, false);
    }
}
