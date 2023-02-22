package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.MapLoader;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class BuildingComponent extends Component {
    private int maxPeopleNum = 10;

    private int hasSpawnedNum = 0;

    private Entity mood;

    private boolean isStartingSpawn = false;
    private final boolean isInfinity;
    private Duration spawnInterval=Duration.seconds(2);

    // 生成队伍计时器
    private LocalTimer spawnTimer;


    private final ArrayList<Entity> queueResidents = new ArrayList<>();

    public void setMaxPeopleNum(int maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public void setMood(Entity mood) {
        this.mood = mood;
    }

    public Entity getMood() {
        return mood;
    }

    public int getHasSpawnedNum() {
        return hasSpawnedNum;
    }

    public int getQueueNum() {
        return queueResidents.size();
    }

    public void setStart(boolean isStartingSpawn) {
        this.isStartingSpawn = isStartingSpawn;
        if(isStartingSpawn){
            this.mood= FXGL.spawn("Mood",entity.getX()+100,entity.getY());
            this.mood.getComponent(MoodComponent.class).setPeople_num(getQueueNum());
        }else if(mood!=null){
            mood.removeFromWorld();
            mood=null;
        }
    }

    public boolean isStart() {
        return isStartingSpawn;
    }

    // 用于随机更新产生人的时间间隔,单位为秒
    private void randomSpawnInterval(double min,double max){
        spawnInterval=Duration.seconds(FXGL.random(min,max));
    }

    private void randomSpawnInInfinity(){
        if(hasSpawnedNum<5)
            randomSpawnInterval(5,15);
        else if(hasSpawnedNum<10)
            randomSpawnInterval(4,8);
        else if(hasSpawnedNum<20)
            randomSpawnInterval(3,5);
        else
            randomSpawnInterval(3,4);
    }

    public BuildingComponent(boolean is_infinity) {
        isInfinity=is_infinity;
    }

    @Override
    public void onAdded() {
        spawnTimer = FXGL.newLocalTimer();
        SpawnData spawnData = new SpawnData(entity.getCenter().getX(), entity.getBottomY());
        spawnData.put("size", 100.0);
        spawnData.put("building", entity);
        Entity trigger_area = FXGL.spawn("BuildingArea", spawnData);
        if(isInfinity) {
            trigger_area.setOpacity(0);
            setStart(true);
        } else
            trigger_area.setOpacity(0.3);
    }

    @Override
    public void onUpdate(double tpf) {
        if (Objects.equals(MapLoader.getMapLevel(), "infinity") &&!InfiniteGameLoadControllerComponent.INITED)
            return;
        if (!spawnTimer.elapsed(spawnInterval))
            return;
        spawnTimer.capture();
        // 无尽模式并且开始产生则继续
        if(!isInfinity && !isStartingSpawn )
            return;
        // 没有开始产生或则产生人数超出上限则返回
        if(!isStartingSpawn || hasSpawnedNum >= maxPeopleNum)
            return;
        if(isInfinity)
            randomSpawnInInfinity();

        Entity person = FXGL.getGameWorld().spawn("People",
                entity.getX() + Config.SPAWNING_X_GAP * queueResidents.size(),
                entity.getBottomY() - 5);

        person.setUpdateEnabled(false);

        if(mood!=null && mood.hasComponent(MoodComponent.class))
            mood.getComponent(MoodComponent.class).setPeople_num(queueResidents.size());

        ++hasSpawnedNum;
        queueResidents.add(person);
    }

    public void followToPlayer() {

        for (Iterator<Entity> it = queueResidents.iterator(); it.hasNext(); ) {
            if(!PeopleComponent.canFollow()){
                FXGL.getNotificationService().pushNotification("队伍满员啦！");
                break;
            }
            Entity person = it.next();

            // 先移除，再添加组件
            FXGL.getGameWorld().removeEntity(person);
            PhysicsComponent physicsComponent = new PhysicsComponent();
            physicsComponent.setBodyType(BodyType.DYNAMIC);

            if (!person.hasComponent(PhysicsComponent.class)) {
                person.addComponent(physicsComponent);
            }

            if (!person.hasComponent(CollidableComponent.class)) {
                person.addComponent(new CollidableComponent(true));
            }

            person.setUpdateEnabled(true);

            FXGL.getGameWorld().addEntity(person);
            if(!person.getComponent(PeopleComponent.class).follow())
                System.out.println("error follow");
            it.remove();
        }
        if(mood!=null && mood.hasComponent(MoodComponent.class)){
            if(queueResidents.size()==0)
                mood.getComponent(MoodComponent.class).reset();
            mood.getComponent(MoodComponent.class).setPeople_num(queueResidents.size());
        }
    }
}
