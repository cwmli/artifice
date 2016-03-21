package com.underwaterotter.ceto;

import android.graphics.RectF;

import com.underwaterotter.glesutils.ModelTexture;
import com.underwaterotter.glesutils.TextureCache;
import com.underwaterotter.glesutils.VertexQuad;
import com.underwaterotter.math.Vector3;

import java.nio.FloatBuffer;

public class Image extends Overlay {

    protected float[] vertices;
    protected float[] stVertices;

    protected FloatBuffer vertexBuffer;
    protected FloatBuffer stVertexBuffer;

    protected boolean flipHorizontal;

    public ModelTexture texture;
    protected RectF texRect;

    protected boolean dirty;

    public Image(){
        super();

        texRect = new RectF(0, 1, 1, 0);

        vertices = new float[8];
        stVertices = new float[8];

        vertexBuffer = VertexQuad.genBuffer();
        stVertexBuffer = VertexQuad.genBuffer();

        dirty = true;
    }

    public Image(Image i){
        copy(i);
    }

    public Image(Object bitmap){
        this(new Vector3(0, 0, 0), bitmap);
    }

    public Image(Vector3 pos, Object bitmap){
        this();

        position(pos);
        texture = bitmap instanceof ModelTexture ? (ModelTexture)bitmap : TextureCache.get((String)bitmap);
        textureRect(texture.stRect(0, texture.height, texture.width, 0));
    }

    public void copy(Image img){

        texture = img.texture;
        textureRect(img.texRect);

        dirty = true;
    }

    public void textureRect(RectF rect){

        texRect = rect;

        //calculate width and height based on clipping in texRect, if any
        width = texRect.width() * texture.width;
        height = texRect.height() * texture.height;

        updateSTVertices();
        updateVertices();
    }

    public void textureRect(int left, int top, int width, int height){
        textureRect(texture.stRect(left, top, left + width, top + height));
    }

    @Override
    public void draw(){

        super.draw();

        Renderer renderer = Renderer.get();

        texture.bind();

        renderer.changeCamera(camera());
        renderer.uModel.valueMat4(modelMatrix);
        renderer.mColor.value4f(mr, mg, mb, ma);
        renderer.aColor.value4f(ar, ag, ab, aa);

        if(dirty) {
            vertexBuffer.position(0);
            vertexBuffer.put(vertices);

            stVertexBuffer.position(0);
            stVertexBuffer.put(stVertices);

            dirty = false;
        }

        renderer.drawVQuad(vertexBuffer, stVertexBuffer);
    }

    public void updateSTVertices(){

        if(!flipHorizontal) {
            stVertices[0] = texRect.left;
            stVertices[1] = texRect.top;

            stVertices[2] = texRect.left;
            stVertices[3] = texRect.bottom;

            stVertices[4] = texRect.right;
            stVertices[5] = texRect.top;

            stVertices[6] = texRect.right;
            stVertices[7] = texRect.bottom;
        } else {
            stVertices[0] = texRect.right;
            stVertices[1] = texRect.top;

            stVertices[2] = texRect.right;
            stVertices[3] = texRect.bottom;

            stVertices[4] = texRect.left;
            stVertices[5] = texRect.top;

            stVertices[6] = texRect.left;
            stVertices[7] = texRect.bottom;
        }

        dirty = true;
    }

    public void updateVertices(){

        vertices[0] = 0;
        vertices[1] = 0;

        vertices[2] = 0;
        vertices[3] = height;

        vertices[4] = width;
        vertices[5] = 0;

        vertices[6] = width;
        vertices[7] = height;

        dirty = true;
    }
}
