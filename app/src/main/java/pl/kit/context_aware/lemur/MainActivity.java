package pl.kit.context_aware.lemur;

        import android.app.AlarmManager;
        import android.app.DialogFragment;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.MenuItem;
        import android.widget.Toast;

        import java.util.Calendar;
        import java.util.LinkedList;

        import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
        import pl.kit.context_aware.lemur.HeartDROID.HeartAlarmReceiver;
        import pl.kit.context_aware.lemur.HeartDROID.Inference;
        import pl.kit.context_aware.lemur.PhoneActions.RingModes;
        import pl.kit.context_aware.lemur.PhoneActions.SendNotification;
        import pl.kit.context_aware.lemur.Readers.ReadLocation;
        import pl.kit.context_aware.lemur.Readers.ReadTime;
        import pl.kit.context_aware.lemur.TmpTests.TestService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScriptsToExportPickerFragment.NoticeDialogSTEFListener, ScriptsToImportPickerFragment.NoticeDialogSTIFListener, DeleteScriptFragment.NoticeDialogDSFListener {
    LinkedList<String> ScriptsToExport = null;
    LinkedList<String> ScriptsToImport = null;

    public void SilentButtonOnClick(View v){
        RingModes.silentMode(this);
    }

    public void VibrationButtonOnClick(View v){
        RingModes.vibrationsMode(this);
    }

    public void NormalRingButtonOnClick(View v){
        RingModes.normalMode(this);
    }

    public void NotificationButtonOnClick(View v){
        SendNotification.sendNotification(this,5,"Wiadomość od Krzysia","Gdzie masz krzesło !?");
    }

    public void ReadTimeButtonOnClick(View v){
        Toast.makeText(this,ReadTime.ReadFullTime(),Toast.LENGTH_SHORT).show();
    }

    public void ReadLocationButtonOnClick(View v){
        ReadLocation rl = new ReadLocation();
        Toast.makeText(this,rl.readLocationByBest(this),Toast.LENGTH_SHORT).show();
    }

    public void startServiceButtonOnClick(View v){
        startService(new Intent(getBaseContext(), TestService.class));
    }

    public void stopServiceButtonOnClick(View v){
        stopService(new Intent(getBaseContext(), TestService.class));
    }

    public void runSimModelButtonOnClick(View v){
        Inference inference = new Inference(this);
        for(String scriptName : FilesOperations.getAllModelNames(this)) {
            inference.runInference(this.getFilesDir() + "/" + scriptName + ".hmr");
        }
    }

    public void sheduleHeartEvery5MinutesOnClick(View v){

        int i = 60;
        Intent intent = new Intent(this, HeartAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), (i * 1000), pendingIntent);
        Toast.makeText(this, "Starting alarm in " + i + " seconds",
                Toast.LENGTH_LONG).show();
    }

    public void ImportScriptButtonOnClick(View v){
        DialogFragment newFragment = new ScriptsToImportPickerFragment();
        newFragment.show(getFragmentManager(), "Import Scripts List Fragment");
        Toast.makeText(this, "Scripts Imported",
                Toast.LENGTH_LONG).show();
    }

    public void ExportScriptButtonOnClick(View v){
        DialogFragment newFragment = new ScriptsToExportPickerFragment();
        newFragment.show(getFragmentManager(), "Export Scripts List Fragment");
        Toast.makeText(this, "Scripts exported",
                Toast.LENGTH_LONG).show();
    }

    public void floatingButtonOnClick(View v){
        Bundle eFileName = new Bundle();
        eFileName.putString("eFileName","");

        Intent intent = new Intent(v.getContext(),EditScript.class);
        intent.putExtras(eFileName);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scriptst_list);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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
             ToDoFragment tdf = new ToDoFragment();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,tdf);
             fragmentTransaction.commit();
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
             catch(Exception e){
                 Toast.makeText(this, "!!!Error occurred!!! \nYou don't have Play Store",
                         Toast.LENGTH_LONG).show();
             }
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

    @Override
    public void onDialogSTEFPositiveClick(DialogFragment dialog) {
        ScriptsToExport = (LinkedList<String>)((ScriptsToExportPickerFragment) dialog).getSelectedScripts().clone();
    }

    @Override
    public void onDialogSTEFNegativeClick(DialogFragment dialog) {
        ScriptsToExport = null;
    }

    @Override
    public void onDialogSTIFPositiveClick(DialogFragment dialog) {
        ScriptsToImport = (LinkedList<String>)((ScriptsToImportPickerFragment)dialog).getSelectedScripts().clone();
    }

    @Override
    public void onDialogSTIFNegativeClick(DialogFragment dialog) {
        ScriptsToImport = null;
    }

    @Override
    public void onDialogDSFPositiveClick(DialogFragment dialog) {
        ScriptsList sl = new ScriptsList();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,sl);
        fragmentTransaction.commit();
    }

    @Override
    public void onDialogDSFNegativeClick(DialogFragment dialog) {

    }
}
