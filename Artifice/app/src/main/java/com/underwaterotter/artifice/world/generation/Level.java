package com.underwaterotter.artifice.world.generation;

import com.underwaterotter.artifice.entities.MobMapper;
import com.underwaterotter.artifice.entities.items.ItemMapper;
import com.underwaterotter.artifice.world.Terrain;
import com.underwaterotter.utils.Block;
import com.underwaterotter.utils.Storable;

import java.util.Arrays;

public abstract class Level implements Storable {

    public static final String UNDERGROUND = "underground";
    public static final String MAP = "map";
    public static final String EXPLORED = "explored";
    public static final String PASSABLE = "passable";
    public static final String CLIMBABLE = "climbable";
    public static final String FLAMMABLE = "flammable";
    public static final String UNSTABLE = "unstable";
    public static final String HIDDEN = "hidden";

    public int mapSize_W = 64;
    public int mapSize_H = 64;

    public int mapLength = mapSize_W * mapSize_H;

    //Surrounding cells index
    //0 1 2
    //3 C 4
    //5 6 7
    public int[] SURROUNDING_CELLS = {-mapSize_W - 1, -mapSize_W, -mapSize_W + 1,
                                             -1, + 1,
                                             mapSize_W - 1, mapSize_W, mapSize_W + 1 };

    public boolean underground;

    public ItemMapper im;
    public MobMapper mm;

    public Map map;
    public boolean[] explored;

    public boolean[] passable = new boolean[mapLength];
    public boolean[] climbable = new boolean[mapLength];
    public boolean[] flammable = new boolean[mapLength];
    public boolean[] unstable = new boolean[mapLength];

    public void init(){

        map = new Map(mapLength);
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

        block.put(UNDERGROUND, underground);

        block.put(MAP, map.data);
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

        underground = block.getBoolean(UNDERGROUND);

        map.data = block.getIntArray(MAP);
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
            int flags = Terrain.flags[map.data[i]];
            passable[i] = (flags & Terrain.PASSABLE) != 0;
            climbable[i] = (flags & Terrain.CLIMBABLE) != 0;
            flammable[i] = (flags & Terrain.FLAMMABLE) != 0;
            unstable[i] = (flags & Terrain.UNSTABLE) != 0;
        }
    }

    public static class Map{
        private int[] data;

        public Map(int length){
            data = new int[length];
        }

        public void data(int[] map){
            this.data = map;
        }

        public void fill(int id){
            Arrays.fill(data, id);
        }

        public void add(int pos, int id){
            data[pos] = id;
        }
    }
}
