package com.lpoo.game.view.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Spheral;

/**
 * Created by Edgar on 04/06/2017.
 */

public class NetworkingMenuScreen extends MainMenuScreen {

    /**
     * Main Menu Screen's Constructor.
     *
     * @param game
     *
     */
    public NetworkingMenuScreen(final Spheral game) {
        super(game);
    }

    /**
     * Function responsible for creating and setting the Menu Buttons.
     * It also sets the buttons Layout in the given table.
     *
     * @param table
     *      Table where the Menu buttons will be organized
     */
    @Override
    protected void createMenuButtons(Table table) {
        //Create buttons
        TextButton signInButton = createSignInBtn();

        TextButton achievementsButton = new TextButton("Achievements", skin1);
        achievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameServices.showAchievements();
            }
        });

        TextButton leaderboardButton = new TextButton("Leaderbord", skin1);
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardMenuScreen(game));
            }
        });

        TextButton back = addBackBtn(false);

        //Add buttons to table
        table.bottom();
        table.add(signInButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
        table.add(achievementsButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
        table.add(leaderboardButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
        table.add(back).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE);
        table.padBottom(BOTTOM_EDGE);
    }

    /**
     * Function to create and initialize the Listener of a SignIn / SignOut Button.
     *
     * @return The Text Button responsible for SignIn / SignOut.
     */
    private TextButton createSignInBtn() {
        final TextButton signInButton = new TextButton(
                gameServices.isSignedIn() ? "Sign Out" : "Sign In!", skin1);

        signInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameServices.isSignedIn()) {
                    gameServices.signOut();
                    signInButton.setText("Sign In!");
                } else {
                    gameServices.signIn();
                    signInButton.setText("Sign Out");
                }
            }
        });

        return signInButton;
    }
}
