package com.underwaterotter.artifice.world;

import android.util.Log;

import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.artifice.world.generation.World;
import com.underwaterotter.math.Magic;
import com.underwaterotter.math.Vector3;

import java.util.Arrays;

public class TestLevel extends Level {

    public String tiles(){
        return Assets.TEST_TILES;
    }

    public void generate(){
        World.buildWorld(map);
        buildFlags();

        World.smoothMap(map, passable);
        buildFlags();

        World.convertTiles(map, passable);
        buildFlags();
    }

    public void decorate(){
        //do decorations
        buildFlags();
    }

    public void prespawnMobs(){
        GameScene.scene.player.worldPosition(
                GameScene.scene.tilemap.cellToWorld(((mapSizeW / 2) * mapSizeW + (mapSizeH / 2))));
    }

    public void prespawnItems(){

    }
}
