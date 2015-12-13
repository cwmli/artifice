package com.underwaterotter.artifice;

public class Preferences {

    public static class DEFAULT{
        public static final String UISCALE = UI.AUTO;
        public static final String GRAPHICS = GFX.NORMAL;
        public static final boolean IMMERSIVE = false;
        public static final boolean SOUND = true;
        public static final boolean MUSIC = true;
        public static final boolean GOOGLEPLAY = true;
    }

    public static class UI{
        public static final String SMALL = "small";
        public static final String MED = "med";
        public static final String HIGH = "high";
        public static final String AUTO = "auto";
    }

    public static class GFX{
        public static final String LOW = "low";
        public static final String NORMAL = "normal";
        public static final String BEST = "best";
    }
}
