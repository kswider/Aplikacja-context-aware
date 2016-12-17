package Editor.Xtypes;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Xtype implements XTT2StringRepresentation {
    private String name; // Mandatory
    private String base; // Mandatory {numeric | symbolic}
    private String domain; // Mandatory
    private String scale; // Optional
    private String ordered; // Optional {numeric | symbolic}
    private String desc; // Optional

    public Xtype(String name, String base, String domain) {
        this.name = name;
        this.base = base;
        this.domain = domain;
    }

    public String getName() {

        return name;
    }

    public String returnStringForModel(){
        String typeString = "xtype [name: " + name + ",\n";
        typeString += "\t   base: " + base + ",\n";
        typeString += "\t   domain: " + domain + "\n\t  ].";
        return typeString;
    }

}
