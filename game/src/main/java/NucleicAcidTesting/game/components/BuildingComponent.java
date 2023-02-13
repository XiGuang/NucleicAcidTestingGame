package NucleicAcidTesting.game.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;

public class BuildingComponent extends Component {
    int max_people_num=20;

    ArrayList<Entity> queue_residents=new ArrayList<Entity>();

    @Override
    public void onUpdate(double tpf) {

    }
}
