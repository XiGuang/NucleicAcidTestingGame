package NucleicAcidTesting.game;

import NucleicAcidTesting.game.ui.MainMenu;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import org.jetbrains.annotations.NotNull;

public class NATSceneFactory extends SceneFactory {

    @NotNull
    @Override
    public FXGLMenu newGameMenu() {
        return super.newGameMenu();
    }

    @NotNull
    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenu();
    }
}
