package Editor.Xtypes;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Xattr implements XTT2StringRepresentation {
    private String name; // Mandatory
    private String className; // Mandatory {simple | general}
    private String type; // Mandatory
    private String comm; // Mandatory {in | out | inter | comm}
    private String callback; // Optional
    private String abbrev; // Optional
    private String desc; // Optional

    public Xattr(Xtype type, String name, String comm, String callback) {
        this.name = name;
        className = "simple";
        this.type = type.getName();
        this.comm = comm;
        this.callback = callback;
    }

    public String getName() {
        return name;
    }

    public String returnStringForModel(){
        String attrString = "xattr [name: " + name + ",\n";
        attrString += "\t   class: " + className + ",\n";
        attrString += "\t   type: " + type + ",\n";
        attrString += "\t   comm: " + comm;
        if(callback.isEmpty()) attrString += "\n\t  ].";
        else attrString += ",\n\t   callback: 'callbacks." + callback + "'\n\t  ].";
        return attrString;


    }
}

