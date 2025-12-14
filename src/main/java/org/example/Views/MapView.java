package org.example.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Controller.MapController;
import org.example.Model.GameManager;
import org.example.Model.GameMap;
import org.example.Model.MapObserver;

import java.util.ArrayList;
import java.util.List;

public class MapView implements Screen, MapObserver {
    private FitViewport viewport;
    private SpriteBatch batch;
    private Stage stage;

    private GameManager maneger;
    private MapController controller;

    ArrayList<String> lvls = new ArrayList<>();

    private ArrayList<Sprite> opponentIcons = new ArrayList<>();
    private Texture mapBackground;

    private Texture greenDot;
    private Texture redDot;
    private Texture redX;
    private Texture lokiHead;
    private Image saveButton;

    private int currentLvl = 0;

    private String currentOp;

    // Positions for lvls on map
    private final float[][] levelPositions = {
            {90, 75}, // level 0
            {180, 160}, // level 1
            {350, 170}, // level 2
            {550, 170}, // level 3
            {600, 250}, // level 4
            {750, 250}  // level 5

    };

    Vector3 coords = new Vector3();

    public void setManeger(GameManager m) {
        this.maneger = m;

    }
    public void setController(MapController c) {
        this.controller = c;
    }

    @Override
    public void show() {
        

        viewport = new FitViewport(800,600);
        batch = new SpriteBatch();
        stage = new Stage(viewport,batch);

        Gdx.input.setInputProcessor(stage);
        mapBackground = new Texture("assets/images/map.png");
        greenDot = new Texture("assets/images/greenDot.png");

        redDot   = new Texture("assets/images/redDot.png");
        redX     = new Texture("assets/images/redX.png");
        lokiHead = new Texture("assets/images/lokiHead.png");
        
        saveButton = new Image(new Texture("assets/images/save.png"));
        saveButton.setPosition(viewport.getWorldWidth()-110, viewport.getWorldHeight()-90);
        saveButton.setSize(100, 100);
        stage.addActor(saveButton);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.save();
            
            }
        });
        
    }

    public void draw(){

        batch.begin();
        batch.draw(mapBackground,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        

        drawOpponent();
        drawSaveButton();

        batch.end();

        stage.draw();
    }

    private void drawSaveButton() {

    }


    private void drawOpponent() {
        opponentIcons.clear();

        for (int i = 0; i < lvls.size(); i ++) {

            float x = levelPositions[i][0];
            float y = levelPositions[i][1];

            if (i < currentLvl) {
                drawComplete(x, y);
            }

            else if (i == currentLvl) {
                drawCurrent(x,y);

            }

            else if (i > currentLvl) {
                drawRest(x,y);

            }
            for (Sprite s : opponentIcons) {
                s.draw(batch);
            }
        }


    }

    public void drawComplete(float x, float y) {
        Sprite base =  makeSprite(greenDot,x,y);
        opponentIcons.add(base);
        Sprite cross = makeSprite(redX, x, y);
        opponentIcons.add(cross);

    }

    public void drawCurrent(float x, float y) {
        Sprite base =  makeSprite(greenDot,x,y);

        opponentIcons.add(base);
        Sprite player =  makeSprite(lokiHead,x - 40,y- 20);
        opponentIcons.add(player);



    }
    public void drawRest(float x, float y) {
        Sprite base =  makeSprite(redDot,x,y);
        opponentIcons.add(base);
    }


    public Sprite makeSprite(Texture tex, float centerX,float centerY) {
        Sprite s = new Sprite(tex);
        s.setSize(25f,25f);
        s.setOriginCenter();
        s.setPosition(centerX-s.getWidth() / 2f, centerY - s.getHeight()/2f);
        return s;
    }



    private void input() {
        // Hämta muskoordinater
        coords.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(coords);

        if (Gdx.input.justTouched()) {

            for (int a = lvls.size() - 1; a >= 0; a--) {
                Polygon poly = generateHitbox(a, opponentIcons);
                String name = lvls.get(a);

                //Send input to roundController
                if (poly.contains(coords.x, coords.y)) {
                    controller.selectLvl(name);
                    break;
                }
            }
    }  }



        private Polygon generateHitbox(int index,ArrayList<Sprite> ops) {
            // AI-generated solution for getting the correct hitbox
            Sprite sprite = opponentIcons.get(index);
            float[] vertices = new float[]{
                    0, 0,
                    sprite.getWidth(), 0,
                    sprite.getWidth(), sprite.getHeight(),
                    0, sprite.getHeight()
            };
            Polygon poly = new Polygon(vertices);

            poly.setOrigin(sprite.getOriginX(), sprite.getOriginY());
            poly.setPosition(sprite.getX(), sprite.getY());
            poly.setRotation(sprite.getRotation());
            poly.setScale(sprite.getScaleX(), sprite.getScaleY());
            return poly;
        }





    @Override
    public void render(float v) {

        maneger.gameLoop();

        input();
        draw();


    }




    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onMapChanged(ArrayList<String> lvls, int currentLvl) {

        this.lvls = lvls;
        this.currentLvl = currentLvl;
    }
}
