package com.underwaterotter.glesutils;

import com.underwaterotter.gleswrap.Program;

import java.util.HashMap;

/**
 * Manages work opengles GLSLShaders
 */
public class GLSL extends Program{

    private static final HashMap<Class<? extends GLSL>, GLSL> GLSLShaders = new HashMap<Class<? extends GLSL>, GLSL>();

    private static GLSL activeProgram = null;
    private static Class<? extends  GLSL> activeProgramClass = null;

    @SuppressWarnings("unchecked")
    public static<T extends GLSL> T use(Class<T> clName){

        if (clName != activeProgramClass) {

            GLSL script = GLSLShaders.get(clName);
            if (script == null) {
                try {
                    script = clName.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GLSLShaders.put(clName, script);
            }

            activeProgram = script;
            activeProgramClass = clName;
            activeProgram.use();
        }

        return (T)activeProgram;
    }

    public static void reset(){

        for (Program program : GLSLShaders.values()){
            program.delete();
        }
        GLSLShaders.clear();

        activeProgram = null;
        activeProgramClass = null;
    }
}
