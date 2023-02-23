package NucleicAcidTesting.game;

import NucleicAcidTesting.game.ui.MainMenu.MainMenu;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import org.jetbrains.annotations.NotNull;

public class NATSceneFactory extends SceneFactory {


    @NotNull
    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenu();
    }
}
