package NucleicAcidTesting.game.EntityFactory;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.AreaComponent.BuildingAreaComponent;
import NucleicAcidTesting.game.components.AreaComponent.SiteAreaComponent;
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
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class NATFactory implements EntityFactory {

    @Spawns("Background")
    public Entity newBackground(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(NATType.BACKGROUND)
                .view("map.png")
                .zIndex(Config.BACKGROUND_LEVEL)
                .build();
    }

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .type(NATType.PLAYER)
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
            SpawnData data=new SpawnData(point);
            data.put("infinity",true);
            getGameWorld().spawn("Building", data);
        }
    }

    @Spawns("Building")
    public Entity newBuilding(SpawnData data) {
        PhysicsComponent physicsComponent=new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.STATIC);
        int num=FXGL.random(1,3);
        double width = 100,height=100;
        switch (num) {
            case 1 -> {
                double ratio=0.3;
                width = 388*ratio;
                height = 599*ratio;
            }
            case 2 -> {
                double ratio=0.3;
                width = 574*ratio;
                height = 531*ratio;
            }
            case 3 -> {
                double ratio=0.3;
                width = 505*ratio;
                height = 540*ratio;
            }
        }
        boolean is_infinity=false;
        if(data.hasKey("infinity"))
            is_infinity=data.get("infinity");
        Texture texture=new Texture(FXGL.image("Building/楼"+num+".png",width,height));

        return FXGL.entityBuilder()
                .type(NATType.BUILDING)
                .at(data.getX() - width/2, data.getY() -height/2)
                .view(texture)
                .bbox(new HitBox(new Point2D(0,height*0.55),BoundingShape.box(width-10,height*0.25)))
                .with(physicsComponent)
                .collidable()
                .with(new BuildingComponent(is_infinity))
                .build();
    }

    @Spawns("People")
    public Entity newPeople(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(NATType.PEOPLE)
                .bbox(new HitBox(new Point2D(0,30),BoundingShape.box(10,10)))
                .with(new KeepInBoundsComponent
                        (new Rectangle2D(Config.WINDOW_MIN_X,Config.WINDOW_MIN_Y,
                                Config.WINDOW_MAX_X-Config.WINDOW_MIN_X,
                                Config.WINDOW_MAX_Y-Config.WINDOW_MIN_Y)))
                .with(new MoveComponent())
                .with(new PeopleComponent())
                .build();
    }
    
     public static void spawnSite() {
        Rectangle2D bound = new Rectangle2D(Config.WINDOW_MIN_X+Config.GAP_TO_WINDOW,Config.WINDOW_MIN_Y+Config.GAP_TO_WINDOW,
                Config.WINDOW_MAX_X,Config.WINDOW_MAX_Y-Config.WINDOW_MIN_Y-Config.GAP_TO_WINDOW-200);
        Point2D point;
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
        }
        getGameWorld().spawn("Site", point);
    }

    @Spawns("Site")
    public Entity newSite(SpawnData data) {
        PhysicsComponent physicsComponent=new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.STATIC);
        double width=445/2.5,height=490/2.5;
        Texture texture=new Texture(FXGL.image("Building/医院.png",width,height));
        texture.setScaleX(-1);
        return FXGL.entityBuilder()
                .type(NATType.SITE)
                .at(data.getX() - width/2, data.getY() -height/2)
                .view(texture)
                .bbox(new HitBox(new Point2D(0,height*0.5),BoundingShape.box(width-8,height*0.45)))
                .collidable()
                .with(physicsComponent)
                .with(new SiteComponent())
                .build();
    }
    
    @Spawns("BuildingArea")
    public Entity newBuildingArea(SpawnData data) {
        double size= data.get("size");
        return FXGL.entityBuilder(data)
                .type(NATType.AREA)
                .zIndex(0)
                .with(new BuildingAreaComponent(data.get("building")))
                .collidable()
                .view(new Circle(size,Color.RED))
                .bbox(new HitBox(new Point2D(-size,-size),BoundingShape.box(size*1.8,size*1.8)))
                .build();
    }

    @Spawns("SiteArea")
    public Entity newSiteArea(SpawnData data) {
        double size=data.get("size");
        return FXGL.entityBuilder(data)
                .type(NATType.AREA)
                .zIndex(0)
                .with(new SiteAreaComponent(data.get("site")))
                .collidable()
                .view(new Circle(size,Color.GREEN))
                .bbox(new HitBox(new Point2D(-size,-size),BoundingShape.box(size*1.8,size*1.8)))
                .build();
    }

}
