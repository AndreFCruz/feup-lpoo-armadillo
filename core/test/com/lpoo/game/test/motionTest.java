package com.lpoo.game.test;

import com.lpoo.game.model.GameModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edgar on 30/05/2017.
 */
public class motionTest extends GameTest {

    GameTester tester = new GameTester("maps/testmap0.tmx");

    @Test
    public void noMotionTest()
    {
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(2));
    }

    @Test
    public void moveLeftTest()
    {

    }

    @Test
    public void moveRightTest()
    {

    }

    @Test
    public void dunkTest()
    {

    }

    @Test
    public void jumpTest()
    {

    }
}