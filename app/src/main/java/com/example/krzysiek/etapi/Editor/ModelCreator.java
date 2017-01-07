package com.example.krzysiek.etapi.Editor;

import com.example.krzysiek.etapi.Editor.Xtypes.Xattr;
import com.example.krzysiek.etapi.Editor.Xtypes.Xrule;
import com.example.krzysiek.etapi.Editor.Xtypes.Xschm;
import com.example.krzysiek.etapi.Editor.Xtypes.Xtype;
import com.example.tomek.etapi.MyApplication;

import java.io.PrintWriter;
import java.io.*;
import java.util.LinkedList;

/**
 * Created by Krzysiek on 2016-12-16.
 */
public class ModelCreator implements Serializable {
    private String modelName;
    private LinkedList<Xtype> types = new LinkedList<>();
    private LinkedList<Xattr> attributes = new LinkedList<>();
    private LinkedList<Xschm>  schemes = new LinkedList<>();
    private LinkedList<Xrule> rules = new LinkedList<>();

    public ModelCreator(String modelName){
        this.modelName = modelName;
    }

    public void setModelName(String modelName){
        this.modelName = modelName;
    }
    public void addType(Xtype xtype){
        types.add(xtype);
    }
    public void addAttribute(Xattr xattr){
        attributes.add(xattr);
    }
    public void addScheme(Xschm xschm){
        schemes.add(xschm);
    }
    public void addRule(Xrule xrule){
        rules.add(xrule);
    }

    /**
     * Method prints current model in console
     */
    public void printModel(){
        for (Xtype i : types) {
            System.out.println(i.returnStringForModel());
        }
        for (Xattr i : attributes) {
            System.out.println(i.returnStringForModel());
        }
        for (Xschm i : schemes) {
            System.out.println(i.returnStringForModel());
        }
        for (Xrule i : rules) {
            System.out.println(i.returnStringForModel());
        }
    }

    /**
     * Method saves current model in 2 files:
     * 1) modelName.hmr - model which is used by HearTDROID
     * 2) modelName.ser - serialized model which is used in loading models to application
     */
    public void saveModel(){
        PrintWriter out = null;
        File file = new File(MyApplication.getContext().getFilesDir().toString() + "/" + modelName + ".hmr");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            out = new PrintWriter(file);
            for (Xtype i : types) {
                out.println(i.returnStringForModel());
            }
            for (Xattr i : attributes) {
                out.println(i.returnStringForModel());
            }
            for (Xschm i : schemes) {
                out.println(i.returnStringForModel());
            }
            for (Xrule i : rules) {
                out.println(i.returnStringForModel());
            }
            fos = new FileOutputStream(MyApplication.getContext().getFilesDir().toString() + "/" + modelName + ".ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (IOException e){

        } finally {
            out.close();
          try {
              if (fos != null) fos.close();
          } catch (IOException e) {}
          try {
              if (fos != null) oos.close();
          } catch (IOException e) {}
        }
    }

    /**
     * Static which loads ModelCreator from file and returns it
     * @return ModelCreator loaded from file modelName.ser
     */
    public ModelCreator loadModel(){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        ModelCreator model = null;
        try{
            fis = new FileInputStream(MyApplication.getContext().getFilesDir().toString() + "/" + modelName + ".ser");
            ois = new ObjectInputStream(fis);
            model = (ModelCreator) ois.readObject();

        } catch (IOException e){

        } catch (ClassNotFoundException e) {

        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {}
            try {
                if (ois != null) ois.close();
            } catch (IOException e) {}
        }
        return model;
    }
}
