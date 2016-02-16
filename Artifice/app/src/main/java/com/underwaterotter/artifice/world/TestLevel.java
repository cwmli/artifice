package com.underwaterotter.artifice.world;

import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.artifice.world.generation.World;

public class TestLevel extends Level {

    public String tiles(){
        return Assets.TEST_TILES;
    }

    public void generate(){
        World.buildWorld(map);
    }

    public void decorate(){

    }

    public void prespawnMobs(){

    }

    public void prespawnItems(){

    }
}
