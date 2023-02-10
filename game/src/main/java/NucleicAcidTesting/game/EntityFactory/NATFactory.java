package NucleicAcidTesting.game.EntityFactory;

import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.BuildingComponent;
import NucleicAcidTesting.game.components.PeopleComponent;
import NucleicAcidTesting.game.components.PlayerComponent;
import NucleicAcidTesting.game.components.SiteComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NATFactory implements EntityFactory {

    @Spawns("Background")
    public Entity newBackground(SpawnData data) {

        return FXGL.entityBuilder(data)
                .view("floor.png")
                .zIndex(-1)
                .build();
    }

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physicsComponent=new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.KINEMATIC);

        return FXGL.entityBuilder(data)
                .type(NATType.PLAYER)
                .viewWithBBox(new Rectangle(20,20, Color.LIGHTBLUE))
                .with(new CollidableComponent(true))
                .with(physicsComponent)
                .with(new KeepOnScreenComponent())
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("Building")
    public Entity newBuilding(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.BUILDING)
                .with(new BuildingComponent())
                .build();
    }

    @Spawns("People")
    public Entity newPeople(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.PEOPLE)
                .with(new PeopleComponent())
                .build();
    }

    @Spawns("Site")
    public Entity newSite(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.SITE)
                .with(new SiteComponent())
                .build();
    }

}
