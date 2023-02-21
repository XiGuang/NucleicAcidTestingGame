package NucleicAcidTesting.game.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Random;

public class FailWin extends VBox{
    public FailWin() {
        GridPane popupPane;
        popupPane = new GridPane();
        popupPane.setHgap(10);
        popupPane.setVgap(10);
        this.setPrefSize(642, 406);
        this.setAlignment(Pos.CENTER);


        BackgroundImage backgroundImage1 = new BackgroundImage(
                new Image("assets/textures/CountDownPic/failed(已去底).png"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        BackgroundImage backgroundImage2 = new BackgroundImage(
                new Image("assets/textures/CountDownPic/failedWindow2(已去底).png"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        //随机出现两种胜利界面

        Background background;
        Random random = new Random();
        if(random.nextInt(100)>50){
            background = new Background(backgroundImage1);
        }
        else {
            background = new Background(backgroundImage2);
        }


        this.setBackground(background);
        this.getChildren().add(popupPane);


        //重置时间图片按钮
        Image resetButton_up = new Image("assets/textures/CountDownPic/resetButton_up(已去底).png");
        Image resetButton_down = new Image("assets/textures/CountDownPic/resetButton_down(已去底).png");
        ImageView ResetButton = new ImageView(resetButton_up);
        ResetButton.setFitWidth(80);
        ResetButton.setFitHeight(80);


        ResetButton.setOnMouseClicked(event -> {


            FXGL.getGameController().resumeEngine();
            FXGL.getGameController().startNewGame();

            this.setVisible(false);
        });
        ResetButton.setOnMouseEntered(event -> ResetButton.setImage(resetButton_down));
        ResetButton.setOnMouseExited(event -> ResetButton.setImage(resetButton_up));
        popupPane.add(ResetButton, 16, 30);


        //退出图片按钮
        Image img2 = new Image("assets/textures/CountDownPic/exit3.png");
        Image exitButton_up = new Image("assets/textures/CountDownPic/exit_down(已去底).png");
        Image exitButton_down = new Image("assets/textures/CountDownPic/exit_up(已去底).png");
        ImageView exitButton = new ImageView(exitButton_up);
        exitButton.setFitWidth(80);
        exitButton.setFitHeight(80);

        exitButton.setOnMouseClicked(event -> {

            FXGL.getGameController().gotoMainMenu();
            this.setVisible(false);
        });
        exitButton.setOnMouseEntered(event -> exitButton.setImage(exitButton_down));
        exitButton.setOnMouseExited(event -> exitButton.setImage(exitButton_up));

        popupPane.add(exitButton, 34, 30);


        FXGL.addUINode(this);
        this.setVisible(false);

        //设置失败窗体格式
        double WINDOW_WIDTH = 960;
        double WINDOW_HEIGHT = 520;
        double popupBoxWidth = this.getPrefWidth();
        double popupBoxHeight = this.getPrefHeight();
        double popupBoxX = (WINDOW_WIDTH - popupBoxWidth) / 2;
        double popupBoxY = (WINDOW_HEIGHT - popupBoxHeight) / 2;
        this.setLayoutX(popupBoxX);
        this.setLayoutY(popupBoxY);
    }
}
