package com.lpoo.game;

import android.app.Activity;
import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

/**
 * Created by andre on 04/06/2017.
 */

public class PlayServices implements GameServices {

    private final static int UNUSED_REQUEST_CODE = 1;

    private Activity activity;

    private GameHelper gameHelper;

    PlayServices(Activity activity, GameHelper gameHelper) {
        this.activity = activity;
        this.gameHelper = gameHelper;
    }

    @Override
    public void signIn() {
        try {
            activity.runOnUiThread(() -> gameHelper.beginUserInitiatedSignIn());
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            activity.runOnUiThread(() -> gameHelper.signOut());
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void unlockAchievement(String achievementID) {
        Games.Achievements.unlock(gameHelper.getApiClient(), achievementID);
    }

    @Override
    public void showScores(int level) {
        showScores(getLeaderboardID(level));
    }

    private void showScores(String leaderboardID) {
        if (gameHelper.isSignedIn()) {
            activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    leaderboardID), UNUSED_REQUEST_CODE);
        }
        else if (!gameHelper.isConnecting()) {
            signIn();
        }
    }

    @Override
    public void showAchievements() {
        if (gameHelper.isSignedIn()) {
            activity.startActivityForResult(Games.Achievements.getAchievementsIntent(
                    gameHelper.getApiClient()), UNUSED_REQUEST_CODE);
        }
    }

    @Override
    public void submitScore(int level, int score) {
        String leaderboardID = getLeaderboardID(level);
        String achievementID = getAchievementID(level, score);

        if (leaderboardID != null)
            submitScore(leaderboardID, score);

        if (achievementID != null)
            unlockAchievement(achievementID);
    }

    private String getAchievementID(int level, int score) {
        switch (level) {
            case 0:
                return activity.getString(R.string.achievement_so_it_begins);
            case 8:
                if (score < 60)
                    return activity.getString(R.string.achievement_impressive_timing_);
            case 9:
                return activity.getString(R.string.achievement_reach_for_the_clouds);
            default:
                System.err.println("Invalid level ID. Was " + level);
        }

        return null;
    }

    private void submitScore(String leaderboardID, int score) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    leaderboardID, score);

            // Incremental achievement -> number of times played
            unlockAchievement(activity.getString(R.string.achievement_really_bored___));
        }
    }

    private String getLeaderboardID(int level) {
        switch (level) {
            case 0: return activity.getString(R.string.leaderboard_level_one);
            case 1: return activity.getString(R.string.leaderboard_level_two);
            case 2: return activity.getString(R.string.leaderboard_level_three);
            case 3: return activity.getString(R.string.leaderboard_level_four);
            case 4: return activity.getString(R.string.leaderboard_level_five);
            case 5: return activity.getString(R.string.leaderboard_level_six);
            case 6: return activity.getString(R.string.leaderboard_level_seven);
            case 7: return activity.getString(R.string.leaderboard_level_eight);
            case 8: return activity.getString(R.string.leaderboard_level_nine);
            case 9: return activity.getString(R.string.leaderboard_level_ten);
            default:
                System.err.println("Invalid level ID. Was " + level);
        }

        return null;
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    @Override
    public String getGravityAchievementID() {
        return activity.getString(R.string.achievement_up_up_and_away);
    }

    @Override
    public String getSpeedAchievementID() {
        return activity.getString(R.string.achievement_speeeed);
    }

    @Override
    public String getWaterAchievementID() {
        return activity.getString(R.string.achievement_dont_touch_the_water);
    }

    @Override
    public String getFallingAchievementID() {
        return activity.getString(R.string.achievement_careful_);
    }
}
