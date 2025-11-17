package org.example.Controller;

import org.example.Model.Game;
import org.example.View.View;

public class Controller{
    private View view;
    private Game game;

    public Controller(View _view, Game _game){
        this.view = _view;
        this.game = _game;
    }
}
