package com.underwaterotter.ceto;

import android.view.KeyEvent;

import com.underwaterotter.cetoinput.Keys;
import com.underwaterotter.utils.Listener;

public class Scene extends Group {

    private Listener.Trigger<Keys.Key> keyTrigger;

    public void create(){

        keyTrigger = new Listener.Trigger<Keys.Key>() {
            @Override
            public void onCall(Keys.Key event) {
                if(Game.instance != null && event.isKeyDown()){
                    switch (event.getKeycode()){
                        case KeyEvent.KEYCODE_BACK:
                            onBackPressed();
                            break;
                        case KeyEvent.KEYCODE_MENU:
                            onMenuPressed();
                            break;
                    }
                }
            }
        };

        Keys.keyListeners.add(keyTrigger);
    }

    @Override
    public void destroy(){
        Keys.keyListeners.remove(keyTrigger);
        super.destroy();
    }

    public void pause(){}

    public void resume(){}

    @Override
    public void update(){
        super.update();
    }

    @Override
    public Camera camera() {
        return Camera.main;
    }

    protected void onBackPressed() {
        Game.instance.finish();
    }

    protected void onMenuPressed() {}
}
