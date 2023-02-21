package NucleicAcidTesting.game.components.AreaComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.TriggerListener;

public abstract class BaseAreaComponent extends Component {
    protected final Entity fromBuilding;

    protected TriggerListener triggerListener;

    public TriggerListener getTriggerListener() {
        return triggerListener;
    }

    public BaseAreaComponent(Entity from_building) {
        this.fromBuilding =from_building;
    }

    @Override
    abstract public void onAdded();
}
