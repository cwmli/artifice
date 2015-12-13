package com.underwaterotter.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialized implements Serializable {

    public static Context context;
    public String fileName;
    private static final long serialVersionUID = 0L;
    private static String extension = ".dat";

    public void serialize(Object obj){

        try {
            FileOutputStream savFile = context.openFileOutput(fileName+extension, context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(savFile);
            out.writeObject(obj);
            out.close();
            savFile.close();
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
    }

    public Object deserialize(){

        Object obj;

        try {
            FileInputStream savFile  = context.openFileInput(fileName+extension);
            ObjectInputStream in = new ObjectInputStream(savFile);
            obj = in.readObject();
            in.close();
            savFile.close();
            return obj;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

}
