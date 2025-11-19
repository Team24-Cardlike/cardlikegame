package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.Controller;
import org.example.Controller.Controller;
import org.example.Model.Game;
import org.example.View.View;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(800,600);

        Game game = new Game();
        View view = new View();
        Controller controller = new Controller(view, game);
        Controller controller = new Controller(game);
        View view = new View(controller);

        game.observers.addObserver(view);
        game.observers.notifyGameInit();
        new Lwjgl3Application(view, config);

    }
}
