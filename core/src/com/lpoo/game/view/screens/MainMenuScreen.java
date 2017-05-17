package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Spheral;

/**
 * A view representing the Main Menu screen. This Menu is presented
 * to the User when the Application is initialized. In this Menu the
 * User chooses what he wishes to do.
 */
public class MainMenuScreen extends MenuScreen {

    //Layout Macros
    /**
     * Constant representing all the Buttons' Width.
     */
    private static final int BUTTON_WIDTH = 400;
    /**
     * Constant representing the extra space around the edges of all Buttons.
     */
    private static final int BUTTON_EDGE = 15;
    /**
     * Constant representing the extra space around the bottom edge of the bottom Button.
     */
    private static final int BOTTOM_EDGE = 35;

    public MainMenuScreen(final Spheral game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Table table = new Table();
        table.debugAll();
        table.setFillParent(true);

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        //playButton.getLabel().setFontScale(2, 2);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelMenuScreen(game));
            }
        });
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CustomizeMenuScreen(game));
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        //table.setDebug(true); //Testing Purposes
        table.bottom();
        table.add(playButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE);
        table.row();
        table.add(optionsButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE);
        table.row();
//        table.add(exitButton).width(BUTTON_WIDTH).padBottom(BOTTOM_EDGE).padTop(BUTTON_EDGE);
        table.add(exitButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE);
        table.padBottom(BOTTOM_EDGE);

        // Add table to stage
        stage.addActor(table);
    }
}