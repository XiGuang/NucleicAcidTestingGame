package NucleicAcidTesting.game.EntityFactory;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.collision.PlayerBuildingHandler;
import NucleicAcidTesting.game.components.BuildingComponent;
import NucleicAcidTesting.game.components.PeopleComponent;
import NucleicAcidTesting.game.components.PlayerComponent;
import NucleicAcidTesting.game.components.SiteComponent;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NATFactory implements EntityFactory {

    @Spawns("Background")
    public Entity newBackground(SpawnData data) {

        return FXGL.entityBuilder(data)
                .view("floor.png")
                .zIndex(Config.BACKGROUND_LEVEL)
                .build();
    }

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);
        physicsComponent.setFixtureDef(new FixtureDef());
        physicsComponent.addSensor(new HitBox("PLAYER_SENSOR",new Point2D(0,0),BoundingShape.box(20,20)), new PlayerBuildingHandler());

        return FXGL.entityBuilder(data)
                .type(NATType.PLAYER)
                .zIndex(Config.ACTION_LEVEL)
                .viewWithBBox(new Rectangle(20, 20, Color.LIGHTBLUE))
                .with(physicsComponent)
                .collidable()
                .with(new KeepOnScreenComponent())
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("Building")
    public Entity newBuilding(SpawnData data) {
        PhysicsComponent physicsComponent=new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.STATIC);
        physicsComponent.addSensor(new HitBox("BUILDING_SENSOR",new Point2D(0,0),BoundingShape.box(Config.SIZE_X,Config.SIZE_Y)),new SensorCollisionHandler(){});

        return FXGL.entityBuilder()
                .type(NATType.BUILDING)
                .at(data.getX() + Config.OFFSET_X, data.getY() + Config.OFFSET_Y)
                .zIndex(Config.BUILDING_LEVEL)
                .view(new Rectangle(Config.SIZE_X, Config.SIZE_Y, Color.GRAY))
                .bbox(new HitBox(new Point2D(0,Config.SIZE_Y*0.75),BoundingShape.box(Config.SIZE_X,Config.SIZE_Y*0.25)))
                .with(physicsComponent)
                .collidable()
                .with(new BuildingComponent())
                .build();
    }

    @Spawns("People")
    public Entity newPeople(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.PEOPLE)
                .zIndex(Config.ACTION_LEVEL)
                .viewWithBBox(new Circle(10, FXGLMath.randomColor()))
                .with(new PeopleComponent())
                .build();
    }

    @Spawns("Site")
    public Entity newSite(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.SITE)
                .zIndex(Config.BUILDING_LEVEL)
                .viewWithBBox(new Rectangle(30, 30, Color.PINK))
                .with(new SiteComponent())
                .build();
    }

}
