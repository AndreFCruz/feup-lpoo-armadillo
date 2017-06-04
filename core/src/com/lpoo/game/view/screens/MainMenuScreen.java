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
    protected static final float BUTTON_WIDTH = VIEWPORT_WIDTH / 2;
    /**
     * Constant representing the extra space around the edges of all Buttons.
     */
    protected static final float BUTTON_EDGE = VIEWPORT_WIDTH / 75;
    /**
     * Constant representing the extra space around the bottom edge of the bottom Button.
     */
    protected static final float BOTTOM_EDGE = VIEWPORT_WIDTH / 75;

    /**
     * Main Menu Screen's Constructor.
     *
     * @param game
     *
     */
    public MainMenuScreen(final Spheral game) {
        super(game);
    }

    /**
     * Function responsible for creating and setting the Menu Buttons.
     * It also sets the buttons Layout in the given table.
     *
     * @param table
     *      Table where the Menu buttons will be organized
     */
    protected void createMenuButtons(Table table) {

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelMenuScreen(game));
            }
        });

        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CustomizeMenuScreen(game));
            }
        });

        TextButton networkingButton = new TextButton("Networking", skin);
        networkingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new NetworkingMenuScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        table.bottom();
        table.add(playButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE).row();
        table.add(optionsButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE).row();
        table.add(networkingButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE).row();
        table.add(exitButton).width(BUTTON_WIDTH).pad(BUTTON_EDGE).row();
        table.padBottom(BOTTOM_EDGE);
    }

    @Override
    public void show() {
        super.show();

        Table table = new Table();
        table.setFillParent(true);

        createMenuButtons(table);

        // Add table to stage
        stage.addActor(table);
    }
}