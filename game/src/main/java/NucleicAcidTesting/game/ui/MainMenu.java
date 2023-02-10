package NucleicAcidTesting.game.ui;

import NucleicAcidTesting.game.Config;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainMenu extends FXGLMenu {

    private Pane loginBox;
    private Pane beginGameBox;

    public MainMenu() {
        super(MenuType.MAIN_MENU);
        beginGameWin();
    }

    private boolean Login() {
        //TODO;
        return false;
    }

    private boolean Sign() {
        //TODO;
        return false;
    }

    private void beginGameWin() {

        double btnHeight = 35, btnWidth = 100;
        double boxHeight = 150, boxWidth = 200;

        Button loginButton = new Button("登入");
        loginButton.setPrefSize(btnWidth, btnHeight);
        loginButton.setOnAction(actionEvent -> {
            beginGameBox.toFront();
        });

        Button signButton = new Button("注册");
        signButton.setPrefSize(btnWidth, btnHeight);
        signButton.setOnAction(actionEvent -> {
            beginGameBox.toFront();
        });

        Button exitButton2 = new Button("退出");
        exitButton2.setPrefSize(btnWidth, btnHeight);
        exitButton2.setOnAction(actionEvent -> FXGL.getGameController().exit());
        VBox loginContainer = new VBox(loginButton, signButton, exitButton2);
        loginContainer.setLayoutX(boxWidth * 0.5 - btnWidth * 0.5);
        loginContainer.setLayoutY(boxHeight * 0.5 - 3 * btnHeight * 0.5);
        loginBox = new Pane(loginContainer);
        loginBox.setPrefSize(boxWidth, boxHeight);
        loginBox.setBackground(new Background(new BackgroundFill(Color.web("#f3ff3f"), CornerRadii.EMPTY, Insets.EMPTY)));


        Button beginButton = new Button("开始游戏");
        beginButton.setPrefSize(btnWidth, btnHeight);
        beginButton.setOnAction(actionEvent -> FXGL.getGameController().startNewGame());


        Button setButton = new Button("设置");
        setButton.setPrefSize(btnWidth, btnHeight);
        setButton.setOnAction(actionEvent -> FXGL.getGameController().gotoGameMenu());

        Button exitButton = new Button("退出");
        exitButton.setPrefSize(btnWidth, btnHeight);
        exitButton.setOnAction(actionEvent -> FXGL.getGameController().exit());

        Button hellpButton = new Button("帮助");
        hellpButton.setPrefSize(btnWidth, btnHeight);
        hellpButton.setOnAction(actionEvent -> new Alert(Alert.AlertType.INFORMATION, "TODO"));

        VBox beginGameContainer = new VBox(beginButton, setButton, exitButton, hellpButton);
        beginGameContainer.setLayoutX(boxWidth * 0.5 - btnWidth * 0.5);
        beginGameContainer.setLayoutY(boxHeight * 0.5 - 4 * btnHeight * 0.5);
        beginGameBox = new Pane(beginGameContainer);
        beginGameBox.setPrefSize(boxWidth, boxHeight);
        beginGameBox.setBackground(new Background(new BackgroundFill(Color.web("#f3ff3f"), CornerRadii.EMPTY, Insets.EMPTY)));


        StackPane stackPane = new StackPane(beginGameBox, loginBox);
        stackPane.setLayoutX(Config.WINDOW_WIDTH * 0.5 - boxWidth * 0.5);
        stackPane.setLayoutY(Config.WINDOW_HEIGHT * 0.5 - boxHeight * 0.5);
        getContentRoot().getChildren().setAll(stackPane);
    }


}
