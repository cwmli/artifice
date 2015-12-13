package com.underwaterotter.artifice.entities;

import com.underwaterotter.ceto.Article;
import com.underwaterotter.math.Vector3;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

public class Entity extends Article implements Storable {

    public static String ID = "id";
    public static String TIMER = "timer";
    public static String W_POS = "wpos";
    public static String C_NUM = "cnum";

    protected Vector3 worldPosition; //xy position
    protected int cellNumber; //cell position based in tilemap mapData[]

    protected float timer;

    private int id = 0;

    @Override
    public void saveToBlock(Block block){
        block.put(TIMER, timer);
        block.put(ID, id);

        double[] wPosArray = { worldPosition.x, worldPosition.y, worldPosition.z };
        block.put(W_POS, wPosArray);

        block.put(C_NUM, cellNumber);
    }

    @Override
    public void loadFromBlock(Block block){
        timer = (float)block.get(TIMER);
        id = block.getInt(ID);

        double[] arrayblock = block.getDoubleArray(W_POS);
        float[] wPosArray = { (float)arrayblock[0], (float)arrayblock[1], (float)arrayblock[2]};
        worldPosition = new Vector3(wPosArray);

        cellNumber = block.getInt(C_NUM);
    }

    public int id(){
        return 0;
    }

    public int cellPosition(){
        return cellNumber;
    }

    public void cellPosition(int pos){
        cellNumber = pos;
    }

    public Vector3 worldPosition(){
        return worldPosition;
    }

    public void worldPosition(Vector3 pos){
        worldPosition = pos;
    }
}
