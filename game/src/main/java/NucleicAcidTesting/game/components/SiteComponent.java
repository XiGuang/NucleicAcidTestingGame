package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.tools.NATMath;
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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class SiteComponent extends Component {
    private int remove_count=0;
    //排队人数
    private int count=0;
    
    private LocalTimer localTimer;
    //检测队列
    List<Entity>site_queue=new ArrayList<>();
    //排队消失队列
    List<Entity>disappear_queue=new ArrayList<>();
    final static double INFINITE_MAX=10000;
    PhysicsComponent physicsComponent;
    //距离
    double distance=INFINITE_MAX;

    public double getDistance(){
        return distance;
    }

    public int getCount(){
        return count;
    }
    
    public void onAdded(){
        localTimer = FXGL.newLocalTimer();

    }

    public void onUpdate(double tpf) {
        List<Entity> follow_list = FXGL.getWorldProperties().getObject("follow_list");
        Entity player = follow_list.get(0);
        distance=entity.distance(player);
        if(distance<30) {
            for (int i=1;i<follow_list.size();i++) {
                Entity person = follow_list.get(i);
                follow_list.remove(person);
                FXGL.getGameWorld().removeEntity(person);
                i--;
                if(!site_queue.contains(person)) {
                    site_queue.add(person);
                    count++;
                    Entity citizen=FXGL.getGameWorld().spawn("People",
                            entity.getX()  + Config.SPAWNING_X_GAP * 4 * site_queue.size(),
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
            FXGL.getGameWorld().removeEntity(disappear_queue.get(0));
            disappear_queue.remove(0);
            for(int i=0;i<disappear_queue.size();i++){
                Entity header=disappear_queue.get(i);
                header.translate(-20, 0);
            }
        }
    }

}


