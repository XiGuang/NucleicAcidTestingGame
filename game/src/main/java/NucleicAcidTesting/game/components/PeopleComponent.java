package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class PeopleComponent extends Component {

    private int queueNum = -1;
    private Entity aheadPerson;
    private Point2D followPoint;
    private State state = State.REST;
    String texture;
    private AnimationChannel acUp,acDown,acLeft,acRight;
    private AnimatedTexture at;
    static MoveDirection dir = MoveDirection.DOWN;
    private boolean isStop = true;

    public enum State {
        REST, FOLLOW,STATIC_FOLLOW
    }

    public PeopleComponent() {
        Random random = new Random();
        texture = "people/student"+(random.nextInt(29) + 1)+".png";
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
        state=State.STATIC_FOLLOW;
        this.aheadPerson=aheadPerson;
        this.queueNum=queueNum;
        return true;
    }

    public void follow(Point2D point){

        if(state!=State.STATIC_FOLLOW){
            var p=entity.getCenter();
            FXGL.getGameWorld().removeEntity(entity);
            entity.getComponent(BoundingBoxComponent.class).clearHitBoxes();
            FXGL.getGameWorld().addEntity(entity);
            entity.getComponent(PhysicsComponent.class).overwritePosition(p);
            state=State.STATIC_FOLLOW;
        }
        followPoint=point;
        aheadPerson=null;
    }

    @Override
    public void onUpdate(double tpf) {

        var physicsComponent=entity.getComponent(PhysicsComponent.class);

        if (state == State.REST)
            return;

        // 跟随
        double distance;
        if(aheadPerson==null)
            distance=followPoint.distance(entity.getCenter());
        else
            distance= entity.distance(aheadPerson);
        Point2D vec;
        if(state==State.STATIC_FOLLOW){
            // 排队时
            if(distance<3)
                physicsComponent.setLinearVelocity(0,0);
            else{
                vec=followPoint.subtract(entity.getCenter()).normalize().multiply(500);
                physicsComponent.setLinearVelocity(vec);
            }
        } else if (distance > 50) {
            // 跟随玩家时

            vec = aheadPerson.getCenter().subtract(entity.getCenter()).multiply(2);
            // 连接从这个人到上一个人的线上的结果。
            var ray_result=FXGL.getPhysicsWorld().raycast(entity.getCenter(),aheadPerson.getCenter());
            if(ray_result.getEntity().isPresent() && !ray_result.getEntity().get().equals(aheadPerson)){
                physicsComponent.setLinearVelocity(-vec.getY(),vec.getX());
            }else
                physicsComponent.setLinearVelocity(vec);

        } else {
            // 跟随玩家，减速阶段
            physicsComponent.setLinearVelocity(
                    NATMath.InterpolationD(physicsComponent.getVelocityX(), 0, tpf * 50),
                    NATMath.InterpolationD(physicsComponent.getVelocityY(), 0, tpf * 50));
        }
         setAnimation();
    }

    private AnimationChannel getAnimationChannel(int start,int end){
        return new AnimationChannel(FXGL.image(texture),
                4,128/4,192/4,
                Duration.seconds(0.75),start,end);

    }
    private void setAnimation(){
        var physicsComponent=entity.getComponent(PhysicsComponent.class);
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