package com.example.tomek.etapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void odczytajGodzine(View v) {
        Toast toast = Toast.makeText( getApplicationContext(),
                ReadTime.ReadFullTime(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void powiadomienie(View v){
        ResultActions rs = new ResultActions();
        rs.sendNotification(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
}
}

