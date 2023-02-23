package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.ui.MainMenu.settlementPane.FailPane;
import NucleicAcidTesting.game.ui.LoadingWin;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CountDownComponent extends Component {
    double minute, second;
    double minute2, second2;
    int CountTime;
    double remainingTime;
    double currentSecond;
    double Delay;
    //计时器 初始延迟的秒数

    boolean inited = false;
    double initDelay = 1.5;
    boolean showLoading = false;
    LoadingWin loadingWin;

    FailPane failWin = new FailPane();
    Text text;

    //构造函数，设置初始秒数
    public CountDownComponent(int CountSecondTime) {
        CountTime = CountSecondTime;

    }


    @Override
    public void onAdded() {

        //根据秒数设置分与秒
        minute = CountTime / 60;
        second = CountTime % 60;
        //存储备份，以便于重置游戏
        minute2 = CountTime / 60;
        second2 = CountTime % 60;
        //设置计时器的时间
        remainingTime = second;
        currentSecond = second;
        //设置计时器启动的初始延迟
        Delay = 0;
        //背景图片以及格式设置
        Image image = new Image("assets/textures/CountDownPic/CountDownBackground.gif");

        Rectangle rectangle = new Rectangle(80, 50);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(3);

        //数字显示设置
        if (second < 10)
            text = FXGL.getUIFactoryService().newText((int) minute + ":0" + (int) second);
        else
            text = FXGL.getUIFactoryService().newText((int) minute + ":" + (int) second);
        text.setFill(Color.BLUE);
        text.fontProperty().unbind();
        text.setFont(Font.font(35));

        //将背景图片与数字显示 结合
        StackPane stackPane = new StackPane(rectangle, text);
        FXGL.addUINode(stackPane, 850, 30);


        //右下角退出按钮
        Image exitButton_up = new Image("assets/textures/CountDownPic/exit_down(已去底).png");
        Image exitButton_down = new Image("assets/textures/CountDownPic/exit_up(已去底).png");
        ImageView exitButton = new ImageView(exitButton_up);
        exitButton.setFitWidth(50);
        exitButton.setFitHeight(50);

        exitButton.setOnMouseClicked(event -> {

            FXGL.getGameController().gotoMainMenu();
            //this.setVisible(false);
        });
        exitButton.setOnMouseEntered(event -> exitButton.setImage(exitButton_down));
        exitButton.setOnMouseExited(event -> exitButton.setImage(exitButton_up));

        FXGL.addUINode(exitButton, 900, 450);

        //右下角暂停/继续按钮
        Image pauseButton_up = new Image("assets/textures/CountDownPic/pause1(已去底).png");
        Image pauseButton_down = new Image("assets/textures/CountDownPic/pause1_down(已去底).png");
        ImageView pauseButton = new ImageView(pauseButton_up);
        pauseButton.setFitWidth(50);
        pauseButton.setFitHeight(50);

        Image continueButton_up = new Image("assets/textures/CountDownPic/continue1(已去底).png");
        Image continueButton_down = new Image("assets/textures/CountDownPic/continue1_down(已去底).png");
        ImageView continueButton = new ImageView(continueButton_up);
        continueButton.setFitWidth(50);
        continueButton.setFitHeight(50);
        pauseButton.setVisible(true);
        continueButton.setVisible(false);

        pauseButton.setOnMouseEntered(event -> pauseButton.setImage(pauseButton_down));
        pauseButton.setOnMouseExited(event -> pauseButton.setImage(pauseButton_up));

        continueButton.setOnMouseEntered(event -> continueButton.setImage(continueButton_down));
        continueButton.setOnMouseExited(event -> continueButton.setImage(continueButton_up));

        pauseButton.setOnMouseClicked(event -> {
                //暂停游戏
                FXGL.getGameController().pauseEngine();
                //改变图标
                pauseButton.setVisible(false);
                continueButton.setVisible(true);
                //this.setVisible(false);
        });
        continueButton.setOnMouseClicked(event -> {
            //暂停游戏
            FXGL.getGameController().resumeEngine();
            //改变图标
            pauseButton.setVisible(true);
            continueButton.setVisible(false);
            //this.setVisible(false);
        });
        FXGL.addUINode(pauseButton, 780, 450);
        FXGL.addUINode(continueButton, 780, 450);

        //重置按钮
        Image resetButton_up = new Image("assets/textures/CountDownPic/resetButton_up(已去底).png");
        Image resetButton_down = new Image("assets/textures/CountDownPic/resetButton_down(已去底).png");
        ImageView ResetButton = new ImageView(resetButton_up);
        ResetButton.setFitWidth(50);
        ResetButton.setFitHeight(50);


        ResetButton.setOnMouseClicked(event -> {
            FXGL.getGameController().resumeEngine();
            FXGL.getGameController().startNewGame();
            //this.setVisible(false);
        });
        ResetButton.setOnMouseEntered(event -> ResetButton.setImage(resetButton_down));
        ResetButton.setOnMouseExited(event -> ResetButton.setImage(resetButton_up));
        FXGL.addUINode(ResetButton,840,450);
    }

    @Override
    public void onUpdate(double tpf) {
        double max_time = 0.015;
        if (!inited) {
            initDelay -= tpf;
            if (tpf <= max_time && initDelay <0) {
                if (showLoading) {
                    loadingWin.closeLoading();
                    showLoading = false;
                    initDelay = 1.5;
                }
                inited = true;
            } else if (!showLoading) {
                loadingWin = new LoadingWin();
                showLoading = true;
            }
            return;
        }
//        if (tpf > max_time) {
//            inited = false;
//            return;
//        } ;
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
                    inited = false;
                    showLoading = false;
//                    minute = minute2;
//                    second = second2;
                    FXGL.getGameController().pauseEngine();
                    failWin.setVisible(true);
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
            }
        }
    }
}
