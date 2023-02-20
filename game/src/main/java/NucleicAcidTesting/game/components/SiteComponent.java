package NucleicAcidTesting.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
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
import java.util.List;

public class SiteComponent extends Component {
    Text text;
    private final int mark=0;
    int score=0;
    private LocalTimer localTimer;
    //检测队列
    List<Entity>site_queue=new ArrayList<>();
    List<Entity>disappear_queue=new ArrayList<>();

    //得到做过核算的人数
    public int getMark(){
        return mark;
    }

    public void onAdded(){

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

    public void onUpdate(double tpf) {
        List<Entity> follow_list = FXGL.getWorldProperties().getObject("follow_list");
        Entity player = follow_list.get(0);
        double distance=entity.distance(player);
        if(distance<50) {
            for (int i=1;i<follow_list.size();i++) {
                Entity person = follow_list.get(i);
                follow_list.remove(person);

                site_queue.add(person);
                person.getComponent(PeopleComponent.class).follow(new Point2D(
                        entity.getX()  + 20 * site_queue.size(),
                        entity.getBottomY()));
            }
        }

        if (!localTimer.elapsed(Duration.seconds(2))) {
            return;
        }
        localTimer.capture();

        if(!site_queue.isEmpty() && site_queue.get(0).distance(entity)<100){
            site_queue.get(0).removeFromWorld();
            site_queue.remove(0);
            score+=10;
            text.setText(String.valueOf(score));
            for(int i = 0; i < site_queue.size(); i++){
                PeopleComponent peopleComponent = site_queue.get(i).getComponent(PeopleComponent.class);
                peopleComponent.follow(new Point2D(entity.getCenter().getX() + 20 * (i+1), entity.getCenter().getY()));
            }
        }
    }

}


