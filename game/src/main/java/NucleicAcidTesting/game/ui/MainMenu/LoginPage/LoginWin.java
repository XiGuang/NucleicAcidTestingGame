package NucleicAcidTesting.game.ui.MainMenu.LoginPage;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.UserInfo;
import NucleicAcidTesting.game.ui.MainMenu.Popup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;

public class LoginWin extends VBox {
    public LoginWin() {

        Image input = new Image("assets/textures/menuImg/menuBg.png",
                Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, false, false);


        BackgroundImage backgroundimage = new BackgroundImage(input,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        double btnHeight = 35, btnWidth = 120;
        Image titleImg = new Image("assets/textures/menuImg/titleLogo.png",
                380, 70, false, false);
        ImageView titleLogo = new ImageView(titleImg);
        HBox titleBox = new HBox(titleLogo);
        titleBox.setPrefHeight(90);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-background-color: rgba(255,255,255,0.71);" +
                "-fx-border-radius: 10px;" +
                "-fx-border-style: dashed;" +
                "-fx-border-width: 2px");

        Font myFont = Font.font("Times Roman", FontWeight.BOLD, 20);

        Text name = new Text("用户名：");
        name.setFont(myFont);
        Text password = new Text("密码：");

        password.setFont(myFont);
        TextField e_name = new TextField();
        e_name.setPrefWidth(150);
        PasswordField e_password = new PasswordField();
        e_password.setPromptText("请输入密码：");

        Button loginButton = new Button("登录");
        loginButton.setPrefSize(btnWidth, btnHeight);

        if (!Config.ONLINE) {
            loginButton.setOnAction(actionEvent -> {
                this.toBack();
            });
        } else {
            loginButton.setOnAction(actionEvent -> {
                try {
                    String res = Client.login(e_name.getText(), e_password.getText()).get("result").toString();
                    switch (res) {
                        case "登录成功！" -> {
                            this.toBack();
                            UserInfo.setUserId(e_name.getText());
                        }
                        case "密码错误，请重试！" -> Popup.warningBox("密码错误，请重试！");
                        case "账户不存在，请注册" -> Popup.warningBox("账户不存在，请注册");
                        default -> Popup.warningBox("????");
                    }
                } catch (Exception e) {
                    Popup.warningBox("网络连接错误，请重试！");
                }
            });
        }

        Button signButton = new Button("注册");
        signButton.setPrefSize(btnWidth, btnHeight);
        signButton.setOnAction(actionEvent -> {
            new SignWin();
        });

        GridPane gridPane = new GridPane();
        gridPane.add(name, 0, 0);
        gridPane.add(e_name, 1, 0);
        gridPane.add(password, 0, 1);
        gridPane.add(e_password, 1, 1);

        gridPane.add(loginButton, 0, 2);
        gridPane.add(signButton, 1, 2);
        gridPane.setVgap(20);
        gridPane.setHgap(5);
        gridPane.setPrefHeight(Config.WINDOW_HEIGHT * 0.4);
        gridPane.setAlignment(Pos.CENTER);


        GridPane.setMargin(signButton, new Insets(0, 0, 0, 100));
        this.setPrefSize(Config.WINDOW_WIDTH * 0.5, Config.WINDOW_HEIGHT);
        this.getChildren().addAll(titleBox, gridPane);
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(backgroundimage));

    }

}
