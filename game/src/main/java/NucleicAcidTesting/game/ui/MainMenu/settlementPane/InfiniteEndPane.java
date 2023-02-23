package NucleicAcidTesting.game.ui.MainMenu.settlementPane;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.UserInfo;
import NucleicAcidTesting.game.ui.MainMenu.LoginPage.Client;
import NucleicAcidTesting.game.ui.MainMenu.Popup;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class InfiniteEndPane extends StackPane {
    UploadState uploadState = UploadState.SUCCESS;

    enum UploadState {
        FAIL, SUCCESS, NEW_RECORD
    }

    public InfiniteEndPane() {

        this.setPrefSize(500, 400);
        this.setAlignment(Pos.BOTTOM_CENTER);


        Image input = new Image("assets/textures/menuImg/infiniteImg.png",
                432, 391, false, false);
        ImageView imageView = new ImageView(input);


        GridPane texturePane;
        texturePane = new GridPane();
        texturePane.setPrefSize(500, 400);
        texturePane.setStyle("-fx-background-color: transparent");


        GridPane settlementPane;
        settlementPane = new GridPane();
        settlementPane.setPrefSize(330, 200);
        settlementPane.setHgap(0);
        settlementPane.setVgap(10);
        settlementPane.setAlignment(Pos.CENTER);
        settlementPane.setStyle("-fx-background-color: rgba(75,163,196,0.9);" +
                "-fx-background-radius: 15px;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-width: 4px;" +
                "-fx-border-style: dashed");

        //重置时间图片按钮


        SettlementButton ResetButton = new SettlementButton("再来一局");
        ResetButton.setOnMouseClicked(event -> {


            FXGL.getGameController().resumeEngine();
            FXGL.getGameController().startNewGame();

            this.setVisible(false);
        });

//        texturePane.add(ResetButton, 1, 1);


        //退出图片按钮

        SettlementButton exitButton = new SettlementButton("返回菜单");
        exitButton.setOnMouseClicked(event -> {
            FXGL.getGameController().gotoMainMenu();
            this.setVisible(false);
        });

        Image gameOverLogo = new Image("assets/textures/menuImg/gameOverLogo.png",
                355 * 0.7, 92 * 0.6, false, false);
        ImageView gameOverLogoView = new ImageView(gameOverLogo);
        try {
            switch (Client.setNumber(UserInfo.getUserId(), FXGL.getWorldProperties().getInt("people_num") * 10)){
                case 0->uploadState = UploadState.SUCCESS;
                case 1->uploadState = UploadState.NEW_RECORD;
            }
        } catch (Exception ignored) {
            uploadState = UploadState.FAIL;
        }

        Text text = new Text("SCORE: " + FXGL.getWorldProperties().getInt("people_num") * 10);
        text.setFont(new Font("宋体", 20));

        settlementPane.add(gameOverLogoView, 0, 0, 3, 1);
        GridPane.setHalignment(gameOverLogoView, HPos.CENTER);
        settlementPane.add(text, 1, 2);
        GridPane.setHalignment(text, HPos.CENTER);
        switch (uploadState){
            case FAIL -> {
                Image stateLogo = new Image("assets/textures/CountDownPic/uploadFailLogo.png",
                        513 * 0.15, 83 * 0.15, false, false);
                ImageView stateView = new ImageView(stateLogo);
                settlementPane.add(stateView,2,1);
                GridPane.setMargin(stateView,new Insets(0,0,-10,0));
                GridPane.setHalignment(settlementPane,HPos.LEFT);
                GridPane.setValignment(settlementPane,VPos.TOP);
            }
            case NEW_RECORD -> {
                Image stateLogo = new Image("assets/textures/CountDownPic/newRecordLogo.png",
                        452 * 0.15, 85 * 0.15, false, false);
                ImageView stateView = new ImageView(stateLogo);
                settlementPane.add(stateView,2,1);
                GridPane.setMargin(stateView,new Insets(0,0,-10,0));
                GridPane.setHalignment(settlementPane,HPos.LEFT);
                GridPane.setValignment(settlementPane,VPos.TOP);
            }
        }
        settlementPane.add(exitButton, 0, 3);
        settlementPane.setGridLinesVisible(true);
        GridPane.setHalignment(exitButton,HPos.RIGHT);
        GridPane.setMargin(exitButton,new Insets(0,-40,0,0));
        settlementPane.add(ResetButton, 2, 3);
        GridPane.setMargin(ResetButton,new Insets(0,0,0,-40));
        GridPane.setHalignment(ResetButton,HPos.LEFT);
        texturePane.add(settlementPane, 0, 0);
        texturePane.setAlignment(Pos.BOTTOM_CENTER);


        this.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().addAll(imageView, texturePane);

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
