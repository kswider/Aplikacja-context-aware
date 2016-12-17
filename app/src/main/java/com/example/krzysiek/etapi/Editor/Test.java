package Editor;

import Editor.RuleExpressions.ALSVExpression;
import Editor.RuleExpressions.ActionExpression;
import Editor.RuleExpressions.DecisionExpression;
import Editor.Xtypes.Xattr;
import Editor.Xtypes.Xrule;
import Editor.Xtypes.Xschm;
import Editor.Xtypes.Xtype;

import java.util.LinkedList;
/**
 * Created by Krzysiek on 2016-12-15.
 */
public class Test {
    public static void main(String[] args){
        String lol = "lol";
        lol = lol.substring(0,lol.length()-1);
        System.out.println(lol);
        // We will use only these types in our model, user cant add his own types
        final Xtype hour_type = new Xtype("hour_type", "symbolic", "[0.000 to 23.000]");
        final Xtype day_type = new Xtype("day_type", "symbolic", "[mon,tue,wen,thu,fri,sat,sun]");
        final Xtype location_type = new Xtype("location_type", "symbolic", "[AGH,home]");
        //System.out.println(hour_type.returnStringForModel());

        //Creating example of attribute
        Xattr attributeE = new Xattr(hour_type,"hour","in","getHour");
        //System.out.println(atrybutE.returnStringForModel());
        LinkedList<Xattr> attrList = new LinkedList();
        attrList.add(attributeE);

        //Creating example of scheme
        Xschm schmE = new Xschm("Daytime",attrList,attrList);
        //System.out.println(schmE.returnStringForModel());

        //Creating example of ALSVExpression, DecisionExpression and ActionExpression which are needed in rule
        ALSVExpression alsvE = new ALSVExpression("location","eq","home");
        LinkedList<ALSVExpression> ALSVList = new LinkedList<>();
        ALSVList.add(alsvE);
        DecisionExpression decisionE = new DecisionExpression("alarm","off");
        LinkedList<DecisionExpression> decisionList = new LinkedList<>();
        decisionList.add(decisionE);
        ActionExpression actionE = new ActionExpression("setSound","");
        LinkedList<ActionExpression> actionList = new LinkedList<>();
        actionList.add(actionE);

        //Creating example of rule
        Xrule ruleE = new Xrule("Daytime","1",ALSVList,decisionList,actionList);
        //System.out.println(ruleE.returnStringForModel());

        // Creating model by adding everything into ModelCreator and saving it
        ModelCreator model = new ModelCreator("model1");
        model.addType(hour_type);
        model.addType(day_type);
        model.addType(location_type);
        model.addAttribute(attributeE);
        model.addScheme(schmE);
        model.addRule(ruleE);
        model.printModel();
        model.saveModel();
    }
}
