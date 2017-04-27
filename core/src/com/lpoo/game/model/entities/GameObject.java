package com.lpoo.game.model.entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by andre on 27/04/2017.
 */

public abstract class GameObject {
    protected Body body;

    public abstract void update(float delta);

    public Body getBody() {
        return body;
    }

}
