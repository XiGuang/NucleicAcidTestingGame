package NucleicAcidTesting.game.EntityFactory;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.*;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepInBoundsComponent;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class NATFactory implements EntityFactory {

    @Spawns("Background")
    public Entity newBackground(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(NATType.BACKGROUND)
                .view("floor.png")
                .zIndex(Config.BACKGROUND_LEVEL)
                .build();
    }

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .type(NATType.PLAYER)
                .view(new Rectangle(20, 40, Color.LIGHTBLUE))
                .bbox(new HitBox(new Point2D(0,20),BoundingShape.box(20,20)))
                .with(physicsComponent)
                .collidable()
                .with(new KeepOnScreenComponent())
                .with(new PlayerComponent())
                .with(new MoveComponent())
                .build();
    }

    /**
     * 随机生成居民楼
     * @param num 生成楼的数量
     * @param min_x 生成的最小x范围
     * @param min_y 生成的最小y范围
     * @param max_x 生成的最大x范围
     * @param max_y 生成的最大y范围
     */
    public static void spawnBuildings(int num, int min_x, int min_y, int max_x, int max_y) throws RuntimeException {
        Rectangle2D bound = new Rectangle2D(min_x, min_y, max_x - min_x, max_y - min_y);
        for (int i = 0; i < num; ++i) {
            Point2D point;
            int cycle_num = 0;    // 防止找不到生成位置
            while (true) {
                point = FXGLMath.randomPoint(bound);
                List<Entity> buildings = getGameWorld().getEntitiesInRange(new Rectangle2D(
                        point.getX() - Config.GAP_X / 2,
                        point.getY() - Config.GAP_Y / 2,
                        Config.GAP_X,
                        Config.GAP_Y));

                // 避免物体重叠
                if (buildings.isEmpty())
                    break;

                // 防止找不到生成位置
                if (++cycle_num > Config.MAX_CYCLE_TIME+num)
                    throw new RuntimeException("NOT FOUND LOCATION TO SET BUILDING");

            }
            getGameWorld().spawn("Building", point);
        }
    }

    @Spawns("Building")
    public Entity newBuilding(SpawnData data) {
        PhysicsComponent physicsComponent=new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.STATIC);

        return FXGL.entityBuilder()
                .type(NATType.BUILDING)
                .at(data.getX() + Config.OFFSET_X, data.getY() + Config.OFFSET_Y)
                .view(new Rectangle(Config.SIZE_X, Config.SIZE_Y, Color.GRAY))
                .bbox(new HitBox(new Point2D(0,Config.SIZE_Y*0.75),BoundingShape.box(Config.SIZE_X,Config.SIZE_Y*0.25)))
                .with(physicsComponent)
                .collidable()
                .with(new BuildingComponent())
                .build();
    }

    @Spawns("People")
    public Entity newPeople(SpawnData data) {
        PhysicsComponent physicsComponent=new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .type(NATType.PEOPLE)
                .viewWithBBox(new Circle(10, FXGLMath.randomColor()))
                .with(new KeepInBoundsComponent
                        (new Rectangle2D(Config.WINDOW_MIN_X,Config.WINDOW_MIN_Y,
                                Config.WINDOW_MAX_X-Config.WINDOW_MIN_X,
                                Config.WINDOW_MAX_Y-Config.WINDOW_MIN_Y)))
                .with(new MoveComponent())
                .build();
    }

    @Spawns("Site")
    public Entity newSite(SpawnData data) {
        PhysicsComponent physicsComponent=new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.STATIC);

        return FXGL.entityBuilder(data)
                .type(NATType.SITE)
                .viewWithBBox(new Rectangle(30, 30, Color.PINK))
                .collidable()
                .with(physicsComponent)
                .with(new SiteComponent())
                .build();
    }
    
    @Spawns("Effect")
    public Entity newEffect(SpawnData data) {
        double size= data.get("size");
        return FXGL.entityBuilder(data)
                .type(NATType.EFFECT)
                .zIndex(0)
                .with(new EffectComponent(data.get("building")))
                .collidable()
                .view(new Circle(size,Color.RED))
                .bbox(new HitBox(new Point2D(-size/2,-size/2),BoundingShape.box(size*1.8,size*1.8)))
                .build();
    }

}
