package NucleicAcidTesting.game.ui.MainMenu;

import NucleicAcidTesting.game.Config;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import java.util.List;

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
            rankButton.setOnAction(actionEvent -> {rankPane.toFront();
                try {
                    getRank();
                } catch (Exception e) {
                    Popup.warningBox("网络连接错误，请重试！");
                }
            });
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



    class DetailPane extends Pane {
        public DetailPane() {
            this.setPrefSize(boxWidth * 0.7, boxHeight);
            this.setStyle("-fx-background-color:rgba(153,204,255,0.6);");
            this.setLayoutX(Config.WINDOW_WIDTH * 0.25);
            setOriginPane();
            setClassGamePane();
            setRankPane();




            this.getChildren().add(rankPane);
            this.getChildren().add(classGamePane);
            this.getChildren().add(originPane);

        }
    }

    private void setOriginPane() {
        Image Img = new Image("assets/textures/menuImg/detailBg.gif",
                400, 400, false, false);
        ImageView imageView = new ImageView(Img);
        imageView.setOpacity(1);

        originPane.add(imageView, 0, 0);
    }

    private void setClassGamePane() {
        Button classGameButton = new Button("开始游戏");
        classGameButton.setOnAction(actionEvent -> FXGL.getGameController().startNewGame());
        classGameButton.setPrefSize(200, 50);
        GridPane.setHalignment(classGameButton, HPos.CENTER);
        Image classicGameImg = new Image("assets/textures/menuImg/classicGameImg.png",
                500, 250, false, false);
        ImageView classicGameImgView = new ImageView(classicGameImg);
        classGamePane.add(classicGameImgView, 0, 0);
        classGamePane.add(classGameButton, 0, 1);
        classGamePane.setVgap(20);
    }

    private void setRankPane() {
        Image rankLogo = new Image("assets/textures/menuImg/rankLogo.png",
                250, 100, false, false);
        ImageView rankLogoView = new ImageView(rankLogo);

        GridPane.setHalignment(rankLogoView, HPos.CENTER);
        rankPane.add(rankLogoView, 0, 0);
        rankPane.setVgap(20);
    }

    private void getRank() throws Exception {
        ScrollPane scrollPane = new ScrollPane();
        List<JSONObject> rankList = Client.Ranking();
        VBox vBox = new VBox();
        vBox.setPrefSize(Config.WINDOW_WIDTH * 0.5,Config.WINDOW_HEIGHT);

        for (JSONObject jsonObject : rankList) {
            String ranking = jsonObject.get("Ranking").toString();
            Text rankingText = new Text(ranking);
            rankingText.setFont(Font.font("Times Roman", FontWeight.LIGHT, 20));
            rankingText.resize(1000,1000);
            String name =  jsonObject.get("Name").toString();
            Text nameText = new Text(name);
            nameText.setFont(Font.font("Times Roman", FontWeight.LIGHT, 20));
            String score = jsonObject.get("Number").toString();
            Text scoreText = new Text(score);
            scoreText.setFont(Font.font("Times Roman", FontWeight.LIGHT, 20));
            GridPane gridPane = new GridPane();
            String gap1 = "----------------------------------";
            String gap2 = "----------------------------------------";
            String gap3 = "-----------";
            String empty = "     ";
            Text emptyText1 = new Text(empty);
            Text emptyText2 = new Text(empty);
            Text gapText1 = new Text(gap1);
            Text gapText2 = new Text(gap2);
            Text gapText3= new Text(gap3);
            gridPane.add(emptyText1,0,0);
            gridPane.add(rankingText,1,0);
            gridPane.add(nameText,2,0);
            gridPane.add(scoreText,3,0);
            gridPane.add(emptyText2,0,1);
            gridPane.add(gapText1,1,1);
            gridPane.add(gapText2,2,1);
            gridPane.add(gapText3,3,1);

            gridPane.setStyle("-fx-background-color: #99CCFF;");
            vBox.getChildren().add(gridPane);

        }
        vBox.setStyle("-fx-background-color: #99CCFF;");
        scrollPane.setContent(vBox);
        scrollPane.setPrefSize(Config.WINDOW_WIDTH * 0.5,Config.WINDOW_HEIGHT*0.6);

        scrollPane.setBackground(new Background(new BackgroundFill(Color.web("#99CCFF"), CornerRadii.EMPTY, Insets.EMPTY)));


        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rankPane.add(scrollPane, 0, 1);


    }

    public MainWin() {
        Pane pane = new Pane(new ChoicePane(), new DetailPane());
        this.getChildren().add(pane);
        this.setPrefSize(boxWidth, boxHeight);
        this.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
