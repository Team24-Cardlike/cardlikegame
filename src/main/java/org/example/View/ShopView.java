package org.example.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.Upgrades.DamageUpgrade;
import org.example.Model.Upgrades.Upgrade;

import java.util.List;

public class ShopView implements Screen {
    private List<DamageUpgrade> items;
    private Stage stage;
    private Table table;

    public ShopView(List<DamageUpgrade> items) {
        this.items = items;
    }

    @Override
    public void show() {
        SpriteBatch batch = new SpriteBatch();

        stage = new Stage(new ScreenViewport(), batch);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        shopGrid();
        Gdx.input.setInputProcessor(stage);
    }

    private void shopGrid(){
        int columns = 5;
        int count = 0;

        for(Upgrade item: items){
            Texture tex = new Texture("assets/images/3sun.png");

            TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
            ImageButton itemButton = new ImageButton(drawable);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Table coords: " + table.getX() + ", " + table.getY());
                    itemPopup(item);
                }
            });

            table.add(itemButton).size(100,100).pad(10);
            count++;
            if (count % columns == 0) {
                table.row();
            }
        }
    }

    private void itemPopup(Upgrade item){
        table.setTouchable(Touchable.disabled);

        Group popup = new Group();

        Texture backgroundTexture = new Texture("assets/images/background.png");
        Image background = new Image(backgroundTexture);
        popup.addActor(background);

        float pw = background.getWidth();
        float ph = background.getHeight();
        popup.setPosition(
                (stage.getWidth() - pw) / 2f,
                (stage.getHeight() - ph) / 2f
        );

        BitmapFont font = new BitmapFont();
        Label.LabelStyle ls = new Label.LabelStyle(font, Color.WHITE);
        Label title = new Label("Name placeholder", ls);
        title.setPosition(20, background.getHeight() - 40);
        popup.addActor(title);

        Label desc = new Label("Beskrivning", ls);
        desc.setPosition(20, background.getHeight() -40);
        popup.addActor(desc);

        Image buyButton = new Image(new Texture("assets/images/endTurn.png"));
        buyButton.setSize(120, 60);
        buyButton.setPosition(20, 20);
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Köpte: " + "namn");
                popup.remove();
                table.setTouchable(Touchable.enabled);
            }
        });
        popup.addActor(buyButton);

        stage.addActor(popup);
        popup.setZIndex(Integer.MAX_VALUE); // säkerställ att det ligger överst

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        if (Gdx.input.getInputProcessor() != stage) {
            System.out.println("WARNING: ShopView stage is NOT the current input processor!");
        }

        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h){ stage.getViewport().update(w,h,true); }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override public void dispose(){ stage.dispose(); }

}
