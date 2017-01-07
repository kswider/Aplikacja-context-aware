package com.example.krzysiek.etapi.HeartDROID.actions;

import android.widget.TextView;
import android.widget.Toast;

import heart.Action;
import heart.State;

import com.example.tomek.etapi.MyApplication;
import com.example.tomek.etapi.R;
import com.example.tomek.etapi.ResultActions;
import com.example.krzysiek.etapi.HeartDROID.Inference;
/**
 * Created by Krzysiek on 2016-12-27.
 */

public class setSound implements Action {

    @Override
    public void execute(State state) {
        String argument = String.valueOf(state.getValueOfAttribute("sound_settings"));
        ResultActions rs = new ResultActions();
        switch (argument){
            case "on":
                rs.normalMode(Inference.getmContext());
                break;
            case "off":
                rs.silentMode(Inference.getmContext());
                break;
            case "vibration":
                rs.vibrationsMode(Inference.getmContext());
                break;
        }

    }

}