package com.underwaterotter.ceto;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;

import com.underwaterotter.glesutils.TextureAtlas;
import com.underwaterotter.glesutils.VertexQuad;
import com.underwaterotter.math.Vector2;
import com.underwaterotter.math.Vector3;

import java.nio.FloatBuffer;

public class Text extends Overlay {

    protected String[] lines;
    protected int lineSpacing;
    protected Font font;

    protected float[] vertices = new float[8];
    protected float[] stVertices = new float[8];

    protected FloatBuffer vertexBuffer;
    protected FloatBuffer stVertexBuffer;

    public int textLength;

    protected boolean dirty = true;

    private static String BY_NEWLINE = System.getProperty("line.separator");

    public Text(String lines, Font font){
        super();

        this.lines = lines.split(BY_NEWLINE);
        this.font = font;
    }

    public String text(){

        StringBuilder sb = new StringBuilder();
        for(String s : lines){
            sb.append(s);
        }

        return sb.toString();
    }

    public void text(String text){
        this.lines = text.split(BY_NEWLINE);
    }

    public Font font(){
        return font;
    }

    public void font(Font font){
        this.font = font;
        dirty = true;
    }

    public int lineSpacing(){
        return lineSpacing;
    }

    public void lineSpacing(int lineSpacing){
        this.lineSpacing = lineSpacing;
    }

    public int length(){

        int len = 0;
        for(String s : lines){
            len += s.length();
        }

        return len;
    }

    @Override
    public void draw(){

        super.draw();

        Renderer renderer = Renderer.get();

        font.fontDir.texture.bind();

        renderer.changeCamera(camera());

        renderer.uModel.valueMat4(modelMatrix);
        renderer.mColor.value4f(mr, mg, mb, ma);
        renderer.aColor.value4f(ar, ag, ab, aa);

        if(dirty){
            updateAllVertices();
            dirty = false;
        }

        renderer.drawVQuads(vertexBuffer, stVertexBuffer, length());
    }

    public void updateAllVertices() {


        vertexBuffer = VertexQuad.genBuffer(length());
        stVertexBuffer = VertexQuad.genBuffer(length());

        textLength = 0;
        for (int i = 0; i < lines.length; i++) {
            width = 0;
            for (int c = 0; c < lines[i].length(); c++) {
                RectF charRect = font.get(lines[i].charAt(c));

                if(charRect == null){
                    charRect = null;
                }

                int lineOffset = font.lineHeight * i;// + (i * lineSpacing);
                height = lineOffset;
                int pxSpacing;

                //Used mainly for bitmapped characters
                if (c > 0 && font.charSpacing != 0) {
                    pxSpacing = font.fontSpacing(font.fontDir.getAsBitmap(lines[i].charAt(c - 1)), font.fontDir.getAsBitmap(lines[i].charAt(c)));
                } else {
                    pxSpacing = 0;
                }

                float pxW = font.width(charRect);
                float pxH = font.height(charRect);

                vertices[0] = width;
                vertices[1] = lineOffset;

                vertices[2] = width;
                vertices[3] = pxH + lineOffset;

                vertices[4] = pxW + width;
                vertices[5] = lineOffset;

                vertices[6] = pxW + width;
                vertices[7] = pxH + lineOffset;

                stVertices[0] = charRect.left;
                stVertices[1] = charRect.top;

                stVertices[2] = charRect.left;
                stVertices[3] = charRect.bottom;

                stVertices[4] = charRect.right;
                stVertices[5] = charRect.top;

                stVertices[6] = charRect.right;
                stVertices[7] = charRect.bottom;

                stVertexBuffer.put(stVertices);
                vertexBuffer.put(vertices);

                width += pxW;

                textLength++;
            }
        }
    }

    public static class Font{

        public static char[] LATIN_SCRIPT_S = " !\"#$%&'()*+,-./0123456789:;<=>?@[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007f".toCharArray();

        public static char[] LATIN_SCRIPT_U = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`{|}~\u007f".toCharArray();

        public static char[] LATIN_SCRIPT_F = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~".toCharArray();

        TextureAtlas fontDir;

        public int sfontColor;

        public int baseline;
        public int lineHeight;

        protected int charSpacing;

        public Font(Object source, int color) {
            fontDir = new TextureAtlas(source);

            charSpacing = 0;
            sfontColor = color;
        }

        public int getSpacing(){
            return charSpacing;
        }

        public void setSpacing(int spc){
            charSpacing = spc;
        }

        public RectF get(char c){
            return fontDir.get(c);
        }

        public float width(RectF rect){
            return fontDir.width(rect);
        }

        public float height(RectF rect){
            return fontDir.height(rect);
        }

        public void splitFontBitmap(int cellWidth, int cellHeight, char[] latinType){
            fontDir.populateFixedCells(cellWidth, cellHeight, latinType);
        }

        public void splitFont(char[] latinType){

            int offsetX;

            int width = fontDir.texture.width;
            int height = fontDir.texture.height;

            checkSpaceGlyph:
            for(offsetX = 0; offsetX < width; offsetX++){
                for(int y = 0; y < height; y++){
                    if(fontDir.texture.bitmap.getPixel(offsetX,y) == Color.WHITE){
                        break checkSpaceGlyph;
                    }
                }
            }

            fontDir.add(" ", new RectF(0, 0, (float) offsetX / width, height));

            for (int i=0; i < latinType.length; i++) {

                char ch = latinType[i];
                if (ch == ' ') {
                    continue;
                } else {

                    boolean found;
                    int separator = offsetX;

                    do {
                        if (++separator >= width) {
                            break;
                        }
                        found = true;
                        for (int j=0; j < height; j++) {
                            if (fontDir.texture.bitmap.getPixel( separator, j ) != Color.TRANSPARENT) {
                                found = false;
                                break;
                            }
                        }
                    } while (!found);

                    fontDir.cells.put( ch, new RectF( (float)offsetX / width, 0, (float)separator / width, height ) );
                    offsetX = separator + 1;
                }
            }

            //fontDir.populateFreeCells(fontDir.texture.width, fontDir.texture.height, offsetX, Color.BLACK, latinType);

            lineHeight = fontDir.texture.height;
        }

        public static Font build(Object bmp, int color, char[] latinType){
            Font font = new Font(bmp, color);
            font.splitFont(latinType);

            return font;
        }

        public int fontSpacing(Bitmap prev, Bitmap current) {

            Vector2 leftGlyphPt = null;
            Vector2 rightGlyphPt = null;

            //get rightmost pixel of previous, begin search on right side
            for (int col = prev.getWidth(); col > 0; col--) {
                for (int row = 0; row < prev.getHeight(); row++) {
                    if (prev.getPixel(col, row) == sfontColor) {
                        leftGlyphPt = new Vector2(col, row);
                    }
                }
            }

            //get leftmost pixel of current, begin search on left side
            for (int col = 0; col < current.getWidth(); col++) {
                for (int row = 0; row < current.getHeight(); row++) {
                    if (current.getPixel(col, row) == sfontColor) {
                        rightGlyphPt = new Vector2(col + prev.getWidth(), row);
                    }
                }
            }

            if (leftGlyphPt != null && rightGlyphPt != null) {
                return charSpacing - (int)rightGlyphPt.distance(leftGlyphPt);
            } else {
                return 0;
            }
        }
    }
}
