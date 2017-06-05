package com.lpoo.game.test;

import com.lpoo.game.model.GameModel;

import org.junit.Test;

import static com.lpoo.game.model.GameModel.ModelState.LIVE;
import static org.junit.Assert.*;

/**
 * Created by Edgar on 05/06/2017.
 */

public class collisionObjectsTest extends GameTest {

    GameTester tester = new GameTester("maps/testmap6.tmx");

    @Test
    public void collisionBoxTest() {

        //Testing Collision with Boxes
        tester.rotateLeftDuringTime(0.5f);
        tester.ballJump();
        assertEquals(LIVE, tester.noMotionDuringTime(2));
    }

    @Test
    public void collisionPlatformTest() {
        //Testing Collision with Boxes
        tester.rotateRightDuringTime(0.5f);
        tester.ballJump();
        assertEquals(LIVE, tester.noMotionDuringTime(2));
    }
}
