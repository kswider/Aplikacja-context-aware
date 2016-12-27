package com.example.krzysiek.etapi.HeartDROID.actions;

import heart.Action;
import heart.State;
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
                System.out.println("Dźwięki włączone"); // TODO on Android
                rs.normalMode(Inference.getmContext());
                break;
            case "off":
                System.out.println("Dźwięki wyłączone"); // TODO on Android
                rs.silentMode(Inference.getmContext());
                break;
            case "vibration":
                System.out.println("Wibracje włączone"); // TODO on Android
                rs.vibrationsMode(Inference.getmContext());
                break;
        }

    }

}