package NucleicAcidTesting.game.components;

import NucleicAcidTesting.game.ui.SuccessPane;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NeedScoreComponent extends Component {

    int needScore;

    public NeedScoreComponent(int need_score) {
        this.needScore=need_score;
    }

    @Override
    public void onAdded() {
        Text text=new Text("目标分数："+needScore);
        text.setFont(Font.font("宋体", FontWeight.BOLD,30));
        FXGL.addUINode(text, 40, 50);
    }

    @Override
    public void onUpdate(double tpf) {
        if(needScore<= FXGL.getWorldProperties().getInt("people_num")){
            SuccessPane successPane=new SuccessPane();
            FXGL.getGameController().pauseEngine();
            successPane.setVisible(true);
        }
    }
}
