package com.lpoo.game;

/**
 * The Game Services used in the Game.
 */
public interface GameServices {

    /**
     * Sign In User in the Game Services.
     */
    void signIn();

    /**
     * Sign Out User in the Game Services.
     */
    void signOut();

    /**
     * @return If the User is signed in return true, false otherwise.
     */
    boolean isSignedIn();

    /**
     * Submit the scores of the given Level to the Game Services.
     *
     * @param level The level that will have his scores updated.
     * @param score The score to update with.
     */
    void submitScore(int level, int score);

    /**
     * Unnlocks the Achievement with the given ID.
     *
     * @param achievementID Achievement's ID.
     */
    void unlockAchievement(String achievementID);

    //gets the scores/achievements and displays them through google's default widget

    /**
     * Gets the scores of the given level and displays them through google's default widget.
     *
     * @param level Level that will have its scores displayed.
     */
    void showScores(int level);

    /**
     * Gets the achievements and displays them through google's default widget.
     */
    void showAchievements();

    /**
     * @return The Gravity Achievement's ID.
     */
    String getGravityAchievementID();

    /**
     * @return The Speed Achievement's ID.
     */
    String getSpeedAchievementID();

    /**
     * @return The Water Achievement's ID.
     */
    String getWaterAchievementID();

    /**
     * @return The Falling Achievement's ID.
     */
    String getFallingAchievementID();

}