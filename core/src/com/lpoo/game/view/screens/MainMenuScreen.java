package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lpoo.game.Spheral;

/**
 * Created by andre on 04/05/2017.
 */

public class MainMenuScreen extends ScreenAdapter {
    private final Spheral game;

    private OrthographicCamera camera;

    private Stage stage;

    MainMenuScreen(final Spheral game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        SpriteBatch batch = game.getBatch();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        game.font.draw(batch, "Welcome to Spheral !", 100, 150);

        if (Gdx.input.isTouched())
            game.font.draw(batch, "YAYY", 100, 100);
        else
            game.font.draw(batch, "Tap anywhere to begin.", 100, 100);

        batch.end();

    }
}
