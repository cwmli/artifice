package com.underwaterotter.artifice.world;


import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.artifice.world.generation.World;
import com.underwaterotter.math.Rand;

public class TestLevel extends Level {

    public String tiles(){
        return Assets.TEST_TILES;
    }

    public void generate(){
        World coarsemap = new World(mapSizeW, mapSizeH, Rand.range(0,65536l),World.AMP, World.RISE, World.DROP,
                World.F1, World.F2, World.F3, World.SMOOTH, World.REDIS);

        World.convertTiles(map, watermap, passable);
        buildFlags();

        World.addWorldLayers(map, maplayer);
    }

    public void decorate(){
        //do decorations
        buildFlags();
    }

    public void prespawnMobs(){
        GameScene.scene.player.worldPosition(
                GameScene.scene.tilemap.cellToWorld((int)Math.floor((map.length) / 2) + 2));
    }

    public void prespawnItems(){

    }
}
