package com.underwaterotter.artifice.world;


import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.artifice.world.generation.World;

public class TestLevel extends Level {

    public String tiles(){
        return Assets.TEST_TILES;
    }

    public void generate(){
        World.buildWorld(map);
        buildFlags();

        World.smoothMap(map, passable);
        buildFlags();

        World.addLiquids(watermap, passable);
        World.buildLiquidBed(map);
        buildFlags();

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
