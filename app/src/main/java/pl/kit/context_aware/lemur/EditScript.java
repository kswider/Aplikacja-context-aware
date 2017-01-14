package pl.kit.context_aware.lemur;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
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

    String rememberedModelName; //used in deleting old models
    private int hour;
    private int minute;
    private double time;
    private Double longitude;
    private Double latitude;
    private LinkedList<Integer> days = new LinkedList<>();
    private LinkedList<Integer> actions = new LinkedList<>();

    private String scriptNameToLoad;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void SetTimeOnClick(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setHour(hour);
        ((TimePickerFragment) newFragment).setMinute(minute);
        newFragment.show(getFragmentManager(), "Time Picker");
    }

    public void SetDayOnClick(View v) {
        DialogFragment newFragment = new DayOfWeekPickerFragment();
        ((DayOfWeekPickerFragment) newFragment).setDaysOfWeek(days);
        newFragment.show(getFragmentManager(), "DayOfWeek Picker");
    }

    public void SetActionOnClick(View v) {
        DialogFragment newFragment = new ActionPickerFragment();
        ((ActionPickerFragment)newFragment).setActions(this.actions);
        newFragment.show(getFragmentManager(), "Action Picker");
    }

    public void SetLocationOnClick(View v) {
        Toast.makeText(this, "TO DO", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String scriptNameToEdit = intent.getExtras().getString("eFileName");
        rememberedModelName = scriptNameToEdit;
        Log.i("App",scriptNameToEdit);
        if(scriptNameToEdit.isEmpty()) {
            hour = -1;
            minute = -1;
            time = -1;
            longitude = null;
            latitude = null;

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
        else {
            scriptNameToLoad = this.getFilesDir() + "/" + scriptNameToEdit +".ser";
            ModelCreator loadedModel = ModelCreator.loadModel(scriptNameToLoad);

            if(!loadedModel.getAttribute("minute").getValues().isEmpty()) {
                minute = Integer.valueOf(loadedModel.getAttribute("minute").getValues().getFirst());
            }
            if(!loadedModel.getAttribute("hour").getValues().isEmpty()){
                hour = Integer.valueOf(loadedModel.getAttribute("hour").getValues().getFirst());
            }
            if(!loadedModel.getAttribute("time").getValues().isEmpty()){
                time = Double.valueOf(loadedModel.getAttribute("time").getValues().getFirst());
            }
            //longitude = ... TODO
            //latitude = ... TODO

            final String[] daysOfWeekArray = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

            for (String day : loadedModel.getAttribute("day").getValues()){
                for(int i = 0; i<daysOfWeekArray.length;++i){
                    if(daysOfWeekArray[i].equals(day)){
                        days.add(i);
                    }
                }
            }


            final String[] argumentsArray = {"bluetooth","wifi","datatransmission","sound"}; //TODO message, it will be different when we implement setting action differently
            final String[] stateArray = {"off", "on", "vibration"};
            for(int j = 0; j < argumentsArray.length; ++j) {
                for (String action : loadedModel.getAttribute(argumentsArray[j]).getValues()) {
                    for (int i = 0; i < stateArray.length; ++i) {
                        if (stateArray[i].equals(action)) {
                            actions.add(i + j*2);
                        }
                    }
                }
            }


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_script);
            Toolbar toolbar = (Toolbar) findViewById(R.id.edit_script_toolbar);

            //filling fields with loaded attributes
            scriptName = (EditText) findViewById(R.id.es_set_tiitle_sub);
            scriptName.setText(scriptNameToEdit);
            TextView timeTV = (TextView) this.findViewById(R.id.es_set_time_sub);
            if(!loadedModel.getAttribute("minute").getValues().isEmpty()) {
                timeTV.setText(hour + ":" + minute);
            }
            else{
                timeTV.setText("");
            }

            TextView daysTV = (TextView) this.findViewById(R.id.es_set_day_sub);
            daysTV.setText("");
            for(int i=0; i<days.size(); i++){
                Resources res = this.getResources();
                daysTV.setText(daysTV.getText().toString() + res.getStringArray(R.array.days)[days.get(i)] + ",");
            }
            TextView actionsTV = (TextView) this.findViewById(R.id.es_set_action_sub);
            actionsTV.setText("");
            for(int i=0; i<actions.size(); i++){
                Resources res = this.getResources();
                actionsTV.setText(actionsTV.getText().toString() + res.getStringArray(R.array.actions)[actions.get(i)] + ",");
            }

            //toolbar.setTitle(getResources().getString(R.string.es_Script));
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        }
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
                ModelCreator newModel = ModelCreator.createBasicModel(scriptName.getText().toString(), this);

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
                    //setting values of Attributes, needed in loading models
                    attributesList.add(newModel.getAttribute("time"));
                    newModel.getAttribute("hour").addValue(String.valueOf(hour));
                    newModel.getAttribute("minute").addValue(String.valueOf(minute));
                    newModel.getAttribute("time").addValue(String.valueOf(time));
                    alsv = new ALSVExpression(newModel.getAttribute("time"), Double.toString(time));
                    ALSVList.add(alsv);
                }
                if (!days.isEmpty()) {
                    attributesList.add(newModel.getAttribute("day"));

                    final String[] daysOfWeekArray = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
                    LinkedList<String> selectedDays = new LinkedList<>();
                    for (Integer day : days) {
                        newModel.getAttribute("day").addValue(daysOfWeekArray[day]);
                        selectedDays.add(daysOfWeekArray[day]);
                    }
                    alsv = new ALSVExpression(newModel.getAttribute("day"), selectedDays);
                    ALSVList.add(alsv);
                }
                if ((longitude != null) && (latitude != null)) {
                    attributesList.add(newModel.getAttribute("longitude"));
                    newModel.getAttribute("longitude").addValue(String.valueOf(longitude));
                    alsv = new ALSVExpression(newModel.getAttribute("longitude"), longitude.toString());
                    ALSVList.add(alsv);

                    attributesList.add(newModel.getAttribute("latitude"));
                    newModel.getAttribute("latitude").addValue(String.valueOf(latitude));
                    alsv = new ALSVExpression(newModel.getAttribute("latitude"), latitude.toString());
                    ALSVList.add(alsv);
                }
                if (!actions.isEmpty()) {
                    for(Integer actionNumber : actions){ // TODO still need some changes
                        if(actionNumber < 2) attributesToSetList.add(newModel.getAttribute("bluetooth"));
                        if(actionNumber > 1 && actionNumber < 4) attributesToSetList.add(newModel.getAttribute("wifi"));
                        if(actionNumber > 3 && actionNumber < 6) attributesToSetList.add(newModel.getAttribute("datatransmission"));
                        if(actionNumber > 5 && actionNumber < 9) attributesToSetList.add(newModel.getAttribute("sound"));
                    }

                    final String[] actionsArray = {"off", "on", "vibration"};
                    for (Integer actionNumber : actions) {
                        if(actionNumber < 2){
                            newModel.getAttribute("bluetooth").addValue(actionsArray[actionNumber]);
                            decision = new DecisionExpression(newModel.getAttribute("bluetooth"), actionsArray[actionNumber]);
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setBluetooth");
                            actionList.add(action);
                        }
                        if(actionNumber > 1 && actionNumber < 4){
                            newModel.getAttribute("wifi").addValue(actionsArray[actionNumber-2]);
                            decision = new DecisionExpression(newModel.getAttribute("wifi"), actionsArray[actionNumber-2]);
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setWifi");
                            actionList.add(action);
                        }
                        if(actionNumber > 3 && actionNumber < 6){
                            newModel.getAttribute("datatransmission").addValue(actionsArray[actionNumber-4]);
                            decision = new DecisionExpression(newModel.getAttribute("datatransmission"), actionsArray[actionNumber-4]);
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setDataTransmission");
                            actionList.add(action);
                        }
                        if(actionNumber > 5 && actionNumber < 9){
                            newModel.getAttribute("sound").addValue(actionsArray[actionNumber-6]);
                            decision = new DecisionExpression(newModel.getAttribute("sound"), actionsArray[actionNumber-6]);
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setSound");
                            actionList.add(action);
                        }
                    } //TODO actions for sending messages


                }
                // Creating and adding scheme to model
                Xschm scheme = new Xschm("SetEverything", attributesList, attributesToSetList);
                newModel.addScheme(scheme);
                //Creating Rules
                LinkedList<Xrule> rulesList = new LinkedList<>();
                Xrule rule = new Xrule(scheme, 1, ALSVList, decisionList, actionList);
                newModel.addRule(rule);
                newModel.saveModel();

                //deleting old models
                if(!rememberedModelName.equals(scriptName.getText().toString())){
                    File fileSer = new File(this.getFilesDir() + "/" + rememberedModelName +".ser");
                    File fileHmr = new File(this.getFilesDir() + "/" + rememberedModelName +".hmr");
                    fileSer.delete();
                    fileHmr.delete();
                }
                /* //needed for debugging
                for (Integer file : actions) {
                        Log.i("File ", file.toString());
                }
                */
                //SimpleNumeric a = new SimpleNumeric(5.5);

                Toast.makeText(this, "Script added successfullly!", Toast.LENGTH_SHORT).show();
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
        time = (double)hour + ((double)minute / 60);
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
