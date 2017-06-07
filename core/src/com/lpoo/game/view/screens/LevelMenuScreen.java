package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Armadillo;

import java.util.ArrayList;

/**
 * A view representing the Level Menu screen. In this Menu the User
 * is able to chose which level to play, from the available ones.
 */
public class LevelMenuScreen extends MenuScreen {

    /**
     * Array containing all the buttons used to select Levels.
     */
    protected ArrayList<TextButton> levelButtons = new ArrayList<>();

    /**
     * Since the Level Buttons are square, this Constant represents both their Width and Height.
     */
    private static final float BUTTON_SIDE = VIEWPORT_WIDTH / 9;
    /**
     * Constant representing the extra space around the edges of all Buttons.
     */
    private static final float BUTTON_EDGE = VIEWPORT_WIDTH / 25;
    /**
     * Constant representing the distance between the first line of Level Buttons and the screen Top.
     */
    private static final float TOP_EDGE = VIEWPORT_WIDTH / 7;
    /**
     * Constant representing the distance between the stage elements and the screen side limits.
     */
    private static final float SIDE_DISTANCE = VIEWPORT_WIDTH / 18;

    /**
     * Number of Buttons per Line of the Table.
     */
    private static final float BUTTONS_PER_LINE = 4;

    /**
     * Level Menu Screen's Constructor.
     * It initializes all the elements used in the stage of this screen.
     *
     * @param game The current game session.
     */
    LevelMenuScreen(final Armadillo game) {
        super(game);
    }

    /**
     * Function used to create the Level Buttons and associate them to a given table, organized.
     * It also adds Listeners to the Level buttons.
     *
     * @param table Table where the Level Buttons will be organized.
     */
    private void createLevelButtons(Table table) {

        for (int i = 1; i <= game.getNumMaps(); ++i) {
            levelButtons.add(new TextButton(String.valueOf(i), skin2));

            table.add(levelButtons.get(i - 1)).size(BUTTON_SIDE, BUTTON_SIDE).pad(BUTTON_EDGE);

            //Adding the button's listener
            final int j = (i - 1);
            addLevelListener(j);

            if ((i % BUTTONS_PER_LINE) == 0)
                table.row();
        }
    }

    /**
     * Adds a listener on click to a certain level button.
     *
     * @param idx The index in the levelButtons array of the button that will install the listener
     */
    protected void addLevelListener(final int idx) {
        levelButtons.get(idx).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, idx));
            }
        });
    }

    /**
     * Function used to create the static Elements of the Stage, and organize them.
     * Also adds Listeners to its Elements.
     *
     * @param table       Table where the static elements will be organized.
     * @param levelsTable Table containing the level buttons, that will be associated to the scroller.
     */
    private void createStaticElements(Table table, Table levelsTable) {

        TextButton back = addBackBtn(true);

        ScrollPane scroller = new ScrollPane(levelsTable, skin1);
        scroller.getStyle().background = null;

        table.add(back).top().size(DEFAULT_BUTTON_SIZE, DEFAULT_BUTTON_SIZE).padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3);
        table.add(scroller).fill().expand().padRight(SIDE_DISTANCE);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void show() {
        super.show();

        // Table containing the Level Buttons
        Table levels = new Table();
        levels.top();
        levels.padTop(TOP_EDGE);
        createLevelButtons(levels);

        // Table containing the Static Elements
        Table staticElements = new Table();
        staticElements.setFillParent(true);
        createStaticElements(staticElements, levels);

        stage.addActor(staticElements);
        Gdx.input.setInputProcessor(stage);
    }
}
