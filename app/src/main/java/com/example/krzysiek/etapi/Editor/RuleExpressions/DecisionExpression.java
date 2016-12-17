package Editor.RuleExpressions;

import Editor.Xtypes.XTT2StringRepresentation;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class DecisionExpression implements XTT2StringRepresentation {
    private String attributeName;
    private String value;

    public DecisionExpression(String attributeName, String value) {
        this.attributeName = attributeName;
        this.value = value;
    }

    @Override
    public String returnStringForModel() {
        String decisionString = attributeName + " set " + value;
        return decisionString;
    }
}
