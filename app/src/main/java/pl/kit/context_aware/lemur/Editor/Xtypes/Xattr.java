package pl.kit.context_aware.lemur.Editor.Xtypes;

import java.io.Serializable;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Xattr implements XTT2StringRepresentation, Serializable {
    private String name; // Mandatory
    private String abbrev; // Mandatory
    private String className; // Mandatory {simple | general}
    private String type; // Mandatory
    private String comm; // Mandatory {in | out | inter | comm}
    private String callback; // Optional
    private String desc; // Optional

    public Xattr(Xtype type, String name, String abbrev, String comm, String callback) {
        this.name = name;
        this.abbrev = abbrev;
        className = "simple";
        this.type = type.getName();
        this.comm = comm;
        this.callback = callback;
    }

    /**
     * Method returns name of Xattr
     * @return String name of Xattr
     */
    public String getName() {
        return name;
    }

    /**
     * Method returns String representation of object, useful in saving model into file
     * @return String representation of Xattr
     */
    public String returnStringForModel(){
        String attrString = "xattr [name: " + name + ",\n";
        attrString += "\t   class: " + className + ",\n";
        attrString += "\t   type: " + type + ",\n";
        attrString += "\t   comm: " + comm;
        if(callback.isEmpty()) attrString += "\n\t  ].";
        else attrString += ",\n\t   callback: '" + callback + "'\n\t  ].";
        return attrString;


    }

}

