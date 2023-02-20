package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepInBoundsComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.time.LocalTimer;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class SiteComponent extends Component {
    Text text;
    private int remove_count=0;
    private int mark=0;
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
        if(distance<30) {
            for (int i=1;i<follow_list.size();i++) {
                Entity person = follow_list.get(i);
                follow_list.remove(person);

//                if(!site_queue.contains(person)&&!disappear_queue.contains(person)) {
//                    site_queue.add(person);
//                    disappear_queue.add(person);
//
//                    if (site_queue.size() == 1){
//                        person.getComponent(PeopleComponent.class).follow(entity, 1);
//                    }else{
//                        person.getComponent(PeopleComponent.class).follow(site_queue.get(i-1), site_queue.size());
//                        System.out.println(i);
//                    }
//                }

                FXGL.getGameWorld().removeEntity(person);
                i--;
                if(!site_queue.contains(person)) {
                    site_queue.add(person);
                    Entity citizen=FXGL.getGameWorld().spawn("People",
                            entity.getX()  + Config.SPAWNING_X_GAP * 4 * disappear_queue.size(),
                            entity.getBottomY() - 5);

                    disappear_queue.add(citizen);
                }
            }
        }

        while(!disappear_queue.isEmpty()) {
            if (!localTimer.elapsed(Duration.seconds(2))){
                remove_count++;
                return;
            }
            localTimer.capture();
            if(remove_count==0)
                break;

            Entity person=disappear_queue.get(0);

            FXGL.getGameWorld().removeEntity(person);
            disappear_queue.remove(0);
            mark++;
            text.setText(String.valueOf(mark*10+score));

            for(int i=0;i<disappear_queue.size();i++){
                disappear_queue.get(i).translate(-20,0);
            }
//            for(int i=0;i<disappear_queue.size();i++){
//                if(i==0) {
//                    person = disappear_queue.get(i);
//                    person.getComponent(PeopleComponent.class).follow(entity, 1);
//                }
//                else{
//                    person = disappear_queue.get(i);
//                    person.getComponent(PeopleComponent.class).follow(disappear_queue.get(i-1), 1);
//                }
//            }
            if(disappear_queue.isEmpty())
                remove_count=0;
        }
    }

}


