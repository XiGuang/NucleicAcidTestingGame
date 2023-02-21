package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class TipsComponent extends Component {
    private String tips;
    private Texture texture;
    private StackPane tipsPane;

    private Entity player;

    public TipsComponent(String tips) {
        this.tips=tips;
    }

    public TipsComponent(Texture texture){
        this.texture=texture;
    }

    @Override
    public void onAdded() {
        player=FXGL.getGameWorld().getSingleton(NATType.PLAYER);
        if(texture==null) {
            Rectangle background = new Rectangle(20, 20, Color.WHITE);
            Text text = new Text(tips);
            text.setFont(Font.font(15));

            tipsPane = new StackPane(background, text);
            FXGL.addUINode(tipsPane,player.getRightX()-getGameScene().getViewport().getX()+8,
                    player.getY()-getGameScene().getViewport().getY()+5);
        }else{
            tipsPane=new StackPane(texture);
            FXGL.addUINode(tipsPane,player.getX()-getGameScene().getViewport().getX()-15,
                    player.getY()-getGameScene().getViewport().getY()+5);
        }


    }

    @Override
    public void onUpdate(double tpf) {
        FXGL.removeUINode(tipsPane);
        if(texture==null){
            FXGL.addUINode(tipsPane,player.getRightX()-getGameScene().getViewport().getX()+8,
                    player.getY()-getGameScene().getViewport().getY()+5);
        }else{
            FXGL.addUINode(tipsPane,player.getX()-getGameScene().getViewport().getX()-15,
                    player.getY()-getGameScene().getViewport().getY()+5);
        }

    }

    @Override
    public void onRemoved() {
        FXGL.removeUINode(tipsPane);
    }
}
