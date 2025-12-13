package org.example.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Model.ShopController;
import org.example.Model.Upgrades.DamageUpgrade;
import org.example.Model.Upgrades.Upgrade;
import org.example.Model.Upgrades.UpgradeLibrary;

import java.util.ArrayList;
import java.util.List;

public class ShopView implements Screen {
    private List<Upgrade> items;
    private UpgradeLibrary upgradeLibrary;
    private Stage stage;
    private Table table;
    private SpriteBatch batch;
    private ShopController shopController;
    private Image closeShop;

    public ShopView() {
        //this.shopController = shopController;
        items = new ArrayList<>();
    }

    public void setShopController(ShopController ctrl){
        this.shopController = ctrl;
        this.upgradeLibrary = shopController.getUpgradeLibrary();
    }

    public void update(){
        updateList();
    }

    private void updateList(){
        for(int i = items.size(); i < 10; i++){
            Upgrade random = upgradeLibrary.getRandomUpgrade();
            if(shopController.getUserUpgrades() != null){
                if((items.isEmpty() || !items.contains(random)) && !shopController.getUserUpgrades().contains(random.getName())){
                    items.add(random);
                }
            }
            else if(items.isEmpty() || !items.contains(random)){
                items.add(random);
            }
        }
    }

    /**
     * Initialize shop view
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);


        update();
        Texture bg = new Texture("assets/images/background.png");
        Image background = new Image(bg);
        background.setFillParent(true);
        stage.addActor(background);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        shopGrid();

        closeShop = new Image(new Texture("assets/images/endTurn.png"));
        closeShop.setPosition(0, 200);
        closeShop.setSize(50, 50);
        stage.addActor(closeShop);

        closeShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopController.closeShop();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private void shopGrid(){
        int columns = 5;
        int count = 0;

        for(Upgrade item: items){
            Texture tex = new Texture(item.getPic());

            TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(tex));
            ImageButton itemButton = new ImageButton(drawable);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
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
        Label title = new Label(item.getName(), ls);
        title.setPosition(20, background.getHeight() - 40);
        popup.addActor(title);

        Label desc = new Label(item.getDesc(), ls);
        desc.setPosition(20, background.getHeight() -80);
        popup.addActor(desc);

        Image buyButton = new Image(new Texture(item.getPic()));
        buyButton.setSize(120, 60);
        buyButton.setPosition(20, 20);
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Köpte: " + item.getName());
                shopController.upgradeBaught(item);
                popup.remove();
                items.remove(item);
                updateList();
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

        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h){
        if(w <= 0 || h <= 0) return;
        stage.getViewport().update(w,h,true);

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

    @Override public void dispose(){ stage.dispose(); }

}
