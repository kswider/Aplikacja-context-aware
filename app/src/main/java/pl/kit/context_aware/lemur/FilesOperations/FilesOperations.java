package pl.kit.context_aware.lemur.FilesOperations;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import pl.kit.context_aware.lemur.R;

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

    /**
     * Method returns names of all models on External Storage
     * @param mContext
     * @return LinkedList with Strings containg model names
     */
    public static LinkedList<String> getAllModelNamesFromExternalStorage(Context mContext){
        LinkedList<String> models = new LinkedList<String>();
        File folder = new File(mContext.getExternalFilesDir(null),"models");
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles){
            models.add(file.getName().substring(0,file.getName().length()-6));
        }
        return models;
    }

    /**
     * Method checks if external Storage is available
     * @return True if External Storage is available, False if it isn't
     */
    public static boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    /**
     * Method returning path to External Storage, creates directory if it isn't created yet.
     * @param mContext
     * @return File path to External Storage
     */
    @Nullable
    public static File getExternalDirectory(Context mContext){
        if(isExternalStorageWritable()) {
            File file = new File(mContext.getExternalFilesDir(null), "models");
            if(!file.exists()) {
                if (!(file.mkdirs())) {
                    Log.e("Error", "Directory not created");
                }
            }
            return file;
        }
        Toast.makeText(mContext, "Cannot reach External Storage!", Toast.LENGTH_SHORT).show();
        return null;
    }

    /**
     * Method zipping and exports model named modelName to external Storage
     * @param mContext
     * @param modelName name of model which will be exported
     */
    public static void exportModel(Context mContext,String modelName){
        final int BUFFER_SIZE = 2048;
        byte[] buffer = new byte[BUFFER_SIZE];
        try{
            modelName = "test";
            File path = new File (getExternalDirectory(mContext), modelName + ".model");
            FileOutputStream fos = new FileOutputStream(path);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze= new ZipEntry(modelName+".ser");
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(mContext.getFilesDir() + "/" + modelName + ".ser");
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();

            ze = new ZipEntry(modelName + ".hmr");
            zos.putNextEntry(ze);
            in = new FileInputStream(mContext.getFilesDir() + "/" + modelName + ".hmr");
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();

            zos.close();

        }catch(IOException ex){
        }
    }

    /**
     * Method imports model from External Storage and saves it unzipped into internal
     * @param mContext
     * @param modelName
     */
    public static void importModel(Context mContext, String modelName){
        final int BUFFER_SIZE = 2048;
        try {
            File path = new File(getExternalDirectory(mContext),modelName + ".model");
            FileInputStream fis = new FileInputStream(path);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null){
                int count;
                byte[] buffer = new byte[BUFFER_SIZE];
                FileOutputStream fos = new FileOutputStream(mContext.getFilesDir() + "/" +entry.getName());
                BufferedOutputStream dest = new BufferedOutputStream(fos,BUFFER_SIZE);
                while((count = zis.read(buffer,0,BUFFER_SIZE)) != -1){
                    dest.write(buffer,0,count);
                }
                dest.flush();
                dest.close();
            }
            zis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
