package com.example.krzysiek.etapi.Editor;

import com.example.krzysiek.etapi.Editor.RuleExpressions.ALSVExpression;
import com.example.krzysiek.etapi.Editor.RuleExpressions.ActionExpression;
import com.example.krzysiek.etapi.Editor.RuleExpressions.DecisionExpression;
import com.example.krzysiek.etapi.Editor.Xtypes.Xattr;
import com.example.krzysiek.etapi.Editor.Xtypes.Xrule;
import com.example.krzysiek.etapi.Editor.Xtypes.Xschm;
import com.example.krzysiek.etapi.Editor.Xtypes.Xtype;

import java.util.LinkedList;
/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Test {
    public static void main(String[] args){
        // We will use only these types in our model, user cant add his own types
        final Xtype hour_type = new Xtype("hour_type", "numeric", "[0.000 to 23.000]");
        final Xtype day_type = new Xtype("day_type", "symbolic", "[mon/1,tue/2,wen/3,thu/4,fri/5,sat/6,sun/7]","yes");
        final Xtype location_type = new Xtype("location_type", "symbolic", "[AGH,home]");
        final Xtype sound_type = new Xtype("sound_type","symbolic","[on,off,vibration]");
        //System.out.println(hour_type.returnStringForModel());

        //Creating example of attribute
        Xattr hour = new Xattr(hour_type,"hour","hour1" ,"in","getHour");
        Xattr day = new Xattr(day_type,"day","day1","in","getDay");
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
        ModelCreator model = new ModelCreator("model1");
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
