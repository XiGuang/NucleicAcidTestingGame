package NucleicAcidTesting.game.collision;

import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.SensorCollisionHandler;

public class PlayerBuildingHandler extends SensorCollisionHandler {
    @Override
    protected void onCollisionBegin(Entity other) {
        if(other.isType(NATType.BUILDING))
            other.setOpacity(0.5);
    }

    @Override
    protected void onCollisionEnd(Entity other) {
        if(other.isType(NATType.BUILDING))
            other.setOpacity(1);
    }
}
