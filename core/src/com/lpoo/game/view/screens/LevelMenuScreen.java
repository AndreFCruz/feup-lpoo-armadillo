package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Spheral;

import java.util.ArrayList;

/**
 * A view representing the Level Menu screen. In this Menu the User
 * is able to chose which level to play, from the available ones.
 */
public class LevelMenuScreen extends MenuScreen {

    /**
     * Array containing all the buttons used to select Levels.
     */
    private ArrayList<TextButton> levelButtons = new ArrayList<TextButton>();

    //Layout Macros
    /**
     * Since the Level Buttons are square, this Constant represents both their Width and Height.
     */
    private static final int BUTTON_SIDE = 80;
    /**
     * Constant representing the extra space around the edges of all Buttons.
     */
    private static final int BUTTON_EDGE = 30;
    /**
     * Constant representing the distance between the first line of Level Buttons and the screen Top.
     */
    private static final int TOP_EDGE = 100;
    /**
     * Constant representing the distance between the stage elements and the screen side limits.
     */
    private static final int SIDE_DISTANCE = 40;

    /**
     * Number of Buttons per Line of the Table.
     */
    private static final int BUTTONS_PER_LINE = 4;

    /**
     * Level Menu Screen's Constructor.
     *
     * @param game
     *
     */
    LevelMenuScreen(final Spheral game) {
        super(game);
    }

    /**
     * Function used to create the Level Buttons and associate them to a given table, organized.
     * It also adds Listeners to the Level buttons.
     *
     * @param table
     *      Table where the Level Buttons will be organized.
     */
    private void createLevelButtons(Table table) {

        for (int i = 1 ;  i <= game.getNumMaps(); ++i) {
            levelButtons.add(new TextButton(String.valueOf(i), skin));

            //Adding to table and setting Layout aspect
            table.add(levelButtons.get(i-1)).width(BUTTON_SIDE).height(BUTTON_SIDE).pad(BUTTON_EDGE);

            final int j = (i-1); //Needed for Listener initialization

            //Adding Listener
            levelButtons.get(i-1).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, j)); //TODO: Mudar para que cada um leve ao seu level certo
                }
            });

            if ((i % BUTTONS_PER_LINE) == 0)
                table.row();
        }
    }

    /**
     * Function used to create the static Elements of the Stage, and organize them.
     * Also adds Listeners to its Elements.
     *
     * @param table
     *          Table where the static elements will be organized.
     * @param levelsTable
     *          Table containing the level buttons, that will be associated to the scroller.
     */
    private void createStaticElements (Table table, Table levelsTable) {

        TextButton back = addBackBtn();

        //Creating and setting the Scroller
        ScrollPane scroller = new ScrollPane(levelsTable, skin);
        scroller.getStyle().background = null;  //Setting the scroll background invisible

        table.add(back).top().padLeft(SIDE_DISTANCE).padTop(TOP_EDGE / 3);
        table.add(scroller).fill().expand().padRight(SIDE_DISTANCE);
    }

    @Override
    public void show() {
        super.show();

        // Table containing the Level Buttons
        Table levels = new Table();

        //Layout Aspect
        levels.top();
        levels.padTop(TOP_EDGE);

        createLevelButtons(levels);

        //Table containing the Static Elements
        Table staticElements = new Table();
        staticElements.setFillParent(true);

        createStaticElements(staticElements, levels);

        // Add table to stage
        stage.addActor(staticElements);

        Gdx.input.setInputProcessor(stage);
    }
}
