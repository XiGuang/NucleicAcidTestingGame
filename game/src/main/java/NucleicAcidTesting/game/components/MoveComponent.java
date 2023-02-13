package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class MoveComponent extends Component {

    PhysicsComponent physics;

    public void moveRight() {
        physics.setVelocityX(Config.Velocity);
    }

    public void moveLeft() {
        physics.setVelocityX(-Config.Velocity);
    }

    public void moveUp() {
        physics.setVelocityY(-Config.Velocity);
    }

    public void moveDown() {
        physics.setVelocityY(Config.Velocity);
    }

    public void stop() {
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }
}
