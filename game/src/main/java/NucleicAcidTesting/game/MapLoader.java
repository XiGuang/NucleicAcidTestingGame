package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;
import com.almasb.fxgl.entity.SpawnData;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MapLoader {

    static String mapLevel = "1";

    public static void setMapLevel(String mapLevel) {
        MapLoader.mapLevel = mapLevel;
    }

    public static String getMapLevel() {
        return mapLevel;
    }

    static void loadMap() {
        switch (mapLevel) {
            case "1" -> loadMapLevel1();
            case "2" -> loadMapLevel2();
            case "infinity"->loadMapInfinity();
        }
    }

    private static void loadMapLevel1() {
        spawn("Background", -Config.WINDOW_WIDTH, -Config.WINDOW_HEIGHT);
        spawn("UICountDown", -40, -200);
        spawn("ControlButton");
        spawn("Score");
        SpawnData needScoreData=new SpawnData();
        needScoreData.put("need_score",200);
        spawn("NeedScore",needScoreData);
        spawn("Site", 400, 0);
        spawn("Building",-100,0);
        spawn("Building",-682,209);
        spawn("Building",792,123);
        spawn("Building",-502,-312);
        spawn("Building",302,-293);
    }

    private static void loadMapLevel2() {
        spawn("Background", -Config.WINDOW_WIDTH, -Config.WINDOW_HEIGHT);
        SpawnData countDownData=new SpawnData(-40, -200);
        countDownData.put("time",110);  // 设定关卡时长
        spawn("UICountDown", countDownData);
        spawn("ControlButton");
        spawn("Score");
        SpawnData needScoreData=new SpawnData();
        needScoreData.put("need_score",500);
        spawn("NeedScore",needScoreData);
        spawn("Building",-100,-202);
        spawn("Building",-670,178);
        spawn("Building",723,92);
        spawn("Building",-474,-298);
        spawn("Building",291,-252);
        spawn("Building",490,-492);
        spawn("Building",392,381);
        spawn("Site", 200, 0);
    }

    private static void loadMapInfinity() {
        spawn("Background", -Config.WINDOW_WIDTH, -Config.WINDOW_HEIGHT);
        spawn("ControlButton");
        spawn("Score");
        NATFactory.spawnSite();
        NATFactory.spawnBuildings(10,Config.WINDOW_MIN_X+Config.GAP_TO_WINDOW,
                Config.WINDOW_MIN_Y+Config.GAP_TO_WINDOW,
                Config.WINDOW_MAX_X-Config.GAP_TO_WINDOW,
                Config.WINDOW_MAX_Y-Config.GAP_TO_WINDOW);
    }
}
