package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;

public class MoveComponent extends Component {
    private BoundingBoxComponent bbox;

    private MoveDirection moveDir;

    private boolean movedThisFrame = false;

    private final LazyValue<EntityGroup> barriers = new LazyValue<>(() -> {
        return entity.getWorld().getGroup(NATType.BUILDING, NATType.SITE);
    });

    public MoveDirection getMoveDir() {
        return moveDir;
    }

    public void setMoveDirection(MoveDirection moveDir) {
        if (movedThisFrame)
            return;

        movedThisFrame = true;

        this.moveDir = moveDir;

        switch (moveDir) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
        }
    }

    @Override
    public void onAdded() {
        moveDir = FXGLMath.random(MoveDirection.values()).get();
    }

    private double speed = 0;

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60;

        movedThisFrame = false;
    }

    private void up() {
        getEntity().setRotation(270);
        move(0, -5 * speed);
    }

    private void down() {
        getEntity().setRotation(90);
        move(0, 5 * speed);
    }

    private void left() {
        getEntity().setRotation(180);
        move(-5 * speed, 0);
    }

    private void right() {
        getEntity().setRotation(0);
        move(5 * speed, 0);
    }

    private final Vec2 velocity = new Vec2();

    private void move(double dx, double dy) {
        if (!getEntity().isActive())
            return;

        velocity.set((float) dx, (float) dy);

        int length = Math.round(velocity.length());

        velocity.normalizeLocal();

        var blocks = barriers.get().getEntitiesCopy();

        for (int i = 0; i < length; i++) {
            entity.translate(velocity.x, velocity.y);

            boolean collision = false;

            for (Entity block : blocks) {
                if (block.getBoundingBoxComponent().isCollidingWith(bbox)) {
                    collision = true;
                    break;
                }
            }

            if (collision) {
                entity.translate(-velocity.x, -velocity.y);
                break;
            }
        }
    }
}
