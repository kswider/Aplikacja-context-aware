package Editor.RuleExpressions;

import Editor.Xtypes.XTT2StringRepresentation;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class ALSVExpression implements XTT2StringRepresentation {
    private String attributeName;
    private String ALVSOperator;
    private String value;

    public ALSVExpression(String attributeName, String ALVSOperator, String value) {
        this.attributeName = attributeName;
        this.ALVSOperator = ALVSOperator;
        this.value = value;
    }

    @Override
    public String returnStringForModel() {
        String ALSVString =  attributeName + " " + ALVSOperator + " " + value;
        return ALSVString;

    }
}
