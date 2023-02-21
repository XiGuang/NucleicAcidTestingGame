package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.time.LocalTimer;

import java.util.ArrayList;
import java.util.Iterator;

public class BuildingComponent extends Component {
    private int maxPeopleNum = 20;

    private int hasSpawnedNum = 0;

    private boolean isStartingSpawn = false;

    // 生成队伍计时器
    private LocalTimer spawnTimer;


    private final ArrayList<Entity> queueResidents = new ArrayList<>();

    public int getMaxPeopleNum() {
        return maxPeopleNum;
    }

    public void setMaxPeopleNum(int maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public int getHasSpawnedNum() {
        return hasSpawnedNum;
    }

    public int getQueueNum() {
        return queueResidents.size();
    }

    public void isSpawning(boolean isStartingSpawn) {
        this.isStartingSpawn = isStartingSpawn;
    }

    public boolean isSpawning() {
        return isStartingSpawn;
    }

    @Override
    public void onAdded() {
        spawnTimer = FXGL.newLocalTimer();
        SpawnData spawnData = new SpawnData(entity.getCenter().getX(), entity.getBottomY());
        spawnData.put("size", 100.0);
        spawnData.put("building", entity);
        Entity trigger_area = FXGL.spawn("BuildingArea", spawnData);
        trigger_area.setOpacity(0.3);
    }

    @Override
    public void onUpdate(double tpf) {
        if (!isStartingSpawn || hasSpawnedNum >= maxPeopleNum || !spawnTimer.elapsed(Config.SPAWNING_INTERVAL))
            return;
        spawnTimer.capture();
        Entity person = FXGL.getGameWorld().spawn("People",
                entity.getX() + Config.SPAWNING_X_GAP * queueResidents.size(),
                entity.getBottomY() - 5);

        person.setUpdateEnabled(false);

        ++hasSpawnedNum;
        queueResidents.add(person);
    }

    public void followToPlayer() {

        for (Iterator<Entity> it = queueResidents.iterator(); it.hasNext(); ) {
            if(!PeopleComponent.canFollow()){
                FXGL.getNotificationService().pushNotification("队列已满");
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
    }
}
