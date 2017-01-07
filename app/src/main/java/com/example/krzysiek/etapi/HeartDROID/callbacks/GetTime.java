package com.example.krzysiek.etapi.HeartDROID.callbacks;

import android.util.Log;

import com.example.tomek.etapi.ReadTime;

import heart.Callback;
import heart.Debug;
import heart.HeaRT;
import heart.WorkingMemory;
import heart.alsvfd.SimpleNumeric;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;

import java.util.Calendar;
/**
 * Created by Krzysiek on 2016-12-27.
 */

public class GetTime implements Callback{
    @Override
    public void execute(Attribute subject, WorkingMemory wmm) {
        double hour = ReadTime.ReadHour();
        double minute = ReadTime.ReadMinute();
        double time = hour + (minute/60);
        try {
            wmm.setAttributeValue(subject,new SimpleNumeric(time),false);
        } catch (AttributeNotRegisteredException e) {
            Debug.debug("CALLBACK",
                    Debug.Level.WARNING,
                    "Callback failed to set value of"+subject.getName()+", as the attribute is not registered in the Working Memory.");
        } catch (NotInTheDomainException e) {
            Debug.debug("CALLBACK",
                    Debug.Level.WARNING,
                    "Callback failed to set value of"+subject.getName()+", as the obtained value was not in the domain of attribute.");
        }
    }
}