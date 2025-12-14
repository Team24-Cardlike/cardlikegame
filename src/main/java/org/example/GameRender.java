package org.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import org.example.Controller.MenuController;
import org.example.Model.GameState.*;
import org.example.Model.Round;
import org.example.Model.Save;
import org.example.Model.StateObserver;
import org.example.Views.MapView;
import org.example.Views.RoundView;
import org.example.Controller.RoundController;
import org.example.Model.GameManager;
import org.example.Views.MainMenuView;
import org.example.Views.ShopView;

import java.util.HashMap;
import java.util.Map;

public class GameRender extends Game implements StateObserver {

    private final RoundView roundView;
    private final MainMenuView menuView;
    private final MapView mapView;
    private final ShopView shopView;
    private Screen current;



    GameRender(RoundView roundView, MainMenuView menuView, MapView mapView, ShopView shopwView) {
        //this.round = round;
        this.mapView = mapView;
        this.roundView = roundView;
        this.menuView = menuView;
        this.shopView = shopwView;

        //this.manager = manager;
        //this.menuController = menuController;
        //this.rview = rview;
        //this.mview = mview;
        //viewState = false;
        //this.roundController = roundController;
        //screens = new HashMap<>();
    }

    /*@Override
    public void render() {
        super.render();   // This tells the active Screen to render (View)
        //manager.gameLoop(); // This updates the game logic
    }*/


    public void switchView(String state) {
        switch (state) {
            case "round":
                setScreen(roundView);

                break;
            case "map":
                setScreen(mapView);
                break;
            case "shop":
                setScreen(shopView);
                break;
            case "menu":
                setScreen(menuView);
                break;
        }
    }

    @Override
    public void updateState(String state) {
        switchView(state);
    }


        // graphics

        //roundView.draw();         // graphics


        // roundView.playSelectedCards(); // Move cards up
    //}

    @Override
    public void create() {
       /* MenuState x;
        screens.put("menu", x = new MenuState(manager, menuController));
        screens.put("shop", new ShopState(manager));
        screens.put("round", new RoundState(manager, roundController));
        screens.put("map", new MapState());
        setScreen(screens.get("menu").getView());
        //mview.show();
        //rview.create();
        //roundView.create();
        manager.setState(x);
        manager.initRound();

        updateState(manager.getState());*/
        setScreen(menuView);
    }
}


// package org.example;

// import com.badlogic.gdx.ApplicationAdapter;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.*;
// import com.badlogic.gdx.math.Vector3;
// import com.badlogic.gdx.scenes.scene2d.InputEvent;
// import com.badlogic.gdx.scenes.scene2d.Stage;
// import com.badlogic.gdx.scenes.scene2d.ui.Image;
// import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
// import com.badlogic.gdx.utils.Array;
// import com.badlogic.gdx.utils.ScreenUtils;
// import com.badlogic.gdx.utils.viewport.FitViewport;
// import org.example.Model.Game;
// import org.example.Model.GameObserver;

// public class GameRender extends ApplicationAdapter {
//     Texture background;
//     Texture card;
//     Image startButton;

//     SpriteBatch spriteBatch;
//     Sprite cardSprite;
//     FitViewport viewport;

//     private Stage stage;

//     private Array<Sprite> cards;
//     private Array<Boolean> selected;
//     private Array<Boolean> hovered;

//     @Override
//     public void create() {

//         background =  new Texture(Gdx.files.internal("assets/images/bräde.png"));
//         card = new Texture("assets/images/3sun.png");

//         viewport = new FitViewport(8, 5);
//         spriteBatch = new SpriteBatch();
//         stage = new Stage(viewport, spriteBatch);

//         startButton = new Image(new Texture("assets/images/start (1).png"));
//         startButton.setPosition(0,0);
//         startButton.setSize(2, 1);
//         stage.addActor(startButton);
//         Gdx.input.setInputProcessor(stage);

//         cardSprite = new Sprite(card);
//         cardSprite.setSize(1, 2);

//         selected = new Array<>();
//         cards = new Array<>();
//         hovered = new Array<>();

//         for (int i = 0; i < 5; i++) {
//             Sprite s = new Sprite(card);
//             s.setSize(1, 1.5f);
//             s.setPosition(1 + i * 1.2f, 1);
//             cards.add(s);
//             selected.add(false); // alla börjar omarkerade
//             hovered.add(false);
//         }

//         startButton.addListener(new ClickListener() {
//             @Override
//             public void clicked(InputEvent event, float x, float y) {
//                 playSelectedCards();
//             }
//         });
//     }


//     @Override
//     public void resize(int width, int height) {
//         viewport.update(width, height, true); // true centers the camera

//         // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
//         // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
//         if(width <= 0 || height <= 0) return;

//         // Resize your application here. The parameters represent the new window size.
//     }

//     @Override
//     public void render() {
//         // Draw your application here.
//         input();
//         logic();
//         draw();
//     }

//     private void input() {
//         //float speed = 4f;
//         //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta
//         Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//         viewport.getCamera().unproject(cords);

//         for (int a = 0; a < cards.size; a++){
//             Sprite card = cards.get(a);
//             hovered.set(a, card.getBoundingRectangle().contains(cords.x, cords.y));
//         }

//         if (Gdx.input.justTouched()) {

//             for (int i = 0; i < cards.size; i++){
//                 Sprite card = cards.get(i);
//                 if(card.getBoundingRectangle().contains(cords.x, cords.y)){
//                     selected.set(i, !selected.get(i));
//                 }
//             }
//         }
//     }

//     private void playSelectedCards(){
//         float lift = viewport.getWorldHeight() * 0.1f; // t.ex. 10% uppåt
//         for (int i = 0; i < cards.size; i++) {
//             if (selected.get(i)) {
//                 Sprite card = cards.get(i);
//                 card.setY(card.getY() + lift);
//                 selected.set(i, false);
//             }
//         }
//     }

//     private void logic() {

//     }

//     private void draw() {
//         ScreenUtils.clear(Color.BLACK);
//         viewport.apply();
//         spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
//         spriteBatch.begin();


//         // store the worldWidth and worldHeight as local variables for brevity
//         float worldWidth = viewport.getWorldWidth();
//         float worldHeight = viewport.getWorldHeight();
//         spriteBatch.draw(background, 0, 0, worldWidth, worldHeight); // draw the background

//         for (int i = 0; i < cards.size; i++) {
//             Sprite card = cards.get(i);

//             if (selected.get(i))
//                 card.setColor(Color.GOLD);
//             else if (hovered.get(i)) {
//                 // Ljusgrå highlight = hover
//                 card.setColor(Color.LIGHT_GRAY);
//             }
//             else
//                 card.setColor(Color.WHITE);
//             card.draw(spriteBatch);
//         }
//         spriteBatch.end();

//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.draw();
//     }

//     @Override
//     public void pause() {
//         // Invoked when your application is paused.
//     }

//     @Override
//     public void resume() {
//         // Invoked when your application is resumed after pause.
//     }

//     @Override
//     public void dispose() {
//     }


// }
