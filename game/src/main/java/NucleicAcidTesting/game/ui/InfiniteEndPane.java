package NucleicAcidTesting.game.ui;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.UserInfo;
import NucleicAcidTesting.game.ui.MainMenu.LoginPage.Client;
import NucleicAcidTesting.game.ui.MainMenu.Popup;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class InfiniteEndPane extends StackPane {

    public InfiniteEndPane() {

        this.setPrefSize(500, 400);
        this.setAlignment(Pos.BOTTOM_CENTER);


        Image input = new Image("assets/textures/menuImg/infiniteImg.png",
                432, 391, false, false);
        ImageView imageView = new ImageView(input);


        GridPane texturePane;
        texturePane = new GridPane();
        texturePane.setPrefSize(500,400);
        texturePane.setStyle("-fx-background-color: transparent");


        GridPane settlementPane;
        settlementPane = new GridPane();
        settlementPane.setPrefSize(330,200);
        settlementPane.setHgap(110);
        settlementPane.setVgap(10);
        settlementPane.setAlignment(Pos.CENTER);
        settlementPane.setStyle("-fx-background-color: rgba(129,200,222,0.9);" +
                "-fx-background-radius: 15px;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-width: 4px;" +
                "-fx-border-style: dashed");

        //重置时间图片按钮
        Image resetButton_up = new Image("assets/textures/CountDownPic/resetButton_up(已去底).png");
        Image resetButton_down = new Image("assets/textures/CountDownPic/resetButton_down(已去底).png");
        ImageView ResetButton = new ImageView(resetButton_up);
        ResetButton.setFitWidth(60);
        ResetButton.setFitHeight(60);


        ResetButton.setOnMouseClicked(event -> {


            FXGL.getGameController().resumeEngine();
            FXGL.getGameController().startNewGame();

            this.setVisible(false);
        });
        ResetButton.setOnMouseEntered(event -> ResetButton.setImage(resetButton_down));
        ResetButton.setOnMouseExited(event -> ResetButton.setImage(resetButton_up));
//        texturePane.add(ResetButton, 1, 1);


        //退出图片按钮
        Image img2 = new Image("assets/textures/CountDownPic/exit3.png");
        Image exitButton_up = new Image("assets/textures/CountDownPic/exit_down(已去底).png");
        Image exitButton_down = new Image("assets/textures/CountDownPic/exit_up(已去底).png");
        ImageView exitButton = new ImageView(exitButton_up);
        exitButton.setFitWidth(60);
        exitButton.setFitHeight(60);

        exitButton.setOnMouseClicked(event -> {

            FXGL.getGameController().gotoMainMenu();
            this.setVisible(false);
        });
        exitButton.setOnMouseEntered(event -> exitButton.setImage(exitButton_down));
        exitButton.setOnMouseExited(event -> exitButton.setImage(exitButton_up));
        Image gameOverLogo = new Image("assets/textures/menuImg/gameOverLogo.png",
                460*0.55, 92*0.55, false, false);
        ImageView gameOverLogoView = new ImageView(gameOverLogo);
        try {
            Client.setNumber(UserInfo.getUserId(),FXGL.getWorldProperties().getInt("people_num")*10);
        } catch (Exception ignored) {
            System.out.println("网络连接失败！");
        }

        Text text =new Text("SCORE: "+FXGL.getWorldProperties().getInt("people_num")*10);
        GridPane.setHalignment(text, HPos.CENTER);
        text.setFont(new Font("宋体",20));
        settlementPane.add(gameOverLogoView,0,0,2,1);
        settlementPane.add(text,0,1,2,1);

//        texturePane.add(exitButton, 0, 1);
        settlementPane.add(exitButton,0,2);
        settlementPane.add(ResetButton,1,2);
        texturePane.add(settlementPane,0,0);
        texturePane.setAlignment(Pos.BOTTOM_CENTER);
        FXGL.addUINode(this);
        this.setVisible(false);

        this.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().addAll(imageView,texturePane);

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
