package pl.kit.context_aware.lemur;

import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import pl.kit.context_aware.lemur.Editor.ModelCreator;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.ALSVExpression;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.ActionExpression;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.DecisionExpression;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xattr;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xrule;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xschm;
import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.TmpTests.ListItem;

import static android.R.id.list;
import static java.security.AccessController.getContext;

public class EditScript extends AppCompatActivity implements DayOfWeekPickerFragment.NoticeDialogDOWPFListener
        , ActionPickerFragment.NoticeDialogAPFListener, TimePickerFragment.NoticeDialogTPFListener {

    EditText scriptName;

    private int hour;
    private int minute;
    private double time;
    private Double longitude;
    private Double latitude;
    private LinkedList<Integer> days;
    private LinkedList<Integer> actions;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void SetTimeOnClick(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "Time Picker");
    }

    public void SetDayOnClick(View v) {
        DialogFragment newFragment = new DayOfWeekPickerFragment();
        newFragment.show(getFragmentManager(), "DayOfWeek Picker");
    }

    public void SetActionOnClick(View v) {
        DialogFragment newFragment = new ActionPickerFragment();
        newFragment.show(getFragmentManager(), "Action Picker");
    }

    public void SetLocationOnClick(View v) {
        Toast.makeText(this, "TO DO", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hour = -1;
        minute = -1;
        time = -1;
        longitude = null;
        latitude = null;
        actions = new LinkedList();
        days = new LinkedList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_script);
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_script_toolbar);
        scriptName = (EditText) findViewById(R.id.es_set_tiitle_sub);
        //toolbar.setTitle(getResources().getString(R.string.es_Script));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_script_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_script:

                //Creating basic model containing all needed types and attributes
                ModelCreator newModel = ModelCreator.createBasicModel(scriptName.getText().toString(), this); //TODO name should be given by user

                //Creating lists which are needed in Scheme
                LinkedList<Xattr> attributesList = new LinkedList<>();
                LinkedList<Xattr> attributesToSetList = new LinkedList<>();

                //Creating lists which are needed in Rule
                LinkedList<ALSVExpression> ALSVList = new LinkedList<>();
                LinkedList<DecisionExpression> decisionList = new LinkedList<>();
                LinkedList<ActionExpression> actionList = new LinkedList<>();

                //Creating expressions which will be added to lists
                ALSVExpression alsv = null;
                DecisionExpression decision = null;
                ActionExpression action = null;

                if (!(time == -1)) {
                    attributesList.add(newModel.getAttribute("time"));

                    alsv = new ALSVExpression(newModel.getAttribute("time"), Double.toString(time));
                    ALSVList.add(alsv);
                }
                if (!days.isEmpty()) {
                    attributesList.add(newModel.getAttribute("day"));

                    final String[] daysOfWeekArray = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
                    LinkedList<String> selectedDays = new LinkedList<>();
                    for (Integer day : days) {
                        selectedDays.add(daysOfWeekArray[day]);
                    }
                    alsv = new ALSVExpression(newModel.getAttribute("day"), selectedDays);
                    ALSVList.add(alsv);
                }
                if ((longitude != null) && (latitude != null)) {
                    attributesList.add(newModel.getAttribute("longitude"));
                    alsv = new ALSVExpression(newModel.getAttribute("longitude"), longitude.toString());
                    ALSVList.add(alsv);

                    attributesList.add(newModel.getAttribute("latitude"));
                    alsv = new ALSVExpression(newModel.getAttribute("latitude"), latitude.toString());
                    ALSVList.add(alsv);
                }
                if (!actions.isEmpty()) {
                    attributesToSetList.add(newModel.getAttribute("sound"));

                    final String[] actionsArray = {"off", "on", "vibration"};
                    for (Integer actionNumber : actions) {
                        decision = new DecisionExpression(newModel.getAttribute("sound"), actionsArray[actionNumber]);
                        decisionList.add(decision);
                    }
                    action = new ActionExpression("pl.kit.conext_aware.lemur.HeartDROID.actions.SetSound"); //TODO another loop but need to check if it is possible to do few actions in one rule
                    actionList.add(action);

                }
                // Creating and adding scheme to model
                Xschm scheme = new Xschm("SetSounds", attributesList, attributesToSetList);
                newModel.addScheme(scheme);
                //Creating Rules
                LinkedList<Xrule> rulesList = new LinkedList<>();
                Xrule rule = new Xrule(scheme, 1, ALSVList, decisionList, actionList);
                newModel.addRule(rule);
                newModel.saveModel();

                /* //needed for debugging
                for (Integer file : actions) {
                        Log.i("File ", file.toString());
                }
                */
                Toast.makeText(this, getResources().getString(R.string.es_Added), Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onDialogDOWPFPositiveClick(DialogFragment dialog) {
        days = (LinkedList<Integer>) ((DayOfWeekPickerFragment) dialog).getDays().clone();
    }

    @Override
    public void onDialogDOWPFNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogAPFPositiveClick(DialogFragment dialog) {
        actions = (LinkedList<Integer>) ((ActionPickerFragment) dialog).getActions().clone();
    }

    @Override
    public void onDialogAPFNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogTPFPositiveClick(DialogFragment dialog) {
        hour = ((TimePickerFragment) dialog).getHour();
        minute = ((TimePickerFragment) dialog).getMinute();
        time = hour + (minute / 60);
    }

    @Override
    public void onDialogTPFNegativeClick(DialogFragment dialog) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("EditScript Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
