package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.NATType;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class SiteComponent extends Component {
    public void onAdded(){
        Canvas canvas =new Canvas();
        Rectangle2D bound=new Rectangle2D(20,40,200,400);
        Point2D point2D;
        point2D= FXGLMath.randomPoint(bound);
        GraphicsContext g2d=canvas.getGraphicsContext2D();
        g2d.fillRect (point2D.getX()- Config.GAP_X/2,
                point2D.getY()-Config.GAP_Y,
                    Config.GAP_X,
                    Config.GAP_Y);
        StackPane pane=new StackPane(canvas);
        entity.getViewComponent().addChild(pane);
    }
}
