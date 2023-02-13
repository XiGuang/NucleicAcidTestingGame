package NucleicAcidTesting.game.ui.MainMenu;

import NucleicAcidTesting.game.Config;
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

public class LoginWin extends GridPane {
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
        titleBox.setAlignment(Pos.CENTER);

        Text name = new Text("用户名：");
        name.setFont(Font.font("Times Roman", FontWeight.BOLD, 20));
        Text password = new Text("密码：");
        password.setFont(Font.font("Times Roman", FontWeight.BOLD, 20));
        TextField e_name = new TextField();
        e_name.setPrefWidth(150);
        PasswordField e_password = new PasswordField();
        e_password.setPromptText("请输入密码：");

        Button loginButton = new Button("登录");
        loginButton.setPrefSize(btnWidth, btnHeight);

        if(!Config.ONLINE){
            loginButton.setOnAction(actionEvent -> {
                this.toBack();
            });
        }
        else {
            loginButton.setOnAction(actionEvent -> {
                try {
                    String res = Client.login(e_name.getText(), e_password.getText()).get("result").toString();
                    switch (res) {
                        case "登录成功！" -> this.toBack();
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
        this.add(titleBox,0,0,5,1);
        this.add(name, 1, 2);
        this.add(e_name, 3, 2);
        this.add(password, 1, 3);
        this.add(e_password, 3, 3);

        this.add(loginButton, 1, 4);
        this.add(signButton, 3, 4);


        GridPane.setMargin(signButton, new Insets(0, 0, 0, 100));
        this.setAlignment(Pos.CENTER);
        // this.setGridLinesVisible(true);
        this.setBackground(new Background(backgroundimage));
        this.setVgap(20);
        this.setHgap(5);
    }

}
