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
