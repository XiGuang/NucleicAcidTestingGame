package NucleicAcidTesting.game.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControlButtonComponent extends Component {
    @Override
    public void onAdded() {

        //右下角退出按钮
        Image exitButton_up = new Image("assets/textures/CountDownPic/exit_down(已去底).png");
        Image exitButton_down = new Image("assets/textures/CountDownPic/exit_up(已去底).png");
        ImageView exitButton = new ImageView(exitButton_up);
        exitButton.setFitWidth(50);
        exitButton.setFitHeight(50);

        exitButton.setOnMouseClicked(event -> {

            FXGL.getGameController().gotoMainMenu();
            //this.setVisible(false);
        });
        exitButton.setOnMouseEntered(event -> {
            exitButton.setFitWidth(55);
            exitButton.setFitHeight(55);
            exitButton.setImage(exitButton_down);
        });
        exitButton.setOnMouseExited(event -> {
            exitButton.setFitWidth(50);
            exitButton.setFitHeight(50);
            exitButton.setImage(exitButton_up);
        });

        FXGL.addUINode(exitButton, 900, 450);


        /*
        //右下角暂停/继续按钮
        Image pauseButton_up = new Image("assets/textures/CountDownPic/pause1(已去底).png");
        Image pauseButton_down = new Image("assets/textures/CountDownPic/pause1_down(已去底).png");
        ImageView pauseButton = new ImageView(pauseButton_up);
        pauseButton.setFitWidth(50);
        pauseButton.setFitHeight(50);

        Image continueButton_up = new Image("assets/textures/CountDownPic/continue1(已去底).png");
        Image continueButton_down = new Image("assets/textures/CountDownPic/continue1_down(已去底).png");
        ImageView continueButton = new ImageView(continueButton_up);
        continueButton.setFitWidth(50);
        continueButton.setFitHeight(50);
        pauseButton.setVisible(true);
        continueButton.setVisible(false);

        pauseButton.setOnMouseEntered(event -> {
            pauseButton.setFitWidth(55);
            pauseButton.setFitHeight(55);
            pauseButton.setImage(pauseButton_down);
        });
        pauseButton.setOnMouseExited(event -> {
            pauseButton.setFitWidth(50);
            pauseButton.setFitHeight(50);
            pauseButton.setImage(pauseButton_up);
        });

        continueButton.setOnMouseEntered(event -> {
            continueButton.setFitWidth(55);
            continueButton.setFitHeight(55);
            continueButton.setImage(continueButton_down);
        });
        continueButton.setOnMouseExited(event -> {
            continueButton.setFitWidth(50);
            continueButton.setFitHeight(50);
            continueButton.setImage(continueButton_up);
        });

        pauseButton.setOnMouseClicked(event -> {
            //暂停游戏
            FXGL.getGameController().pauseEngine();
            //改变图标
            pauseButton.setVisible(false);
            continueButton.setVisible(true);
            //this.setVisible(false);
        });
        continueButton.setOnMouseClicked(event -> {
            FXGL.getGameController().resumeEngine();
            //改变图标
            pauseButton.setVisible(true);
            continueButton.setVisible(false);
            //this.setVisible(false);
        });
        FXGL.addUINode(pauseButton, 780, 450);
        FXGL.addUINode(continueButton, 780, 450);
        */
        //重置按钮
        Image resetButton_up = new Image("assets/textures/CountDownPic/resetButton_up(已去底).png");
        Image resetButton_down = new Image("assets/textures/CountDownPic/resetButton_down(已去底).png");
        ImageView ResetButton = new ImageView(resetButton_up);
        ResetButton.setFitWidth(50);
        ResetButton.setFitHeight(50);


        ResetButton.setOnMouseClicked(event -> {
            FXGL.getGameController().resumeEngine();
            FXGL.getGameController().startNewGame();
            //this.setVisible(false);
        });
        ResetButton.setOnMouseEntered(event -> {
            ResetButton.setImage(resetButton_down);
            ResetButton.setFitWidth(55);
            ResetButton.setFitHeight(55);
        });
        ResetButton.setOnMouseExited(event -> {
            ResetButton.setImage(resetButton_up);
            ResetButton.setFitWidth(50);
            ResetButton.setFitHeight(50);
        });
        FXGL.addUINode(ResetButton,840,450);
    }
}
