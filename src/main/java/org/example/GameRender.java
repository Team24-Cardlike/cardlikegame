package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
// import org.lwjgl.opengl.GL20;

public class GameRender extends ApplicationAdapter {
    Texture background;
    Texture card;
    SpriteBatch spriteBatch;
    Sprite cardSprite;
    FitViewport viewport;

    private Array<Sprite> cards;
    private Array<Boolean> selected;
    private Array<Boolean> hovered;

    @Override
    public void create() {
        background =  new Texture(Gdx.files.internal("assets/images/bräde.png"));
        card = new Texture("assets/images/soler3.png");
        cardSprite = new Sprite(card);
        cardSprite.setSize(1, 2);

        selected = new Array<>();
        cards = new Array<>();
        hovered = new Array<>();

        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(card);
            s.setSize(1, 1.5f);
            s.setPosition(1 + i * 1.2f, 1);
            cards.add(s);
            selected.add(false); // alla börjar omarkerade
            hovered.add(false);
        }

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera

        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        // Draw your application here.
        input();
        logic();
        draw();
    }

    private void input() {
        //float speed = 4f;
        //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta
        Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(cords);

        for (int a = 0; a < cards.size; a++){
            Sprite card = cards.get(a);
            hovered.set(a, card.getBoundingRectangle().contains(cords.x, cords.y));
        }

        if (Gdx.input.justTouched()) {
            for (int i = 0; i < cards.size; i++){
                Sprite card = cards.get(i);
                if(card.getBoundingRectangle().contains(cords.x, cords.y)){
                    selected.set(i, !selected.get(i));
                }
            }
        }
    }

    private void logic() {

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(background, 0, 0, worldWidth, worldHeight); // draw the background

        for (int i = 0; i < cards.size; i++) {
            Sprite card = cards.get(i);

            if (selected.get(i))
                card.setColor(Color.GOLD);
            else if (hovered.get(i)) {
                // Ljusgrå highlight = hover
                card.setColor(Color.LIGHT_GRAY);
            }
            else
                card.setColor(Color.WHITE);
            card.draw(spriteBatch);
        }
/*
        card1.setPosition(viewport.getWorldWidth()/5, viewport.getWorldHeight()/4);
        card2.setPosition(viewport.getWorldWidth()/3, viewport.getWorldHeight()/4);
        card3.setPosition(viewport.getWorldWidth()/2.5f, viewport.getWorldHeight()/4);

        card1.draw(spriteBatch);
        card2.draw(spriteBatch);
        card3.draw(spriteBatch);
*/
        spriteBatch.end();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
    }



}
