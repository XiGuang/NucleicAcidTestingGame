package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SiteComponent extends Component {

    private LocalTimer localTimer;
    //检测队列
    List<Entity> siteQueue = new ArrayList<>();

    private int maxQueueSize = 40;

    private double NATTime = 2;  // 做核酸的时间

    private boolean isFaster;   // 是否开始加速

    public double getNATTime() {
        return NATTime;
    }

    public void setNATTime(double NATTime) {
        this.NATTime = NATTime;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public void setFaster(boolean faster) {
        isFaster = faster;
    }

    public boolean isFaster() {
        return isFaster;
    }

    public void onAdded() {
        SpawnData spawnData = new SpawnData(entity.getCenter().getX(), entity.getCenter().getY());
        spawnData.put("size", 130.0);
        spawnData.put("site", entity);
        Entity trigger_area = FXGL.spawn("SiteArea", spawnData);
        trigger_area.setOpacity(0.25);

        localTimer = FXGL.newLocalTimer();

    }

    public void queueUp() {
        // 排队
        List<Entity> follow_list = FXGL.getWorldProperties().getObject("follow_list");
        Iterator<Entity> iterator = follow_list.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            // 队列满时
            if(siteQueue.size()>=maxQueueSize){
                Entity person = iterator.next();
                if(!person.hasComponent(PeopleComponent.class))
                    return;
                person.getComponent(PeopleComponent.class).followPlayer();
                FXGL.getNotificationService().pushNotification("核酸站队伍满啦！");
                return;
            }

            Entity person = iterator.next();
            if(!person.hasComponent(PeopleComponent.class))
                return;
            siteQueue.add(person);
            person.getComponent(PeopleComponent.class).follow(new Point2D(
                    entity.getRightX() + 30 * siteQueue.size() - 1,
                    entity.getBottomY()));
            iterator.remove();
        }
    }

    public void onUpdate(double tpf) {
        // 可变做核酸速度
        if (isFaster)
            NATTime = NATMath.InterpolationD(NATTime, 0.5, tpf);
        else
            NATTime = 2;

        if (!localTimer.elapsed(Duration.seconds(NATTime))) {
            return;
        }
        localTimer.capture();

        if (!siteQueue.isEmpty() && siteQueue.get(0).distance(entity) < 300) {
            siteQueue.get(0).removeFromWorld();
            siteQueue.remove(0);
            FXGL.getWorldProperties().setValue("people_num",
                    FXGL.getWorldProperties().getInt("people_num") + 1);

            for (int i = 0; i < siteQueue.size(); i++) {
                PeopleComponent peopleComponent = siteQueue.get(i).getComponent(PeopleComponent.class);
                peopleComponent.follow(new Point2D(entity.getRightX() + 30 * i, entity.getCenter().getY()));
            }
        }
    }

}


