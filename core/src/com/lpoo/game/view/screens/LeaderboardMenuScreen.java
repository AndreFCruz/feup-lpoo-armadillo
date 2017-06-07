package com.lpoo.game.view.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Armadillo;

/**
 * Class responsible for controlling the Leaderboard's Menu Screen.
 */
public class LeaderboardMenuScreen extends LevelMenuScreen {

    /**
     * Level Menu Screen's Constructor.
     *
     * @param game The current game session.
     */
    LeaderboardMenuScreen(Armadillo game) {
        super(game);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void addLevelListener(final int level) {
        levelButtons.get(level).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameServices.showScores(level);
            }
        });
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected TextButton addBackBtn(boolean style) {
        TextButton back = new TextButton("Back", (style ? skin2 : skin1));

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new NetworkingMenuScreen(game));
            }
        });

        return back;
    }
}
