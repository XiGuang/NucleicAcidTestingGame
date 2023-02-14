package NucleicAcidTesting.game.EntityFactory;

import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.CountDownComponent;
import NucleicAcidTesting.game.components.TipsComponent;
import NucleicAcidTesting.game.components.MoodComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class NATUIFactory implements EntityFactory {

    @Spawns("UICountDown")
    public Entity newUICountDown(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.COUNT_DOWN)
                // 在CountDownComponent中传入初始的秒数来进行倒计时
                .with(new CountDownComponent(130))
                .build();
    }

    @Spawns("Mood")
    public Entity newMood(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.MOOD)
                .with(new MoodComponent())
                .build();
    }

    @Spawns("Tips")
    public Entity newTips(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new TipsComponent(data.get("tips")))
                .build();
    }
}
