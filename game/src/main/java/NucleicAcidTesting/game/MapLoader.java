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
        NATFactory.spawnBuildings(10, (int) (Config.WINDOW_MIN_X + Config.GAP_TO_WINDOW),
                (Config.WINDOW_MIN_Y + Config.GAP_TO_WINDOW),
                (Config.WINDOW_MAX_X - Config.GAP_TO_WINDOW),
                (Config.WINDOW_MAX_Y - Config.GAP_TO_WINDOW));
    }

    private static void loadMapLevel2() {
        spawn("Background", 0, 100);
        spawn("UICountDown", -40, -200);
        spawn("Building", 0, 0);
        spawn("Site", 100, 0);
    }
}
