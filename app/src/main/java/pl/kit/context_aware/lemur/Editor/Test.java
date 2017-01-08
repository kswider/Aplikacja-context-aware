package pl.kit.context_aware.lemur.Editor;


import java.util.LinkedList;

import pl.kit.context_aware.lemur.Editor.RuleExpressions.ALSVExpression;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.ActionExpression;
import pl.kit.context_aware.lemur.Editor.RuleExpressions.DecisionExpression;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xattr;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xrule;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xschm;
import pl.kit.context_aware.lemur.Editor.Xtypes.Xtype;

/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Test {
    public void run(String internalPath){
        // We will use only these types in our model, user cant add his own types
        final Xtype hour_type = new Xtype("hour_type", "numeric", "[0 to 23]");
        final Xtype minute_type = new Xtype("minute_type", "numeric", "[0 to 59");
        final Xtype time_type = new Xtype("time_type", "numeric", "[0.0000 to 23.0000]");
        final Xtype day_type = new Xtype("day_type", "symbolic", "[mon/1,tue/2,wen/3,thu/4,fri/5,sat/6,sun/7]","yes");
        final Xtype longitude_type = new Xtype("longitude_type", "numeric", "[-180.0000000 to 180.0000000]");
        final Xtype latitude_type = new Xtype("latitude_type", "numeric","[-90.0000000 to 90.0000000]");
        final Xtype sound_type = new Xtype("sound_type","symbolic","[on,off,vibration]");

        //System.out.println(hour_type.returnStringForModel());

        //Creating example of attribute
        Xattr hour = new Xattr(hour_type,"hour","hour1" ,"in","");
        Xattr minute = new Xattr(minute_type,"minute","minute1" ,"in","");
        Xattr time = new Xattr(time_type,"time","time1" ,"in","getTime");
        Xattr day = new Xattr(day_type,"day","day1","in","getDay");
        Xattr longitude = new Xattr(longitude_type,"longitude","longitude1","in","getLongitude");
        Xattr latitude = new Xattr(latitude_type,"latitude","latitude1","in","getLatitude");
        Xattr sound = new Xattr(sound_type,"sound","sound1","inter","");
        //System.out.println(atrybutE.returnStringForModel());
        LinkedList<Xattr> attrList1 = new LinkedList();
        attrList1.addLast(hour);
        attrList1.addLast(day);
        LinkedList<Xattr> attrList2 = new LinkedList();
        attrList2.addLast(sound);

        //Creating example of scheme
        Xschm setSounds = new Xschm("SetSounds",attrList1,attrList2);
        //System.out.println(schmE.returnStringForModel());

        //Creating example of ALSVExpression, DecisionExpression and ActionExpression which are needed in rule
        ALSVExpression alsv1 = new ALSVExpression(hour,"8","17");

        ALSVExpression alsv2 = new ALSVExpression(day,"mon","fri");

        LinkedList<ALSVExpression> ALSVList = new LinkedList<>();
        ALSVList.add(alsv1);
        ALSVList.add(alsv2);

        DecisionExpression decisionE = new DecisionExpression(sound,"vibration");
        LinkedList<DecisionExpression> decisionList = new LinkedList<>();
        decisionList.add(decisionE);
        ActionExpression actionE = new ActionExpression("setSound");
        LinkedList<ActionExpression> actionList = new LinkedList<>();
        actionList.add(actionE);

        //Creating example of rule
        Xrule ruleE = new Xrule("SetSounds","1",ALSVList,decisionList,actionList);
        //System.out.println(ruleE.returnStringForModel());

        // Creating model by adding everything into ModelCreator and saving it
        ModelCreator model = new ModelCreator("model1",internalPath);
       /* model.addType(hour_type);
        model.addType(day_type);
        model.addType(location_type);
        model.addType(sound_type);
        model.addAttribute(hour);
        model.addAttribute(day);
        model.addAttribute(sound);
        model.addScheme(setSounds);
        model.addRule(ruleE);*/
        model = model.loadModel();
        model.printModel();
        //model.saveModel();

    }
}
