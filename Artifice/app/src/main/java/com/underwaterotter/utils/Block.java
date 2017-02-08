package com.underwaterotter.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class Block {

    private JSONObject jsonObj;
    private static final String IDENTIFIER = "className";
    private static String EXTENSION = ".json";

    public Block(){
        jsonObj = new JSONObject();
    }

    public Block(JSONObject jsonObj){
        this.jsonObj = jsonObj;
    }

    public Block(String json){
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String stringify(){
        return jsonObj.toString();
    }

    public boolean isNull(){
        return this.jsonObj == null;
    }

    public Object get(String name){
        return jsonObj.opt(name);
    }

    public Object[] getArray(String name){

        try {
            JSONArray jArray = jsonObj.getJSONArray(name);
            Object[] values = new Object[jArray.length()];

            for (int i = 0; i < jArray.length()-1; i++){
                values[i] = jArray.opt(i);
            }
            return values;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean getBoolean(String name){
        return jsonObj.optBoolean(name);
    }

    public boolean[] getBooleanArray(String name){

        try {
            JSONArray jArray = jsonObj.getJSONArray(name);
            boolean[] values = new boolean[jArray.length()];

            for (int i = 0; i < jArray.length()-1; i++){
                values[i] = jArray.optBoolean(i);
            }
            return values;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public double getDouble(String name){
        return jsonObj.optDouble(name);
    }

    public double[] getDoubleArray(String name){

        try {
            JSONArray jArray = jsonObj.getJSONArray(name);
            double[] values = new double[jArray.length()];

            for (int i = 0; i < jArray.length()-1; i++){
                values[i] = jArray.optDouble(i);
            }
            return values;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public Integer getInt(String name){
        return jsonObj.optInt(name);
    }

    public int[] getIntArray(String name){

        try {
            JSONArray jArray = jsonObj.getJSONArray(name);
            int[] values = new int[jArray.length()];

            for (int i = 0; i < jArray.length()-1; i++){
                values[i] = jArray.optInt(i);
            }
            return values;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public long getLong(String name){
        return jsonObj.optLong(name);
    }

    public long[] getLongArray(String name){

        try {
            JSONArray jArray = jsonObj.getJSONArray(name);
            long[] values = new long[jArray.length()];

            for (int i = 0; i < jArray.length()-1; i++){
                values[i] = jArray.optLong(i);
            }
            return values;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getString(String name){
        return jsonObj.optString(name);
    }

    public String[] getStringArray(String name){

        try {
            JSONArray jArray = jsonObj.getJSONArray(name);
            String[] values = new String[jArray.length()];

            for (int i = 0; i < jArray.length()-1; i++){
                values[i] = jArray.optString(i);
            }
            return values;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public Block getBlock(String name){
        return new Block(jsonObj.optJSONObject(name));
    }

    public Block[] getBlockArray(String name){

        try {
            JSONArray jArray = jsonObj.getJSONArray(name);
            Block[] values = new Block[jArray.length()];

            for (int i = 0; i < jArray.length()-1; i++){
                values[i] = new Block(jArray.optJSONObject(i));
            }
            return values;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public Storable getStorable(String name){

        try {
            JSONObject block = jsonObj.optJSONObject(name);

            String className = block.optString(IDENTIFIER);

            Class<?> JStorClass = Class.forName(className);
            if (JStorClass != null){
                Storable rClass = (Storable)JStorClass.newInstance();
                rClass.loadFromBlock(new Block(block));
                return rClass;
            } else {
                return null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e){
            e.printStackTrace();
            return null;
        }
    }

    public Storable[] getStorableArray(String name){

        try {
            JSONArray blocks = jsonObj.optJSONArray(name);
            Storable[] rClasses = new Storable[blocks.length()];

            for (int i = 0; i < blocks.length()-1; i++) {
                String className = blocks.optJSONObject(i).optString(IDENTIFIER);

                Class<?> JStorClass = Class.forName(className);
                if (JStorClass != null) {
                    Storable rClass = (Storable) JStorClass.newInstance();
                    rClass.loadFromBlock(new Block(blocks.optJSONObject(i)));
                    rClasses[i] = rClass;
                } else {
                    return null;
                }
            }
            return rClasses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<String> namesArray(){

        ArrayList<String> names = new ArrayList<String>();

        Iterator<String> fields = jsonObj.keys();

        while (fields.hasNext()){
            names.add(fields.next());
        }

        return names;
    }

    public JSONObject put(String name, Object value){

        try {
            return jsonObj.put(name, value);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, int value){

        try {
            return jsonObj.put(name, value);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, int[] values){

        try {
            JSONArray buffer = new JSONArray();
            for (int i : values){
                buffer.put(i);
            }
            return jsonObj.put(name, buffer);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, long value){

        try {
            return jsonObj.put(name, value);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, long[] values){

        try {
            JSONArray buffer = new JSONArray();
            for (long l : values){
                buffer.put(l);
            }
            return jsonObj.put(name, buffer);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, double value){

        try {
            return jsonObj.put(name, value);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, double[] values){

        try {
            JSONArray buffer = new JSONArray();
            for (double d : values){
                buffer.put(d);
            }
            return jsonObj.put(name, buffer);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, boolean value){

        try {
            return jsonObj.put(name, value);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, boolean[] values){

        try {
            JSONArray buffer = new JSONArray();
            for (boolean b : values){
                buffer.put(b);
            }
            return jsonObj.put(name, buffer);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, Enum<?> value){

        try {
            return jsonObj.put(name, value.name());
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, Storable obj){

        try {
            Block block = new Block();
            block.put(IDENTIFIER, obj.getClass().getName());
            obj.saveToBlock(block);
            return jsonObj.put(name, block);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, Collection<? extends Storable> objs){

        try {
            JSONArray buffer = new JSONArray();
            for (Storable obj : objs){
                Block block = new Block();
                block.put(IDENTIFIER, obj.getClass().getName());
                buffer.put(block);
                obj.saveToBlock(block);
            }
            return jsonObj.put(name, buffer);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject put(String name, Block value){

        try {
            return jsonObj.put(name, value.jsonObj);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public void remove(String name){
        jsonObj.remove(name);
    }

    public static boolean delete(String fileName){
        File fileToDelete = new File(fileName);

        return fileToDelete.delete();
    }


    //JSON I/O
    public static boolean write(Block jsonObj, String fileName){

        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(fileName+ EXTENSION));

            buf.write(jsonObj.toString());
            buf.close();

            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public static Block read(String fileName){
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName+ EXTENSION));
            StringBuilder jsonString = new StringBuilder();

            String jsonPartial = buf.readLine();

            while (jsonPartial != null){
                jsonString.append(jsonPartial);
                jsonPartial = buf.readLine();
            }
            buf.close();

            return new Block(jsonString.toString());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
