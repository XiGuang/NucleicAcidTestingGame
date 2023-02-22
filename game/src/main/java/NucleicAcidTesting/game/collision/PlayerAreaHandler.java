package NucleicAcidTesting.game.collision;

import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.AreaComponent.BuildingAreaComponent;
import NucleicAcidTesting.game.components.AreaComponent.SiteAreaComponent;
import NucleicAcidTesting.game.components.SiteComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerAreaHandler extends CollisionHandler {
    private TriggerListener triggerListener;

    Entity keyTips;

    public PlayerAreaHandler() {
        super(NATType.PLAYER, NATType.AREA);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity effect) {
        if(effect.hasComponent(BuildingAreaComponent.class))
            triggerListener=effect.getComponent(BuildingAreaComponent.class).getTriggerListener();
        else if(effect.hasComponent(SiteAreaComponent.class))
            triggerListener=effect.getComponent(SiteAreaComponent.class).getTriggerListener();
        FXGL.getInput().addTriggerListener(triggerListener);

        keyTips=FXGL.getGameWorld().spawn("Tips",new SpawnData().put("tips","E"));
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity effect) {
        FXGL.getInput().removeTriggerListener(triggerListener);

        // 防止走出碰撞范围后无法触发onKeyEnd
        if(effect.hasComponent(SiteAreaComponent.class)){
            var site_area_component=effect.getComponent(SiteAreaComponent.class);
            if(site_area_component.tips!=null)
                site_area_component.tips.removeFromWorld();
            if(site_area_component.getFromBuilding().hasComponent(SiteComponent.class))
                site_area_component.getFromBuilding().getComponent(SiteComponent.class).setFaster(false);
        }

        FXGL.getGameWorld().removeEntity(keyTips);
    }
}
