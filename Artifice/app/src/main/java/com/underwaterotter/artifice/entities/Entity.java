package com.underwaterotter.artifice.entities;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.ceto.Group;
import com.underwaterotter.math.Vector3;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

public class Entity extends Group implements Storable {

    public static String ID = "id";
    public static String TIMER = "timer";
    public static String W_POS = "wpos";
    public static String C_NUM = "cnum";

    protected Vector3 position; //xy position
    protected int cellNumber;        //cell position based in tilemap mapData[]
    protected Level currentLevel;

    protected float timer;

    private int id = 0;

    public Entity(){
        super();
        currentLevel = Artifice.getLevel();
    }

    @Override
    public void update(){
        super.update();
        cellNumber = currentLevel.worldToCell(position);
    }

    @Override
    public void saveToBlock(Block block){
        block.put(TIMER, timer);
        block.put(ID, id);

        double[] wPosArray = { position.x, position.y, position.z };
        block.put(W_POS, wPosArray);

        block.put(C_NUM, cellNumber);
    }

    @Override
    public void loadFromBlock(Block block){
        timer = (float)block.get(TIMER);
        id = block.getInt(ID);

        double[] arrayblock = block.getDoubleArray(W_POS);
        float[] wPosArray = { (float)arrayblock[0], (float)arrayblock[1], (float)arrayblock[2]};
        position = new Vector3(wPosArray);

        cellNumber = block.getInt(C_NUM);
    }

    public int id(){
        return 0;
    }

    public int cellPosition(){
        return cellNumber;
    }

    public Vector3 worldPosition(){
        return position;
    }

    public void worldPosition(Vector3 pos){
        position = pos;
    }

    public Level level(){
        return currentLevel;
    }
}
