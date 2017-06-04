package com.lpoo.game;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

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
    public void rateGame() {
        String str = "Your PlayStore Link"; // TODO
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void unlockAchievement(String achievementID) {
        Games.Achievements.unlock(gameHelper.getApiClient(), achievementID);
    }

    @Override
    public void showScores(){
        if (gameHelper.isSignedIn()) {
            activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    activity.getString(R.string.leaderboard_fastest_levels)), UNUSED_REQUEST_CODE);
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
        else if (!gameHelper.isConnecting()) {
//            signIn();
        }
    }

    @Override
    public void submitScore(int score) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    activity.getString(R.string.leaderboard_fastest_levels), score);

            // Incremental achievement -> number of times played
            unlockAchievement(activity.getString(R.string.achievement_really_bored___));
        }
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
}
