package pl.kit.context_aware.lemur.Activities;

        import android.Manifest;
        import android.app.ActivityManager;
        import android.app.AlarmManager;
        import android.app.DialogFragment;
        import android.app.Notification;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.view.View;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.MenuItem;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;

        import pl.kit.context_aware.lemur.DialogFragments.DeleteScriptFragment;
        import pl.kit.context_aware.lemur.DialogFragments.ImportExportFragment;
        import pl.kit.context_aware.lemur.DialogFragments.NotificationMessageDetailsFragment;
        import pl.kit.context_aware.lemur.DialogFragments.SMSMessageDetailsFragment;
        import pl.kit.context_aware.lemur.DialogFragments.ScriptsToExportPickerFragment;
        import pl.kit.context_aware.lemur.DialogFragments.ScriptsToImportPickerFragment;
        import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
        import pl.kit.context_aware.lemur.HeartDROID.HeartAlarmReceiver;
        import pl.kit.context_aware.lemur.HeartDROID.Inference;
        import pl.kit.context_aware.lemur.PhoneActions.BluetoothManager;
        import pl.kit.context_aware.lemur.PhoneActions.ConnectionManager;
        import pl.kit.context_aware.lemur.PhoneActions.RingModes;
        import pl.kit.context_aware.lemur.PhoneActions.SendNotification;
        import pl.kit.context_aware.lemur.PhoneActions.SendSMS;
        import pl.kit.context_aware.lemur.R;
        import pl.kit.context_aware.lemur.Readers.ReadLocation;
        import pl.kit.context_aware.lemur.Readers.ReadTime;
        import pl.kit.context_aware.lemur.Services.MainForegroundService;
        import pl.kit.context_aware.lemur.TmpTests.TestService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScriptsToExportPickerFragment.NoticeDialogSTEFListener,
        ScriptsToImportPickerFragment.NoticeDialogSTIFListener, DeleteScriptFragment.NoticeDialogDSFListener, SMSMessageDetailsFragment.NoticeSMSMessageDetailsFragmentListener,NotificationMessageDetailsFragment.NoticeNotificationMessageDetailsFragmentListener  {

    LinkedList<String> ScriptsToExport = null; //List of scripts to export after appropriate button clicked
    LinkedList<String> ScriptsToImport = null; //List of scripts to import after appropriate button clicked

    /**
     * Action for Silent Button Clicked (part of ActionsTests Fragment)
     * Turns phone into silent mode
     * @param v current view.
     */
    public void SilentButtonOnClick(View v){
        RingModes.silentMode(this);
    }

    /**
     * Action for Vibration Button Clicked (part of ActionsTests Fragment)
     * Turns phone into vibration mode
     * @param v current view.
     */
    public void VibrationButtonOnClick(View v){
        RingModes.vibrationsMode(this);
    }

    /**
     * Action for Normal Ring Mode Button Clicked (part of ActionsTests Fragment)
     * Turns phone into normal ring mode
     * @param v current view.
     */
    public void NormalRingButtonOnClick(View v){
        RingModes.normalMode(this);
    }

    /**
     * Action for Send Notifocation Button Clicked (part of ActionsTests Fragment)
     * Sends Notification to user
     * @param v current view.
     */
    public void NotificationButtonOnClick(View v){
        SendNotification.sendNotification(this,5,getResources().getString(R.string.tmp_message_main),getResources().getString(R.string.tmp_message_sub));
    }

    public void NotificationExtendedButtonOnClick(View v){
        DialogFragment newFragment = new NotificationMessageDetailsFragment();
        newFragment.show(getFragmentManager(), "NotificationExtended");
    }

    /**
     * Action for Send Turn On WiFi Button Clicked (part of ActionsTests Fragment)
     * Turns WiFi On
     * @param v current view.
     */
    public void TurnOnWiFiButtonOnClick(View v){
        ConnectionManager.turnOnWiFi(this);
    }

    /**
     * Action for Turn Off WiFi Button Clicked (part of ActionsTests Fragment)
     * Turns WiFi Off
     * @param v current view.
     */
    public void TurnOffWiFiButtonOnClick(View v){
        ConnectionManager.turnOffWiFi(this);
    }

    /**
     * Action for Turn On Bluetooth Button Clicked (part of ActionsTests Fragment)
     * Turns On Bluetooth
     * @param v current view.
     */
    public void TurnOnBluetoothButtonOnClick(View v){
        BluetoothManager.turnOnBluetooth();
    }

    /**
     * Action for Turn Off Bluetooth Button Clicked (part of ActionsTests Fragment)
     * Turns Off Bluetooth
     * @param v current view.
     */
    public void TurnOffBluetoothButtonOnClick(View v){
        BluetoothManager.turnOffBluetooth();
    }

    /**
     * Action for SendSMS button Clicked (part of ActionsTests Fragment)
     * Sends test sms
     * @param v current view.
     */
    public void SendSMSOnClick(View v){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SendSMS.sendMessage(this, "889871414", "siemka :)");
        }else{
            Toast.makeText(this,this.getResources().getString(R.string.pl_send_sms),Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Action for SendSMS button Clicked (part of ActionsTests Fragment)
     * Sends test sms
     * @param v current view.
     */
    public void SendSMSExtendedOnClick(View v) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            DialogFragment newFragment = new SMSMessageDetailsFragment();
            newFragment.show(getFragmentManager(), "SendSMSExtended");
        } else {
            Toast.makeText(this, this.getResources().getString(R.string.pl_send_sms), Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Action for Read Time Button Clicked (part of ReadersTests Fragment)
     * Shows Toast with current time
     * @param v current view.
     */
    public void ReadTimeButtonOnClick(View v){
        Toast.makeText(this,ReadTime.ReadFullTime(),Toast.LENGTH_SHORT).show();
    }

    /**
     * Action for Read Location Button Clicked (part of ReadersTests Fragment)
     * Shows Toast with current location
     * @param v current view.
     */
    public void ReadLocationButtonOnClick(View v){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            ReadLocation rl = new ReadLocation();
            Toast.makeText(this, rl.readLocationByBest(this), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,this.getResources().getString(R.string.pl_fine_location),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Action for Start Service Button Clicked (part of OtherTests Fragment)
     * starts background service which counts to 10 on Toasts
     * @param v current view.
     */
    public void startServiceButtonOnClick(View v){
        startService(new Intent(getBaseContext(), TestService.class));
    }

    /**
     * Action for Stop Service Button Clicked (part of OtherTests Fragment)
     * stops service if is going in background
     * @param v current view.
     */
    public void stopServiceButtonOnClick(View v){
        stopService(new Intent(getBaseContext(), TestService.class));
    }

    /**
     * Action for runSimModel Button Clicked (part of OtherTests Fragment)
     * Runs all models
     * @param v current view.
     */
    public void runSimModelButtonOnClick(View v){
        Inference inference = new Inference(this);
        for(String scriptName : FilesOperations.getAllModelNames(this)) {
            inference.runInference(this.getFilesDir() + "/" + scriptName + ".hmr");
        }
    }

    /**
     * Action for sheduleHeartEvery5 Button Clicked (part of OtherTests Fragment)
     * Runs all models every 1 minute
     * @param v current view.
     */
    public void sheduleHeartEvery5MinutesOnClick(View v){

        int i = 30;
        Intent intent = new Intent(this, HeartAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), (i * 1000), pendingIntent);
        Toast.makeText(this, getResources().getString(R.string.schedulde_1) + i + getResources().getString(R.string.schedulde_2),
                Toast.LENGTH_LONG).show();
    }

    /**
     * Action for Import Scripts Button Clicked (part of ImportExportScripts Fragment)
     * Checks for permissions to external storage, then opens dialog with list of scripts to import.
     * Importing action is implemented in method called after PositiveButton Clicked (onDialogSTIFPositiveClick)
     * @param v current view.
     */
    public void ImportScriptButtonOnClick(View v){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            DialogFragment newFragment = new ScriptsToImportPickerFragment();
            newFragment.show(getFragmentManager(), "Import Scripts List Fragment");
        }
        else{
            Toast.makeText(this,this.getResources().getString(R.string.pl_write_external),Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    /**
     * Action for Export Scripts Button Clicked (part of ImportExportScripts Fragment)
     * Checks for permissions to external storage, then opens dialog with list of scripts to export.
     * Importing action is implemented in method called after PositiveButton Clicked (onDialogSTEFPositiveClick)
     * @param v current view.
     */
    public void ExportScriptButtonOnClick(View v){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
        DialogFragment newFragment = new ScriptsToExportPickerFragment();
        newFragment.show(getFragmentManager(), "Export Scripts List Fragment");
        }
        else{
            Toast.makeText(this,this.getResources().getString(R.string.pl_write_external),Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    /**
     * Action for floating button clicked (part of ScriptsList Fragment)
     * Starts new Activity which can be used to create scripts
     * @param v current view.
     */
    public void floatingButtonOnClick(View v){
        Bundle eFileName = new Bundle();
        eFileName.putString("eFileName","");

        Intent intent = new Intent(v.getContext(),EditScript.class);
        intent.putExtras(eFileName);
        startActivity(intent);
    }


    /**
     * Method called always when this Activity is created (= when app start)
     * Checks permissions and ask for missing
     * Loads default fragment to the main container (ScriptsLists)
     * Loads toolbar
     * Loads NavigationView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //list of missing permissions
        ArrayList<String> permissionsNeeded = new ArrayList<String>();

        //checks for permissions and adds them to the list if missing
        if (ContextCompat.checkSelfPermission(this.getApplication(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){

            permissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(this.getApplication(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){

            permissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (ContextCompat.checkSelfPermission(this.getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this.getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        //Asks for missing premissions
        if(permissionsNeeded.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    permissionsNeeded.toArray((new String[permissionsNeeded.size()])),
                    1);
        }

        //Checks if foreground service is already working and if not, starts it
        ActivityManager manager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        Boolean isAlreadyRunning = false;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (MainForegroundService.class.getName().equals(service.service.getClassName())) {
                isAlreadyRunning = true;
            }
        }
        if(!isAlreadyRunning) startService(new Intent(getBaseContext(), MainForegroundService.class));


        //loads layout objects
        ScriptsList sl = new ScriptsList();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,sl);
        fragmentTransaction.commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * method called after back is pressed.
     * closes NavigationView
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * method called after one of items from navigation view is selected
     * handle different action depending which item was selected
     * @param item selected item
     * @return true after success
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.MyScripts) {
             ScriptsList sl = new ScriptsList();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,sl);
             fragmentTransaction.commit();
        } else if (id == R.id.IEScripts){
             ImportExportFragment ief = new ImportExportFragment();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,ief);
             fragmentTransaction.commit();
         } else if (id == R.id.Settings){
             Intent intent = new Intent();
             intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
             Uri uri = Uri.fromParts("package", getPackageName(), null);
             intent.setData(uri);
             startActivity(intent);
         } else if (id == R.id.AboutApp){
             AboutApp aa = new AboutApp();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,aa);
             fragmentTransaction.commit();
         } else if (id == R.id.RateApp){
             Intent intent = new Intent(Intent.ACTION_VIEW);
             intent.setData(Uri.parse("market://details?id=com.android.app"));
             try{
                 startActivity(intent);
             }
             catch(Exception e){}
         } else if (id == R.id.Readers){
             ReadersTests rt = new ReadersTests();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,rt);
             fragmentTransaction.commit();
         } else if (id == R.id.Actions){
             ActionsTests at = new ActionsTests();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,at);
             fragmentTransaction.commit();
         } else if (id == R.id.Others) {
             OtherTests ot = new OtherTests();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,ot);
             fragmentTransaction.commit();
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * method called after positive button is clicked in ScriptsToExportFragment
     * Exports selected scripts to external storage.
     * @param dialog dialog in which button was clicked
     */
    @Override
    public void onDialogSTEFPositiveClick(DialogFragment dialog) {
        ScriptsToExport = (LinkedList<String>)((ScriptsToExportPickerFragment) dialog).getSelectedScripts().clone();
        for(String script : ScriptsToExport){
            FilesOperations.exportModel(this,script);
        }
        Toast.makeText(this, getResources().getString(R.string.exported),
                Toast.LENGTH_LONG).show();
    }

    /**
     * method called after positive button is clicked in ScriptsToImportFragment
     * Imports selected scripts to internal storage.
     * @param dialog dialog in which button was clicked
     */
    @Override
    public void onDialogSTIFPositiveClick(DialogFragment dialog) {
        ScriptsToImport = (LinkedList<String>)((ScriptsToImportPickerFragment)dialog).getSelectedScripts().clone();
        for(String script : ScriptsToImport){
            FilesOperations.importModel(this,script);
        }
        Toast.makeText(this, getResources().getString(R.string.imported),
                Toast.LENGTH_LONG).show();
    }

    /**
     * method called after negative button is clicked in DeleteScriptFragment
     * Delets selected script
     * @param dialog dialog in which button was clicked
     */
    @Override
    public void onDialogDSFPositiveClick(DialogFragment dialog) {
        ScriptsList sl = new ScriptsList();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,sl);
        fragmentTransaction.commit();
    }

    /**
     * SMSMessageDetails listener for positive button clicked
     * saves number and message and refreshes list
     * @param dialog SMSMessageDetailsFragment object
     */
    @Override
    public void onSMSMessageDetailsFragmentPositiveClick(DialogFragment dialog) {
        SendSMS.sendMessage(this,((SMSMessageDetailsFragment)dialog).getPhoneNo(),((SMSMessageDetailsFragment)dialog).getMessage());
    }

    /**
     * NotificationMessageDetails listener for positive button clicked
     * Saves title and message and refreshes the list
     * @param dialog NotificationMessageDetailsFragment object
     */
    @Override
    public void onNotificationMessageDetailsFragmentPositiveClick(DialogFragment dialog) {
        SendNotification.sendNotification(this,7,((NotificationMessageDetailsFragment)dialog).getnotiTitle(),((NotificationMessageDetailsFragment)dialog).getMessage());
    }

}
