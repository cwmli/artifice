package com.underwaterotter.math;

/**
 * Vector4
 * column-major order
 * 0 ~ x
 * 1 ~ y
 * 2 ~ z
 * 3 ~ w
 */
public class Vector4 {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4(){
        x = 0;
        y = 0;
        z = 0;
        w = 0;
    }

    public Vector4(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w =w ;
    }

    public float[] getAsFloat(){
        float[] vec4 = { x, y, z, w };
        return vec4;
    }

    public float distance(Vector4 vec4){
        return (float)Math.sqrt((vec4.x - x) * (vec4.x - x) + (vec4.y - y)* (vec4.y - y) + (vec4.z - z) * (vec4.z - z) + (vec4.w - w) * (vec4.w - w));
    }

    public float length(){
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Vector4 normalize(){
        return new Vector4( x / length(), y / length(), z / length(), w / length());
    }

    public float dot(Vector4 vec4){
        return ((x * vec4.x) + (y * vec4.y) + (z * vec4.z) + (w * vec4.w));
    }
}
