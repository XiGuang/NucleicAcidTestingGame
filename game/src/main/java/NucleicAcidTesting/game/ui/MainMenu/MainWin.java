package NucleicAcidTesting.game.ui.MainMenu;

import NucleicAcidTesting.game.Config;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainWin extends Pane {
    public MainWin() {
        double btnHeight = 35, btnWidth = 100;
        double boxHeight = Config.WINDOW_HEIGHT, boxWidth = Config.WINDOW_WIDTH;
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
        this.getChildren().add(beginGameContainer);
        this.setPrefSize(boxWidth, boxHeight);

        this.setBackground(new Background(new BackgroundFill(Color.web("#f3ff3f"), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
