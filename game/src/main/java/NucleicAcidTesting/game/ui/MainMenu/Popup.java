package NucleicAcidTesting.game.ui.MainMenu;

import javafx.scene.control.Alert;

public class Popup {
    public static void warningBox(String infoMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void infoBox(String infoMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void errorBox(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
