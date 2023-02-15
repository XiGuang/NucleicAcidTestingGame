package NucleicAcidTesting.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class CountDownComponent extends Component {
    double minute,second;
    int CountTime;
    double remainingTime ;
    double currentSecond ;
    double Delay;
    //计时器 初始延迟的秒数
    Text text;
    public CountDownComponent(int CountSecondTime){
        CountTime = CountSecondTime;

    }
    //构造函数，设置初始秒数
    @Override
    public void onAdded() {

        //根据秒数设置分与秒
        minute = CountTime/60;
        second = CountTime%60;

        //设置计时器的时间
        remainingTime = second;
        currentSecond = second;

        //设置计时器启动的初始延迟
        Delay = 2;

        //背景图片以及格式设置
        Image image = new Image("assets/textures/CountDwonPic/CountDownBackground.gif");
        Rectangle rectangle = new Rectangle(80,50);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(3);

        //数字显示设置
        text = FXGL.getUIFactoryService().newText((int)minute+":"+(int)second);
        text.setFill(Color.BLUE);
        text.fontProperty().unbind();
        text.setFont(Font.font(35));

        //将背景图片与数字显示 结合
        StackPane stackPane = new StackPane(rectangle,text);
        FXGL.addUINode(stackPane,850,30);


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
                    System.out.println("倒计时为0");
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
