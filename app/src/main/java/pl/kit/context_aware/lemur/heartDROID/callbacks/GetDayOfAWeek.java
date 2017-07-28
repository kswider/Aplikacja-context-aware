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

public class GetDayOfAWeek implements Callback{
    @Override
    /**
     * Callback which puts current number of a day of a week into argument day
     */
    public void execute(Attribute subject, WorkingMemory wmm) {
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