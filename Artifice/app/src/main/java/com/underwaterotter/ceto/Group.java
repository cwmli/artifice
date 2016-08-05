package com.underwaterotter.ceto;

import java.util.ArrayList;

/**
 * Scene graph group node
 */
public class Group extends Article {
    protected ArrayList<Article> entities;

    public Group(){
        entities = new ArrayList<Article>();
    }

    @Override
    public void update(){

        for (int i = 0; i < entities.size(); ++i){
            Article e = entities.get(i);
            if (e != null && e.exists && e.awake){
                e.update();
            }
        }
    }

    @Override
    public void draw(){

        for (int i = 0; i < entities.size(); ++i){
            Article e = entities.get(i);
            if (e != null && e.exists && e.visible){
                e.draw();
            }
        }
    }

    @Override
    public void destroy(){
        for (int i = 0; i < entities.size(); ++i){
            Article e = entities.get(i);
            if (e != null) {
                e.destroy();
            }
        }
        entities.clear();
        entities = null;
    }

    @Override
    public void kill(){ //equivalent of disabling this group and all members

        for (int i = 0; i < entities.size(); ++i){
            Article e = entities.get(i);
            if (e != null && e.exists) {
                e.kill();
            }
        }
        super.kill();
    }

    public Article insert(Article e) { //inserts to front of arraylist

        if (e.parent == this){
            return  e;
        }

        if (e.parent != null) { //clear previous group
            e.parent.entities.remove(e);
        }

        e.parent = this;
        entities.add(0, e);
        return e;
    }

    public int indexOf(Article e) {
        return entities.indexOf(e);
    }

    public Article add(Article e) { //singular

        if (e.parent == this){
            return  e;
        }

        if (e.parent != null) { //clear previous group
            e.parent.entities.remove(e);
        }

        e.parent = this;
        entities.add(e);
        return  e;
    }

    public Article remove(Article e) {

        if (entities.remove(e)){
            e.parent = null;
            return e;
        } else {
            return null;
        }
    }

    public int livingEntities(){

        int counter = 0;

        for(Article e : entities){
            if(e.active){
                counter++;
            }
        }

        return counter;
    }

    public Article sendToBack(Article e) {

        if (!entities.contains(e)){
            return null;
        }

        if (entities.remove(e)){
            entities.add(e);
            return e;
        }
        return null;
    }

    public Article bringToFront(Article e){

        if (!entities.contains(e)){
            return null;
        }

        if (entities.remove(e)){
            entities.add(0, e);
            return e;
        }
        return null;
    }
}
