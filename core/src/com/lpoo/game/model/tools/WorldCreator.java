package com.lpoo.game.model.tools;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andre on 27/04/2017.
 */

public interface WorldCreator {
    public void generateWorld(World world, Map map);
}
