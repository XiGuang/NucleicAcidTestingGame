package NucleicAcidTesting.game.ui.MainMenu;

import NucleicAcidTesting.game.Config;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainWin extends StackPane {
    double btnHeight = Config.WINDOW_HEIGHT * 0.15, btnWidth = Config.WINDOW_WIDTH * 0.2;
    double boxHeight = Config.WINDOW_HEIGHT, boxWidth = Config.WINDOW_WIDTH;

    FunctionPane originPane = new FunctionPane();
    FunctionPane classGamePane = new FunctionPane();
    FunctionPane rankPane = new FunctionPane();

    class ChoiceButton extends Button {
        double Height = Config.WINDOW_HEIGHT * 0.15, Width = Config.WINDOW_WIDTH * 0.2;

        public ChoiceButton(String s) {
            super(s);
            this.setPrefSize(btnWidth, btnHeight);
            this.setFont(Font.font("Times Roman", FontWeight.BOLD, 20));
            this.setStyle("-fx-background-color: transparent;" +
                    "-fx-border-radius: 0, 0;");
            this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
                this.setStyle("-fx-border-color: transparent, black;" +
                        "-fx-background-color: #6699FF;" +
                        "-fx-border-width: 1, 1;" +
                        "-fx-border-style: solid;" +
                        "-fx-border-radius: 0, 0;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-insets: 1 1 1 1,0;");
            });
            ;
            this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
                this.setStyle("-fx-background-color: transparent;" +
                        "-fx-text-fill: black;");
            });
        }
    }

    class ChoicePane extends VBox {
        public ChoicePane() {
            Button classicGameButton = new ChoiceButton("限时闯关");
            classicGameButton.setOnAction(actionEvent -> classGamePane.toFront());
            Button infiniteGameButton = new ChoiceButton("无尽游戏");
            infiniteGameButton.setOnAction(actionEvent -> FXGL.getGameController().startNewGame());
            Button setButton = new ChoiceButton("设置");
            setButton.setOnAction(actionEvent -> FXGL.getGameController().gotoGameMenu());
            Button rankButton = new ChoiceButton("排行榜");
            rankButton.setOnAction(actionEvent -> rankPane.toFront());
            Button exitButton = new ChoiceButton("退出");
            exitButton.setOnAction(actionEvent -> FXGL.getGameController().exit());
            Button hellpButton = new ChoiceButton("帮助");
            hellpButton.setOnAction(actionEvent -> new Alert(Alert.AlertType.INFORMATION, "TODO"));

            this.getChildren().addAll(classicGameButton, infiniteGameButton, setButton, rankButton, exitButton, hellpButton);
            this.setPrefSize(boxWidth * 0.2, boxHeight);
            this.setStyle("-fx-background-color:rgba(153,204,255,0.6);");
            this.setAlignment(Pos.CENTER);
        }
    }

    class FunctionPane extends GridPane {
        public FunctionPane() {
            this.setPrefSize(boxWidth * 0.7, boxHeight);
            this.setStyle("-fx-background-color:rgba(153,204,255);");
            this.setAlignment(Pos.CENTER);
        }
    }

    class DetailPane extends Pane {
        public DetailPane() {

            Image Img = new Image("assets/textures/menuImg/detailBg.gif",
                    400, 400, false, false);
            ImageView imageView = new ImageView(Img);
            imageView.setOpacity(1);
            this.setPrefSize(boxWidth * 0.7, boxHeight);
            this.setStyle("-fx-background-color:rgba(153,204,255,0.6);");
            this.setLayoutX(Config.WINDOW_WIDTH * 0.25);
            originPane.add(imageView, 0, 0);

            Button classGameButton = new Button("开始游戏");
            classGameButton.setOnAction(actionEvent -> FXGL.getGameController().startNewGame());
            classGameButton.setPrefSize(200, 50);
            GridPane.setHalignment(classGameButton, HPos.CENTER);
            Image classicGameImg = new Image("assets/textures/menuImg/classicGameImg.png",
                    500, 250, false, false);
            ImageView classicGameImgView = new ImageView(classicGameImg);
            classGamePane.add(classicGameImgView, 0, 0);
            classGamePane.add(classGameButton, 0, 1);
            classGamePane.setHgap(20);
            classGamePane.setVgap(20);


            Text rankTitle = new Text("排行榜");
            rankTitle.setFont(Font.font("Times Roman", FontWeight.BOLD, 20));
            GridPane.setHalignment(rankTitle, HPos.CENTER);
            ScrollPane scrollPane = new ScrollPane();
            rankPane.add(rankTitle, 0, 0);
            rankPane.add(scrollPane, 0, 1);

            this.getChildren().add(rankPane);
            this.getChildren().add(classGamePane);
            this.getChildren().add(originPane);

        }
    }


    public MainWin() {
        Pane pane = new Pane(new ChoicePane(), new DetailPane());
        this.getChildren().add(pane);
        this.setPrefSize(boxWidth, boxHeight);
        this.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
