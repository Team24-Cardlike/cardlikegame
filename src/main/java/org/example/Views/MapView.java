package org.example.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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
import org.example.Model.MapObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapView implements Screen, MapObserver {
    private FitViewport viewport;
    private SpriteBatch batch;
    private Stage stage;
    private MapController controller;

    ArrayList<String> lvls = new ArrayList<>();
    private ArrayList<Sprite> mapSprites = new ArrayList<>();
    private ArrayList<Sprite> lvlSprites = new ArrayList<>();

    private Texture mapBackground;
    private Texture lvlRune;
    private Texture lvlRuneBoss;
    private Texture redX;
    private Texture lokiHead;
    private Image saveButton;

    private Set<String> completedLvls;
    private Set<String> availableLvls;

    private String lastLVL;
    Vector3 coords = new Vector3();

    // Positions for lvls on map
    private final float[][] levelPositions = {
            //[Heimdall, Wolf, Balder, Imp, Imp2, Gnome, Freja, Tyr, Tor, Oden]
            {130, 68}, // level 0 Heimdall
            {272, 185}, // level 1 Wolf
            {550, 215}, // level 2 Balder
            {300, 400}, // level 3 Imp
            {785, 215}, // level 4 Imp2
            {610,370},  // level 5 Gnome
            {1000, 300} , // level 5 Freja
            {940, 410} , // level 5 Tyr
            {760, 450} , // level 5 Tor
            {860, 540}  // level 5  Oden

    };

    public void setController(MapController c) {
        this.controller = c;
    }

    @Override
    public void show() {
        viewport = new FitViewport(1280,720);
        batch = new SpriteBatch();
        stage = new Stage(viewport,batch);

        Gdx.input.setInputProcessor(stage);
        mapBackground = new Texture("assets/images/map.png");

        lvlRune   = new Texture("assets/images/LVLRune.png");
        lvlRuneBoss = new Texture("assets/images/BossRune.png");
        redX     = new Texture("assets/images/redX.png");
        lokiHead = new Texture("assets/images/lokiMapIcon.png");
        
        saveButton = new Image(new Texture("assets/images/buttons/saveGameButton.png"));
        saveButton.setPosition(viewport.getWorldWidth()-105, viewport.getWorldHeight()-60);
        saveButton.setSize(100, 50);

        stage.addActor(saveButton);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {                
                controller.save();
            }
        });
        lokiHead = new Texture("assets/images/lokiMapIcon.png");
    }

    public void draw(){
        batch.begin();
        batch.draw(mapBackground,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());

        drawOpponent();
        batch.end();
        stage.draw();
    }

    private void drawOpponent() {
        mapSprites.clear();
        List<String> regularOps = List.of("Imp", "Imp2", "Wolf", "Gnome");

        for (int i = 0; i < lvls.size(); i ++) {

            String name = lvls.get(i);
            float x = levelPositions[i][0];
            float y = levelPositions[i][1];
            //if name is regular op, draw regular rune
            boolean regular = regularOps.contains(name);
            if (completedLvls.contains(name)) {
                drawComplete(x, y, name,regular);
            }
            else if (availableLvls.contains(name)) {
                drawCurrent(x,y,regular);
            }

            else  {
                drawRest(x,y,regular);
            }
            for (Sprite s : lvlSprites) {
                s.draw(batch);
            }
            for (Sprite s : mapSprites) {
                s.draw(batch);
            }
        }
    }

    public void drawComplete(float x, float y,String name, boolean regular) {
        Sprite base;
        if(regular) {base = makeSprite(lvlRune,x,y);}
        else { base =  makeSprite(lvlRuneBoss,x,y);}
        lvlSprites.add(base);

        Sprite cross = makeSprite(redX, x, y);
        mapSprites.add(cross);
        if (lastLVL == name) {
            Sprite player =  makeSprite(lokiHead, x, y);
            mapSprites.add(player);
        }
    }

    public void drawCurrent(float x, float y, boolean regular) {
        Sprite base;
        if(regular) {base = makeSprite(lvlRune,x,y);}
        else { base =  makeSprite(lvlRuneBoss,x,y);}
        lvlSprites.add(base);
    }

    public void drawRest(float x, float y, boolean regular) {
        Sprite base;
        if(regular) {base = makeSprite(lvlRune,x,y);}
        else { base =  makeSprite(lvlRuneBoss,x,y);}
        lvlSprites.add(base);
    }

    public Sprite makeSprite(Texture tex, float centerX,float centerY) {
        Sprite s = new Sprite(tex);
        s.setSize(80f,80f);
        s.setOriginCenter();
        s.setPosition(centerX-s.getWidth() / 2f, centerY - s.getHeight()/2f);
        return s;
    }

    private void input() {
        //Get mouse coordinates
        coords.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(coords);

        if (Gdx.input.justTouched()) {
            Polygon backgroundPoly = new Polygon(new float[]{
                    400, 300,
                    575, 300,
                    575, 400,
                    400, 400
            });

            for (int a =  0; a < lvls.size(); a++) {
                Polygon poly = generateHitbox(a, lvlSprites);
                String name = lvls.get(a);                
                // Send input to roundController
                if (poly.contains(coords.x, coords.y)) {                                                        
                    if (availableLvls.contains(name)) {
                        lastLVL = name;
                    }
                    controller.selectLvl(name);
                    break;
                }
            }
            if(backgroundPoly.contains(coords.x, coords.y)){
                controller.openShop();
            }
    }  }

    private Polygon generateHitbox(int index,ArrayList<Sprite> ops) {
        // AI-generated solution for getting the correct hitboxes
        Sprite sprite = ops.get(index);
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
        stage.act(v);
        Gdx.input.setInputProcessor(stage);
        controller.updateManager();

        draw();
        input();
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        if (batch != null) batch.dispose();
        if (stage != null) stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {}

    @Override
    public void onMapChanged(ArrayList<String> lvls, Set<String> completedLvls, Set<String> availableLvls) {
        this.lvls = lvls;
        this.completedLvls = completedLvls;
        this.availableLvls = availableLvls;
    }
}
