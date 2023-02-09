package NucleicAcidTesting.game.collision;

import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerPoepleHandler extends CollisionHandler {
    public PlayerPoepleHandler(Object a, Object b) {
        super(NATType.PLAYER,NATType.PEOPLE);
    }

    @Override
    protected void onCollisionBegin(Entity Player, Entity People) {

    }
}
