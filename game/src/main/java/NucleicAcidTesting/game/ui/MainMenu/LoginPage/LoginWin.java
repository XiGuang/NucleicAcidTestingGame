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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class LoginWin extends VBox {
    public LoginWin() {

        Image input = new Image("assets/textures/menuImg/menuBg.png",
                Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, false, false);


        BackgroundImage backgroundimage = new BackgroundImage(input,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        double btnHeight = 35, btnWidth = 250;
        Image titleImg = new Image("assets/textures/menuImg/titleLogo.png",
                380, 70, false, false);
        ImageView titleLogo = new ImageView(titleImg);

        HBox titleBackground = new HBox(titleLogo);
        titleBackground.setPrefSize(450, 100);
        titleBackground.setStyle("-fx-background-color: rgba(255,255,255,0.71);" +
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-border-style: dashed;" +
                "-fx-border-width: 2px");
        titleBackground.setAlignment(Pos.CENTER);
        HBox titleContainer = new HBox(titleBackground);

        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setStyle("-fx-background-color: transparent");

        Font myFont = Font.font("Times Roman", FontWeight.BOLD, 16);
        Text name = new Text("用户名：");
        name.setFont(myFont);
        Text password = new Text("密码：");
        password.setFont(myFont);
        TextField e_name = new TextField();
        e_name.setPrefWidth(130);

        PasswordField e_password = new PasswordField();
        e_password.setPromptText("请输入密码：");
        Button loginButton = new Button("登录");
        loginButton.setPrefSize(btnWidth, btnHeight);
        loginButton.setOnAction(actionEvent -> {
            if (!Config.ONLINE) this.toBack();
            else {
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
            }
        });
        loginButton.setStyle("-fx-background-color: rgba(85,192,232,0.7);" +
                "-fx-border-color: rgba(255,255,255,0.82);" +
                "-fx-border-radius: 5px;" +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: white;");
        loginButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
            loginButton.setStyle("-fx-border-color: rgba(33,33,33,0.82);" +
                    "-fx-background-color: rgba(85,192,232,0.7);" +
                    "-fx-border-radius: 5px;" +
                    "-fx-background-radius: 5px;" +
                    "-fx-text-fill: white;");
        });
        ;
        loginButton.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
            loginButton.setStyle(("-fx-background-color: rgba(85,192,232,0.7);" +
                    "-fx-border-color: rgba(255,255,255,0.82);" +
                    "-fx-border-radius: 5px;" +
                    "-fx-background-radius: 5px;" +
                    "-fx-text-fill: white;"));
        });
        loginButton.setFont(myFont);

        GridPane loginBackground = new GridPane();
        loginBackground.add(name, 0, 0);
        loginBackground.add(e_name, 1, 0);
        loginBackground.add(password, 0, 1);
        loginBackground.add(e_password, 1, 1);

        loginBackground.add(loginButton, 0, 2, 2, 1);
        loginBackground.setVgap(15);
        loginBackground.setHgap(5);
        loginBackground.setPrefSize(450, Config.WINDOW_HEIGHT * 0.33);
        loginBackground.setAlignment(Pos.BOTTOM_CENTER);
        loginBackground.setStyle("-fx-background-color: transparent");

        HBox loginContainer = new HBox(loginBackground);
        loginContainer.setAlignment(Pos.CENTER);
        loginContainer.setStyle("-fx-background-color: transparent");


        Text signNow = new Text("没有账号？");
        signNow.setStyle("-fx-text-fill: gray;");
        signNow.setFont(Font.font("Times Roman", FontWeight.EXTRA_LIGHT, 14));
        Button signButton = new Button("立即注册", signNow);
        signButton.setFont(Font.font("Times Roman", FontWeight.EXTRA_LIGHT, 14));
        signButton.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: blue;" +
                "-fx-underline: true");
        signButton.setPrefSize(btnWidth, btnHeight);
        signButton.setOnAction(actionEvent -> {
            new SignWin();
        });


        this.getChildren().addAll(titleContainer, loginContainer, signButton);
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(backgroundimage));

    }

}
