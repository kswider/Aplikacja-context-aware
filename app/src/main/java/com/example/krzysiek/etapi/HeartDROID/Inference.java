package com.example.krzysiek.etapi.HeartDROID;

import android.content.Context;
import android.widget.Toast;

import com.example.tomek.etapi.MyApplication;

import heart.*;
import heart.exceptions.*;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceFile;
import heart.uncertainty.ConflictSetFireAll;
import heart.xtt.*;


/**
 * Created by Krzysiek on 2016-12-27.
 */
public class Inference  {

    static Context mContext;

    public Inference(Context mContext) {
        this.mContext = mContext;
    }

    public static Context getmContext() {
        return mContext;
    }
    public String ubabu(){
        try {
            SourceFile simpleModel = new SourceFile(MyApplication.getContext().getFilesDir().toString() + "/simple-model.hmr");
        }catch (Exception e){ return "aaaaaaaaaaaaa";}
        return "asdasdasdasd";
    }


    public void runInference(String [] args){
        try {
            //Loading a file with a model
            XTTModel model = null;
            SourceFile simpleModel = new SourceFile(MyApplication.getContext().getFilesDir().toString() + "/simple-model.hmr");
            HMRParser parser = new HMRParser();

            //Parsing the file with the model
            parser.parse(simpleModel);
            model = parser.getModel();

            try{
                Debug.debugLevel = Debug.Level.SILENT;
                HeaRT.fixedOrderInference(model, new String[]{"SetSounds"},
                        new Configuration.Builder().setCsr(new ConflictSetFireAll())
                                .build());

            }catch(UnsupportedOperationException e){
                e.printStackTrace();
            } catch (AttributeNotRegisteredException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } catch (BuilderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotInTheDomainException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (ModelBuildingException e) {
            e.printStackTrace();
        } catch (ParsingSyntaxException e) {
            e.printStackTrace();
        }
        return "asdasdasdasdasd";
    }
}
