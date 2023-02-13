package NucleicAcidTesting.game.ui.MainMenu;

import NucleicAcidTesting.game.Config;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class SignWin {
    public SignWin() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setPrefSize(350, 250);

        Image image = new Image("assets/textures/menuImg/signBg.gif",
                100, 100, false, false);
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(350 - 100);
        imageView.setLayoutY(250 - 100);
        Pane imgPane = new Pane(imageView);
        imgPane.setPrefSize(350, 250);

        double btnHeight = 35, btnWidth = 100;
        Text name = new Text("用户名：");
        name.setFont(Font.font("Times Roman", FontWeight.BOLD, 10));
        Text password = new Text("密码：");
        password.setFont(Font.font("Times Roman", FontWeight.BOLD, 10));
        Text confirmPassword = new Text("确认密码：");
        confirmPassword.setFont(Font.font("Times Roman", FontWeight.BOLD, 10));

        TextField e_name = new TextField();
        e_name.setPrefWidth(150);
        e_name.setPromptText("用户名长度请控制在1~10位之内：");
        PasswordField e_password = new PasswordField();
        e_password.setPromptText("密码长度请控制在6~10位之内：");
        PasswordField e_confirm_password = new PasswordField();
        e_confirm_password.setPromptText("请确认密码：");


        Button signButton = new Button("立即注册！");
        signButton.setPrefSize(btnWidth, btnHeight);

        if (!Config.ONLINE) {
            signButton.setOnAction(actionEvent -> {
                stage.close();
            });
        } else {
            signButton.setOnAction(actionEvent -> {
                if (e_name.getText().length() > 10 || e_name.getText().length() < 1) {
                    Popup.warningBox("用户名长度请控制在1~10位之内！");
                    e_name.setText("");
                    e_password.setText("");
                    e_confirm_password.setText("");
                } else if (e_password.getText().length() > 10 || e_password.getText().length() < 6) {
                    Popup.warningBox("密码长度请控制在6~10位之内！");
                    e_password.setText("");
                    e_confirm_password.setText("");
                } else if (!Objects.equals(e_confirm_password.getText(), e_password.getText())) {
                    Popup.warningBox("两次输入密码不一致");
                    e_password.setText("");
                    e_confirm_password.setText("");
                } else {
                    try {
                        String res = Client.Registration(e_name.getText(), e_password.getText()).get("result").toString();
                        switch (res) {
                            case "注册成功，请登录" -> {
                                Popup.infoBox("注册成功，请登录！");
                                stage.close();
                            }
                            case "账户名重复，请重新注册" -> {
                                Popup.warningBox("账户名重复！");
                                e_name.setText("");
                                e_password.setText("");
                                e_confirm_password.setText("");
                            }
                            default -> Popup.warningBox("????");
                        }
                    } catch (Exception e) {
                        Popup.warningBox("网络连接错误，请重试！");
                    }
                }
            });
        }


        pane.add(name, 0, 0);
        pane.add(e_name, 1, 0);
        pane.add(password, 0, 1);
        pane.add(e_password, 1, 1);
        pane.add(confirmPassword, 0, 2);
        pane.add(e_confirm_password, 1, 2);
        pane.add(signButton, 1, 3);

        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(5);


        StackPane stackPane = new StackPane(imgPane, pane);
        stackPane.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
