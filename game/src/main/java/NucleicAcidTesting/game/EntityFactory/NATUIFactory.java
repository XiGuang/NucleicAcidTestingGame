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
import com.almasb.fxgl.texture.Texture;

public class NATUIFactory implements EntityFactory {

    @Spawns("UICountDown")
    public Entity newUICountDown(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.COUNT_DOWN)
                // 在CountDownComponent中传入初始的秒数来进行倒计时
                .with(new CountDownComponent(100))
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
        if(data.hasKey("tips")){
            return FXGL.entityBuilder(data)
                    .with(new TipsComponent((String) data.get("tips")))
                    .build();
        }else{
            return FXGL.entityBuilder(data)
                    .with(new TipsComponent((Texture) data.get("texture")))
                    .build();
        }

    }
}
