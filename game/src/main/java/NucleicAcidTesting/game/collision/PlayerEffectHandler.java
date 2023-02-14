package NucleicAcidTesting.game.collision;

import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.EffectComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerEffectHandler extends CollisionHandler {
    private TriggerListener triggerListener;

    Entity keyTips;

    public PlayerEffectHandler() {
        super(NATType.PLAYER, NATType.EFFECT);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity effect) {

        triggerListener=effect.getComponent(EffectComponent.class).getTriggerListener();
        FXGL.getInput().addTriggerListener(triggerListener);

        keyTips=FXGL.getGameWorld().spawn("Tips",new SpawnData().put("tips","E"));
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity effect) {
        FXGL.getInput().removeTriggerListener(triggerListener);
        FXGL.getGameWorld().removeEntity(keyTips);
    }
}
