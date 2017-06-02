package com.lpoo.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements GameServices, GameHelper.GameHelperListener {
    private GameHelper gameHelper;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.hideStatusBar = true;
        config.useAccelerometer = true;

        gameHelper.setup(this);

		initialize(new Spheral(this), config);
	}

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn() {
        try {
            runOnUiThread( () -> gameHelper.beginUserInitiatedSignIn() );
        } catch (final Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread( () -> gameHelper.signOut() );
        } catch (final Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void submitScore(int score) {
//        Games.Leaderboards.submitScore(gameHelper.getApiClient(), , score);
    }

    @Override
    public void unlockAchievements(String achievementID) {
        //TODO
    }

    @Override
    public void showScores() {
        //TODO
    }

    @Override
    public void showAchievements() {

    }

    @Override
    public void onSignInFailed() {
        System.out.println("Sign in failed");
    }

    @Override
    public void onSignInSucceeded() {
        System.out.println("Sign in succeeded");
    }
}
