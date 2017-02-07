package com.underwaterotter.glesutils;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.underwaterotter.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;


//Populate method indexes as follows: (sample 4x4 cells)
// 0  1  2  3
// 4  5  6  7
// 8  9  10 11
// 12 13 14 15

public class TextureAtlas {

    private ModelTexture texture;

    private HashMap<Object, RectF> cells;

    public TextureAtlas(Object texture){

        this.texture = texture instanceof ModelTexture ? (ModelTexture)texture : TextureCache.get(texture);
        cells = new HashMap<Object, RectF>();
    }

    public void bind() {
        texture.bind();
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public Bitmap getTextureBitmap() {
        return texture.getBitmap();
    }

    public RectF getCell(Object o) {
        return cells.get(o);
    }

    public void addCell(Object o, RectF rect) {
        cells.put(o, rect);
    }

    public TextureAtlas populateFixedCells(int cellWidth, int cellHeight){
        return populateFixedCells(cellWidth, cellHeight, null);
    }

    public TextureAtlas populateFixedCells(int cellWidth, int cellHeight, char[] ids){

        int cols = texture.getWidth() / cellWidth;

        for(int r = 0; r < texture.getHeight() / cellHeight; r++){

            for(int c = 0; c < cols; c++){

                if(ids != null) {
                    cells.put(
                            ids[r * cols + c],
                            texture.stRect(c * cellWidth,
                                    r * cellHeight,
                                    c * cellWidth + cellWidth,
                                    r * cellHeight + cellHeight)
                    );
                } else {
                    cells.put(
                            r * cols + c,
                            texture.stRect(c * cellWidth,
                                    r * cellHeight,
                                    c * cellWidth + cellWidth,
                                    r * cellHeight + cellHeight)
                    );
                }
            }
        }

            return this;
    }

    public void populateFreeCells(int width, int rowHeight, int offsetW, int color){
       populateFreeCells(width, rowHeight, offsetW, color, null);
    }

    //construct RectFs based on one pixel wide separations
    public void populateFreeCells(int width, int height, int offsetW, int color, char[] ids){

        ArrayList<Vector2> points = new ArrayList<Vector2>();

        int counter = 0;

        int rows = texture.getHeight() / height;

        for(int row = 0; row < rows; row++){

            for(int x = (0 + offsetW); x < width; x++){

                Vector2 separatorPt = new Vector2(x, row * height);

                for(int y = row * height; y < (row * height) + height; y++){
                    if(texture.getBitmap().getPixel(x, y) == color){
                        separatorPt = null;
                        break;
                    }
                }

                if(separatorPt != null) {
                    points.add(separatorPt);

                    if (points.size() == 2) {

                        Vector2 ptOne = points.get(0);
                        Vector2 ptTwo = points.get(1);

                        points.clear();

                        points.add(ptTwo);

                        if (ids == null) {
                            cells.put(
                                    counter++,
                                    texture.stRect((int) ptOne.x,
                                            (int) ptOne.y,
                                            (int) ptTwo.x,
                                            (int) ptTwo.y + height) //ptOne.y + height is equivalent
                            );
                        } else {
                            if (++counter > ids.length) {
                                return;
                            }
                            cells.put(
                                    ids[counter],
                                    texture.stRect((int) ptOne.x,
                                            (int) ptOne.y,
                                            (int) ptTwo.x,
                                            (int) ptTwo.y + height)
                            );
                        }

                        ptOne = null;
                        ptTwo = null;
                    }
                }
            }
        }
    }

    public void add(Object id, RectF stRect){
        cells.put(id, stRect);
    }

    public void remove(Object id){
        cells.remove(id);
    }

    public RectF get(Object id){
        return cells.get(id);
    }

    public Bitmap getAsBitmap(Object id){

        RectF cellBox = cells.get(id);

        int x = (int)cellBox.left * texture.getWidth();
        int y = (int)cellBox.top * texture.getHeight();
        int w = (int)cellBox.height() * texture.getHeight();
        int h = (int)cellBox.width() * texture.getWidth();

        int[] colorPortion = new int[w * h];

        texture.getBitmap().getPixels(colorPortion, 0, texture.getWidth(), x, y, w, h);

        return Bitmap.createBitmap(colorPortion, w, h, Bitmap.Config.ARGB_8888);
    }

    //return the width/height in PIXELS (not normalized values)
    public int width(RectF rect){
        return (int)(rect.width() * texture.getWidth());
    }

    public int height(RectF rect){
        return (int)(rect.height() * texture.getHeight());
    }
}
