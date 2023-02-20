package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class MoveComponent extends Component {
    PhysicsComponent physics;
    static MoveDirection dir = MoveDirection.STOP;

    public static MoveDirection getDir() {
        return dir;
    }

    public void moveRight() {
        physics.setVelocityX(Config.Velocity);
        dir = MoveDirection.RIGHT;
    }

    public void moveLeft() {
        physics.setVelocityX(-Config.Velocity);
        dir = MoveDirection.LEFT;
    }

    public void moveUp() {
        physics.setVelocityY(-Config.Velocity);
        dir = MoveDirection.UP;
    }

    public void moveDown() {
        physics.setVelocityY(Config.Velocity);
        dir = MoveDirection.DOWN;
    }


    public void stop() {
        physics.setVelocityX(0);
        physics.setVelocityY(0);
        dir = MoveDirection.STOP;
    }
}
