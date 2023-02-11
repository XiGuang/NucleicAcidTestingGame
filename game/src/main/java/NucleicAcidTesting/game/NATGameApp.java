package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;
import NucleicAcidTesting.game.components.PlayerComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;

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

        NATFactory.spawnBuildings(10, (int) (-getAppWidth()+Config.SIZE_X/2+Config.GAP_TO_WINDOW),
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

    }


}