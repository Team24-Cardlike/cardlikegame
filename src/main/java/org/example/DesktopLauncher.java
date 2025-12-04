package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import org.example.Controller.Controller;
import org.example.Model.Round;
import org.example.Model.Opponent;
import org.example.Views.RoundView;
import org.example.Views.MainMenuView;

//Du är mer av ett problem nu än problemet själv -Kristoffer under roleplaying workshop till Axel :)
//Holy words

public class DesktopLauncher{
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(("Maven LibGDX test"));
        config.setWindowedMode(800,600);

        Opponent opp = new Opponent(2000, 25, 3, "enemyEvil");
        Game game = new Game(opp);
        View view = new View();
        Controller controller = new Controller(game);
        GameRender gameRender = new GameRender(game, view, controller);
                
        view.setGame(game);
        view.setController(controller);
        
        game.observers.addObserver(view);
        game.observers.notifyGameInit();
        new Lwjgl3Application(gameRender, config);
    }

}
