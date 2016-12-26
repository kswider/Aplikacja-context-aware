package com.example.krzysiek.etapi.Editor.RuleExpressions;

import com.example.krzysiek.etapi.Editor.Xtypes.XTT2StringRepresentation;
import com.example.krzysiek.etapi.Editor.Xtypes.Xattr;

import java.io.Serializable;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class ALSVExpression implements XTT2StringRepresentation, Serializable {
    private String attributeName;
    private String ALSVOperator;
    private String value = "";
    private String from = "";
    private String to = "";

    public ALSVExpression(Xattr attribute, String value) {
        this.attributeName= attribute.getName();
        this.ALSVOperator = "eq";
    }

    public ALSVExpression(Xattr attributeName, String from, String to) {
        this.attributeName = attributeName.getName();
        ALSVOperator = "in";
        this.from = from;
        this.to = to;
    }

    /**
     * Method returns String representation of object, useful in saving model into file
     * @return String representation of ALSVExpression
     */
    @Override
    public String returnStringForModel() {
        String ALSVString = "";
        if (from.isEmpty()) ALSVString =  attributeName + " " + ALSVOperator + " " + value;
        else if(value.isEmpty()) ALSVString = attributeName + " " + ALSVOperator + "[" + from + " to " + to + "]";
        return ALSVString;

    }
}
