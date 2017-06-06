package com.lpoo.game.desktop;

import com.lpoo.game.GameServices;

/**
 * Created by andre on 02/06/2017.
 */

class NullGameServices implements GameServices {

    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void submitScore(int level, int score) {

    }

    @Override
    public void unlockAchievement(String achievementID) {

    }

    @Override
    public void showScores(int level) {

    }

    @Override
    public void showAchievements() {

    }

    @Override
    public String getGravityAchievementID() {
        return null;
    }

    @Override
    public String getSpeedAchievementID() {
        return null;
    }

    @Override
    public String getWaterAchievementID() {
        return null;
    }

    @Override
    public String getFallingAchievementID() {
        return null;
    }
}