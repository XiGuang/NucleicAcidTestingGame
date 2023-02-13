package NucleicAcidTesting.game;

import com.almasb.fxgl.entity.Entity;

import java.util.Comparator;

public class EntityComparator implements Comparator<Entity> {

    @Override
    public int compare(Entity e1, Entity e2) {
        return (int) ((e1.getBottomY()-e2.getBottomY())*100);
    }
}
