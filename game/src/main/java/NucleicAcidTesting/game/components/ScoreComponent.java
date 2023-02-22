package NucleicAcidTesting.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreComponent extends Component {
    Text text;

    @Override
    public void onAdded() {
        Rectangle rectangle = new Rectangle(80,50);
        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(3);
        text = FXGL.getUIFactoryService().newText(String.valueOf(FXGL.getWorldProperties().getInt("people_num")*10));
        text.setFill(Color.BLUE);
        text.fontProperty().unbind();
        text.setFont(Font.font(35));

        StackPane stackPane = new StackPane(rectangle,text);
        FXGL.addUINode(stackPane,400,30);
    }

    @Override
    public void onUpdate(double tpf) {
        text.setText(String.valueOf(FXGL.getWorldProperties().getInt("people_num")*10));
    }
}
