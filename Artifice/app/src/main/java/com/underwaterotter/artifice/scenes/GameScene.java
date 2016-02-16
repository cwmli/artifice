package com.underwaterotter.artifice.scenes;

import com.underwaterotter.artifice.Artifice;
import com.underwaterotter.ceto.Group;
import com.underwaterotter.artifice.WorldTilemap;
import com.underwaterotter.artifice.entities.Mob;
import com.underwaterotter.artifice.entities.items.Item;
import com.underwaterotter.artifice.entities.main.Char;
import com.underwaterotter.artifice.sprites.ItemSprite;
import com.underwaterotter.artifice.sprites.MobSprite;

public class GameScene extends UIScene {

    public static GameScene scene;

    public WorldTilemap tilemap;

    public Char player;

    private Group world;
    private Group liquid;
    private Group weather;
    private Group mobs;
    private Group items;
    private Group fauna;
    private Group pouches;

    public void create(){
        //pre-init level setup
        if(Artifice.depth < 0){
            Artifice.level.isUnderground = true;
        }
        Artifice.level.init();

        tilemap = new WorldTilemap();

        world = new Group();
        add(world);

        liquid = new Group();
        add(liquid);

        weather = new Group();
        add(weather);

        world.add(tilemap);
        world.add(liquid);
        world.add(weather);

        mobs = new Group();

        for(Mob m : Artifice.level.mm.mobs.values()){
            addMob(m);
        }
        add(mobs);

        items = new Group();

        for(Item i : Artifice.level.im.items.values()){
            addItem(i);
        }
        add(items);

        fauna = new Group();
        add(fauna);

        pouches = new Group();
        add(pouches);
    }

    @Override
    public void destroy(){

        scene = null;

        super.destroy();
    }

    @Override
    public synchronized void update(){
        super.update();



        mobs.update();

        items.update();
    }

    public void addMob(Mob mob){
        MobSprite s = mob.sprite;
        s.visible = s.isVisible();
        mobs.add(mob);
        s.setMob(mob);
    }

    public void addItem(Item item){
        ItemSprite s = item.sprite;
        s.visible = s.isVisible();
        items.add(item);
    }

    public static void add(Mob mob){
        Artifice.level.mm.add(mob);
        scene.addMob(mob);
    }

    public static void add(Item item){
        Artifice.level.im.add(item);
        scene.addItem(item);
    }

    public static void exploreCell(int cell){
        if(scene != null){
            scene.tilemap.explore(cell);
        }
    }
}
