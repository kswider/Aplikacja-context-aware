package com.example.krzysiek.etapi.Editor.RuleExpressions;

import com.example.krzysiek.etapi.Editor.Xtypes.XTT2StringRepresentation;

import java.io.Serializable;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class ActionExpression implements XTT2StringRepresentation, Serializable {
    private String actionName;

    public ActionExpression(String actionName) {
            this.actionName = actionName;
    }

    /**
     * Method returns String representation of object, useful in saving model into file
     * @return String representation of ActionExpression
     */
    @Override
    public String returnStringForModel() {
        String actionString;
        actionString = "'actions." + actionName + "'";
        return actionString;
    }
}
