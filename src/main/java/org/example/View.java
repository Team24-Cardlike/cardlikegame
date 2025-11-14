package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Model.Board;
import org.example.Model.Card;
import org.example.Model.User;

import java.util.ArrayList;

public class View extends ApplicationAdapter {
    FitViewport viewport;
    SpriteBatch spriteBatch;

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

    private void draw(Board board, User user, float worldWidth, float worldHeight) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // store the worldWidth and worldHeight as local variables for brevity
        //float worldWidth = viewport.getWorldWidth();
        //float worldHeight = viewport.getWorldHeight();
        Texture background = board.getBoard();
        spriteBatch.draw(background, 0, 0, worldWidth, worldHeight); // draw the background
        ArrayList<Sprite> cards = user.getHand();

        for (int i = 0; i < cards.size(); i++) {
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
        spriteBatch.end();

       stage.act(Gdx.graphics.getDeltaTime());
       stage.draw();
    }
}
