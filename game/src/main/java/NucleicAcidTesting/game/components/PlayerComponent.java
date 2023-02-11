package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.NATMath;
import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.List;

public class PlayerComponent extends Component {

    LazyValue<EntityGroup> entityGroupLazyValue = new LazyValue<>(() -> FXGL.getGameWorld().getGroup(NATType.BUILDING, NATType.SITE));

    @Override
    public void onAdded() {

    }

    @Override
    public void onUpdate(double tpf) {
        List<Entity> entities = entityGroupLazyValue.get().getEntitiesCopy();

        for (var e : entities) {
            Point2D point_left_up = e.getPosition();
            Bounds bounds = e.getViewComponent().getChildren().get(0).getBoundsInLocal();
            Rectangle2D rectangle2D = new Rectangle2D(point_left_up.getX(), point_left_up.getY(), bounds.getWidth(), bounds.getHeight());

            if (rectangle2D.contains(entity.getCenter())) {
                e.setOpacity(NATMath.InterpolationD(e.getOpacity(),0.5,tpf*3));
            } else if (e.getOpacity() != 1)
                e.setOpacity(NATMath.InterpolationD(e.getOpacity(),1,tpf*3));
        }

    }
}


