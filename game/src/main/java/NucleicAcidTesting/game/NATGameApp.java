package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;
import NucleicAcidTesting.game.EntityFactory.NATUIFactory;
import NucleicAcidTesting.game.components.MoveComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class NATGameApp extends GameApplication {

    private Entity player;

    private MoveComponent moveComponent;



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

    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                moveComponent.moveUp();
            }

            @Override
            protected void onActionEnd() {
                moveComponent.stop();
            }
        }, KeyCode.W, VirtualButton.UP);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                moveComponent.moveLeft();
            }

            @Override
            protected void onActionEnd() {
                moveComponent.stop();
            }
        }, KeyCode.A,VirtualButton.LEFT);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                moveComponent.moveDown();
            }

            @Override
            protected void onActionEnd() {
                moveComponent.stop();
            }
        }, KeyCode.S,VirtualButton.DOWN);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                moveComponent.moveRight();
            }

            @Override
            protected void onActionEnd() {
                moveComponent.stop();
            }
        }, KeyCode.D,VirtualButton.RIGHT);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new NATFactory());
        getGameWorld().addEntityFactory(new NATUIFactory());

        spawn("Background",0,0);

        player = spawn("Player",0,0);
        moveComponent = player.getComponent(MoveComponent.class);

        spawn("People",10,10);

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



        /// 按y轴的高度进行渲染
        List<Entity> entities =getGameWorld().getEntities();
        // 按bottomY从小到大排序（屏幕上小下大）
        entities.sort(new EntityComparator());
        for(int z=0;z<entities.size();++z){
            entities.get(z).setZIndex(z+1);
        }
    }

}