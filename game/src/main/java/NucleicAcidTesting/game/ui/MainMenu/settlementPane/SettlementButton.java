package NucleicAcidTesting.game.ui.MainMenu.settlementPane;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SettlementButton extends Button {
    public SettlementButton(String s) {
        super(s);
        this.setPrefSize(130, 50);
        this.setFont(Font.font("Times Roman", FontWeight.BOLD, 16));
        this.setStyle("-fx-background-color: rgba(85,192,232,0.7);" +
                "-fx-border-color: rgba(255,255,255,0.82);" +
                "-fx-border-width: 3px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;" +
                "-fx-text-fill: white;");
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> this.setStyle("-fx-border-color: rgb(246,168,69);" +
                "-fx-background-color: rgba(85,192,232,0.7);" +
                "-fx-border-width: 3px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;" +
                "-fx-text-fill: white;"));

        this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> this.setStyle(("-fx-background-color: rgba(85,192,232,0.7);" +
                "-fx-border-width: 3px;" +
                "-fx-border-color: rgba(255,255,255,0.82);" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;" +
                "-fx-text-fill: white;")));
    }
}
