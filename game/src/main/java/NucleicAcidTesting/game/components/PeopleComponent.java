package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.NATMath;
import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class PeopleComponent extends Component {
    PhysicsComponent physics_component;

    int queue_num =-1;
    Entity ahead_person;
    State state=State.REST;

    public enum State{
        REST,FOLLOW
    }

    public boolean follow(){
        ArrayList<Entity> follow_list=(ArrayList<Entity>)FXGL.getWorldProperties().
                objectProperty("follow_list").get();
        if(follow_list.size()> Config.MAX_FOLLOW_NUM+1)
            return false;
        ahead_person=follow_list.get(follow_list.size()-1);
        follow_list.add(entity);
        queue_num=follow_list.size()-1;
        state=State.FOLLOW;
        return true;
    }

    @Override
    public void onUpdate(double tpf) {
        if(state==State.REST)
            return;

        // 跟随
        double distance=entity.distance(ahead_person);
        if (distance > 50)
            physics_component.setLinearVelocity(new Point2D(
                    2 * (ahead_person.getCenter().getX() - entity.getCenter().getX()),
                    2 * (ahead_person.getCenter().getY() - entity.getCenter().getY())
            ));
         else
            physics_component.setLinearVelocity(
                    NATMath.InterpolationD(physics_component.getVelocityX(), 0, tpf*10),
                    NATMath.InterpolationD(physics_component.getVelocityY(), 0, tpf*10));
    }
}
