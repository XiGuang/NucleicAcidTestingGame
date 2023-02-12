package NucleicAcidTesting.game.components;

import com.almasb.fxgl.entity.component.Component;

public class MoodComponent extends Component {
   //出楼人数
    private int people_num=1;
    //构造函数一
    public MoodComponent(int peopleNum) {
        people_num = peopleNum;
    }

    //构造函数二
    public MoodComponent(){}

    //存时间
    private SimpleDoubleProperty progress=new SimpleDoubleProperty();

    //添加圆形进度条
    public void onAdded(){
        Duration duration=Duration.ofSeconds(2/people_num);
        //圆形进度条
        ProgressIndicator pi = new ProgressIndicator(0.6);
        progress.addListener((observable, oldValue, newValue) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pi.setProgress(newValue.doubleValue()/60);
        });
        //添加组件
        entity.getViewComponent().addChild(pi);
    }

    //记录游戏运行时间
    public void onUpdate(double tpf){
        //秒数累加
        progress.set(progress.get()+tpf);
    }
}
