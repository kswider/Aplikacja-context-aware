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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import pl.kit.context_aware.lemur.Editor.ModelCreator;
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
        File folder ;
        Pattern pattern = Pattern.compile("^.+\\.model$");
        Matcher matcher;
        if ( (folder = new File(mContext.getExternalFilesDir(null),"models")).exists()) {
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                matcher = pattern.matcher(file.toString());
                if(matcher.matches()) {
                    models.add(file.getName().substring(0, file.getName().length() - 6));
                }
            }
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

            //adding notifications and SMS files if they exist:
            ModelCreator loadedModel = ModelCreator.loadModel(mContext.getFilesDir() + "/" + modelName +".ser");
            LinkedList<String> list;
            list = loadedModel.getAttribute("notificationNumber").getValues(); // checking if there is a need to delete notification
            if(!list.isEmpty()){
                String name = list.pop();
                ze = new ZipEntry(name + ".notification");
                zos.putNextEntry(ze);
                in = new FileInputStream(mContext.getFilesDir() + "/" + name + ".notification");
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
                zos.closeEntry();
            }
            list = loadedModel.getAttribute("smsNumber").getValues(); // checking if there is a need to delete notification
            if(!list.isEmpty()){
                String name = list.pop();
                ze = new ZipEntry(name + ".sms");
                zos.putNextEntry(ze);
                in = new FileInputStream(mContext.getFilesDir() + "/" + name + ".sms");
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
                zos.closeEntry();
            }

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

    /**
     * Method which is used in moving models from assets to the phone memory
     * @param mContext
     */
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


    /**
     * Method creates file containing sms to send, filename is specified time
     * @param mContext
     * @param phoneNumber
     * @param message
     */
    public static String createSMS(Context mContext,String phoneNumber,String message) {
        PrintWriter out = null;
        LinkedList<String> smsList = new LinkedList<String>();
        File sms = null;
        int i;
        for(i=0;i<1000;++i) {
            sms = new File(mContext.getFilesDir().toString() + "/" + i + ".sms");
            if(!sms.isFile()) break;
        }
        try {
            out = new PrintWriter(sms);
            out.println(phoneNumber);
            out.println(message);
        } catch (IOException e) {

        } finally {
            out.close();
        }
        return Integer.toString(i);
    }

    /**
     * Method loads sms to send from file
     * @param mContext
     * @param filename
     * @return LinkedList with phone number and message
     */
    public static LinkedList<String> loadSMS(Context mContext,String filename){
        LinkedList<String> sms = new LinkedList<>();
        Scanner in = null;
        File file = new File(mContext.getFilesDir().toString() + "/" + filename + ".sms");
        try {
            in = new Scanner(file);
            sms.add(in.nextLine());
            sms.add(in.nextLine());
        } catch (IOException e) {

        } finally {
            in.close();
        }
        return sms;
    }
    /**
     * Method deletes sms, it is used when we edit existing model
     * @param mContext
     * @param filename
     */
    public static void deleteSMS(Context mContext, String filename){
        File file = new File(mContext.getFilesDir().toString() + "/" + filename + ".sms");
        file.delete();
    }

    /**
     * Method creates file containing notification to show, filename is specified time
     * @param mContext
     * @param title
     * @param message
     */
    public static String createNotification(Context mContext,String title,String message) {
        PrintWriter out = null;
        File notification = null;
        int i;
        for(i=0;i<1000;++i) {
            notification = new File(mContext.getFilesDir().toString() + "/" + i + ".notification");
            if(!notification.isFile()) break;
        }
        try {
            out = new PrintWriter(notification);
            out.println(title);
            out.println(message);
        } catch (IOException e) {

        } finally {
            out.close();
        }
        return Integer.toString(i);
    }

    /**
     * Method loads notification from file
     * @param mContext
     * @param filename
     * @return LinkedList with title and message
     */
    public static LinkedList<String> loadNotification(Context mContext,String filename){
        LinkedList<String> notification = new LinkedList<>();
        Scanner in = null;
        File file = new File(mContext.getFilesDir().toString() + "/" + filename + ".notification");
        try {
            in = new Scanner(file);
            notification.add(in.nextLine());
            notification.add(in.nextLine());
        } catch (IOException e) {

        } finally {
            in.close();
        }
        return notification;
    }

    /**
     * Method deletes notification, it is used when we edit existing model
     * @param mContext
     * @param filename
     */
    public static void deleteNotification(Context mContext, String filename){
        File file = new File(mContext.getFilesDir().toString() + "/" + filename + ".notification");
        file.delete();
    }
}
