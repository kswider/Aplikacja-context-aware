package Editor.RuleExpressions;

import Editor.Xtypes.XTT2StringRepresentation;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class ActionExpression implements XTT2StringRepresentation {
    private String actionName;
    private String actionParameters = "";

    public ActionExpression(String actionName, String actionParameters) {
        this.actionName = actionName;
        this.actionParameters = actionParameters;
    }

    @Override
    public String returnStringForModel() {
        String actionString;
        if(actionParameters.isEmpty()) actionString = "'actions." + actionName + "'";
        else actionString = "'actions." + actionName + ",[" + actionParameters + "]'";
        return actionString;
    }
}
