package com.lpoo.game.test;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.lpoo.game.model.GameModel;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edgar on 30/05/2017.
 */
public class FirstTest {

    //TiledMap map = new TmxMapLoader(/*new InternalFileHandleResolver()*/).load("maps/map1.tmx");
    TestClass useful = new TestClass("maps/testMap.tmx");

    @Test
    public void thisAlwaysPasses()
    {
        assertTrue(true);
    }

    @Test
    public void basicTest()
    {
        assertEquals(GameModel.ModelState.LIVE, useful.updateDuringTime(2));
        //useful.updateDuringTime(2);
    }

    //TODO TESTS, DIREITA ESQUERDA E DUNK

    @Test
    public void loadMap()
    {
        //TODO: Ver a cena de algúem para ver como estão a fazer isto
    }
}