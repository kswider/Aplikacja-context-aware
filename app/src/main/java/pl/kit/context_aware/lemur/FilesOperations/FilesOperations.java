package pl.kit.context_aware.lemur.FilesOperations;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Krzysiek on 2017-01-11.
 */

public class FilesOperations {

    /**
     * Method returns names of all models
     * @return LinkedList with Strings containg model names
     */
    public static LinkedList<String> getAllModelNames(Context mContext){
        LinkedList<String> models = new LinkedList<String>();
        Pattern pattern = Pattern.compile("^.+\\.hmr$");
        Matcher matcher;
        File folder = new File(mContext.getFilesDir().toString());
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles){
            matcher = pattern.matcher(file.toString());
            if(matcher.matches()){
                models.add(file.getName().substring(0,file.getName().length()-4));
            }
        }
        return models;
    }

    public static void createBasicModelFiles(Context mContext) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader((mContext.getAssets().open("simple-model.hmr"))));


            PrintWriter zapis = new PrintWriter(mContext.getFilesDir().toString() + "/simple-model.hmr");

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                zapis.println(mLine);
            }

            zapis.close();
            reader.close();
        }catch (FileNotFoundException e){}
        catch (IOException e){}
    }
}
