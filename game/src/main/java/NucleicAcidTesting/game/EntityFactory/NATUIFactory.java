package NucleicAcidTesting.game.EntityFactory;

import NucleicAcidTesting.game.NATType;
import NucleicAcidTesting.game.components.*;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.Texture;

public class NATUIFactory implements EntityFactory {

    @Spawns("UICountDown")
    public Entity newUICountDown(SpawnData data) {
        int time=100;
        if(data.hasKey("time"))
            time=data.get("time");

        return FXGL.entityBuilder(data)
                .type(NATType.COUNT_DOWN)
                // 在CountDownComponent中传入初始的秒数来进行倒计时
                .with(new CountDownComponent(time))
                .build();
    }
    @Spawns("ControlButton")
    public Entity newControlButton(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(NATType.CONTROL_BUTTON)
                .with(new ControlButtonComponent())
                .build();
    }

    @Spawns("Mood")
    public Entity newMood(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(NATType.MOOD)
                .zIndex(Integer.MAX_VALUE)
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

    @Spawns("Score")
    public Entity newScore(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new ScoreComponent())
                .build();
    }

    @Spawns("NeedScore")
    public Entity newNeedScore(SpawnData data) {
        int need_score=100;
        if(data.hasKey("need_score"))
            need_score=data.get("need_score");

        return FXGL.entityBuilder(data)
                .with(new NeedScoreComponent(need_score))
                .build();
    }

    @Spawns("InfiniteGameLoadController")
    public Entity newInfiniteGameLoadController(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new InfiniteGameLoadControllerComponent())
                .build();
    }
}
