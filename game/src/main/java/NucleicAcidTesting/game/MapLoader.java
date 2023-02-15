package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MapLoader {

    static String mapLevel;

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
        spawn("Background", 0, 0);
        spawn("UICountDown", -40, -200);
//        NATFactory.spawnSite();
        NATFactory.spawnBuildings(10, (int) (-getAppWidth() + Config.SIZE_X / 2 + Config.GAP_TO_WINDOW),
                (int) (-getAppHeight() + Config.SIZE_Y / 2 + Config.GAP_TO_WINDOW),
                (int) (getAppWidth() - Config.SIZE_X / 2 - Config.GAP_TO_WINDOW),
                (int) (getAppHeight() - Config.SIZE_Y / 2 - Config.GAP_TO_WINDOW));
    }

    private static void loadMapLevel2() {
        spawn("Background", 0, 100);
        spawn("UICountDown", -40, -200);
        spawn("Building", 0, 0);
        spawn("Site", 100, 0);
    }
}
