package com.lpoo.game.test;

import com.lpoo.game.model.GameModel;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Edgar on 30/05/2017.
 */
public class motionTest extends GameTest {

    GameTester tester = new GameTester("maps/testmap0.tmx");

    float deltaError = 0.05f;

    @Test
    public void noMotionTest()
    {
        //Initial Ball position
        float[] init_pos = {tester.getBallXPosition(), tester.getBallYPosition()};

        //Position did not move
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(5));
        assertEquals(init_pos[0], tester.getBallXPosition(), deltaError);
        assertEquals(init_pos[1], tester.getBallYPosition(), deltaError);
    }

    @Test
    public void moveLeftTest()
    {
        //Initial Ball position
        float[] init_pos = {tester.getBallXPosition(), tester.getBallYPosition()};

        //RotatedLeft
        assertEquals(GameModel.ModelState.LIVE, tester.rotateLeftDuringTime(1));
        assertTrue(init_pos[0] > tester.getBallXPosition());
        assertTrue(init_pos[0] - 3 > tester.getBallXPosition());
    }

    @Test
    public void moveRightTest()
    {
        //Initial Ball position
        float[] init_pos = {tester.getBallXPosition(), tester.getBallYPosition()};

        //RotatedRight
        assertEquals(GameModel.ModelState.LIVE, tester.rotateRightDuringTime(1));
        assertTrue(init_pos[0] < tester.getBallXPosition());
        assertTrue(init_pos[0] + 3 < tester.getBallXPosition());
    }

    @Test
    public void jumpTest()
    {
        //Initial Ball position
        float[] init_pos = {tester.getBallXPosition(), tester.getBallYPosition()};

        //Jumped
        tester.ballJump();
        //Giving time for the ball to elevate
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(0.5f));

        assertEquals(init_pos[0], tester.getBallXPosition(), deltaError);
        assertTrue(init_pos[1] < tester.getBallYPosition());
        assertTrue(init_pos[1] + 3 < tester.getBallYPosition());

        //Checking the ball is still going up - but losing velocity
        float[] other_pos = {tester.getBallXPosition(), tester.getBallYPosition()};
        tester.noMotionDuringTime(0.5f);

        assertEquals(other_pos[0], tester.getBallXPosition(), deltaError);
        assertTrue(other_pos[1] < tester.getBallYPosition());
        assertFalse(other_pos[1] + 3 < tester.getBallYPosition()); //Not as fast
        assertTrue(other_pos[1] + 1 < tester.getBallYPosition());

        //Checking the ball is going down now, thanks to gravity
        other_pos[0] = tester.getBallXPosition();
        other_pos[1] = tester.getBallYPosition();
        tester.noMotionDuringTime(1);

        assertEquals(other_pos[0], tester.getBallXPosition(), deltaError);
        assertTrue(other_pos[1] > tester.getBallYPosition());
        assertTrue(other_pos[1] - 3 > tester.getBallYPosition());

        //The ball is now again in the initial position, after rebounding
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(3));
        assertEquals(init_pos[0], tester.getBallXPosition(), deltaError);
        assertEquals(init_pos[1], tester.getBallYPosition(), deltaError);
    }

    GameTester helperTester = new GameTester("maps/testmap0.tmx");

    @Test
    public void dunkTest()
    {
        //Both balls start in the same position in a equal map.
        assertEquals(tester.getBallXPosition(), helperTester.getBallXPosition(), deltaError);
        assertEquals(tester.getBallYPosition(), helperTester.getBallYPosition(), deltaError);

        //Both balls jump
        tester.ballJump();
        helperTester.ballJump();
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(0.4f));
        assertEquals(GameModel.ModelState.LIVE, helperTester.noMotionDuringTime(0.4f));

        assertEquals(tester.getBallXPosition(), helperTester.getBallXPosition(), deltaError);
        assertEquals(tester.getBallYPosition(), helperTester.getBallYPosition(), deltaError);

        //Ball 2 dunks
        float[] dunk_moment = { tester.getBallXPosition(), tester.getBallYPosition() };
        tester.noMotionDuringTime(0.5f);
        helperTester.ballJump();
        helperTester.noMotionDuringTime(0.5f);

        assertTrue(tester.getBallYPosition() > helperTester.getBallYPosition() + 5);
        assertTrue(tester.getBallYPosition() > dunk_moment[1] + 2);
        assertTrue(helperTester.getBallYPosition() < dunk_moment[1] - 2);
        assertEquals(tester.getBallXPosition(), helperTester.getBallXPosition(), deltaError);

        //Balls will be in the initial position, after rebounding
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(5));
        assertEquals(GameModel.ModelState.LIVE, helperTester.noMotionDuringTime(5));
        assertEquals(tester.getBallXPosition(), helperTester.getBallXPosition(), deltaError);
        assertEquals(tester.getBallYPosition(), helperTester.getBallYPosition(), deltaError);
    }

    @Test
    public void diagonalJumpTest()
    {
        assertEquals(GameModel.ModelState.LIVE, tester.rotateRightDuringTime(0.5f));

        //Ball position before jump
        float[] init_pos = {tester.getBallXPosition(), tester.getBallYPosition()};

        //Jumped
        tester.ballJump();
        //Giving time for the ball to elevate
        tester.noMotionDuringTime(0.5f);

        assertTrue(init_pos[0] + 3 < tester.getBallXPosition());
        assertTrue(init_pos[1] + 3 < tester.getBallYPosition());

        //Checking the ball is still going up - but losing velocity (Vertical component)
        float[] other_pos = {tester.getBallXPosition(), tester.getBallYPosition()};
        tester.noMotionDuringTime(0.5f);

        assertTrue(other_pos[0] + 3 < tester.getBallXPosition());
        assertFalse(other_pos[1] + 3 < tester.getBallYPosition()); //Not as fast
        assertTrue(other_pos[1] + 1 < tester.getBallYPosition());
    }

    GameTester anotherTester = new GameTester("maps/testmap1.tmx");

    @Test
    public void surroundedByWallsTest()
    {
        //Initial Ball position
        float[] init_pos = {anotherTester.getBallXPosition(), anotherTester.getBallYPosition()};

        //Rotating Right
        assertEquals(GameModel.ModelState.LIVE, anotherTester.rotateRightDuringTime(5));
        assertEquals(init_pos[0], anotherTester.getBallXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.getBallYPosition(), deltaError);

        //Rotating Left
        anotherTester.rotateLeftDuringTime(5);
        assertEquals(init_pos[0], anotherTester.getBallXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.getBallYPosition(), deltaError);

        ///Jumping and Dunking
        anotherTester.ballJump();
        anotherTester.noMotionDuringTime(0.5f);
        assertEquals(init_pos[0], anotherTester.getBallXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.getBallYPosition(), deltaError);

        anotherTester.ballJump();
        anotherTester.noMotionDuringTime(0.2f);
        anotherTester.ballJump();
        anotherTester.noMotionDuringTime(0.5f);
        anotherTester.ballJump();
        assertEquals(GameModel.ModelState.LIVE, anotherTester.noMotionDuringTime(0.1f));
        assertEquals(init_pos[0], anotherTester.getBallXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.getBallYPosition(), deltaError);
    }
}