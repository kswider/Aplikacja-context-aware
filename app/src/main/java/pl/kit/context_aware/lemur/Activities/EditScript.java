package pl.kit.context_aware.lemur.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.LinkedList;

import pl.kit.context_aware.lemur.ArrayAdapters.ActionsArrayAdapter;
import pl.kit.context_aware.lemur.ArrayAdapters.DaysArrayAdapter;
import pl.kit.context_aware.lemur.ArrayAdapters.LocationsArrayAdapter;
import pl.kit.context_aware.lemur.ArrayAdapters.TimesArrayAdapter;
import pl.kit.context_aware.lemur.DialogFragments.ActionPickerFragment;
import pl.kit.context_aware.lemur.DialogFragments.DatePickerFragment;
import pl.kit.context_aware.lemur.DialogFragments.DayOfWeekPickerFragment;
import pl.kit.context_aware.lemur.DialogFragments.NotificationMessageDetailsFragment;
import pl.kit.context_aware.lemur.DialogFragments.SMSMessageDetailsFragment;
import pl.kit.context_aware.lemur.DialogFragments.TimePickerFragment;
import pl.kit.context_aware.lemur.Editor.ModelCreator;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.ALSVExpression;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.ActionExpression;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.DecisionExpression;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xattr;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xrule;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xschm;
import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.ListItems.ActionItem;
import pl.kit.context_aware.lemur.ListItems.DayItem;
import pl.kit.context_aware.lemur.ListItems.LocationItem;
import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.ListItems.TimeItem;

public class EditScript extends AppCompatActivity implements DayOfWeekPickerFragment.NoticeDialogDOWPFListener
        , ActionPickerFragment.NoticeDialogAPFListener, TimePickerFragment.NoticeDialogTPFListener, SMSMessageDetailsFragment.NoticeSMSMessageDetailsFragmentListener
        , NotificationMessageDetailsFragment.NoticeNotificationMessageDetailsFragmentListener, DatePickerFragment.NoticeDialogDPFListener{

    String rememberedModelName; //used in deleting old models
    private int hour; //hour selected or loaded from file
    private int minute; //minute selected or loaded from file
    private double time; //time calculated with hour and minute
    private Double latitude; //latitude selected or loaded from file
    private Double longitude; //longitude selected or loaded from file
    private String notificationNumber; //used in deleting old files, when we edit model
    private String notificationTitle;
    private String notificationMessage; //TODO
    private String smsNumber; //used in deleting old files, when we edit model
    private String smsPhoneNumber;
    private String smsMessage; //TODO
    private LinkedList<Integer> daysCyclical = new LinkedList<>(); //list of daysCyclical selected or loaded from file
    private static final int PLACE_PICKER_REQUEST = 1; //variable needed for place picker
    private String scriptNameToLoad; // ??
    EditText scriptName; //Edit text field with script name
    TextView TVDaysCyclocal;
    private TimeItem tmpTimeInterval;

    private ListView listDays;
    private ListView listTime;
    private ListView listLocation;
    private ListView listAction;

    ArrayList<TimeItem> times;
    ArrayList<DayItem> days;
    ArrayList<LocationItem> locations;
    ArrayList<ActionItem> actions;

    TimesArrayAdapter timeAdapter;
    DaysArrayAdapter daysAdapter;
    LocationsArrayAdapter locationsAdapter;
    ActionsArrayAdapter actionsAdapter;

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
        ((TimePickerFragment) newFragment).setPosition(-1);
        newFragment.show(getFragmentManager(), "Time Picker");

    }

    /**
     * Action for the Set Time Interval Clicked
     * Opens TimePickerFragment with previously selected items checked
     * @param v current View
     */
    public void SetTimeIntervalOnClick(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setPosition(-1);
        ((TimePickerFragment) newFragment).setTypeInterval(2);
        newFragment.show(getFragmentManager(), "Time Picker");
        DialogFragment newFragment1 = new TimePickerFragment();
        ((TimePickerFragment) newFragment1).setPosition(-1);
        ((TimePickerFragment) newFragment1).setTypeInterval(1);
        newFragment1.show(getFragmentManager(), "Time Picker");

    }

    /**
     * Action for the Set Day Clicked
     * Opens DayOfWeekPickerFragment with previously selected items checked
     * @param v current View
     */
    public void SetDayOnClick(View v) {
        DialogFragment newFragment = new DayOfWeekPickerFragment();
        ((DayOfWeekPickerFragment) newFragment).setPosition(-1);
        ((DayOfWeekPickerFragment) newFragment).setDaysOfWeek(daysCyclical);
        newFragment.show(getFragmentManager(), "DayOfWeek Picker");
    }

    /**
     * Action for the Set Action Clicked
     * Opens SetActionPickerFragment with previously selected items checked
     * @param v current View
     */
    public void SetActionOnClick(View v) {
        DialogFragment newFragment = new ActionPickerFragment();
        ((ActionPickerFragment)newFragment).setPosition(-1);
        newFragment.show(getFragmentManager(), "Action Picker");
    }

    public void SetDateOnClick(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment)newFragment).setPosition(-1);
        newFragment.show(getFragmentManager(), "Date Picker");
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
            int PLACE_PICKER_REQUEST = -1;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_script);
        Intent intent = getIntent();

        listDays = (ListView)findViewById(R.id.es_lv_days);
        listTime = (ListView)findViewById(R.id.es_lv_time);
        listLocation = (ListView)findViewById(R.id.es_lv_location);
        listAction = (ListView)findViewById(R.id.es_lv_actions);



        days = new ArrayList<DayItem>();
        daysAdapter = new DaysArrayAdapter(this, days);
        listDays.setAdapter(daysAdapter);
        ListUtils.setDynamicHeight(listDays);

        times = new ArrayList<TimeItem>();
        timeAdapter = new TimesArrayAdapter(this, times);
        listTime.setAdapter(timeAdapter);
        ListUtils.setDynamicHeight(listTime);

        locations = new ArrayList<LocationItem>();
        locationsAdapter = new LocationsArrayAdapter(this,locations);
        listLocation.setAdapter(locationsAdapter);
        ListUtils.setDynamicHeight(listLocation);

        actions = new ArrayList<ActionItem>();
        actionsAdapter = new ActionsArrayAdapter(this, actions);
        listAction.setAdapter(actionsAdapter);
        ListUtils.setDynamicHeight(listAction);

        String scriptNameToEdit = intent.getExtras().getString("eFileName");
        rememberedModelName = scriptNameToEdit;

        TVDaysCyclocal = (TextView) findViewById(R.id.es_repeating_days);

        hour = -1;
        minute = -1;
        time = -1;
        latitude = null;
        longitude = null;
        notificationNumber = "";
        notificationTitle = "tytul test";
        notificationMessage = "zobaczymy czy dziala"; //TODO
        smsNumber = "";
        smsPhoneNumber = "";
        smsMessage = ""; // TODO
        //if we are creating new model
        if(scriptNameToEdit.isEmpty()) {
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

            if(!loadedModel.getAttribute("notification").getValues().isEmpty()){ // TODO
                notificationNumber = loadedModel.getAttribute("notificationNumber").getValues().pop();
                notificationTitle = loadedModel.getAttribute("notification").getValues().getFirst();
                notificationMessage = loadedModel.getAttribute("notification").getValues().getLast();
            }
            if(!loadedModel.getAttribute("sms").getValues().isEmpty()){ // TODO
                smsNumber = loadedModel.getAttribute("smsNumber").getValues().pop();
                smsPhoneNumber = loadedModel.getAttribute("sms").getValues().getFirst();
                smsMessage = loadedModel.getAttribute("sms").getValues().getLast();
            }

            final String[] daysOfWeekArray = this.getResources().getStringArray(R.array.days_short);

            for (String day : loadedModel.getAttribute("day").getValues()){
                for(int i = 0; i<daysOfWeekArray.length;++i){
                    if(daysOfWeekArray[i].equals(day)){
                        daysCyclical.add(i);
                    }
                }
            }

            /* TODO
            final String[] argumentsArray = {"bluetooth","wifi","sound","notification","sms"}; //TODO message, it will be different when we implement setting action differently
            final String[] stateArray = {"off", "on", "vibration"};
            for(int j = 0; j < argumentsArray.length; ++j) {
                for (String action : loadedModel.getAttribute(argumentsArray[j]).getValues()) {
                    for (int i = 0; i < stateArray.length; ++i) {
                        if (stateArray[i].equals(action)) {
                            actions.add(i + j*2);
                        }
                    }
                    if (j == 3) {
                        actions.add(7); //TODO
                        break;
                    }
                    else if (j == 4){
                        actions.add(8); //TODO
                        break;
                    }
                }
            }
        */
            Toolbar toolbar = (Toolbar) findViewById(R.id.edit_script_toolbar);



            //filling fields with loaded attributes
            scriptName = (EditText) findViewById(R.id.es_set_tiitle_sub);
            scriptName.setText(scriptNameToEdit);

            //TODO!!!
            if(notificationMessage.equals("")){
                //notificationTitleTV.setText("");
                //notificationMessageTV.setText("");
            }
            else {
                //notificationTitleTV.setText(notificationTitle);
                //notificationMessageTV.setText(notificationMessage);
            }
            if(smsMessage.equals("")){
                //smsNumberTV.setText("");
                //smsMessageTV.setText("");
            }
            else {
                //smsNumberTV.setText(smsNumber);
                //smsNumberTV.setText(smsMessage);
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
                ALSVExpression alsvExpression = null;
                DecisionExpression decisionExpression = null;
                ActionExpression actionExpression = null;

                //Creatung list of rules
                LinkedList<Xrule> rulesList = new LinkedList<>();

                if(!actions.isEmpty()){
                    boolean notificationToSend = false;
                    boolean smsToSend = false;
                    for(ActionItem action : actions){
                        int actionType = action.getActionType();
                        switch(actionType){
                            case ActionItem.ACTION_BLUETOOTH_ON:
                                attributesToSetList.add(newModel.getAttribute("bluetooth"));
                                newModel.getAttribute("bluetooth").addValue("on");
                                decisionExpression = new DecisionExpression(newModel.getAttribute("bluetooth"), "on");
                                decisionList.add(decisionExpression);
                                actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setBluetooth");
                                actionList.add(actionExpression);
                                break;
                            case ActionItem.ACTION_BLUETOOTH_OFF:
                                attributesToSetList.add(newModel.getAttribute("bluetooth"));
                                newModel.getAttribute("bluetooth").addValue("off");
                                decisionExpression = new DecisionExpression(newModel.getAttribute("bluetooth"), "off");
                                decisionList.add(decisionExpression);
                                actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setBluetooth");
                                actionList.add(actionExpression);
                                break;
                            case ActionItem.ACTION_WIFI_ON:
                                attributesToSetList.add(newModel.getAttribute("wifi"));
                                newModel.getAttribute("wifi").addValue("on");
                                decisionExpression = new DecisionExpression(newModel.getAttribute("wifi"), "on");
                                decisionList.add(decisionExpression);
                                actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setWifi");
                                actionList.add(actionExpression);
                                break;
                            case ActionItem.ACTION_WIFI_OFF:
                                attributesToSetList.add(newModel.getAttribute("wifi"));
                                newModel.getAttribute("wifi").addValue("off");
                                decisionExpression = new DecisionExpression(newModel.getAttribute("wifi"), "off");
                                decisionList.add(decisionExpression);
                                actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setWifi");
                                actionList.add(actionExpression);
                                break;
                            case ActionItem.ACTION_SOUND_OFF:
                                attributesToSetList.add(newModel.getAttribute("sound"));
                                newModel.getAttribute("sound").addValue("off");
                                decisionExpression = new DecisionExpression(newModel.getAttribute("sound"), "off");
                                decisionList.add(decisionExpression);
                                actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setSound");
                                actionList.add(actionExpression);
                                break;
                            case ActionItem.ACTION_SOUND_ON:
                                attributesToSetList.add(newModel.getAttribute("sound"));
                                newModel.getAttribute("sound").addValue("on");
                                decisionExpression = new DecisionExpression(newModel.getAttribute("sound"), "on");
                                decisionList.add(decisionExpression);
                                actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setSound");
                                actionList.add(actionExpression);
                                break;
                            case ActionItem.ACTION_VIBRATIONS_MODE:
                                attributesToSetList.add(newModel.getAttribute("sound"));
                                newModel.getAttribute("sound").addValue("vibration");
                                decisionExpression = new DecisionExpression(newModel.getAttribute("sound"), "vibration");
                                decisionList.add(decisionExpression);
                                actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.setSound");
                                actionList.add(actionExpression);
                                break;
                            case ActionItem.ACTION_SEND_NOTIFICATION:
                                notificationToSend = true;
                                newModel.getAttribute("notification").addValue(action.getNotificationTitle());
                                newModel.getAttribute("notification").addValue(action.getNotificationMessage());
                                break;
                            case ActionItem.ACTION_SEND_SMS:
                                smsToSend = true;
                                newModel.getAttribute("sms").addValue(action.getSmsPhoneNumber());
                                newModel.getAttribute("sms").addValue(action.getSmsMessage());
                                break;
                        }
                    }
                    if(notificationToSend){
                        attributesToSetList.add(newModel.getAttribute("notification"));
                        attributesToSetList.add(newModel.getAttribute("notificationNumber"));
                        decisionExpression = new DecisionExpression(newModel.getAttribute("notification"), "sent");
                        decisionList.add(decisionExpression);
                        actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.sendNotification");
                        actionList.add(actionExpression);

                        if(!notificationNumber.equals("")){
                            FilesOperations.deleteNotification(this,notificationNumber); //deleting old notification before we create new one
                        }
                        notificationNumber = FilesOperations.createNotification(this,newModel.getAttribute("notification").getValues());
                        newModel.getAttribute("notificationNumber").addValue(notificationNumber);
                        decisionExpression = new DecisionExpression(newModel.getAttribute("notificationNumber"), notificationNumber);
                        decisionList.add(decisionExpression);
                    }
                    if(smsToSend){
                        attributesToSetList.add(newModel.getAttribute("sms"));
                        attributesToSetList.add(newModel.getAttribute("smsNumber"));
                        decisionExpression = new DecisionExpression(newModel.getAttribute("sms"), "sent");
                        decisionList.add(decisionExpression);
                        actionExpression = new ActionExpression("pl.kit.context_aware.lemur.HeartDROID.actions.sendSMS");
                        actionList.add(actionExpression);

                        if(!smsNumber.equals("")){
                            FilesOperations.deleteSMS(this,smsNumber); //deleting old notification before we create new one
                        }
                        smsNumber = FilesOperations.createNotification(this,newModel.getAttribute("sms").getValues());
                        newModel.getAttribute("smsNumber").addValue(smsNumber);
                        decisionExpression = new DecisionExpression(newModel.getAttribute("smsNumber"), smsNumber);
                        decisionList.add(decisionExpression);
                    }

                }
                //Adding appropriate arguments to attributesList which is used in creating Xschm
                //Adding attribites which are needed in inference to ALSVlist
                //Setting values of Attributes, needed in loading models
                if (!(times.isEmpty())) {
                    attributesList.add(newModel.getAttribute("time"));
                    LinkedList<String> timesList = new LinkedList<>();

                    for(TimeItem time : times){
                        newModel.getAttribute("hour").addValue(String.valueOf(time.getHours()));
                        newModel.getAttribute("minute").addValue(String.valueOf(time.getMinutes()));
                        String timeString =  String.valueOf(((double)time.getHours() + ((double)time.getMinutes() / 60)));
                        newModel.getAttribute("time").addValue(timeString);

                        timesList.add(timeString); // Adding times to list, which will be used in ALSV expression
                    }

                    alsvExpression = new ALSVExpression(newModel.getAttribute("time"), timesList);
                    ALSVList.add(alsvExpression);
                }
                if (!daysCyclical.isEmpty()) {
                    attributesList.add(newModel.getAttribute("day"));

                    final String[] daysOfWeekArray = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
                    LinkedList<String> selectedDays = new LinkedList<>();
                    for (Integer day : daysCyclical) {
                        newModel.getAttribute("day").addValue(daysOfWeekArray[day]);
                        selectedDays.add(daysOfWeekArray[day]);
                    }
                    alsvExpression = new ALSVExpression(newModel.getAttribute("day"), selectedDays);
                    ALSVList.add(alsvExpression);
                }
                if (!days.isEmpty()){
                    attributesList.add(newModel.getAttribute("dayFromCalendar"));

                    LinkedList<String> selectedDays = new LinkedList<>();

                    for(DayItem day : days){
                        String selectedDay = String.format("%4d",day.getYear()) + String.format("%02d",day.getMonth()) + String.format("%02d",day.getDay());
                        newModel.getAttribute("dayFromCalendar").addValue(selectedDay);
                        selectedDays.add(selectedDay);
                    }
                    alsvExpression = new ALSVExpression(newModel.getAttribute("dayFromCalendar"), selectedDays);
                    ALSVList.add(alsvExpression);
                }

                if (!locations.isEmpty()) {
                    attributesList.add(newModel.getAttribute("latitude"));
                    attributesList.add(newModel.getAttribute("longitude"));

                    // Creating and adding scheme to model
                    Xschm scheme = new Xschm("SetEverything", attributesList, attributesToSetList);
                    newModel.addScheme(scheme);
                    int numberOFRule = 1;
                    for(LocationItem location : locations){
                        newModel.getAttribute("latitude").addValue(String.valueOf(location.getLatitude()));
                        newModel.getAttribute("longitude").addValue(String.valueOf(location.getLongitude()));

                        alsvExpression = new ALSVExpression(newModel.getAttribute("latitude"), String.valueOf(location.getLatitude()));
                        ALSVList.add(alsvExpression);
                        alsvExpression = new ALSVExpression(newModel.getAttribute("longitude"), String.valueOf(location.getLongitude()));
                        ALSVList.add(alsvExpression);

                        Xrule rule = new Xrule(scheme, numberOFRule, ALSVList, decisionList, actionList);
                        rulesList.add(rule);

                        ALSVList.removeLast();
                        ALSVList.removeLast();
                        numberOFRule++;
                    }
                }
                else{
                    // Creating and adding scheme to model
                    Xschm scheme = new Xschm("SetEverything", attributesList, attributesToSetList);
                    newModel.addScheme(scheme);
                    //Creating only one rule if no locations are selected
                    Xrule rule = new Xrule(scheme, 1, ALSVList, decisionList, actionList);
                    rulesList.add(rule);
                }

                //Adding rules to the model
                for(Xrule rule : rulesList){
                    newModel.addRule(rule);
                }
                //Saving the model
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
        String [] daysStr = this.getResources().getStringArray(R.array.days_short);
        daysCyclical = ((DayOfWeekPickerFragment) dialog).getDays();
        TVDaysCyclocal.setText("");
        for(int i=0;i<daysCyclical.size();i++){
            if(TVDaysCyclocal.getText().equals("")) TVDaysCyclocal.setText(daysStr[daysCyclical.get(i)]);
            else TVDaysCyclocal.setText(TVDaysCyclocal.getText()+","+daysStr[daysCyclical.get(i)]);
        }

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
        if(((ActionPickerFragment)dialog).getPosition() == -1){
            actions.add(new ActionItem("","",((ActionPickerFragment)dialog).getActions()));
        } else{
            actions.get(((ActionPickerFragment)dialog).getPosition()).setActionType(((ActionPickerFragment)dialog).getActions());
        }
        actionsAdapter.notifyDataSetChanged();
        ListUtils.setDynamicHeight(listAction);
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
        if(((TimePickerFragment) dialog).getPosition() == -1) {
            if (((TimePickerFragment) dialog).getTypeInterval() == 0)
                times.add(new TimeItem(((TimePickerFragment) dialog).getHour(), ((TimePickerFragment) dialog).getMinute(), ((TimePickerFragment) dialog).getHour(), ((TimePickerFragment) dialog).getMinute()));
            else if (((TimePickerFragment) dialog).getTypeInterval() == 1) {
                tmpTimeInterval = new TimeItem(((TimePickerFragment) dialog).getHour(), ((TimePickerFragment) dialog).getMinute(), -1, -1);
            } else if (((TimePickerFragment) dialog).getTypeInterval() == 2) {
                tmpTimeInterval.setHoursEnd(((TimePickerFragment) dialog).getHour());
                tmpTimeInterval.setMinutesEnd(((TimePickerFragment) dialog).getMinute());
                if(tmpTimeInterval.isRightIntervalType()) times.add(tmpTimeInterval);
                else Toast.makeText(getBaseContext(),getBaseContext().getResources().getString(R.string.es_WrongInterval),Toast.LENGTH_LONG).show();
            }
        }else{
            if(((TimePickerFragment)dialog).getTypeInterval() == 0) {
                times.get(((TimePickerFragment) dialog).getPosition()).setHours(((TimePickerFragment) dialog).getHour());
                times.get(((TimePickerFragment) dialog).getPosition()).setMinutes(((TimePickerFragment) dialog).getMinute());
                times.get(((TimePickerFragment) dialog).getPosition()).setHoursEnd(((TimePickerFragment) dialog).getHour());
                times.get(((TimePickerFragment) dialog).getPosition()).setMinutesEnd(((TimePickerFragment) dialog).getMinute());
            }else if(((TimePickerFragment)dialog).getTypeInterval() == 1) {
                tmpTimeInterval = new TimeItem(times.get(((TimePickerFragment) dialog).getPosition()));
                times.get(((TimePickerFragment) dialog).getPosition()).setHours(((TimePickerFragment) dialog).getHour());
                times.get(((TimePickerFragment) dialog).getPosition()).setMinutes(((TimePickerFragment) dialog).getMinute());
            }
            else if(((TimePickerFragment)dialog).getTypeInterval() == 2) {
                times.get(((TimePickerFragment) dialog).getPosition()).setHoursEnd(((TimePickerFragment) dialog).getHour());
                times.get(((TimePickerFragment) dialog).getPosition()).setMinutesEnd(((TimePickerFragment) dialog).getMinute());
                if(!times.get(((TimePickerFragment) dialog).getPosition()).isRightIntervalType()){
                    times.get(((TimePickerFragment) dialog).getPosition()).setHours(tmpTimeInterval.getHours());
                    times.get(((TimePickerFragment) dialog).getPosition()).setMinutes(tmpTimeInterval.getMinutes());
                    times.get(((TimePickerFragment) dialog).getPosition()).setHoursEnd(tmpTimeInterval.getHoursEnd());
                    times.get(((TimePickerFragment) dialog).getPosition()).setMinutesEnd(tmpTimeInterval.getMinutesEnd());
                    Toast.makeText(this,this.getResources().getString(R.string.es_WrongInterval),Toast.LENGTH_LONG).show();
                }
            }
        }
        timeAdapter.notifyDataSetChanged();
        ListUtils.setDynamicHeight(listTime);
    }

    /**
     * TimePickerFragment listener for negative button clicked
     * Saves selected items to private linked list.
     * @param dialog TimePickerFragment dialog object
     */
    @Override
    public void onDialogTPFNegativeClick(DialogFragment dialog) {
        if(((TimePickerFragment)dialog).getTypeInterval() != 0){
            times.remove(times.size()-1);
        }
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
            if (resultCode == RESULT_OK) {
                double latitude,longitude;
                Place place = PlacePicker.getPlace(data, this);
                latitude = place.getLatLng().latitude;
                latitude = ((double)Math.round(latitude*1000)) / 1000;
                longitude = place.getLatLng().longitude;
                longitude = ((double)Math.round(longitude*1000)) / 1000;

                if(requestCode == -1) locations.add(new LocationItem(latitude,longitude));
                if(requestCode != -1){
                    locations.get(requestCode).setLatitude(latitude);
                    locations.get(requestCode).setLongitude(longitude);
                }
                locationsAdapter.notifyDataSetChanged();
                ListUtils.setDynamicHeight(listLocation);
            }

    }

    @Override
    public void onSMSMessageDetailsFragmentPositiveClick(DialogFragment dialog) {
        if(((SMSMessageDetailsFragment)dialog).getPosition() == -1){
            ActionItem sms = new ActionItem();
            sms.setSmsPhoneNumber(((SMSMessageDetailsFragment)dialog).getPhoneNo());
            sms.setSmsMessage(((SMSMessageDetailsFragment)dialog).getMessage());
            sms.setActionType(ActionItem.ACTION_SEND_SMS);
            sms.setSubText(sms.getSmsPhoneNumber()+":\n"+sms.getSmsMessage());
            actions.add(sms);
        }else{
            actions.get(((SMSMessageDetailsFragment)dialog).getPosition()).setSmsMessage(((SMSMessageDetailsFragment)dialog).getMessage());
            actions.get(((SMSMessageDetailsFragment)dialog).getPosition()).setSmsPhoneNumber(((SMSMessageDetailsFragment)dialog).getPhoneNo());
            actions.get(((SMSMessageDetailsFragment)dialog).getPosition()).setSubText(((SMSMessageDetailsFragment)dialog).getPhoneNo()+":\n"+((SMSMessageDetailsFragment)dialog).getMessage());
        }
        actionsAdapter.notifyDataSetChanged();
        ListUtils.setDynamicHeight(listAction);
    }

    @Override
    public void onSMSMessageDetailsFragmentNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onNotificationMessageDetailsFragmentPositiveClick(DialogFragment dialog) {
        if(((NotificationMessageDetailsFragment)dialog).getPosition() == -1){
            ActionItem noti = new ActionItem();
            noti.setNotificationMessage(((NotificationMessageDetailsFragment)dialog).getMessage());
            noti.setNotificationTitle(((NotificationMessageDetailsFragment)dialog).getnotiTitle());
            noti.setActionType(ActionItem.ACTION_SEND_NOTIFICATION);
            noti.setSubText(noti.getNotificationTitle()+"\n"+noti.getNotificationMessage());
            actions.add(noti);
        }else{
            actions.get(((NotificationMessageDetailsFragment)dialog).getPosition()).setNotificationMessage(((NotificationMessageDetailsFragment)dialog).getMessage());
            actions.get(((NotificationMessageDetailsFragment)dialog).getPosition()).setNotificationTitle(((NotificationMessageDetailsFragment)dialog).getnotiTitle());
            actions.get(((NotificationMessageDetailsFragment)dialog).getPosition()).setSubText(((NotificationMessageDetailsFragment)dialog).getnotiTitle() +"\n"+((NotificationMessageDetailsFragment)dialog).getMessage());
        }
        actionsAdapter.notifyDataSetChanged();
        ListUtils.setDynamicHeight(listAction);
    }

    @Override
    public void onNotificationMessageDetailsFragmentNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogDPFPositiveClick(DialogFragment dialog) {
        if(((DatePickerFragment)dialog).getPosition() == -1){
            days.add(new DayItem(((DatePickerFragment)dialog).getDay(),((DatePickerFragment)dialog).getMonth(),((DatePickerFragment)dialog).getYear()));
        }else{
            days.get(((DatePickerFragment)dialog).getPosition()).setDay(((DatePickerFragment)dialog).getDay());
            days.get(((DatePickerFragment)dialog).getPosition()).setMonth(((DatePickerFragment)dialog).getMonth());
            days.get(((DatePickerFragment)dialog).getPosition()).setYear(((DatePickerFragment)dialog).getYear());
        }
        daysAdapter.notifyDataSetChanged();
        ListUtils.setDynamicHeight(listDays);
    }

    @Override
    public void onDialogDPFNegativeClick(DialogFragment dialog) {

    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
