package com.lpoo.game.view.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Armadillo;

/**
 * Class responsible for controlling the Networking Menu Screen.
 */
public class NetworkingMenuScreen extends MainMenuScreen {

    /**
     * Networking Menu Screen's Constructor.
     * It initializes all the Networking Screen elements.
     *
     * @param game The current game session.
     */
    public NetworkingMenuScreen(final Armadillo game) {
        super(game);
    }

    /**
     * Function responsible for creating and setting the Networking Buttons.
     * It also sets the buttons Layout in the given table.
     *
     * @param table Table where the Menu buttons will be organized
     */
    @Override
    protected void createMenuButtons(Table table) {
        table.bottom();

        createSignInBtn(table);
        createAchievementButton(table);
        createLeaderboardButton(table);

        TextButton back = addBackBtn(false);
        table.add(back).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE);

        table.padBottom(BOTTOM_EDGE);
    }

    /**
     * Adds the Achievements Button to the Networking Menu.
     *
     * @param table The table to where the Achievements button will be added.
     */
    private void createAchievementButton(Table table) {
        TextButton achievementsButton = new TextButton("Achievements", skin1);

        achievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameServices.showAchievements();

            }
        });
        table.add(achievementsButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
    }

    /**
     * Adds the Leaderboard Button to the Networking Menu.
     *
     * @param table The table to where the Leaderboard button will be added.
     */
    private void createLeaderboardButton(Table table) {
        TextButton leaderboardButton = new TextButton("Leaderbord", skin1);

        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardMenuScreen(game));
            }
        });

        table.add(leaderboardButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
    }

    /**
     * Adds the Sign In / Sign Out Button to the Networking Menu.
     *
     * @param table The table to where the button will be added.
     */
    private void createSignInBtn(Table table) {
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
        table.add(signInButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
    }
}
