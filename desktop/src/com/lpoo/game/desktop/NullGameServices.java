package com.lpoo.game.desktop;

import com.lpoo.game.GameServices;

/**
 * Class implementing game services on the desktop.
 * Since they are not available its methods do nothing.
 */
class NullGameServices implements GameServices {

    /**
     * {@inheritDoc}
     */
    @Override
    public void signIn() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signOut() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSignedIn() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitScore(int level, int score) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementAchievement(String achievementID) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockAchievement(String achievementID) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showScores(int level) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showAchievements() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rateGame() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGravityAchievementID() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpeedAchievementID() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWaterAchievementID() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFallingAchievementID() {
        return null;
    }
}