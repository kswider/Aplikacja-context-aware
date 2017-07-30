package pl.kit.context_aware.lemur.heartDROID;

import android.content.Context;
import android.util.Log;

import heart.*;
import heart.exceptions.*;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceFile;
import heart.uncertainty.ConflictSetFireAll;
import heart.xtt.XTTModel;

/**
 * Created by Krzysiek on 2016-12-27.
 */
public class Inference  {

    static private Context mContext;

    public Inference(Context mContext) {
        this.mContext = mContext;
    }
    public static Context getmContext() {
        return mContext;
    }

    /**
     * Method runs inference using heartDROID using model from given path
     * @param path String representation of path to the model, which we want to use in inference
     */
    public void runInference(String path){
        try {
            //Loading a file with a model
            XTTModel model = null;
            SourceFile simpleModel = new SourceFile(path);
            HMRParser parser = new HMRParser();

            //Parsing the file with the model
            parser.parse(simpleModel);
            model = parser.getModel();


            try{
                Debug.debugLevel = Debug.Level.SILENT;
                HeaRT.fixedOrderInference(model, new String[]{"SetEverything"},
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
        Log.d("Lemur","Inference");
    }
}
