package com.lpoo.game;

/**
 * Created by andre on 02/06/2017.
 */

public class GameServicesAdapter implements GameServices {

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
    public void rateGame() {

    }

    @Override
    public void submitScore(int score) {

    }

    @Override
    public void unlockAchievement(String achievementID) {

    }

    @Override
    public void showScores() {

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
}