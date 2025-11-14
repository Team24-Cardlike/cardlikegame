package src.main.java.org.example;

public class View {

    /*private void draw(Viewport viewport, SpriteBatch spriteBatch) {
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

        spriteBatch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
*/
}
