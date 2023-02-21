package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SiteComponent extends Component {
    Text text;

    int score=0;
    private LocalTimer localTimer;
    //检测队列
    List<Entity>site_queue=new ArrayList<>();

    private double NATTime=2;  // 做核酸的时间

    private boolean isFaster;   // 是否开始加速

    public double getNATTime() {
        return NATTime;
    }

    public void setNATTime(double NATTime) {
        this.NATTime = NATTime;
    }

    public void setFaster(boolean faster) {
        isFaster = faster;
    }

    public boolean isFaster() {
        return isFaster;
    }

    public void onAdded(){
        SpawnData spawnData = new SpawnData(entity.getCenter().getX(), entity.getCenter().getY());
        spawnData.put("size", 130.0);
        spawnData.put("site", entity);
        Entity trigger_area = FXGL.spawn("SiteArea", spawnData);
        trigger_area.setOpacity(0.25);

        localTimer = FXGL.newLocalTimer();
        Rectangle rectangle = new Rectangle(80,50);
        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(3);
        text = FXGL.getUIFactoryService().newText(String.valueOf(score));
        text.setFill(Color.BLUE);
        text.fontProperty().unbind();
        text.setFont(Font.font(35));

        StackPane stackPane = new StackPane(rectangle,text);
        FXGL.addUINode(stackPane,400,30);
    }

    public void queueUp(){
        // 排队
        List<Entity> follow_list = FXGL.getWorldProperties().getObject("follow_list");
        Iterator<Entity> iterator = follow_list.iterator();
        iterator.next();
        while(iterator.hasNext()){
            Entity person=iterator.next();
            site_queue.add(person);
            person.getComponent(PeopleComponent.class).follow(new Point2D(
                    entity.getRightX() + 30 * site_queue.size()-1,
                    entity.getBottomY()));
            iterator.remove();
        }
    }

    public void onUpdate(double tpf) {
        // 可变做核酸速度
        if(isFaster)
            NATTime= NATMath.InterpolationD(NATTime,0.5,tpf);
        else
            NATTime=2;

        if (!localTimer.elapsed(Duration.seconds(NATTime))) {
            return;
        }
        localTimer.capture();

        if(!site_queue.isEmpty() && site_queue.get(0).distance(entity)<300){
            site_queue.get(0).removeFromWorld();
            site_queue.remove(0);
            score+=10;
            text.setText(String.valueOf(score));
            for(int i = 0; i < site_queue.size(); i++){
                PeopleComponent peopleComponent = site_queue.get(i).getComponent(PeopleComponent.class);
                peopleComponent.follow(new Point2D(entity.getRightX() + 30 * i, entity.getCenter().getY()));
            }
        }
    }

}


