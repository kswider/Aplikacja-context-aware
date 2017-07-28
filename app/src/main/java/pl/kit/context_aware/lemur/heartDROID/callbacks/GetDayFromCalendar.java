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
 * Created by Krzysiek on 2017-04-25.
 */


public class GetDayFromCalendar implements Callback {
    @Override
    /**
     * Callback which puts current date into argument dayFromCalendar
     */
    public void execute(Attribute subject, WorkingMemory wmm) {
        int day = Integer.valueOf(ReadTime.ReadDate());
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