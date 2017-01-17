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
 */
public class ActionsTests extends Fragment {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    public ActionsTests() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        button1 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.SilentButton);
        button2 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.VibrationButton);
        button3 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.NormalRingButton);
        button4 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.NotificationButton);
        button5= (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.TurnOnWiFiButton);
        button6 = (Button) inflater.inflate(R.layout.fragment_actions_tests, container, false)
                .findViewById(R.id.TurnOffWiFiButton);

        return inflater.inflate(R.layout.fragment_actions_tests, container, false);
    }
}
