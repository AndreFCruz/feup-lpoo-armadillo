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
     * Array containing the the Labels associated to a skin
     */
    ArrayList<Label> skinLabels = new ArrayList<Label>();

    /**
     * Index on the array of Skins of the currently active skin.
     */
    private int current_skin = 0;

    //private

    private static final int NUMBER_OF_SKINS = 6;

    //Layout Macros

    /**
     * Constant representing the size of the Buttons
     */
    private static final int BUTTON_SIZE = 120;

    /**
     * Constant representing the extra space around the edges of all Images.
     */
    private static final int IMAGE_EDGE = 30;
    /**
     * Constant representing the distance between the first line of Level Buttons and the screen Top.
     */
    private static final int TOP_EDGE = 100;
    /**
     * Constant representing the distance between the stage elements and the screen limits.
     */
    private static final int SIDE_DISTANCE = 40;

    /**
     * Customize Menu Screen's Constructor.
     *
     * @param game
     *
     */
    CustomizeMenuScreen(final Spheral game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        //Table containing all the possibles Ball appearances
        Table skins = new Table();
        skins.debugAll();

        //Table containing the screen elements that shall not move with the slider
        Table fixElements = new Table();
        fixElements.debugAll();
        fixElements.setFillParent(true);

        Label labelOne = new Label ("Current", skin);

        Image imageOne = new Image ( new Texture ("skins/skin01.png"));
        //imageOne.setScale(1.4f,1.4f);

        Label labelTwo = new Label ("Current", skin);
        Image imageTwo = new Image ( new Texture ("skins/skin02.png"));

        Label labelThree = new Label ("Current", skin);
        Image imageThree = new Image ( new Texture ("skins/skin03.png"));

        Label labelFour = new Label ("Current", skin);
        Image imageFour = new Image ( new Texture ("skins/skin04.png"));

        final Label labelFive = new Label ("Current", skin);
        Image imageFive = new Image ( new Texture ("skins/skin05.png"));

        final Label labelSix = new Label ("Current", skin);
        Image imageSix = new Image ( new Texture ("skins/skin06.png"));

        //Adding all the Images/Buttons to the same line
        skins.add(imageOne).pad(IMAGE_EDGE);
        skins.add(imageTwo).pad(IMAGE_EDGE);
        skins.add(imageThree).pad(IMAGE_EDGE);
        skins.add(imageFour).pad(IMAGE_EDGE);
        skins.add(imageFive).pad(IMAGE_EDGE);
        skins.add(imageSix).pad(IMAGE_EDGE);

        /**
     * Function used to create the Skins' Buttons and Labels and associate them to a given table, organized.
     * It also adds Listeners to the Skins' Buttons.
     *
     * @param table
     *      Table where the Skins' Labels and Buttons will be organized.
     */
    private void createSkins (Table table) {
        for (int i=0; i < NUMBER_OF_SKINS; ++i) {
            //Adding Buttons and Labels to the Arrays
            skinButtons.add( new Button( new TextureRegionDrawable (new TextureRegion (new Texture ( "skins/skin" + (i < 10 ? "0" : "") + i + ".png")))));
            skinLabels.add(new Label ("Current", skin));

            //Adding Listeners to the Buttons
            skinButtons.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setCurrentSkin(1); //TODO: change
                }
            });

            // /Adding the Buttons to the Table
            table.add(skinButtons.get(i)).size(BUTTON_SIZE, BUTTON_SIZE).pad(IMAGE_EDGE);
        }
        table.row();

        //Adding the Labels to the Table
        for (int i=0; i < NUMBER_OF_SKINS; ++i)
            table.add(skinLabels.get(i)).width(BUTTON_SIZE);
    }
>>>>>>> d7e4f34a3de48f45d266f8d0b7a1371b1672588e

    /**
     * Set the current skin to the given Index
     *
     * @param idx
     *          Index to set the current skin to.
     */
    private void setCurrentSkin(int idx) {
        for (int i=0; i < skinLabels.size(); ++i) {

            if (i == idx)
                skinLabels.get(i).setVisible(true);
            else
                skinLabels.get(i).setVisible(false);
        }
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

        //Creating and adding the Listener to the back button
        TextButton back = new TextButton("Back", skin);

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        //Creating and setting the Scroller
        ScrollPane scroller = new ScrollPane(skinsTable, skin);
        scroller.getStyle().background = null;  //Setting the scroll background invisible
        /*//INVISIBLE SCROLLER
        scroller.getStyle().vScroll = null;
        scroller.getStyle().vScrollKnob = null;*/

        table.add(back).top().left().padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3);
        table.row();
        table.add(scroller).fill().expand().padBottom(SIDE_DISTANCE);
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

        // Add Elements to stage
        stage.addActor(staticElements);

        Gdx.input.setInputProcessor(stage); //TODO: averiguar o pq de ter de ser aqu (se não não dá)
    }
}
