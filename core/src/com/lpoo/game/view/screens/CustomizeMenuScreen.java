package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lpoo.game.Spheral;
import com.lpoo.game.view.entities.BallView;
import com.lpoo.game.view.entities.ViewFactory;

import java.util.ArrayList;

/**
 * A view representing the Customize Menu screen. In this Menu the User
 * is able to chose which appearance his ball shall have.
 */
class CustomizeMenuScreen extends MenuScreen {

    /**
     * Array containing the Buttons the will allow the User to choose a skin. Each Button references a Skin.
     */
    private ArrayList<Button> skinButtons = new ArrayList<>();

    /**
     * Array containing the Labels associated to the skins.
     */
    private ArrayList<Label> skinLabels = new ArrayList<>();

    /**
     * Index on the array of Skins of the currently active skin.
     */
    private static int currentSkin = 0;

    /**
     * Constant representing the size of the Buttons
     */
    private static final float BUTTON_SIZE = VIEWPORT_WIDTH / 6;

    /**
     * Constant representing the extra space around the edges of all Images.
     */
    private static final float IMAGE_EDGE = VIEWPORT_WIDTH / 15;
    /**
     * Constant representing the distance between the first line of Level Buttons and the screen Top.
     */
    private static final float TOP_EDGE = VIEWPORT_WIDTH / 7;
    /**
     * Constant representing the distance between the stage elements and the screen limits.
     */
    private static final float SIDE_DISTANCE = VIEWPORT_WIDTH / 18;
    /**
     * Constant representing the distance between the scroller and the screen bottom limit.
     */
    private static final float SCROLLER_DISTANCE = VIEWPORT_WIDTH / 35;

    /**
     * Customize Menu Screen's Constructor.
     * It takes a game as a parameter and calls its super Class Constructor.
     *
     * @param game
     *          The current Game.
     *
     */
    CustomizeMenuScreen(final Spheral game) {
        super(game);
    }

    /**
     * Function used to create the Skins' Buttons and Labels and associate them to a given table, organized.
     * It also adds Listeners to the Skins' Buttons.
     *
     * @param table
     *      Table where the Skins' Labels and Buttons will be organized.
     */
    private void createSkins (Table table) {
        for (int i=0; i < game.getNumSkins(); ++i) {

            //Adding Buttons and Labels to the Arrays
            skinButtons.add( new Button( new TextureRegionDrawable (new TextureRegion (game.getAssetManager().get( "big_skins/skin" + (i < 10 ? "0" : "") + i + ".png", Texture.class)))));
            skinLabels.add(new Label ("Current", skin1));

            final int j = i; //Needed for Listener initialization
            skinButtons.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setCurrentLabel(j);
                }
            });

            table.add(skinButtons.get(i)).size(BUTTON_SIZE, BUTTON_SIZE).pad(IMAGE_EDGE);
        }
        table.row();

        for (int i=0; i < game.getNumSkins(); ++i)
            table.add(skinLabels.get(i)).fill().width(BUTTON_SIZE).center();

        initializeCurrentSkin();
    }

    /**
     * Initialize the currentSkin.
     * Sets only the currentSkin's label to visible.
     */
    private void initializeCurrentSkin() {
        for (int i = 0; i < skinLabels.size(); ++i) {
            if (i == currentSkin)
                skinLabels.get(currentSkin).setVisible(true);
            else
                skinLabels.get(i).setVisible(false);
        }
    }

    /**
     * Updates the Current Skin, setting it to the index of the skin of the pressed Label.
     *
     * @param j
     *          Index of the skin of the pressed Label.
     */
    private void setCurrentLabel(final int j) {
        skinLabels.get(currentSkin).setVisible(false);
        skinLabels.get(j).setVisible(true);
        currentSkin = j;

        BallView.setCurrentSkin(currentSkin);
        ViewFactory.resetBallView();
    }

    /**
     * Function responsible for creating the static Elements of the Stage (Scroller and Back Button), and organize them.
     * Also adds Listeners to its Elements.
     *
     * @param table
     *          Table where the static elements will be organized.
     * @param skinsTable
     *          Table containing the level buttons, that will be associated to the scroller.
     */
    private void createStaticElements (Table table, Table skinsTable) {
        TextButton back = addBackBtn(true);

        //Creating and setting the Scroller
        ScrollPane scroller = new ScrollPane(skinsTable, skin1);
        scroller.getStyle().background = null;

        table.add(back).size(DEFAULT_BUTTON_SIZE, DEFAULT_BUTTON_SIZE).top().left().padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3).row();
        table.add(scroller).fill().expand().padBottom(SCROLLER_DISTANCE);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void show() {
        super.show();

        //Table containing all the possibles Ball appearances
        Table skins = new Table();

        //Table containing the screen elements that shall not move with the scroller
        Table staticElements = new Table();
        staticElements.setFillParent(true);

        createSkins(skins);
        createStaticElements(staticElements, skins);

        stage.addActor(staticElements);
        Gdx.input.setInputProcessor(stage);
    }
}
