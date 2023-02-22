package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MapLoader {

    static String mapLevel = "1";

    public static void setMapLevel(String mapLevel) {
        MapLoader.mapLevel = mapLevel;
    }

    static void loadMap() {
        switch (mapLevel) {
            case "1" -> loadMapLevel1();
            case "2" -> loadMapLevel2();
        }
    }

    private static void loadMapLevel1() {
        spawn("Background", -Config.WINDOW_WIDTH, -Config.WINDOW_HEIGHT);
        spawn("UICountDown", -40, -200);
        NATFactory.spawnSite();
        spawn("Building",-100,0);
        spawn("Building",-682,209);
        spawn("Building",792,123);
        spawn("Building",-502,-312);
        spawn("Building",302,-293);
    }

    private static void loadMapLevel2() {
        spawn("Background", 0, 100);
        spawn("UICountDown", -40, -200);
        spawn("Building",-100,-202);
        spawn("Building",-670,178);
        spawn("Building",723,92);
        spawn("Building",-474,-298);
        spawn("Building",291,-252);
        spawn("Building",490,-492);
        spawn("Building",392,381);
        spawn("Site", 100, 0);
    }
}
