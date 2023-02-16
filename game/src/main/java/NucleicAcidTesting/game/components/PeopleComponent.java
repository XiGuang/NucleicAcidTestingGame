package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import java.util.List;

public class PeopleComponent extends Component {
    PhysicsComponent physicsComponent;

    int queueNum = -1;
    Entity aheadPerson;
    State state = State.REST;

    public enum State {
        REST, FOLLOW
    }

    public static boolean canFollow() {
        List<Entity> follow_list = FXGL.getWorldProperties().getObject("follow_list");
        return follow_list.size() < Config.MAX_FOLLOW_NUM + 1;
    }

    public boolean follow() {
        if (queueNum != -1)
            return false;

        List<Entity> follow_list = FXGL.getWorldProperties().getObject("follow_list");

        if (follow_list.size() > Config.MAX_FOLLOW_NUM + 1)
            return false;
        aheadPerson = follow_list.get(follow_list.size() - 1);

        follow_list.add(entity);
        queueNum = follow_list.size() - 1;
        state = State.FOLLOW;
        return true;
    }

    public boolean follow(Entity aheadPerson,int queueNum){
        this.aheadPerson=aheadPerson;
        this.queueNum=queueNum;
        return true;
    }

    @Override
    public void onUpdate(double tpf) {
        if (state == State.REST)
            return;

        // 跟随
        double distance = entity.distance(aheadPerson);
        if (distance > 50)
            physicsComponent.setLinearVelocity(new Point2D(
                    2 * (aheadPerson.getCenter().getX() - entity.getCenter().getX()),
                    2 * (aheadPerson.getCenter().getY() - entity.getCenter().getY())
            ));
        else
            physicsComponent.setLinearVelocity(
                    NATMath.InterpolationD(physicsComponent.getVelocityX(), 0, tpf * 10),
                    NATMath.InterpolationD(physicsComponent.getVelocityY(), 0, tpf * 10));
    }
}
