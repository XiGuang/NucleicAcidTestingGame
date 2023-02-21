package NucleicAcidTesting.game.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingWin extends Stage {

    Stage loadWin;
    public LoadingWin() {
        loadWin = new Stage();
        Image image = new Image("assets/textures/loadingImg.png",
                557, 102, false, false);
        ImageView imageView = new ImageView(image);
        GridPane pane = new GridPane();
        pane.setPrefSize(557, 102);
        pane.add(imageView,0,0);
        pane.setStyle("-fx-background-color: transparent;");

        Scene scene = new Scene(pane);
        scene.setFill(null);
        loadWin.setScene(scene);
        loadWin.initStyle(StageStyle.TRANSPARENT);
        loadWin.show();

    }

    public void closeLoading(){
        loadWin.close();

    }
}
