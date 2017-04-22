package pl.kit.context_aware.lemur;

import android.*;
import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

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

    String rememberedModelName; //used in deleting old models
    private int hour; //hour selected or loaded from file
    private int minute; //minute selected or loaded from file
    private double time; //time calculated with hour and minute
    private Double latitude; //latitude selected or loaded from file
    private Double longitude; //longitude selected or loaded from file
    private LinkedList<Integer> days = new LinkedList<>(); //list of days selected or loaded from file
    private LinkedList<Integer> actions = new LinkedList<>(); //list of actions selected or loaded from file
    private static final int PLACE_PICKER_REQUEST = 1; //variable needed for place picker
    private String scriptNameToLoad; // ??
    EditText scriptName; //Edit text field with script name

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * Action for the Set Time Clicked
     * Opens TimePickerFragment with previously selected items checked
     * @param v current View
     */
    public void SetTimeOnClick(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setHour(hour);
        ((TimePickerFragment) newFragment).setMinute(minute);
        newFragment.show(getFragmentManager(), "Time Picker");
    }

    /**
     * Action for the Set Day Clicked
     * Opens DayOfWeekPickerFragment with previously selected items checked
     * @param v current View
     */
    public void SetDayOnClick(View v) {
        DialogFragment newFragment = new DayOfWeekPickerFragment();
        ((DayOfWeekPickerFragment) newFragment).setDaysOfWeek(days);
        newFragment.show(getFragmentManager(), "DayOfWeek Picker");
    }

    /**
     * Action for the Set Action Clicked
     * Opens SetActionPickerFragment with previously selected items checked
     * @param v current View
     */
    public void SetActionOnClick(View v) {
        DialogFragment newFragment = new ActionPickerFragment();
        ((ActionPickerFragment)newFragment).setActions(this.actions);
        newFragment.show(getFragmentManager(), "Action Picker");
    }

    public void SetDateOnClick(View v) {
        Toast.makeText(this,"TO DO",Toast.LENGTH_SHORT).show();
    }

    /**
     * Action for the Set Action Clicked
     * Opens PlacePicker with current location in center
     * @param v current View
     */
    public void SetLocationOnClick(View v) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            int PLACE_PICKER_REQUEST = 1;
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this,this.getResources().getString(R.string.pl_fine_location),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method running at Activity start
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String scriptNameToEdit = intent.getExtras().getString("eFileName");
        rememberedModelName = scriptNameToEdit;

        hour = -1;
        minute = -1;
        time = -1;
        latitude = null;
        longitude = null;

        //if we are creating new model
        if(scriptNameToEdit.isEmpty()) {
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
        //if we are editing already existing model
        else {
            scriptNameToLoad = this.getFilesDir() + "/" + scriptNameToEdit +".ser";
            ModelCreator loadedModel = ModelCreator.loadModel(scriptNameToLoad);

            //reading values of previus attributes from model
            if(!loadedModel.getAttribute("minute").getValues().isEmpty()) {
                minute = Integer.valueOf(loadedModel.getAttribute("minute").getValues().getFirst());
            }
            if(!loadedModel.getAttribute("hour").getValues().isEmpty()){
                hour = Integer.valueOf(loadedModel.getAttribute("hour").getValues().getFirst());
            }
            if(!loadedModel.getAttribute("time").getValues().isEmpty()){
                time = Double.valueOf(loadedModel.getAttribute("time").getValues().getFirst());
            }
            if(!loadedModel.getAttribute("latitude").getValues().isEmpty()){
                latitude = Double.valueOf(loadedModel.getAttribute("latitude").getValues().getFirst());
            }
            if(!loadedModel.getAttribute("longitude").getValues().isEmpty()){
                longitude = Double.valueOf(loadedModel.getAttribute("longitude").getValues().getFirst());
            }

            final String[] daysOfWeekArray = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

            for (String day : loadedModel.getAttribute("day").getValues()){
                for(int i = 0; i<daysOfWeekArray.length;++i){
                    if(daysOfWeekArray[i].equals(day)){
                        days.add(i);
                    }
                }
            }


            final String[] argumentsArray = {"bluetooth","wifi","sound","message"}; //TODO message, it will be different when we implement setting action differently
            final String[] stateArray = {"off", "on", "vibration"};
            for(int j = 0; j < argumentsArray.length; ++j) {
                for (String action : loadedModel.getAttribute(argumentsArray[j]).getValues()) {
                    for (int i = 0; i < stateArray.length; ++i) {
                        if (stateArray[i].equals(action)) {
                            actions.add(i + j*2);
                        }
                    }
                    if (action.equals("notification") ) actions.add(7);
                }
            }


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_script);
            Toolbar toolbar = (Toolbar) findViewById(R.id.edit_script_toolbar);

            //filling fields with loaded attributes
            scriptName = (EditText) findViewById(R.id.es_set_tiitle_sub);
            scriptName.setText(scriptNameToEdit);
            TextView timeTV = (TextView) this.findViewById(R.id.es_set_time_sub);
            if(time == -1) {
                timeTV.setText("---");
            }
            else{
                timeTV.setText(hour + ":" + minute);
            }

            TextView daysTV = (TextView) this.findViewById(R.id.es_set_day_sub);
            daysTV.setText("");
            if(days.isEmpty()){
                daysTV.setText("---");
            }
            else {
                for (int i = 0; i < days.size(); i++) {
                    Resources res = this.getResources();
                    daysTV.setText(daysTV.getText().toString() + res.getStringArray(R.array.days)[days.get(i)] + ",");
                }
            }

            TextView LocSubTV = (TextView) findViewById(R.id.es_set_location_sub);
            if(latitude == null){
                LocSubTV.setText("---");
            }
            else{
                LocSubTV.setText( Double.toString(latitude) + "  " + Double.toString(longitude));
            }

            TextView actionsTV = (TextView) this.findViewById(R.id.es_set_action_sub);
            actionsTV.setText("");
            if(actions.isEmpty()){
                actionsTV.setText("---");
            }
            else {
                for (int i = 0; i < actions.size(); i++) {
                    Resources res = this.getResources();
                    actionsTV.setText(actionsTV.getText().toString() + res.getStringArray(R.array.actions)[actions.get(i)] + ",");
                }
            }

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        }
    }


    /**
     * Method creating toolbar
     * @param menu
     * @return true if created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_script_toolbar, menu);
        return true;
    }

    /**
     * Method operating toolbar buttons clicked actions
     * @param item selected(clicked) item
     * @return true if button clicked
     */
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

                //Adding appropriate arguments to attributesList which is used in creating Xschm
                //Adding attribites which are needed in inference to ALSVlist
                //Setting values of Attributes, needed in loading models
                if (!(time == -1)) {
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
                if ((latitude != null) && (longitude != null)) {
                    attributesList.add(newModel.getAttribute("latitude"));
                    newModel.getAttribute("latitude").addValue(String.valueOf(latitude));
                    alsv = new ALSVExpression(newModel.getAttribute("latitude"), latitude.toString());
                    ALSVList.add(alsv);

                    attributesList.add(newModel.getAttribute("longitude"));
                    newModel.getAttribute("longitude").addValue(String.valueOf(longitude));
                    alsv = new ALSVExpression(newModel.getAttribute("longitude"), longitude.toString());
                    ALSVList.add(alsv);
                }

                //Adding appropriate arguments to attributesToSetList which is used in creating Xschm
                if (!actions.isEmpty()) {
                    for(Integer actionNumber : actions){ // TODO still need some changes
                        if(actionNumber < 2) attributesToSetList.add(newModel.getAttribute("bluetooth"));
                        if(actionNumber > 1 && actionNumber < 4) attributesToSetList.add(newModel.getAttribute("wifi"));
                        if(actionNumber > 3 && actionNumber < 7) attributesToSetList.add(newModel.getAttribute("sound"));
                        if(actionNumber==7) attributesToSetList.add(newModel.getAttribute("message"));
                    }

                    //Filling decisionList and actionList with appropriate content
                    final String[] actionsArray = {"off", "on", "vibration"};
                    for (Integer actionNumber : actions) {
                        if(actionNumber < 2){
                            newModel.getAttribute("bluetooth").addValue(actionsArray[actionNumber]);
                            decision = new DecisionExpression(newModel.getAttribute("bluetooth"), actionsArray[actionNumber]);
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.SetBluetooth");
                            actionList.add(action);
                        }
                        if(actionNumber > 1 && actionNumber < 4){
                            newModel.getAttribute("wifi").addValue(actionsArray[actionNumber-2]);
                            decision = new DecisionExpression(newModel.getAttribute("wifi"), actionsArray[actionNumber-2]);
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.SetWifi");
                            actionList.add(action);
                        }
                        if(actionNumber > 3 && actionNumber < 7){
                            newModel.getAttribute("sound").addValue(actionsArray[actionNumber-4]);
                            decision = new DecisionExpression(newModel.getAttribute("sound"), actionsArray[actionNumber-4]);
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.SetSound");
                            actionList.add(action);
                        }
                        if(actionNumber == 7){
                            newModel.getAttribute("message").addValue("notification");
                            decision = new DecisionExpression(newModel.getAttribute("message"), "notification");
                            decisionList.add(decision);
                            action = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.SendMessage");
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
                Toast.makeText(this, "Script added successfullly!", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    /**
     * DayOfWeekPickerFragment listener for positive button clicked
     * Saves selected items to private linked list.
     * @param dialog DayOfWeekPickerFragment dialog object
     */
    @Override
    public void onDialogDOWPFPositiveClick(DialogFragment dialog) {
        days = (LinkedList<Integer>) ((DayOfWeekPickerFragment) dialog).getDays().clone();
    }

    /**
     * DayOfWeekPickerFragment listener for negative button clicked
     * Saves selected items to private linked list.
     * @param dialog DayOfWeekPickerFragment dialog object
     */
    @Override
    public void onDialogDOWPFNegativeClick(DialogFragment dialog) {

    }

    /**
     * ActionPickerFragment listener for positive button clicked
     * Saves selected items to private linked list.
     * @param dialog ActionPickerFragment dialog object
     */
    @Override
    public void onDialogAPFPositiveClick(DialogFragment dialog) {
        actions = (LinkedList<Integer>) ((ActionPickerFragment) dialog).getActions().clone();
    }

    /**
     * ActionPickerFragment listener for negative button clicked
     * Saves selected items to private linked list.
     * @param dialog ActionPickerFragment dialog object
     */
    @Override
    public void onDialogAPFNegativeClick(DialogFragment dialog) {

    }

    /**
     * TimePickerFragment listener for positive button clicked
     * Saves selected items to private linked list.
     * @param dialog TimePickerFragment dialog object
     */
    @Override
    public void onDialogTPFPositiveClick(DialogFragment dialog) {
        hour = ((TimePickerFragment) dialog).getHour();
        minute = ((TimePickerFragment) dialog).getMinute();
        time = (double)hour + ((double)minute / 60);
    }

    /**
     * TimePickerFragment listener for negative button clicked
     * Saves selected items to private linked list.
     * @param dialog TimePickerFragment dialog object
     */
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

    /**
     * PlacePicker listener for result of the activity
     * Saves selected items to private double pools;
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                latitude = place.getLatLng().latitude;
                latitude = ((double)Math.round(latitude*1000)) / 1000;
                //latitude = Math.round(latitude);
                longitude = place.getLatLng().longitude;
                longitude = ((double)Math.round(longitude*1000)) / 1000;
                TextView LocSubTV = (TextView) findViewById(R.id.es_set_location_sub);
                LocSubTV.setText( Double.toString(latitude) + "  " + Double.toString(longitude));
            }
        }

    }
}
