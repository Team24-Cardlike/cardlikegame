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
        //this.upgradeLibrary = shopController.getUpgradeLibrary();
    }

    public void update(){
        items = shopController.getUpdatedList();
    }

    /**
     * Initialize shop view
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(1280,720), batch);

        update();

        Texture bg = new Texture("assets/images/background.png");
        Image background = new Image(bg);
        background.setFillParent(true);
        stage.addActor(background);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Italic.ttf"));
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
        closeShop.setSize(100, 100);
        closeShop.setPosition(stage.getWidth() - 120, stage.getHeight() - 120); // Top-right
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

        Texture backgroundTexture = new Texture("assets/images/itemBG.png");
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
        desc.setPosition(20, popupHeight - 100);
        desc.setWidth(popupWidth - 40);
        desc.setWrap(true);
        popup.addActor(desc);

        Label cost = new Label("Cost: " + item.getCost() + "g", ls);
        cost.setPosition(popupWidth/2f - cost.getWidth()/2, 85);
        cost.setWrap(true);
        popup.addActor(cost);

        Label insufficientGold = new Label("Insufficient gold amount.", ls);
        insufficientGold.setPosition(popupWidth/2f - insufficientGold.getWidth()/2, 85);
        insufficientGold.setWrap(true);

        Image buyButton = new Image(new Texture("assets/images/buyButton.png"));
        buyButton.setSize(120, 60);
        buyButton.setPosition(popupWidth/2f - 60, 20);
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: MAKE SURE THAT U CANT BUY WITH INSUFFICIENT GOLD!
                if(shopController.upgradeBought(item)){
                    popup.remove();
                    //items.remove(item);
                    shopController.removeFromItems(item);
                    update();
                    shopGrid();
                    table.setTouchable(Touchable.enabled);
                }
                else{
                    cost.remove();
                    popup.addActor(insufficientGold);
                }
            }
        });
        popup.addActor(buyButton);

        Image backButton = new Image(new Texture("assets/images/redX.png"));
        backButton.setSize(30, 30);
        backButton.setPosition(popupWidth-31, popupHeight-31);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popup.remove();
                shopGrid();
                table.setTouchable(Touchable.enabled);
            }
        });
        popup.addActor(backButton);

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
