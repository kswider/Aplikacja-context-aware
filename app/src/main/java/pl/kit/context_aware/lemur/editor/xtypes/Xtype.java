package pl.kit.context_aware.lemur.editor.xtypes;

import java.io.Serializable;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Xtype implements XTT2StringRepresentation, Serializable {
    private String name; // Mandatory
    private String base; // Mandatory {numeric | symbolic}
    private String domain; // Mandatory
    private String scale; // Optional
    private String ordered = ""; // Optional
    private String desc; // Optional
    public Xtype(String name, String base, String domain) {
        this.name = name;
        this.base = base;
        this.domain = domain;
    }
    public Xtype(String name, String base, String domain, String ordered) {
        this.name = name;
        this.base = base;
        this.domain = domain;
        this.ordered = ordered;
    }

    public String getName() {

        return name;
    }

    /**
     * Method returns String representation of object, useful in saving model into file
     * @return String representation of Xtype
     */
    public String returnStringForModel(){
        String typeString = "xtype [name: " + name + ",\n";
        typeString += "\t   base: " + base + ",\n";
        typeString += "\t   domain: " + domain;
        if (ordered.isEmpty()) return typeString + "\n\t  ].";
        else {
            typeString += ",\n";
            typeString += "\t   ordered: " + ordered + "\n\t  ].";
            return typeString;
        }
    }
}
