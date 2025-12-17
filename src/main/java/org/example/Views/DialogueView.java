package org.example.Views;

import com.badlogic.gdx.Screen;

public class DialogueView implements Screen {
    float timer = 0;
    int visibleChars = 0;

    public void update(float delta) {
        timer += delta;
        if (timer > 0.05f) { // speed
            visibleChars++;
            timer = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch,
                fullText.substring(0, Math.min(visibleChars, fullText.length())),
                x, y
        );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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
}
