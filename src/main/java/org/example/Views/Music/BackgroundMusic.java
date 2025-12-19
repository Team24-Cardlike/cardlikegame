package org.example.Views.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BackgroundMusic {
    private Music music;
    public BackgroundMusic(String musicPath) {        
        music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        music.setVolume(0.5f);
        music.play();
        music.setLooping(true);
    }
}
