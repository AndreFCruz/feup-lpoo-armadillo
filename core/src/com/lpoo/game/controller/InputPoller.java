package com.lpoo.game.controller;

/**
 * An Interface containing the functions all the Input Handlers should implement.
 */
public interface InputPoller {

    /**
     * Polls for input to be handled in game.
     *
     * @param delta Time elapsed since last update
     */
    void pollInput(float delta);
}
