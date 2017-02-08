package com.underwaterotter.utils;

import java.util.ArrayList;

public class Listener<T> {

    private ArrayList<Trigger<T>> listeners = new ArrayList<Listener.Trigger<T>>();

    public boolean matched = false;

    public void add(Trigger<T> trigger){
        if(!listeners.contains(trigger)) {
            listeners.add(trigger);
        }
    }

    public void remove(Trigger<T> trigger){
        listeners.remove(trigger);
    }

    public void clear(){
        listeners.clear();
    }

    public void processTrigger(T t){

        if (!listeners.isEmpty()) {
            for (int i = 0; i < listeners.size(); i++) {
                Trigger<T> trigger = listeners.get(i);
                if (trigger.work && !matched) {
                    trigger.onCall(t);
                }
            }
        }
    }

    public interface Trigger<T> {
        boolean work = true;

        void onCall(T event);
    }
}
