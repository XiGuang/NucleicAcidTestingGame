package NucleicAcidTesting.game.ui.MainMenu.StartPage;

import NucleicAcidTesting.game.Config;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class ChoiceButton extends Button {
    double Height = Config.WINDOW_HEIGHT * 0.15, Width = Config.WINDOW_WIDTH * 0.2;

    public ChoiceButton(String s) {
        super(s);
        this.setPrefSize(Width, Height);
        this.setFont(Font.font("Times Roman", FontWeight.BOLD, 20));
        this.setStyle("-fx-background-color: transparent;" +
                "-fx-border-radius: 0, 0;"+
                "-fx-text-fill: white;");
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
            this.setStyle("-fx-border-color: transparent;" +
                    "-fx-background-color: rgba(153,204,255);" +
                    "-fx-text-fill: black;" );
        });
        ;
        this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
            this.setStyle("-fx-background-color: transparent;" +
                    "-fx-text-fill: white;");
        });
    }
}
