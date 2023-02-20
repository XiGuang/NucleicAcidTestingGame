package NucleicAcidTesting.game.collision;

import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.AreaComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerAreaHandler extends CollisionHandler {
    private TriggerListener triggerListener;

    Entity keyTips,Mood;

    public PlayerAreaHandler() {
        super(NATType.PLAYER, NATType.AREA);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity effect) {

        triggerListener=effect.getComponent(AreaComponent.class).getTriggerListener();
        FXGL.getInput().addTriggerListener(triggerListener);

        keyTips=FXGL.getGameWorld().spawn("Tips",new SpawnData().put("tips","E"));
    }

    @Override
    protected void onCollisionEnd(Entity player, Entity effect) {
        FXGL.getInput().removeTriggerListener(triggerListener);
        FXGL.getGameWorld().removeEntity(keyTips);
    }
}
