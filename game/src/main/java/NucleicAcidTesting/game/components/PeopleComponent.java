package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;

public class PeopleComponent extends Component {
    PhysicsComponent physicsComponent;

    int queueNum = -1;
    Entity aheadPerson;
    State state = State.REST;

    private AnimationChannel acUp,acDown,acLeft,acRight;
    private AnimatedTexture at;
    static MoveDirection dir = MoveDirection.DOWN;
    private boolean isStop = true;

    public enum State {
        REST, FOLLOW
    }

    @Override
    public void onAdded() {
        getEntity().getViewComponent().clearChildren();
        acUp = getAnimationChannel(12,15);
        acDown = getAnimationChannel(0,3);
        acLeft = getAnimationChannel(4,7);
        acRight = getAnimationChannel(8,11);
        at = new AnimatedTexture(acDown);
        entity.getViewComponent().addChild(at);
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
         setAnimation();
    }

    private AnimationChannel getAnimationChannel(int start,int end){
        return new AnimationChannel(FXGL.image("player.png"),
                4,128/4,192/4,
                Duration.seconds(0.75),start,end);

    }
    private void setAnimation(){
        double x_speed = physicsComponent.getVelocityX();
        double y_speed = physicsComponent.getVelocityY();

        if(x_speed == 0&&y_speed==0){
            at.stop();
            isStop =true;
            return;
        }

        if (Math.abs(y_speed)>Math.abs(x_speed)){
            if(y_speed >0)  dir = MoveDirection.DOWN;
            else dir =MoveDirection.UP;
        }
        else {
            if(x_speed <0)  dir = MoveDirection.LEFT;
            else dir =MoveDirection.RIGHT;
        }

        if(dir == MoveDirection.UP){
            if(at.getAnimationChannel()!=acUp || isStop){
                at.loopAnimationChannel(acUp);
                isStop =false;
            }
        }
        if(dir == MoveDirection.DOWN){
            if(at.getAnimationChannel()!=acDown || isStop){
                at.loopAnimationChannel(acDown);
                isStop =false;
            }
        }
        if(dir == MoveDirection.RIGHT){
            if(at.getAnimationChannel()!=acRight || isStop){
                at.loopAnimationChannel(acRight);
                isStop =false;
            }
        }
        if(dir == MoveDirection.LEFT){
            if(at.getAnimationChannel()!=acLeft || isStop){
                at.loopAnimationChannel(acLeft);
                isStop =false;
            }
        }
    }
}