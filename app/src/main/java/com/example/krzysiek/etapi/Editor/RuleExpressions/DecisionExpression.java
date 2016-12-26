package com.example.krzysiek.etapi.Editor.RuleExpressions;

import com.example.krzysiek.etapi.Editor.Xtypes.XTT2StringRepresentation;
import com.example.krzysiek.etapi.Editor.Xtypes.Xattr;

import java.io.Serializable;

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
