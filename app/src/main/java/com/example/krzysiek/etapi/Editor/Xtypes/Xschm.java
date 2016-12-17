package Editor.Xtypes;

import java.util.LinkedList;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Xschm implements XTT2StringRepresentation {
    private String name; // Mandatory
    private String desc; // Optional
    private LinkedList<Xattr> attributesList = new LinkedList(); // Mandatory
    private LinkedList<Xattr> attributesToSetList = new LinkedList(); // Mandatory

    public Xschm(String name, LinkedList<Xattr> attributesList, LinkedList<Xattr> attributesToSetList) {
        this.name = name;
        this.attributesList = attributesList;
        this.attributesToSetList = attributesToSetList;
    }

    @Override
    public String returnStringForModel() {
        String schmString = "xschm '" + name + "': [";
        for (Xattr i: attributesList) {
            schmString += i.getName() + ",";
        }
        schmString = schmString.substring(0, schmString.length() - 1);
        schmString += "] ==> [";
        for(Xattr i : attributesToSetList){
            schmString += i.getName() + ",";
        }
        schmString = schmString.substring(0, schmString.length() - 1);
        schmString += "].";
        return schmString;
    }
}
