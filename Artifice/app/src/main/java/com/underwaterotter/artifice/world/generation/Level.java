package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.entities.MobMapper;
import com.underwaterotter.artifice.entities.items.ItemMapper;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

import java.util.Arrays;

public abstract class Level implements Storable {

    public static final String UNDERGROUND = "underground";
    public static final String OVERWORLD_MAP = "overworld";
    public static final String OVERWORLD_SUCCESS = "overworld_gen";
    public static final String MAP = "map";
    public static final String EXPLORED = "explored";
    public static final String PASSABLE = "passable";
    public static final String CLIMBABLE = "climbable";
    public static final String FLAMMABLE = "flammable";
    public static final String UNSTABLE = "unstable";
    public static final String HIDDEN = "hidden";

    public static boolean overworldGenerated = false;

    public int mapSizeW = 64;
    public int mapSizeH = 64;

    //public int mapSizeW = 10;
    //public int mapSizeH = 10;

    public int mapLength = mapSizeW * mapSizeH;

    //Surrounding cells index
    //0 1 2
    //3 C 4
    //5 6 7
    public int[] SURROUNDING_CELLS = {-mapSizeW - 1, -mapSizeW, -mapSizeW + 1,
                                             -1, + 1,
                                             mapSizeW - 1, mapSizeW, mapSizeW + 1 };

    public boolean isUnderground = false;

    public ItemMapper im = new ItemMapper();
    public MobMapper mm = new MobMapper();

    public int[] map;
    public boolean[] explored;

    public boolean[] passable = new boolean[mapLength];
    public boolean[] climbable = new boolean[mapLength];
    public boolean[] flammable = new boolean[mapLength];
    public boolean[] unstable = new boolean[mapLength];

    public void init(){

        map = new int[mapLength];
        explored = new boolean[mapLength];
        Arrays.fill(explored, false);

        im.init();
        mm.init();

        generate();
        decorate();

        buildFlags();

        prespawnMobs();
        prespawnItems();
    }

    @Override
    public void saveToBlock(Block block){
        im.saveToBlock(block);
        mm.saveToBlock(block);

        block.put(UNDERGROUND, isUnderground);

        block.put(OVERWORLD_SUCCESS, overworldGenerated);
        if(!isUnderground)
            block.put(OVERWORLD_MAP, map);
        else
            block.put(MAP, map);

        block.put(EXPLORED, explored);
        block.put(PASSABLE, passable);
        block.put(CLIMBABLE, climbable);
        block.put(FLAMMABLE, flammable);
        block.put(UNSTABLE, unstable);
    }

    @Override
    public void loadFromBlock(Block block){
        im.loadFromBlock(block);
        mm.loadFromBlock(block);

        isUnderground = block.getBoolean(UNDERGROUND);

        overworldGenerated = block.getBoolean(OVERWORLD_SUCCESS);
        if(!isUnderground)
            map = block.getIntArray(OVERWORLD_MAP);
        else if(overworldGenerated)
            map = block.getIntArray(MAP);

        explored = block.getBooleanArray(EXPLORED);
        passable = block.getBooleanArray(PASSABLE);
        climbable = block.getBooleanArray(CLIMBABLE);
        flammable = block.getBooleanArray(FLAMMABLE);
        unstable = block.getBooleanArray(UNSTABLE);
    }

    public void destroy(){
        im.destroy();
        mm.destroy();
    }

    public abstract void generate();

    public abstract void decorate();

    protected abstract void prespawnMobs();

    protected abstract void prespawnItems();

    public abstract String tiles();

    public void buildFlags(){

        for(int i = 0; i < mapLength; i++){
            int flags = Terrain.flags[map[i]];
            passable[i] = (flags & Terrain.PASSABLE) != 0;
            climbable[i] = (flags & Terrain.CLIMBABLE) != 0;
            flammable[i] = (flags & Terrain.FLAMMABLE) != 0;
            unstable[i] = (flags & Terrain.UNSTABLE) != 0;
        }
    }
}
