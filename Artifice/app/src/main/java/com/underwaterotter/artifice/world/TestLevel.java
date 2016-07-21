package com.underwaterotter.artifice.world;


import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.artifice.world.generation.Map;
import com.underwaterotter.math.Rand;

public class TestLevel extends Level {

    public String tiles(){
        return Assets.TEST_TILES;
    }

    public void generate(){
        Map heightmap = new Map(
                mapSizeW, mapSizeH, Rand.range(0,65536l),
                Map.AMP, Map.RISE, Map.DROP,
                Map.F1, Map.F2, Map.F3,
                Map.EOCT1, Map.EOCT2, Map.EOCT3,
                Map.SMOOTH, Map.REDIS);
        heightmap.build();

        int[][] displaymap = heightmap.generateDisplayMap(safeSizeW, safeSizeH);

        foregroundmap = displaymap[0];
        backgroundmap = displaymap[1];
        watermap = displaymap[2];

    }

    public void decorate(){
        //do decorations
        buildFlags();
    }

    public void prespawnMobs(){
        GameScene.scene.player.worldPosition(
                GameScene.scene.tilemap.cellToWorld((int)Math.floor((backgroundmap.length) / 2) + 2));
    }

    public void prespawnItems(){

    }
}
