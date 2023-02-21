package NucleicAcidTesting.game.components.AreaComponent;

import NucleicAcidTesting.game.components.SiteComponent;
import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class SiteAreaComponent extends BaseAreaComponent {

    Entity tips;

    public SiteAreaComponent(Entity fromSiting) {
        super(fromSiting);
    }

    @Override
    public void onAdded() {
        SpawnData data=new SpawnData();
        Texture texture=new Texture(FXGL.image("UI/NATing.png",20,20));
        texture.setScaleX(-1);
        data.put("texture",texture);

        triggerListener =new TriggerListener() {

            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                if(keyTrigger.isKey() && keyTrigger.getName().equals("E")){
                    fromBuilding.getComponent(SiteComponent.class).queueUp();
                    fromBuilding.getComponent(SiteComponent.class).setFaster(true);

                    tips= FXGL.spawn("Tips",data);
                }
            }

            @Override
            protected void onKeyEnd(@NotNull KeyTrigger keyTrigger) {
                if(keyTrigger.isKey() && keyTrigger.getName().equals("E")){
                    fromBuilding.getComponent(SiteComponent.class).setNATTime(2);
                    fromBuilding.getComponent(SiteComponent.class).setFaster(false);
                    tips.removeFromWorld();
                }
            }
        };
    }

}
