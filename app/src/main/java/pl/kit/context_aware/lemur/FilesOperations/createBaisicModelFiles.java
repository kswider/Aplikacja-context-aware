package pl.kit.context_aware.lemur.FilesOperations;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Tomek on 28.12.2016.
 */

public class createBaisicModelFiles {

    public void create(Context mContext) {
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
