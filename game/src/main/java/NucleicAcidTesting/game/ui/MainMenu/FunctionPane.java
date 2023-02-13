package NucleicAcidTesting.game.ui.MainMenu;

import NucleicAcidTesting.game.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class FunctionPane extends GridPane{

        public FunctionPane() {
            this.setPrefSize(Config.WINDOW_WIDTH * 0.7, Config.WINDOW_HEIGHT);
            this.setStyle("-fx-background-color:rgba(153,204,255);");
            this.setAlignment(Pos.CENTER);
        }

}
