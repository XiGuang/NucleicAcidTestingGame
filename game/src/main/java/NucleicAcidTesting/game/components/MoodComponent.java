package NucleicAcidTesting.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class MoodComponent extends Component {
    double time_interval=30;
    //记录游戏结束
    boolean over=false;
    //出楼人数
    private int people_num=0;
    //记录时间
    private final SimpleDoubleProperty progress=new SimpleDoubleProperty();
    //构造函数一
    public MoodComponent(int peopleNum) {
        people_num = peopleNum;
    }
    
    //构造函数二
    public MoodComponent(){}

    //添加圆形进度条
    @Override
    public void onAdded(){
        //添加画布
        Canvas canvas = new Canvas(367,14);
        //添加画笔
        GraphicsContext g2d=canvas.getGraphicsContext2D();

        //添加底层视图
        StackPane pane=new StackPane(
                FXGL.texture("MoodPic/Bar_bg.png"),canvas
        );
        //根据上层遮盖条大小绘制矩形
        double width=canvas.getWidth();
        Rectangle rect=new Rectangle(width,canvas.getHeight());
        rect.setTranslateX(-width);
        canvas.setClip(rect);

        //添加进度条
        entity.getViewComponent().addChild(pane);

        //添加监听器
        progress.addListener((ob,ov,nv)->{
            //上层遮盖条移动距离
            if(people_num==0) {
                //画绿色进度条
                g2d.setFill(Color.rgb(0,255,0));
                g2d.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
                if((nv.doubleValue() / time_interval * width)<=(width/3)){
                    rect.setTranslateX(-width +
                        Math.min(width, nv.doubleValue() / time_interval * width));
                }

                //画橘色进度条
                else if ((nv.doubleValue() / time_interval * width)>(width/3)&&(nv.doubleValue() / time_interval * width)<=(width/3*2)) {
                    //当前长度小于三分之二
                    g2d.setFill(Color.rgb(255,165,0));
                    g2d.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
                    rect.setTranslateX(-width +
                            Math.min(width, nv.doubleValue() / time_interval * width));
                }

                //画红色进度条
                else if((nv.doubleValue() / time_interval <= width)){
                    //当前长度小于等于进度条
                    g2d.setFill(Color.rgb(255,0,0));
                    g2d.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
                    rect.setTranslateX(-width +
                            Math.min(width, nv.doubleValue() / time_interval * width));
                }
                else
                    over=true;
            }

            //人数不是0
            else{
                //画绿色进度条
                g2d.setFill(Color.rgb(0,255,0));
                g2d.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
                if((nv.doubleValue() / time_interval * width)<=(width/3)){
                    rect.setTranslateX(-width +
                            Math.min(width, nv.doubleValue() / time_interval * people_num * width));
                }

                //画橘色进度条
                else if ((nv.doubleValue() / time_interval * width)>(width/3)&&(nv.doubleValue() / time_interval * width)<=(width/3*2)) {
                    //当前长度小于三分之二
                    g2d.setFill(Color.rgb(255,165,0));
                    g2d.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
                    rect.setTranslateX(-width +
                            Math.min(width, nv.doubleValue() / time_interval * people_num * width));
                }

                //画红色进度条
                else if((nv.doubleValue() / time_interval <= width)){
                    //当前长度小于等于进度条
                    g2d.setFill(Color.rgb(255,0,0));
                    g2d.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
                    rect.setTranslateX(-width +
                            Math.min(width, nv.doubleValue() / time_interval * people_num * width));
                }
                else
                    over=true;
            }
        });
    }

    //记录累计时间
    @Override
    public void onUpdate(double tpf){
        progress.set(progress.get()+tpf);
        if(people_num!=0)
            time_interval=time_interval/people_num;
    }

    //清零
    public void reset(){
        progress.set(0);
        over=false;
    }

    //判断游戏是否结束
    public boolean isOver(){
        return over;
    }
   
}
