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
    public static final String MAP = "backgroundmap";
    public static final String EXPLORED = "explored";
    public static final String PASSABLE = "passable";
    public static final String CLIMBABLE = "climbable";
    public static final String FLAMMABLE = "flammable";
    public static final String UNSTABLE = "unstable";
    public static final String HIDDEN = "hidden";

    public static final int SAFE_SIZE = 10;

    public static boolean overworldGenerated = false;

    public int mapSizeW = 20;
    public int mapSizeH = 20;

    public int safeSizeW = mapSizeW;
    public int safeSizeH = mapSizeH + SAFE_SIZE;

    public int mapLength = mapSizeW * mapSizeH;
    public int safeLength = safeSizeW * safeSizeH;

    //Surrounding cells index
    //0 1 2
    //3 C 4
    //5 6 7
    public int[] SURROUNDING_CELLS = {-safeSizeW - 1, -safeSizeW, -safeSizeW + 1,
                                             -1, + 1,
                                             safeSizeW - 1, safeSizeW, safeSizeW + 1 };

    public boolean isUnderground = false;

    public ItemMapper itemMapper = new ItemMapper();
    public MobMapper mobMapper = new MobMapper();

    public int[] backgroundmap;
    public int[] foregroundmap;
    public int[] watermap;

    public boolean[] explored;

    public boolean[] passable = new boolean[safeLength];
    public boolean[] climbable = new boolean[safeLength];
    public boolean[] flammable = new boolean[safeLength];
    public boolean[] unstable = new boolean[safeLength];

    public void init(){

        itemMapper.init();
        mobMapper.init();

        backgroundmap = new int[safeLength];
        foregroundmap = new int[safeLength];
        watermap = new int[safeLength];

        explored = new boolean[safeLength];
        Arrays.fill(explored, false);

        generate();
        decorate();

        prespawnMobs();
        prespawnItems();
    }

    @Override
    public void saveToBlock(Block block){
        itemMapper.saveToBlock(block);
        mobMapper.saveToBlock(block);

        block.put(UNDERGROUND, isUnderground);

        block.put(OVERWORLD_SUCCESS, overworldGenerated);
        if(!isUnderground)
            block.put(OVERWORLD_MAP, backgroundmap);
        else
            block.put(MAP, backgroundmap);

        block.put(EXPLORED, explored);
        block.put(PASSABLE, passable);
        block.put(CLIMBABLE, climbable);
        block.put(FLAMMABLE, flammable);
        block.put(UNSTABLE, unstable);
    }

    @Override
    public void loadFromBlock(Block block){
        itemMapper.loadFromBlock(block);
        mobMapper.loadFromBlock(block);

        isUnderground = block.getBoolean(UNDERGROUND);

        overworldGenerated = block.getBoolean(OVERWORLD_SUCCESS);
        if(!isUnderground)
            backgroundmap = block.getIntArray(OVERWORLD_MAP);
        else if(overworldGenerated)
            backgroundmap = block.getIntArray(MAP);

        explored = block.getBooleanArray(EXPLORED);
        passable = block.getBooleanArray(PASSABLE);
        climbable = block.getBooleanArray(CLIMBABLE);
        flammable = block.getBooleanArray(FLAMMABLE);
        unstable = block.getBooleanArray(UNSTABLE);
    }

    public void destroy(){
        itemMapper.destroy();
        mobMapper.destroy();
    }

    public abstract void generate();

    public abstract void decorate();

    protected abstract void prespawnMobs();

    protected abstract void prespawnItems();

    public abstract String tiles();

    public void buildFlags(){

        for(int i = 0; i < mapLength; i++){
            int flags = Terrain.flags[backgroundmap[i]];
            passable[i] = (flags & Terrain.PASSABLE) != 0;
            climbable[i] = (flags & Terrain.CLIMBABLE) != 0;
            flammable[i] = (flags & Terrain.FLAMMABLE) != 0;
            unstable[i] = (flags & Terrain.UNSTABLE) != 0;
        }
    }
}
