package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BuildingComponent extends Component {
    private int max_people_num=20;
    private int spawned_people_num=0;

    private boolean start_na=false;

    private Entity player;

    private Entity trigger_range;

    TriggerListener trigger_listener;

    private ArrayList<Entity> queue_residents=new ArrayList<Entity>();

    @Override
    public void onAdded() {
        player=FXGL.getGameWorld().getSingleton(NATType.PLAYER);
        SpawnData spawnData=new SpawnData(entity.getCenter().getX(),entity.getBottomY());
        spawnData.put("size",55.0);
        trigger_range=FXGL.spawn("Effect",spawnData);
        trigger_range.setOpacity(0.25);

         trigger_listener=new TriggerListener() {
            @Override
            protected void onActionBegin(@NotNull Trigger trigger) {
                if(trigger.isKey() && trigger.getName().equals("E")){
                    start_na=true;
                    FXGL.getGameWorld().removeEntity(trigger_range);
                    System.out.println("e");
                }
            }
        };

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(NATType.PLAYER,NATType.EFFECT) {
//            @Override
//            protected void onCollisionBegin(Entity player, Entity effect) {
//                if(!start_na){
//                    FXGL.getInput().addTriggerListener(trigger_listener);
//                    System.out.println("one");
//                }
//            }
//
//            @Override
//            protected void onCollisionEnd(Entity player, Entity effect) {
//                if(!start_na){
//                    FXGL.getInput().removeTriggerListener(trigger_listener);
//                }
//            }
        });

    }

    @Override
    public void onUpdate(double tpf) {

    }
}
