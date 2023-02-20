package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.tools.NATMath;
import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.List;

public class PlayerComponent extends Component {

    LazyValue<EntityGroup> entity_group_lazy_value = new LazyValue<>(() -> FXGL.getGameWorld().getGroup(NATType.BUILDING, NATType.SITE));

    MoveDirection moveDirection = MoveDirection.STOP;
    private AnimationChannel acUp,acDown,acLeft,acRight;
    private AnimatedTexture at;
    private boolean isStop = true;

    @Override
    public void onUpdate(double tpf) {
        List<Entity> entities = entity_group_lazy_value.get().getEntitiesCopy();

        for (var e : entities) {
            Point2D point_left_up = e.getPosition();
            Bounds bounds = e.getViewComponent().getChildren().get(0).getBoundsInLocal();
            Rectangle2D rectangle2D = new Rectangle2D(point_left_up.getX(), point_left_up.getY(), bounds.getWidth(), bounds.getHeight());

            if (rectangle2D.contains(entity.getCenter())) {
                e.setOpacity(NATMath.InterpolationD(e.getOpacity(),0.5,tpf*3));
            } else if (e.getOpacity() != 1)
                e.setOpacity(NATMath.InterpolationD(e.getOpacity(),1,tpf*3));
        }
        if(moveDirection != MoveComponent.getDir()){
            moveDirection = MoveComponent.getDir();
        }

        if(moveDirection == MoveDirection.UP){
            if(at.getAnimationChannel()!=acUp || isStop){
                at.loopAnimationChannel(acUp);
                isStop = false;
            }
        }
        if(moveDirection == MoveDirection.DOWN){
            if(at.getAnimationChannel()!=acDown|| isStop){
                at.loopAnimationChannel(acDown);
                isStop = false;
            }
        }
        if(moveDirection == MoveDirection.RIGHT){
            if(at.getAnimationChannel()!=acRight|| isStop){
                at.loopAnimationChannel(acRight);
                isStop = false;
            }
        }
        if(moveDirection == MoveDirection.LEFT){
            if(at.getAnimationChannel()!=acLeft|| isStop){
                at.loopAnimationChannel(acLeft);
                isStop = false;
            }
        }
        if (moveDirection == MoveDirection.STOP){
            at.stop();
            isStop =true;
        }
    }

    @Override
    public void onAdded() {
        acUp = getAnimationChannel(12,15);
        acDown = getAnimationChannel(0,3);
        acLeft = getAnimationChannel(4,7);
        acRight = getAnimationChannel(8,11);
        at = new AnimatedTexture(acDown);
        entity.getViewComponent().addChild(at);
    }
    private AnimationChannel getAnimationChannel(int start,int end){
        return new AnimationChannel(FXGL.image("player.png"),
                4,128/4,192/4,
                Duration.seconds(0.75),start,end);
    }
}


