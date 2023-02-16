package NucleicAcidTesting.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class CountDownComponent extends Component {
    double minute,second;
    double minute2,second2;
    int CountTime;
    double remainingTime ;
    double currentSecond ;
    double Delay;
    //计时器 初始延迟的秒数
    private GridPane popupPane;
    private VBox popupBox;


    Text text;
    //构造函数，设置初始秒数
    public CountDownComponent(int CountSecondTime){
        CountTime = CountSecondTime;

    }


    @Override
    public void onAdded() {

        //根据秒数设置分与秒
        minute = CountTime/60;
        second = CountTime%60;
        //存储备份，以便于重置游戏
        minute2 = CountTime/60;
        second2 = CountTime%60;
        //设置计时器的时间
        remainingTime = second;
        currentSecond = second;
        //设置计时器启动的初始延迟
        Delay = 2;
        //背景图片以及格式设置
        Image image = new Image("assets/textures/CountDownPic/CountDownBackground.gif");

        Rectangle rectangle = new Rectangle(80,50);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(3);

        //数字显示设置
        if(second<10)
            text = FXGL.getUIFactoryService().newText((int)minute+":0"+(int)second);
        else
            text = FXGL.getUIFactoryService().newText((int)minute+":"+(int)second);
        text.setFill(Color.BLUE);
        text.fontProperty().unbind();
        text.setFont(Font.font(35));

        //将背景图片与数字显示 结合
        StackPane stackPane = new StackPane(rectangle,text);
        FXGL.addUINode(stackPane,850,30);



        //时间结束界面
        popupPane = new GridPane();
        popupPane.setHgap(10);
        popupPane.setVgap(10);
        popupBox = new VBox();
        popupBox.setPrefSize(472, 413);
        popupBox.setAlignment(Pos.CENTER);


        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("assets/textures/CountDownPic/fail(已去底).png"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);
        popupBox.setBackground(background);
        popupBox.getChildren().add(popupPane);


        //重置时间图片按钮
        Image resetButton_up = new Image("assets/textures/CountDownPic/resetButton_up(已去底).png");
        Image resetButton_down = new Image("assets/textures/CountDownPic/resetButton_down(已去底).png");
        ImageView ResetButton = new ImageView(resetButton_up);
        ResetButton.setFitWidth(80);
        ResetButton.setFitHeight(80);


        ResetButton.setOnMouseClicked(event -> {

            minute = minute2;
            second = second2;
            popupBox.setVisible(false);
        });
        ResetButton.setOnMouseEntered(event -> ResetButton.setImage(resetButton_down));
        ResetButton.setOnMouseExited(event -> ResetButton.setImage(resetButton_up));
        popupPane.add(ResetButton, 9, 25);







        //退出图片按钮
        Image img2 = new Image("assets/textures/CountDownPic/exit3.png");
        Image exitButton_up = new Image("assets/textures/CountDownPic/exit_down(已去底).png");
        Image exitButton_down = new Image("assets/textures/CountDownPic/exit_up(已去底).png");
        ImageView exitButton = new ImageView(exitButton_up);
        exitButton.setFitWidth(80);
        exitButton.setFitHeight(80);

        exitButton.setOnMouseClicked(event -> {

            FXGL.getGameController().gotoMainMenu();
            popupBox.setVisible(false);
        });
        exitButton.setOnMouseEntered(event -> exitButton.setImage(exitButton_down));
        exitButton.setOnMouseExited(event -> exitButton.setImage(exitButton_up));

        popupPane.add(exitButton, 20, 25);


        FXGL.addUINode(popupBox);
        popupBox.setVisible(false);

        //设置失败窗体格式
        double WINDOW_WIDTH = 960;
        double WINDOW_HEIGHT = 520;
        double popupBoxWidth = popupBox.getPrefWidth();
        double popupBoxHeight = popupBox.getPrefHeight();
        double popupBoxX = (WINDOW_WIDTH - popupBoxWidth) / 2;
        double popupBoxY = (WINDOW_HEIGHT - popupBoxHeight) / 2;
        popupBox.setLayoutX(popupBoxX);
        popupBox.setLayoutY(popupBoxY);


    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        //等待Delay时间然后再启动倒计时
        if (Delay > 0) {
            Delay -= tpf;
            return;
        } else {
            remainingTime -= tpf;

            if (currentSecond - remainingTime >= 1) {
                currentSecond = remainingTime;
                //经过一秒
                second--;
                //倒计时等于0
                if (second == 0 && minute == 0) {
                    //System.out.println("倒计时为0");
                    //显示失败窗口
                    popupBox.setVisible(true);
                }

                //减去一秒后判断下一时刻的情况
                if (second == -1) {
                    if (minute == 0) {
                        minute = 0;
                        second = 0;
                        text.setText("0:00");
                    } else {
                        minute--;
                        second = 59;
                        text.setText((int) minute + ":" + (int) second);
                    }
                } else {
                    if (second < 10)
                        text.setText((int) minute + ":0" + (int) second);
                    else
                        text.setText((int) minute + ":" + (int) second);
                }
            } else {
                return;
            }
        }
    }
}
