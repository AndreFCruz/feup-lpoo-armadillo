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

import java.util.ArrayList;

/**
 * A view representing the Customize Menu screen. In this Menu the User
 * is able to chose which appearance his ball shall have.
 */
public class CustomizeMenuScreen extends MenuScreen {

    /**
     * Array containing the the Buttons associated to a skin
     */
    ArrayList<Button> skinButtons = new ArrayList<Button>();

    /**
     * Array containing the Labels associated to a skin
     */
    ArrayList<Label> skinLabels = new ArrayList<Label>();

    /**
     * Index on the array of Skins of the currently active skin.
     */
    private int current_skin = 0;

    //Layout Macros

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
     *
     * @param game
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
            skinButtons.add( new Button( new TextureRegionDrawable (new TextureRegion (game.getAssetManager().get( "skins/skin" + (i < 10 ? "0" : "") + i + ".png", Texture.class)))));
            skinLabels.add(new Label ("Current", skin));

            final int j = i; //Needed for Listener initialization

            //Adding Listeners to the Buttons
            skinButtons.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setCurrentLabel(j);
                }
            });

            // /Adding the Buttons to the Table
            table.add(skinButtons.get(i)).size(BUTTON_SIZE, BUTTON_SIZE).pad(IMAGE_EDGE);
        }
        table.row();

        //Adding the Labels to the Table
        for (int i=0; i < game.getNumSkins(); ++i)
            table.add(skinLabels.get(i)).width(BUTTON_SIZE).center();

        current_skin = 1; //TODO: FAZER LOAD DOS FICHEIROS DA LAST CHOSEN SKINS
        initializeCurrentSkin();
    }

    /**
     * Initialize the current_skin.
     */
    private void initializeCurrentSkin() {
        for (int i = 0; i < skinLabels.size(); ++i) {
            if (i == current_skin)
                skinLabels.get(current_skin).setVisible(true);
            else
                skinLabels.get(i).setVisible(false);
        }
    }

    /**
     * Set the current visible Label with the current skin.
     */
    private void setCurrentLabel(final int j) {
        skinLabels.get(current_skin).setVisible(false);
        skinLabels.get(j).setVisible(true);
        current_skin = j;
    }

    /**
     * Function used to create the static Elements of the Stage, and organize them.
     * Also adds Listeners to its Elements.
     *
     * @param table
     *          Table where the static elements will be organized.
     * @param skinsTable
     *          Table containing the level buttons, that will be associated to the scroller.
     */
    private void createStaticElements (Table table, Table skinsTable) {

        TextButton back = addBackBtn();

        //Creating and setting the Scroller
        ScrollPane scroller = new ScrollPane(skinsTable, skin);
        scroller.getStyle().background = null;  //Setting the scroll background invisible

        table.add(back).top().left().padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3);
        table.row();
        table.add(scroller).fill().expand().padBottom(SCROLLER_DISTANCE);
    }

    @Override
    public void show() {
        super.show();

        //Table containing all the possibles Ball appearances
        Table skins = new Table();
        skins.debugAll();

        //Table containing the screen elements that shall not move with the slider
        Table staticElements = new Table();
        staticElements.debugAll();
        staticElements.setFillParent(true);

        createSkins(skins);

        createStaticElements(staticElements, skins);

        stage.addActor(staticElements);

        Gdx.input.setInputProcessor(stage);
    }
}
