package com.example.tomek.etapi;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.krzysiek.etapi.HeartDROID.Inference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public void button1OnClick(View v) {
        Toast toast = Toast.makeText( getApplicationContext(),
                ReadTime.ReadFullTime(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void button2OnClick(View v){
        ResultActions rs = new ResultActions();
        rs.sendNotification(this);
        //Toast.makeText(MyApplication.getContext(), "wowowow", Toast.LENGTH_SHORT).show();
    }

    public void button3OnClick(View v){
        ResultActions rs = new ResultActions();
        rs.silentMode(this);
        Toast toast = Toast.makeText( getApplicationContext(),
                "Wyciszono telefon", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void button4OnClick(View v){
        ResultActions rs = new ResultActions();
        rs.vibrationsMode();
        Toast toast = Toast.makeText( getApplicationContext(),
                "Telefon w trycie samych wibracji", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void button5OnClick(View v){
        ResultActions rs = new ResultActions();
        rs.normalMode(this);
        Toast toast = Toast.makeText( getApplicationContext(),
                "Telefon wrócił do stau normalnego", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void button6OnClick(View v){
        ReadLocation rl = new ReadLocation();
        Toast toast = Toast.makeText( getApplicationContext(),
                rl.readLocationByBest(this), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void button7OnClick(View v){
        TextView out = (TextView) findViewById(R.id.textView);

        createBaisicModelFiles cr = new createBaisicModelFiles();
        cr.create(this);

        try {
            File file = new File(MyApplication.getContext().getFilesDir().toString() + "/simple-model.hmr");
            Scanner in = new Scanner(file);

            while (in.hasNext()) {
                String zdanie = in.nextLine();
                out.setText(out.getText() + "\n" + zdanie);
            }
        }catch (FileNotFoundException e){}

        Inference inference = new Inference(this);
        inference.runInference();
        //out.setText(out.getText() + " \n" + inference.runInference(); ????

    }

    public void tmp1OnClick(View v){
        startService(new Intent(getBaseContext(), TestService.class));
    }

    public void tmp2OnClick(View v){
        stopService(new Intent(getBaseContext(), TestService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button4 = (Button) findViewById(R.id.button3);
        final Button button5 = (Button) findViewById(R.id.button3);
        final Button button6 = (Button) findViewById(R.id.button6);
        final Button button7 = (Button) findViewById(R.id.button6);

        TextView out = (TextView) findViewById(R.id.textView);

        out.setText(getFilesDir().toString());
}
}

