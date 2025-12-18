package org.example.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.example.Controller.ShopController;
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

    private BitmapFont font;

    public ShopView() {
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

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(1280,720), batch);

        update();

        Texture bg = new Texture("assets/images/background.png");
        Image background = new Image(bg);
        background.setFillParent(true);
        stage.addActor(background);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Impact.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        generator.dispose();

        table = new Table();
        table.setFillParent(true);
        table.top().padTop(50);
        stage.addActor(table);

        shopGrid();

        closeShop = new Image(new Texture("assets/images/xButton.png"));
        closeShop.setSize(150, 100);
        closeShop.setPosition(stage.getWidth() - 60, stage.getHeight() - 60); // Top-right
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
        table.clear();
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

        float popupWidth = 400;
        float popupHeight = 300;
        background.setSize(popupWidth, popupHeight);
        popup.setPosition((stage.getWidth() - popupWidth)/2f, (stage.getHeight() - popupHeight)/2f);
        /*
        float pw = background.getWidth();
        float ph = background.getHeight();
        popup.setPosition(
                (stage.getWidth() - pw) / 2f,
                (stage.getHeight() - ph) / 2f
        );*/

        Label.LabelStyle ls = new Label.LabelStyle(font, Color.WHITE);
        Label title = new Label(item.getName(), ls);
        title.setPosition(20, popupHeight - 40);
        popup.addActor(title);


        Label desc = new Label(item.getDesc(), ls);
        desc.setPosition(20, popupHeight - 80);
        desc.setWidth(popupWidth - 40);
        desc.setWrap(true);
        popup.addActor(desc);

        Image buyButton = new Image(new Texture(item.getPic()));
        buyButton.setSize(120, 60);
        buyButton.setPosition(popupWidth/2f - 60, 20);
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Köpte: " + item.getName());
                shopController.upgradeBought(item);
                popup.remove();
                items.remove(item);
                updateList();
                shopGrid();
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
