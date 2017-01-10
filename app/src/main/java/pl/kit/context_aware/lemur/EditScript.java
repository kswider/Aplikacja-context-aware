package pl.kit.context_aware.lemur;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.kit.context_aware.lemur.TmpTests.ListItem;

import static android.R.id.list;

public class EditScript extends AppCompatActivity {

    public void SetTimeOnClick(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"Time Picker");
    }

    public void SetDayOnClick(View v){
        DialogFragment newFragment = new DayOfWeekPickerFragment();
        newFragment.show(getFragmentManager(),"Time Picker");
    }

    public void SetActionOnClick(View v){
        DialogFragment newFragment = new ActionPickerFragment();
        newFragment.show(getFragmentManager(),"Time Picker");
    }

    public void SetLocationOnClick(View v){
        Toast.makeText(this,"TO DO",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_script);
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_script_toolbar);
        //toolbar.setTitle(getResources().getString(R.string.es_Script));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                //TODO Adding Script Algorythm
                Toast.makeText(this,getResources().getString(R.string.es_Added),Toast.LENGTH_SHORT).show();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
