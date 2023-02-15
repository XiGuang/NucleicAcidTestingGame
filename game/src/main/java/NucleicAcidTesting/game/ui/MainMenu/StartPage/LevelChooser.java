package NucleicAcidTesting.game.ui.MainMenu.StartPage;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.MapLoader;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.set;

public class LevelChooser extends Button {
    static String chosenLevel;
    String level;
    boolean chosen;
    boolean styled = false;
    double Height = Config.WINDOW_HEIGHT * 0.1, Width = Config.WINDOW_WIDTH * 0.1;

    public LevelChooser(String level, Node levelThumbnail) {
        super("", levelThumbnail);
        this.level = level;
        this.setPrefSize(Width, Height);
        this.setStyle("-fx-background-color: transparent;");
        this.updateBounds();
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
            this.setStyle("-fx-border-color: rgb(202,105,46);" +
                    "-fx-background-color: rgb(246,160,70);" +
                    "-fx-border-radius: 5px;" +
                    "-fx-background-radius: 5px;");
        });
        ;
        this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
            if (!Objects.equals(level, chosenLevel)) {
                this.setStyle("-fx-background-color: transparent;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;");
            }
        });


        this.setOnAction(actionEvent -> {
            chosen = true;
            chosenLevel = level;
            MapLoader.setMapLevel(level);
        });
    }

    public static String getChosenLevel() {
        return chosenLevel;
    }

    public String getLevel() {
        return level;
    }

    public boolean isChosen() {
        return chosen;
    }

    public boolean isStyled() {
        return styled;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public void setStyled(boolean styled) {
        this.styled = styled;
    }

    static public void showChosenButton(List<LevelChooser> levelChooserList) {
        for (LevelChooser levelChooser : levelChooserList) {
            if (!levelChooser.isChosen()) continue;
            else if (levelChooser.isChosen() &&
                    levelChooser.getLevel().equals(LevelChooser.getChosenLevel()) &&
                    levelChooser.styled) {
                continue;
            } else if (levelChooser.isChosen() &&
                    !levelChooser.getLevel().equals(LevelChooser.getChosenLevel()) &&
                    levelChooser.styled) {
                levelChooser.setStyled(false);
                levelChooser.setChosen(false);
                levelChooser.setStyle("-fx-background-color: transparent;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;");
                ;
            } else if (levelChooser.isChosen() &&
                    levelChooser.getLevel().equals(LevelChooser.getChosenLevel()) &&
                    !levelChooser.styled) {
                levelChooser.setStyled(true);
                levelChooser.setChosen(true);
                levelChooser.setStyle("-fx-border-color: rgb(202,105,46);" +
                        "-fx-background-color: rgb(246,160,70);" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;");
            }
        }
    }

    public static List<LevelChooser> getLevels() {
        List<LevelChooser> levelList = new ArrayList<>();

        Image levelThumbnail1 = new Image("assets/textures/menuImg/LevelThumbnail/levelThumbnail1.png",
                Config.WINDOW_WIDTH * 0.1, Config.WINDOW_HEIGHT * 0.1, false, false);
        Image levelThumbnail2 = new Image("assets/textures/menuImg/LevelThumbnail/levelThumbnail2.png",
                Config.WINDOW_WIDTH * 0.1, Config.WINDOW_HEIGHT * 0.1, false, false);

        ImageView thumbnailView1 = new ImageView(levelThumbnail1);
        ImageView thumbnailView2 = new ImageView(levelThumbnail2);

        LevelChooser level1 = new LevelChooser("1", thumbnailView1);
        LevelChooser level2 = new LevelChooser("2", thumbnailView2);

        levelList.add(level1);
        levelList.add(level2);
        return levelList;
    }
}
