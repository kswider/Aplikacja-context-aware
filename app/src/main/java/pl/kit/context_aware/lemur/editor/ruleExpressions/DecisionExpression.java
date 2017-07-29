package pl.kit.context_aware.lemur.editor.ruleExpressions;

import java.io.Serializable;
import pl.kit.context_aware.lemur.editor.xtypes.XTT2StringRepresentation;
import pl.kit.context_aware.lemur.editor.xtypes.Xattr;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class DecisionExpression implements XTT2StringRepresentation, Serializable {
    private String attributeName;
    private String value;

    public DecisionExpression(Xattr attribute, String value) {
        this.attributeName = attribute.getName();
        this.value = value;
    }

    /**
     * Method returns String representation of object, useful in saving model into file
     * @return String representation of DecisionExpression
     */
    @Override
    public String returnStringForModel() {
        String decisionString = attributeName + " set " + value;
        return decisionString;
    }
}
