package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;
import NucleicAcidTesting.game.collision.PlayerBuildingHandler;
import NucleicAcidTesting.game.components.PlayerComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class NATGameApp extends GameApplication {

    private Entity player;

    private PlayerComponent playerComponent;



    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setVersion(Config.VERSION);
        gameSettings.setTitle(Config.TITLE);
        gameSettings.setHeight(Config.WINDOW_HEIGHT);
        gameSettings.setWidth(Config.WINDOW_WIDTH);
        gameSettings.setSceneFactory(new NATSceneFactory());
    }

    @Override
    protected void onPreInit() {
        super.onPreInit();
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                playerComponent.moveUp();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.W, VirtualButton.UP);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                playerComponent.moveLeft();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.A,VirtualButton.LEFT);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                playerComponent.moveDown();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.S,VirtualButton.DOWN);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                playerComponent.moveRight();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.D,VirtualButton.RIGHT);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new NATFactory());

        spawn("Background",0,0);

        player = spawn("Player",0,0);
        playerComponent = player.getComponent(PlayerComponent.class);

        spawnBuildings(10, (int) (-getAppWidth()+Config.SIZE_X/2+Config.GAP_TO_WINDOW),
                (int) (-getAppHeight()+Config.SIZE_Y/2+Config.GAP_TO_WINDOW),
                (int) (getAppWidth()-Config.SIZE_X/2-Config.GAP_TO_WINDOW),
                (int) (getAppHeight()-Config.SIZE_Y/2-Config.GAP_TO_WINDOW));

//        spawn("Building",0,0);

        getGameScene().getViewport().bindToEntity(player,getAppWidth()/2.0,getAppHeight()/2.0);
        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setBounds(Config.WINDOW_MIN_X,Config.WINDOW_MIN_Y,Config.WINDOW_MAX_X,Config.WINDOW_MAX_Y);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0,0);
    }

    @Override
    protected void initUI() {
        super.initUI();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void onUpdate(double tpf) {
        List<Entity> entities=getGameWorld().getEntitiesInRange(new Rectangle2D(player.getPosition().getX(),player.getPosition().getY(),player.getWidth(),player.getHeight()));
        if(!entities.isEmpty()){
            for(var e:entities){
                if(e.isType(NATType.BUILDING)){
                    e.setOpacity(0.5);
                }
            }
        }
    }

    /**
     * 随机生成居民楼
     * @param num 生成楼的数量
     * @param min_x 生成的最小x范围
     * @param min_y 生成的最小y范围
     * @param max_x 生成的最大x范围
     * @param max_y 生成的最大y范围
     */
    private void spawnBuildings(int num, int min_x, int min_y, int max_x, int max_y) throws RuntimeException {
        Rectangle2D bound = new Rectangle2D(min_x, min_y, max_x - min_x, max_y - min_y);
        for (int i = 0; i < num; ++i) {
            Point2D point;
            int cycle_num = 0;    // 防止找不到生成位置
            while (true) {
                point = FXGLMath.randomPoint(bound);
                List<Entity> buildings = getGameWorld().getEntitiesInRange(new Rectangle2D(
                        point.getX() - Config.GAP_X / 2,
                        point.getY() - Config.GAP_Y / 2,
                        Config.GAP_X,
                        Config.GAP_Y));

                // 避免物体重叠
                if (buildings.isEmpty())
                    break;

                // 防止找不到生成位置
                if (++cycle_num > Config.MAX_CYCLE_TIME)
                    throw new RuntimeException("NOT FOUND LOCATION TO SET BUILDING");

            }
            getGameWorld().spawn("Building", point);
        }
    }
}