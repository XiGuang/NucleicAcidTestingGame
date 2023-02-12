package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.NATMath;
import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

public class PeopleComponent extends Component {
    PhysicsComponent physicsComponent;

    @Override
    public void onUpdate(double tpf) {

        // 跟随
        Entity player = FXGL.getGameWorld().getEntitiesByType(NATType.PLAYER).get(0);
        double distance=entity.distance(player);
        if (distance > 50)
            physicsComponent.setLinearVelocity(new Point2D(
                    2 * (player.getCenter().getX() - entity.getCenter().getX()),
                    2 * (player.getCenter().getY() - entity.getCenter().getY())
            ));
         else
            physicsComponent.setLinearVelocity(
                    NATMath.InterpolationD(physicsComponent.getVelocityX(), 0, tpf*10),
                    NATMath.InterpolationD(physicsComponent.getVelocityY(), 0, tpf*10));
    }
}
