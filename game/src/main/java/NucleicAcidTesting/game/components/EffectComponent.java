package NucleicAcidTesting.game.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import org.jetbrains.annotations.NotNull;

public class EffectComponent extends Component {

    private final Entity fromBuilding;

    private TriggerListener triggerListener;

    public TriggerListener getTriggerListener() {
        return triggerListener;
    }

    public EffectComponent(Entity from_building) {
        this.fromBuilding =from_building;
    }

    @Override
    public void onAdded() {
        triggerListener =new TriggerListener() {
            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                if(keyTrigger.isKey() && keyTrigger.getName().equals("E")){
                    if(!fromBuilding.getComponent(BuildingComponent.class).isSpawning()){
                        fromBuilding.getComponent(BuildingComponent.class).isSpawning(true);
                        entity.setOpacity(0);
                    }else if(fromBuilding.getComponent(BuildingComponent.class).getQueueNum()>0){
                        fromBuilding.getComponent(BuildingComponent.class).followToPlayer();
                    }
                }
            }
        };
    }

}
