package NucleicAcidTesting.game.components.AreaComponent;

import NucleicAcidTesting.game.components.SiteComponent;
import NucleicAcidTesting.game.tools.NATMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import org.jetbrains.annotations.NotNull;

public class SiteAreaComponent extends BaseAreaComponent {

    public SiteAreaComponent(Entity fromSiting) {
        super(fromSiting);
    }

    public TriggerListener getTriggerListener() {
        return triggerListener;
    }

    @Override
    public void onAdded() {
        triggerListener =new TriggerListener() {
            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                if(keyTrigger.isKey() && keyTrigger.getName().equals("E")){
                    fromBuilding.getComponent(SiteComponent.class).queueUp();
                    fromBuilding.getComponent(SiteComponent.class).setFaster(true);
                }
            }


            @Override
            protected void onKeyEnd(@NotNull KeyTrigger keyTrigger) {
                if(keyTrigger.isKey() && keyTrigger.getName().equals("E")){
                    fromBuilding.getComponent(SiteComponent.class).setNATTime(2);
                    fromBuilding.getComponent(SiteComponent.class).setFaster(false);
                }
            }
        };
    }

}
