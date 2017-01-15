package pl.kit.context_aware.lemur.HeartDROID.callbacks;

import heart.Callback;
import heart.Debug;
import heart.WorkingMemory;
import heart.alsvfd.SimpleNumeric;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;
import pl.kit.context_aware.lemur.Readers.ReadTime;

/**
 * Created by Krzysiek on 2016-12-27.
 */

public class GetTime implements Callback{
    @Override
    public void execute(Attribute subject, WorkingMemory wmm) {
        double hour = ReadTime.ReadHour();
        double minute = ReadTime.ReadMinute();
        SimpleNumeric time = new SimpleNumeric(hour + (minute/60));

        try {
            wmm.setAttributeValue(subject, time ,false);
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