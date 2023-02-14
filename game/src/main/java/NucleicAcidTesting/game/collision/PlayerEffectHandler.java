package NucleicAcidTesting.game.collision;

import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.EffectComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerEffectHandler extends CollisionHandler {
    private TriggerListener triggerListener;

    public PlayerEffectHandler() {
        super(NATType.PLAYER, NATType.EFFECT);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity effect) {

        triggerListener=effect.getComponent(EffectComponent.class).getTriggerListener();
        FXGL.getInput().addTriggerListener(triggerListener);
        // TODO:添加UI提示，提示玩家按E
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity effect) {
        FXGL.getInput().removeTriggerListener(triggerListener);
    }
}
