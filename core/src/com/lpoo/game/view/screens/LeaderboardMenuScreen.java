package com.lpoo.game.view.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Spheral;

/**
 * Created by Edgar on 05/06/2017.
 */

public class LeaderboardMenuScreen extends LevelMenuScreen {

    /**
     * Level Menu Screen's Constructor.
     *
     * @param game
     */
    LeaderboardMenuScreen(Spheral game) {
        super(game);
    }

    @Override
    protected void addLevelListener(final int level) {
        levelButtons.get(level).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameServices.showScores(level);
            }
        });
    }
}
