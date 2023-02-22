package NucleicAcidTesting.game.components.AreaComponent;

import NucleicAcidTesting.game.components.BuildingComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import org.jetbrains.annotations.NotNull;

public class BuildingAreaComponent extends BaseAreaComponent {

    public BuildingAreaComponent(Entity from_building) {
        super(from_building);
    }

    @Override
    public void onAdded() {
        triggerListener = new TriggerListener() {
            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                if (keyTrigger.isKey() && keyTrigger.getName().equals("E")) {
                    if(!fromBuilding.hasComponent(BuildingComponent.class))
                        return;

                    if (!fromBuilding.getComponent(BuildingComponent.class).isStart()) {
                        fromBuilding.getComponent(BuildingComponent.class).setStart(true);
                        entity.setOpacity(0);
                    } else if (fromBuilding.getComponent(BuildingComponent.class).getQueueNum() > 0) {
                        fromBuilding.getComponent(BuildingComponent.class).followToPlayer();
                    }
                }
            }
        };
    }

}
