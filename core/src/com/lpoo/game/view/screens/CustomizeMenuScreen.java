package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Spheral;

/**
 * A view representing the Customize Menu screen. In this Menu the User
 * is able to chose which appearance his ball shall have.
 */
public class CustomizeMenuScreen extends MenuScreen {

    /**
     * Index on the array of Skins of the currently active skin.
     */
    private int current_skin = 0;

    //Layout Macros
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

        skins.row();

        //Adding the label below the skins -> TODO: need to do the same for time stamps on levelMenu
        skins.add(labelOne);
        skins.add(labelTwo);
        skins.add(labelThree);
        skins.add(labelFour);
        skins.add(labelFive);
        skins.add(labelSix);
        labelSix.setVisible(false); // -> TODO: Function responsible for setting the current skin and the skin changing

        //Create standing Elements
        TextButton back = new TextButton("Back", skin);
        ScrollPane scroller = new ScrollPane(skins, skin);
        scroller.getStyle().background = null;  //Setting the scroll background invisible
        /*//INVISIBLE SCROLLER
        scroller.getStyle().hScroll = null;
        scroller.getStyle().hScrollKnob = null;*/

        //Add listeners to buttons
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        //Add listeners to buttons
        imageFive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                labelFive.setVisible(true);
                labelSix.setVisible(false);
            }
        });
        //Add listeners to buttons
        imageSix.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                labelFive.setVisible(false);
                labelSix.setVisible(true);
            }
        });

        //Standing Elements
        //skins.setDebug(true); //Testing Purposes
        //fixElements.setDebug(true); //Testing Purposes
        fixElements.add(back).top().left().padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3);
        fixElements.row();
        fixElements.add(scroller).fill().expand().padBottom(SIDE_DISTANCE);

        // Add Elements to stage
        stage.addActor(fixElements);

        Gdx.input.setInputProcessor(stage); //TODO: averiguar o pq de ter de ser aqu (se não não dá)
    }
}
