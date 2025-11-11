package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL20;

public class GameRender extends ApplicationAdapter {
    Texture background;

    SpriteBatch batch;

    @Override
    public void create() {
        background =  new Texture(Gdx.files.internal("assets/images/background.png"));

        batch = new SpriteBatch();
    }


    //Drawing the game assets 60 times per second
    @Override
    public void render() {
        Gdx.gl.glClearColor(0 , 0 ,0 ,0); // preparing black background before clear
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears screen before draw-process


        batch.begin();
        //Starting draw-cycle

        float wordlWidth = Gdx.graphics.getWidth();
        float worldHight = Gdx.graphics.getHeight();

        batch.draw(background,0,0,wordlWidth , worldHight);

        //Ending draw-cycle
        batch.end();
    }



}
