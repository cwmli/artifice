package com.underwaterotter.math;

/**
 * Vector2
 * column-major order
 * 0 ~ x
 * 1 ~ y
 */
public class Vector2 {

    public float x;
    public float y;

    public Vector2(){
        x = 0.0f;
        y = 0.0f;
    }

    public Vector2(float[] f){
        x = f[0];
        y = f[1];
    }

    public Vector2( float x, float y){
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 vec2){
        set(vec2.x, vec2.y);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    //returns this vector information as a float array
    public float[] toArray(){
        float[] vec2 = { x, y };
        return vec2;
    }

    public Vector2 difference(Vector2 vec2){
        return new Vector2(x - vec2.x, y - vec2.y);
    }

    public float distance(Vector2 vec2){
        return (float)Math.sqrt((vec2.x - x) * (vec2.x - x) + (vec2.y - y) * (vec2.y - y));
    }

    public float length(){
        return (float)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public Vector2 normalize(){
        return new Vector2( x / length(), y / length());
    }

    public double dot(Vector2 vec2){
        return ((x * vec2.x) + (y * vec2.y));
    }

    public double det(Vector2 vec2) {
        return x * vec2.y - y * vec2.x;
    }
}
