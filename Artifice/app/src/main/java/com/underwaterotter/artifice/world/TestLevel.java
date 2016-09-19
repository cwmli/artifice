package com.underwaterotter.artifice.world;


import com.underwaterotter.artifice.scenes.GameScene;
import com.underwaterotter.artifice.world.generation.Level;
import com.underwaterotter.artifice.world.generation.Map;
import com.underwaterotter.math.Rand;

public class TestLevel extends Level {

    @Override
    public void init(){
        super.init();

        tilemap = new AnimatedTilemap(Assets.TEST_TILES, this,
                WorldTilemap.TILEMAP.LAND, 2) {
            @Override
            protected void setTileAnimations() {

            }
        };

        foretilemap = new AnimatedTilemap(Assets.TEST_TILES, this,
                WorldTilemap.TILEMAP.FOREGROUND, 2) {
            @Override
            protected void setTileAnimations() {

            }
        };

        watertilemap = new AnimatedTilemap(Assets.TEST_TILES, this,
                WorldTilemap.TILEMAP.WATER, 2) {

            @Override
            protected void setTileAnimations() {
                int[] dwater = {
                        Terrain.DWATER_1, Terrain.DWATER_2, Terrain.DWATER_3, Terrain.DWATER_2};
                tileAnimations.put(Terrain.DWATER_1, dwater);
                tileAnimations.put(Terrain.DWATER_2, dwater);
                tileAnimations.put(Terrain.DWATER_3, dwater);

                int[] twater = {
                        Terrain.TWATER_1, Terrain.TWATER_2, Terrain.TWATER_3, Terrain.TWATER_2};
                tileAnimations.put(Terrain.TWATER_1, twater);
                tileAnimations.put(Terrain.TWATER_2, twater);
                tileAnimations.put(Terrain.TWATER_3, twater);

                frames = 4;
            }
        };

        tilemaps.add(tilemap);
//        tilemaps.add(foretilemap);
        tilemaps.add(watertilemap);

        generate();
        decorate();

        prespawnMobs();
        prespawnItems();
    }

    public void generate(){
        Map heightmap = new Map(
                mapWidth, mapHeight, 0,
                Map.AMP, Map.RISE, Map.DROP,
                Map.F1, Map.F2, Map.F3,
                Map.EOCT1, Map.EOCT2, Map.EOCT3,
                Map.SMOOTH, Map.REDIS);
        heightmap.build();

        int[][] displaymap = heightmap.getDisplayMaps();

        map = displaymap[0];
        watermap = displaymap[1];

        buildFlags();
    }

    public void decorate(){
        //do decorations
        buildFlags();
    }

    public void prespawnMobs(){
        GameScene.scene.getPlayer().worldPosition(
                tilemap.cellToWorld(
                        (int)Math.floor((map.length) / 2) + 2));
    }

    public void prespawnItems(){

    }
}
