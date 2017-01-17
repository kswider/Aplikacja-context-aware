package pl.kit.context_aware.lemur.HeartDROID.callbacks;



import heart.Callback;
import heart.Debug;
import heart.WorkingMemory;
import heart.alsvfd.SimpleNumeric;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.Readers.ReadLocation;

/**
 * Created by Krzysiek on 2017-01-07.
 */

public class GetLongitude implements Callback {
    @Override
    public void execute(Attribute subject, WorkingMemory wmm) {
        double longitude = ReadLocation.readLongitudeByBest(Inference.getmContext());
        longitude = ((double)Math.round(longitude*10000)) / 10000;
        try {
            wmm.setAttributeValue(subject,new SimpleNumeric(longitude),false);
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
