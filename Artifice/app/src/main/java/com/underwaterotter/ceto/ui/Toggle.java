package com.underwaterotter.ceto.ui;


public class Toggle extends Button {

    public boolean active;

    public Toggle() {
        super();
        active = false;
    }

    protected void onTouch(){
    };

    protected void onRelease(){

    }

    public void onClick(){
        if(!active){
            active = true;
        } else {
            active = false;
        }
    }
}
