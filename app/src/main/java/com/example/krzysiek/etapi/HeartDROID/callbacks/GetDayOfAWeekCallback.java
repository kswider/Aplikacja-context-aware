package com.example.krzysiek.etapi.HeartDROID.callbacks;

import android.widget.Toast;

import heart.Callback;
import heart.Debug;
import heart.WorkingMemory;
import heart.alsvfd.SimpleNumeric;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;

import com.example.tomek.etapi.MyApplication;
import com.example.tomek.etapi.ReadTime;
/**
 * Created by Krzysiek on 2016-12-27.
 */

public class GetDayOfAWeekCallback implements Callback{
    @Override
    public void execute(Attribute subject, WorkingMemory wmm) {
        //System.out.println("Executing GetDayOfAWeekCallback for "+subject.getName());
        int day = ReadTime.ReadDay() - 1;
        day = (day == 0 ? 7 : day);
        try {
            wmm.setAttributeValue(subject,new SimpleNumeric((double)day),false);
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