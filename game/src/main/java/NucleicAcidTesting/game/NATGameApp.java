package NucleicAcidTesting.game;

import NucleicAcidTesting.game.EntityFactory.NATFactory;
import NucleicAcidTesting.game.components.PlayerComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class NATGameApp extends GameApplication {

    private AStarGrid grid;

    private PlayerComponent playerComponent;

    public AStarGrid getGrid() {
        return grid;
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setVersion(Config.VERSION);
        gameSettings.setTitle(Config.TITLE);
        gameSettings.setHeight(Config.WINDOW_HEIGHT);
        gameSettings.setWidth(Config.WINDOW_WIDTH);
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
        }, KeyCode.W);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                playerComponent.moveLeft();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                playerComponent.moveDown();
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                playerComponent.moveRight();
            }
        }, KeyCode.D);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new NATFactory());

        spawn("Background");

        grid = AStarGrid.fromWorld(getGameWorld(), Config.WINDOW_WIDTH/20, Config.WINDOW_HEIGHT/40, 20, 40, type -> {
            if (type.equals(NATType.BUILDING) || type.equals(NATType.SITE))
                return CellState.NOT_WALKABLE;

            return CellState.WALKABLE;
        });

        Entity player = spawn("Player");
        playerComponent = player.getComponent(PlayerComponent.class);

        getGameScene().getViewport().bindToEntity(player,getAppWidth()/2.0,getAppHeight()/2.0);
        getGameScene().getViewport().setLazy(true);
    }

    @Override
    protected void initPhysics() {
        super.initPhysics();
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