package pl.kit.context_aware.lemur;

        import android.content.Intent;
        import android.os.Bundle;
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

        import pl.kit.context_aware.lemur.PhoneActions.RingModes;
        import pl.kit.context_aware.lemur.PhoneActions.SendNotification;
        import pl.kit.context_aware.lemur.Readers.ReadLocation;
        import pl.kit.context_aware.lemur.Readers.ReadTime;
        import pl.kit.context_aware.lemur.TmpTests.TestService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public void SilentButtonOnClick(View v){
        RingModes rm = new RingModes();
        rm.silentMode(this);
    }

    public void VibrationButtonOnClick(View v){
        RingModes rm = new RingModes();
        rm.vibrationsMode(this);
    }

    public void NormalRingButtonOnClick(View v){
        RingModes rm = new RingModes();
        rm.normalMode(this);
    }

    public void NotificationButtonOnClick(View v){
        SendNotification sn = new SendNotification();
        sn.sendNotification(this,5,"Wiadomość od Krzysia","Gdzie masz krzesło !?");
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
    }

    public void floatingButtonOnClick(View v){
        Intent intent = new Intent(this,EditScript.class);
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

        /*ActionsTests at = new ActionsTests();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container,at);
        fragmentTransaction.commit();*/

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
             ToDoFragment tdf = new ToDoFragment();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,tdf);
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
             ToDoFragment tdf = new ToDoFragment();
             android.support.v4.app.FragmentTransaction fragmentTransaction =
                     getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,tdf);
             fragmentTransaction.commit();
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
}
