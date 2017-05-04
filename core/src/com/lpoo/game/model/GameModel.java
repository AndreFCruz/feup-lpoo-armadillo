package com.lpoo.game.model;

/**
 * Created by andre on 04/05/2017.
 */

class GameModel {
    private static final GameModel ourInstance = new GameModel();

    static GameModel getInstance() {
        return ourInstance;
    }

    private GameModel() {

    }
}
