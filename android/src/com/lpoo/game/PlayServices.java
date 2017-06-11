package com.lpoo.game;

import android.app.Activity;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

/**
 * Play Services class that handles the communication with the Google Play Services.
 */
public class PlayServices implements GameServices {

    /**
     * Code for unused Requests.
     */
    private final static int UNUSED_REQUEST_CODE = 1;

    /**
     * The Play Services' activity class.
     */
    private Activity activity;

    /**
     * Game Helper used to help with the communication with Google Play Services.
     */
    private GameHelper gameHelper;

    /**
     * Play Services constructor.
     *
     * @param activity   The Activity used in the Play Services.
     * @param gameHelper The GameHelper used in the Play services.
     */
    PlayServices(Activity activity, GameHelper gameHelper) {
        this.activity = activity;
        this.gameHelper = gameHelper;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void signIn() {
        try {
            activity.runOnUiThread(() -> gameHelper.beginUserInitiatedSignIn());
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void signOut() {
        try {
            activity.runOnUiThread(() -> gameHelper.signOut());
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void unlockAchievement(String achievementID) {
        if (isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(), achievementID);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void showScores(int level) {
        showScores(getLeaderboardID(level));
    }

    /**
     * Show the scores of a given leaderboard.
     *
     * @param leaderboardID The leaderboard ID to show the score from.
     */
    private void showScores(String leaderboardID) {
        if (isSignedIn()) {
            activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    leaderboardID), UNUSED_REQUEST_CODE);
        } else if (!gameHelper.isConnecting()) {
            signIn();
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void showAchievements() {
        if (isSignedIn()) {
            activity.startActivityForResult(Games.Achievements.getAchievementsIntent(
                    gameHelper.getApiClient()), UNUSED_REQUEST_CODE);
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void submitScore(int level, int score) {
        String leaderboardID = getLeaderboardID(level);
        String achievementID = getAchievementID(level, score);

        if (leaderboardID != null)
            submitScore(leaderboardID, score);

        if (achievementID != null)
            unlockAchievement(achievementID);
    }

    /**
     * Getter for an achievement's ID.
     *
     * @param level The level the achievement is associated with.
     * @param score The Score of the User in the given level.
     * @return The achievement ID.
     */
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

    /**
     * Submit the given score to the given leaderboard.
     *
     * @param leaderboardID LeaderboardID resembling a leaderboard.
     * @param score         Score to update the Leaderboard with.
     */
    private void submitScore(String leaderboardID, int score) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    leaderboardID, score);

            // Incremental achievement -> number of times played
            unlockAchievement(activity.getString(R.string.achievement_really_bored___));
        }
    }

    /**
     * Getter for each level leaderboard.
     *
     * @param level The current level to know the leaderboard from.
     * @return the leaderboard ID.
     */
    private String getLeaderboardID(int level) {
        switch (level) {
            case 0:
                return activity.getString(R.string.leaderboard_level_one);
            case 1:
                return activity.getString(R.string.leaderboard_level_two);
            case 2:
                return activity.getString(R.string.leaderboard_level_three);
            case 3:
                return activity.getString(R.string.leaderboard_level_four);
            case 4:
                return activity.getString(R.string.leaderboard_level_five);
            case 5:
                return activity.getString(R.string.leaderboard_level_six);
            case 6:
                return activity.getString(R.string.leaderboard_level_seven);
            case 7:
                return activity.getString(R.string.leaderboard_level_eight);
            case 8:
                return activity.getString(R.string.leaderboard_level_nine);
            case 9:
                return activity.getString(R.string.leaderboard_level_ten);
            default:
                System.err.println("Invalid level ID. Was " + level);
        }

        return null;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String getGravityAchievementID() {
        return activity.getString(R.string.achievement_up_up_and_away);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String getSpeedAchievementID() {
        return activity.getString(R.string.achievement_speeeed);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String getWaterAchievementID() {
        return activity.getString(R.string.achievement_dont_touch_the_water);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String getFallingAchievementID() {
        return activity.getString(R.string.achievement_careful_);
    }
}
