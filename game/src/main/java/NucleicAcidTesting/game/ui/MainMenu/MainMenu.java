package NucleicAcidTesting.game.ui.MainMenu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.layout.*;

public class MainMenu extends FXGLMenu {

    private Pane loginBox;
    private Pane beginGameBox;

    public MainMenu() {
        super(MenuType.MAIN_MENU);
        beginGameWin();
    }

    private void beginGameWin() {

        StackPane stackPane = new StackPane(new MainWin(), new LoginWin());
        getContentRoot().getChildren().setAll(stackPane);
    }


}
