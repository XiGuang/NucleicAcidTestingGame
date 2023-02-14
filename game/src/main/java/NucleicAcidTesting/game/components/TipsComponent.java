package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class TipsComponent extends Component {
    private final String tips;
    StackPane tipsPane;

    Entity player;

    public TipsComponent(String tips) {
        this.tips=tips;
    }

    @Override
    public void onAdded() {
        player=FXGL.getGameWorld().getSingleton(NATType.PLAYER);
        Rectangle background=new Rectangle(20,20, Color.WHITE);
        Text text= new Text(tips);
        text.setFont(Font.font(15));

        tipsPane = new StackPane(background,text);
        FXGL.addUINode(tipsPane,player.getRightX()-getGameScene().getViewport().getX()+5,
                player.getY()-getGameScene().getViewport().getY()+10);

    }

    @Override
    public void onUpdate(double tpf) {
        FXGL.removeUINode(tipsPane);
        FXGL.addUINode(tipsPane,player.getRightX()-getGameScene().getViewport().getX()+5,
                player.getY()-getGameScene().getViewport().getY()+10);
    }

    @Override
    public void onRemoved() {
        FXGL.removeUINode(tipsPane);
    }
}
