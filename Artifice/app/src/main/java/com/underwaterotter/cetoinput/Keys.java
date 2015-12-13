package com.underwaterotter.cetoinput;

import android.view.KeyEvent;

import com.underwaterotter.utils.Listener;

import java.util.ArrayList;

public class Keys {

    public static Listener<Key> keyListeners = new Listener<Key>();

    public static void processKeyEvents( ArrayList<KeyEvent> events){

        for(KeyEvent e : events){
            int action = e.getAction();

            switch (action){
                case KeyEvent.ACTION_DOWN:
                    keyListeners.processTrigger(new Key(e.getKeyCode(), true));
                    break;
                case KeyEvent.ACTION_UP:
                    keyListeners.processTrigger(new Key(e.getKeyCode(), false));
                    break;
            }
        }
    }

    public static class Key{

        private int keycode;
        private boolean keydown;

        public Key(int keycode, boolean keydown){
            this.keycode = keycode;
            this.keydown = keydown;
        }

        public int getKeycode(){
            return keycode;
        }

        public boolean isKeyDown(){
            return keydown;
        }
    }
}
