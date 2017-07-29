package pl.kit.context_aware.lemur.editor.ruleExpressions;

import java.io.Serializable;
import java.util.LinkedList;

import pl.kit.context_aware.lemur.editor.xtypes.XTT2StringRepresentation;
import pl.kit.context_aware.lemur.editor.xtypes.Xattr;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class ALSVExpression implements XTT2StringRepresentation, Serializable {
    private String attributeName;
    private String ALSVOperator;
    private String value = "";
    private String from = "";
    private String to = "";
    private LinkedList<String> values = null;

    public ALSVExpression(Xattr attribute, String value) {
        this.attributeName= attribute.getName();
        this.ALSVOperator = "eq";
        this.value = value;
    }

    public ALSVExpression(Xattr attributeName, String from, String to) {
        this.attributeName = attributeName.getName();
        this.ALSVOperator = "in";
        this.from = from;
        this.to = to;
    }

    public ALSVExpression(Xattr attributeName, LinkedList<String> values) {
        this.attributeName = attributeName.getName();
        this.ALSVOperator = "in";
        this.values = values;
    }

    /**
     * Method returns String representation of object, useful in saving model into file
     * @return String representation of ALSVExpression
     */
    @Override
    public String returnStringForModel() {
        String ALSVString = "";
        if (!value.isEmpty()) ALSVString =  attributeName + " " + ALSVOperator + " " + value;
        else if(!from.isEmpty()) ALSVString = attributeName + " " + ALSVOperator + " [" + from + " to " + to + "]";
        else if(!values.isEmpty()){
            ALSVString = attributeName + " " + ALSVOperator + " [";
            for(String value : values){
                ALSVString += value + ",";
            }
            ALSVString = ALSVString.substring(0,ALSVString.length()-1);
            ALSVString += "]";
        }
        return ALSVString;

    }
}
