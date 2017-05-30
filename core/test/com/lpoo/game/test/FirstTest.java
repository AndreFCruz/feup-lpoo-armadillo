package com.lpoo.game.test;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edgar on 30/05/2017.
 */
public class FirstTest {
    @Test
    public void thisAlwaysPasses()
    {
        assertTrue(true);
    }

    @Test
    @Ignore
    public void thisIsIgnored()
    {
    }

    @Test
    public void loadMap()
    {
        //TODO: Ver a cena de algúem para ver como estão a fazer isto
    }
}