package org.example.View;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Model.Board;
import org.example.Model.Card;
import org.example.Model.Game;
import org.example.Model.User;

import java.util.ArrayList;

public class View extends ApplicationAdapter {
    FitViewport viewport;
    SpriteBatch spriteBatch;
    Game game;
    private ArrayList<Sprite> cardSprites;


    private void input() {
        //float speed = 4f;
        //float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        Vector3 cords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.getCamera().unproject(cords);
        cardSprites = new ArrayList<>();
        ArrayList<Card> cards = game.getUser().getHand();

        for (int a = 0; a < cards.size(); a++){
            Sprite card = cardSprites.get(a);
            //Sprite cardSprite = new Sprite(cards.get(a).getCardTexture());
            game.getUser().setCardAsHovered(a, card.getBoundingRectangle().contains(cords.x, cords.y));
        }

        if (Gdx.input.justTouched()) {

            for (int i = 0; i < game.getUser().getHand().size(); i++){
                Sprite card = cardSprites.get(i);
                if(card.getBoundingRectangle().contains(cords.x, cords.y)){
                    boolean bool = game.getUser().getBoolSelectedCards().get(i);
                    game.getUser().setCardAsSelectedBool(i, !bool);
                    //selected.set(i, !selected.get(i));
                }
            }
        }
    }

    private void draw(Board board, User user, float worldWidth, float worldHeight, Stage stage) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // store the worldWidth and worldHeight as local variables for brevity
        //float worldWidth = viewport.getWorldWidth();
        //float worldHeight = viewport.getWorldHeight();
        Texture background = board.getBoard();
        spriteBatch.draw(background, 0, 0, worldWidth, worldHeight); // draw the background
        //ArrayList<Sprite> cards = user.getHand();

        for (int i = 0; i < cardSprites.size(); i++) {
            Sprite card = cardSprites.get(i);

            if (user.getBoolSelectedCards().get(i))
                card.setColor(Color.GOLD);
            else if (user.getHoveredCards().get(i)) {
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
