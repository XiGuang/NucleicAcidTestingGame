package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;
import NucleicAcidTesting.game.EntityFactory.NATUIFactory;
import NucleicAcidTesting.game.collision.PlayerAreaHandler;
import NucleicAcidTesting.game.components.BuildingComponent;
import NucleicAcidTesting.game.components.MoodComponent;
import NucleicAcidTesting.game.components.MoveComponent;
import NucleicAcidTesting.game.tools.EntityComparator;
import NucleicAcidTesting.game.ui.FailPane;
import NucleicAcidTesting.game.ui.InfiniteEndPane;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class NATGameApp extends GameApplication {

    private MoveComponent move_component;

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
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("follow_list", new ArrayList<Entity>());
        vars.put("people_num", 0);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                move_component.moveUp();
            }

            @Override
            protected void onActionEnd() {
                move_component.stop();
            }
        }, KeyCode.W, VirtualButton.UP);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                move_component.moveLeft();
            }

            @Override
            protected void onActionEnd() {
                move_component.stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                move_component.moveDown();
            }

            @Override
            protected void onActionEnd() {
                move_component.stop();
            }
        }, KeyCode.S, VirtualButton.DOWN);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                move_component.moveRight();
            }

            @Override
            protected void onActionEnd() {
                move_component.stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);


    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new NATFactory());
        getGameWorld().addEntityFactory(new NATUIFactory());


        Entity player = spawn("Player", 0, 0);
        move_component = player.getComponent(MoveComponent.class);
        List<Entity> follow_list = FXGL.getWorldProperties().getObject("follow_list");
        follow_list.add(player);

        MapLoader.loadMap();

        getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2.0, getAppHeight() / 2.0);
        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setBounds(Config.WINDOW_MIN_X, Config.WINDOW_MIN_Y, Config.WINDOW_MAX_X, Config.WINDOW_MAX_Y);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 0);
        getPhysicsWorld().addCollisionHandler(new PlayerAreaHandler());
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
        /// 按y轴的高度进行渲染
        List<Entity> entities = getGameWorld().getEntities();
        // 按bottomY从小到大排序（屏幕上小下大）
        entities.sort(new EntityComparator());
        for (int z = 0; z < entities.size(); ++z) {
            Entity entity = entities.get(z);
            if (entity.isType(NATType.AREA) || entity.isType(NATType.BACKGROUND) || entity.isType(NATType.MOOD))
                continue;
            entity.setZIndex(z + 1);
        }

        List<Entity> buildings = getGameWorld().getEntitiesByType(NATType.BUILDING);
        for (var building : buildings) {
            if (!building.hasComponent(BuildingComponent.class))
                continue;
            Entity mood = building.getComponent(BuildingComponent.class).getMood();
            if (mood != null && mood.hasComponent(MoodComponent.class)) {
                if (mood.getComponent(MoodComponent.class).isOver()) {
                    Pane overPane;
                    if (Objects.equals(MapLoader.getMapLevel(), "infinity"))
                        overPane = new InfiniteEndPane();
                    else
                        overPane = new FailPane();
                    getGameController().pauseEngine();
                    overPane.setVisible(true);
                }
            }
        }

    }

}