package NucleicAcidTesting.game.ui.MainMenu.StartPage;

import NucleicAcidTesting.game.Config;
import NucleicAcidTesting.game.ui.MainMenu.LoginPage.Client;
import NucleicAcidTesting.game.ui.MainMenu.Popup;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.List;

public class MainWin extends StackPane {
    double boxHeight = Config.WINDOW_HEIGHT, boxWidth = Config.WINDOW_WIDTH;

    FunctionPane originPane = new FunctionPane();
    FunctionPane classGamePane = new FunctionPane();
    FunctionPane rankPane = new FunctionPane();

    VBox rankBox = new VBox();

    class ChoicePane extends VBox {


        public ChoicePane() {
            Button classicGameButton = new ChoiceButton("限时闯关");
            classicGameButton.setOnAction(actionEvent -> classGamePane.toFront());
            Button infiniteGameButton = new ChoiceButton("无尽游戏");
            infiniteGameButton.setOnAction(actionEvent -> FXGL.getGameController().startNewGame());
            Button setButton = new ChoiceButton("设置");
            setButton.setOnAction(actionEvent -> FXGL.getGameController().gotoGameMenu());
            Button rankButton = new ChoiceButton("排行榜");
            rankButton.setOnAction(actionEvent -> {
                rankPane.toFront();
                try {
                    getRank();
                } catch (Exception e) {
                    Popup.warningBox("网络连接错误，请重试！");
                }
            });
            Button exitButton = new ChoiceButton("退出");
            exitButton.setOnAction(actionEvent -> FXGL.getGameController().exit());

            this.getChildren().addAll(classicGameButton, infiniteGameButton, setButton, rankButton, exitButton);
            this.setPrefSize(boxWidth * 0.2, boxHeight);
            // this.setStyle("-fx-background-color:rgba(153,204,255,0.6);");
            // this.setStyle("-fx-background-color:#99CCFFFF;");
            this.setStyle("-fx-background-color:#41a4ed;");

            this.setAlignment(Pos.CENTER);
        }
    }


    class DetailPane extends Pane {
        public DetailPane() {
            this.setPrefSize(boxWidth * 0.8, boxHeight);
            this.setStyle("-fx-background-color:rgba(153,204,255,0.6);");
            this.setLayoutX(Config.WINDOW_WIDTH * 0.2);
            setOriginPane();
            setClassGamePane();
            setRankPane();

            this.getChildren().add(rankPane);
            this.getChildren().add(classGamePane);
            this.getChildren().add(originPane);

        }
    }

    class TopPane extends GridPane {
        public TopPane(MainWin mainWin) {
            Button returnButton = new Button();
            this.setPrefSize(boxWidth, boxHeight * 0.06);
            returnButton.setPrefSize(boxWidth*0.1, boxHeight * 0.06);
            Image image = new Image("assets/textures/menuImg/returnLogo.png",
                    boxHeight * 0.05, boxHeight * 0.05, false, false);
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            returnButton.setBackground(new Background(backgroundimage));
            returnButton.setOnAction(actionEvent -> {mainWin.toBack();});

            DropShadow dropShadow2 = new DropShadow();
            dropShadow2.setOffsetX(0.0);
            dropShadow2.setOffsetY(0.70);
            GridPane.setHalignment(returnButton, HPos.CENTER);
            this.setAlignment(Pos.CENTER_LEFT);
            this.add(returnButton,0,0);
            this.setStyle("-fx-background-color:rgb(3,123,253);");
            this.setEffect(dropShadow2);
            this.toFront();
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
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setStyle("-fx-background-color: rgb(146,184,248);" +
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-border-style: dashed;" +
                "-fx-border-width: 2px");
        gridPane.setPrefSize(600,400);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(classicGameImgView, 0, 0);
        gridPane.add(classGameButton, 0, 1);
        classGamePane.add(gridPane, 0, 0);

    }

    private void setRankPane() {
        Image rankLogo = new Image("assets/textures/menuImg/rankLogo.png",
                125, 50, false, false);
        ImageView rankLogoView = new ImageView(rankLogo);
        GridPane.setHalignment(rankLogoView, HPos.CENTER);
        rankPane.add(rankLogoView, 0, 0);
        String emptyString = "                            ";
        Text rankText = new Text("  排名");
        Font font = Font.font("Times Roman", FontWeight.BOLD, 17);

        rankText.setFont(font);
        Text emptyText1 = new Text(emptyString);
        emptyText1.setFont(font);
        Text nameText = new Text("用户名");
        nameText.setFont(font);
        Text emptyText2 = new Text(emptyString);
        emptyText2.setFont(font);
        Text scoreText = new Text("得分");
        scoreText.setFont(font);
        HBox hBox = new HBox(rankText, emptyText1, nameText, emptyText2, scoreText);
        hBox.setStyle("-fx-background-color:  #719cde;");
        GridPane.setHalignment(hBox, HPos.CENTER);

        rankPane.add(hBox, 0, 1);


        ScrollPane scrollPane = new ScrollPane();


        rankBox.setPrefSize(Config.WINDOW_WIDTH * 0.5, Config.WINDOW_HEIGHT * 5);
        rankBox.setStyle("-fx-background-color: #f5f5f5;");
        scrollPane.setContent(rankBox);
        scrollPane.setPrefSize(Config.WINDOW_WIDTH * 0.5, Config.WINDOW_HEIGHT * 0.7);

        scrollPane.setBackground(new Background(new BackgroundFill(Color.web("#99CCFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setStyle("-fx-border-color: #2b2b2b;" +
                "-fx-border-style: dotted;" +
                "-fx-border-width: 2px;");

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rankPane.add(scrollPane, 0, 2);
    }

    private void getRank() throws Exception {
        List<JSONObject> rankList = Client.Ranking();

        for (JSONObject jsonObject : rankList) {
            String ranking = jsonObject.get("Ranking").toString();
            Text rankingText = new Text(ranking);
            rankingText.setFont(Font.font("Times Roman", FontWeight.LIGHT, 20));

            String name = jsonObject.get("Name").toString();
            Text nameText = new Text(name);
            nameText.setFont(Font.font("Times Roman", FontWeight.LIGHT, 20));
            String score = jsonObject.get("Number").toString();
            Text scoreText = new Text(score);
            scoreText.setFont(Font.font("Times Roman", FontWeight.LIGHT, 20));
            GridPane gridPane = new GridPane();
            String gap1 = "-------------------------------";
            String gap2 = "---------------------------------------";
            String gap3 = "-----------";
            String empty = "     ";
            Text emptyText1 = new Text(empty);
            Text emptyText2 = new Text(empty);
            Text gapText1 = new Text(gap1);
            Text gapText2 = new Text(gap2);
            Text gapText3 = new Text(gap3);
            gridPane.add(emptyText1, 0, 0);
            gridPane.add(rankingText, 1, 0);
            gridPane.add(nameText, 2, 0);
            gridPane.add(scoreText, 3, 0);
            gridPane.add(emptyText2, 0, 1);
            gridPane.add(gapText1, 1, 1);
            gridPane.add(gapText2, 2, 1);
            gridPane.add(gapText3, 3, 1);

            gridPane.setStyle("-fx-background-color: transparent;");
            rankBox.getChildren().add(gridPane);
        }

    }

    public MainWin() {
        Pane pane = new Pane(new ChoicePane(), new DetailPane(), new TopPane(this));
        this.getChildren().add(pane);
        this.setPrefSize(boxWidth, boxHeight);
        this.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
