package pl.kit.context_aware.lemur.Editor.Xtypes;

import java.io.Serializable;
import java.util.LinkedList;

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

    //needed in loading model isn't used in .hmr only saved in .ser
    private LinkedList<String> values = new LinkedList<>();

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
     * Method adds value into attribute. This value is later used in editting models.
     * @param value
     */
    public void addValue(String value){
        this.values.add(value);
    }

    /**
     * Method returns all values from this attribute
     * @return LinkedList with all values
     */
    public LinkedList<String> getValues() {
        return values;
    }

    /**
     * Method returns String representation of object, useful in saving model into file
     * @return String representation of Xattr
     */
    public String returnStringForModel(){
        String attrString = "xattr [name: " + name + ",\n";
        attrString += "\t   abbrev: " + abbrev + ",\n";
        attrString += "\t   class: " + className + ",\n";
        attrString += "\t   type: " + type + ",\n";
        attrString += "\t   comm: " + comm;
        if(callback.isEmpty()) attrString += "\n\t  ].";
        else attrString += ",\n\t   callback: '" + callback + "'\n\t  ].";
        return attrString;


    }

}

