package com.lpoo.game.controller;

/**
 * An Interface containing the functions all the Input Handlers should implement.
 */
public interface InputHandler {

    /**
     * Function where the game handles the User's input.
     *
     * @param delta Time elapsed since last update
     */
    void handleInput(float delta);

}
