package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.example.Model.Game;
import org.example.View.View;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(1920,1080);

        Game game = new Game();
        View view = new View();

        game.observers.addObserver(view);
        game.observers.notifyGameInit();
        new Lwjgl3Application(view, config);



    }
}
