package com.underwaterotter.glesutils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * fill indexes
 * 01 - 45
 * |     |
 * 23 - 67
 *
 * indexes for triangles
 * 0 - 2    T1 = {0,1,2}                    0 - 2 - 4 - 6 - ...
 * | / |                    ~> EXTENDED     | / | / | / | / |
 * 1 - 3    T2 = {1,3,2}          QUAD      1 - 3 - 5 - 7 - ...
 */

public class VertexQuad {

    public static final int BYTES_PER_FLOAT = Float.SIZE / 8;
    public static final int BYTES_PER_SHORT = Short.SIZE / 8;
    public static final int INDICES_PER_QUAD = 6;
    private static final int OFFSET_TO_NEXT_TRIANGLE = 2;

    private static ShortBuffer indices;
    private static int size;

    //standard 8 xy/st/uv coords for a quad
    public static FloatBuffer genBuffer(){
        FloatBuffer emptyBuf = ByteBuffer
                .allocateDirect(8 * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        return emptyBuf;
    }

    public static FloatBuffer genBuffer(int quads){
        FloatBuffer emptyBuf = ByteBuffer
                                .allocateDirect(quads * 8 * BYTES_PER_FLOAT)
                                .order(ByteOrder.nativeOrder())
                                .asFloatBuffer();

        return emptyBuf;
    }

    public static ShortBuffer quadIndices(int quads){

        if(quads > size) {

            size = quads;
            indices = ByteBuffer
                    .allocateDirect(quads * INDICES_PER_QUAD * BYTES_PER_SHORT)
                    .order(ByteOrder.nativeOrder())
                    .asShortBuffer();

            short[] buffer = new short[quads * INDICES_PER_QUAD];

            int index = 0;

            for (int i = 0; i < quads * 4; i+= 4) {
                buffer[index++] = (short) (i + 0); //0,4,8,...
                buffer[index++] = (short) (i + 1); //1,3,5,...
                buffer[index++] = (short) (i + 2); //2,4,6,...
                buffer[index++] = (short) (i + 1); //1,3,5,...
                buffer[index++] = (short) (i + 3); //3,5,7,...
                buffer[index++] = (short) (i + 2); //2,4,6,...
            }

            indices.put(buffer);
            indices.position(0);
        }

        return indices;
    }

    public static void fill(float[] vertices, float x1, float y1, float x2, float y2, int offset){
        //Top Left
        vertices[0 + offset] = x1;
        vertices[1 + offset] = y1;

        //Top Right
        vertices[2 + offset] = x2;
        vertices[3 + offset] = y1;

        //Bottom Right
        vertices[4 + offset] = x1;
        vertices[5 + offset] = y2;

        //Bottom Left
        vertices[6 + offset] = x2;
        vertices[7 + offset] = y2;
    }
}
