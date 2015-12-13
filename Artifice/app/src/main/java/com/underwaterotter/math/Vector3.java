package com.underwaterotter.math;

/**
 * Vector3
 * column-major order
 * 0 ~ x
 * 1 ~ y
 * 2 ~ z
 */
public class Vector3 {

    public float x;
    public float y;
    public float z;

    public Vector3(){
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float[] xyz){
        x = xyz[0];
        y = xyz[1];
        z = xyz[2];
    }

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float[] getAsFloat(){
        float[] vec3 = { x, y, z};
        return vec3;
    }

    public float distance(Vector3 vec3){
        return (float) Math.sqrt((vec3.x - x) * (vec3.x - x) + (vec3.y - y) * (vec3.y - y) + (vec3.z - z) * (vec3.z - z));
    }

    public float length(){
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 normalize(){
        return new Vector3( x / length(), y / length(), z /length());
    }

    public float dot(Vector3 vec3){
        return ((x * vec3.x) + (y * vec3.y) + (z * vec3.z));
    }

    public Vector3 cross(Vector3 rhsVec3){
        return new Vector3(y * rhsVec3.z - z * rhsVec3.y, z * rhsVec3.x - x * rhsVec3.z, x * rhsVec3.y - y * rhsVec3.x);
    }
}
