package com.lpoo.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper;

/**
 * Class responsible for launching the game in Android.
 */
public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener {

    /**
     * Android Launcher's game Helper, for intercation with google play services.
     */
    private GameHelper gameHelper;

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.hideStatusBar = true;
        config.useAccelerometer = true;

        gameHelper.setup(this);

        hideSystemUI();

        PlayServices playServices = new PlayServices(this, gameHelper);

        initialize(new Armadillo(playServices), config);
    }

    /**
     * Function used to hide the system bars.
     */
    private void hideSystemUI() {
        View mDecorView = getWindow().getDecorView();

        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void onSignInFailed() {
        System.out.println("Sign in failed");
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void onSignInSucceeded() {
        System.out.println("Sign in succeeded");
    }
}
