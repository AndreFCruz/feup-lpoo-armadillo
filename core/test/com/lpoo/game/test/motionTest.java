package com.lpoo.game.test;

import com.badlogic.gdx.Game;
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
        float[] init_pos = {tester.ballXPosition(), tester.ballYPosition()};

        //Position did not move
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(5));
        assertEquals(init_pos[0], tester.ballXPosition(), deltaError);
        assertEquals(init_pos[1], tester.ballYPosition(), deltaError);
    }

    @Test
    public void moveLeftTest()
    {
        //Initial Ball position
        float[] init_pos = {tester.ballXPosition(), tester.ballYPosition()};

        //RotatedLeft
        assertEquals(GameModel.ModelState.LIVE, tester.rotateLeftDuringTime(1));
        assertTrue(init_pos[0] > tester.ballXPosition());
        assertTrue(init_pos[0] - 3 > tester.ballXPosition());
    }

    @Test
    public void moveRightTest()
    {
        //Initial Ball position
        float[] init_pos = {tester.ballXPosition(), tester.ballYPosition()};

        //RotatedRight
        assertEquals(GameModel.ModelState.LIVE, tester.rotateRightDuringTime(1));
        assertTrue(init_pos[0] < tester.ballXPosition());
        assertTrue(init_pos[0] + 3 < tester.ballXPosition());
    }

    @Test
    public void jumpTest()
    {
        //Initial Ball position
        float[] init_pos = {tester.ballXPosition(), tester.ballYPosition()};

        //Jumped
        tester.ballJump();
        //Giving time for the ball to elevate
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(0.5f));

        assertEquals(init_pos[0], tester.ballXPosition(), deltaError);
        assertTrue(init_pos[1] < tester.ballYPosition());
        assertTrue(init_pos[1] + 3 < tester.ballYPosition());

        //Checking the ball is still going up - but losing velocity
        float[] other_pos = {tester.ballXPosition(), tester.ballYPosition()};
        tester.noMotionDuringTime(0.5f);

        assertEquals(other_pos[0], tester.ballXPosition(), deltaError);
        assertTrue(other_pos[1] < tester.ballYPosition());
        assertFalse(other_pos[1] + 3 < tester.ballYPosition()); //Not as fast
        assertTrue(other_pos[1] + 1 < tester.ballYPosition());

        //Checking the ball is going down now, thanks to gravity
        other_pos[0] = tester.ballXPosition();
        other_pos[1] = tester.ballYPosition();
        tester.noMotionDuringTime(1);

        assertEquals(other_pos[0], tester.ballXPosition(), deltaError);
        assertTrue(other_pos[1] > tester.ballYPosition());
        assertTrue(other_pos[1] - 3 > tester.ballYPosition());

        //The ball is now again in the initial position, after rebounding
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(3));
        assertEquals(init_pos[0], tester.ballXPosition(), deltaError);
        assertEquals(init_pos[1], tester.ballYPosition(), deltaError);
    }

    GameTester helperTester = new GameTester("maps/testmap0.tmx");

    @Test
    public void dunkTest()
    {
        //Both balls start in the same position in a equal map.
        assertEquals(tester.ballXPosition(), helperTester.ballXPosition(), deltaError);
        assertEquals(tester.ballYPosition(), helperTester.ballYPosition(), deltaError);

        //Both balls jump
        tester.ballJump();
        helperTester.ballJump();
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(0.4f));
        assertEquals(GameModel.ModelState.LIVE, helperTester.noMotionDuringTime(0.4f));

        assertEquals(tester.ballXPosition(), helperTester.ballXPosition(), deltaError);
        assertEquals(tester.ballYPosition(), helperTester.ballYPosition(), deltaError);

        //Ball 2 dunks
        float[] dunk_moment = { tester.ballXPosition(), tester.ballYPosition() };
        tester.noMotionDuringTime(0.5f);
        helperTester.ballJump();
        helperTester.noMotionDuringTime(0.5f);

        assertTrue(tester.ballYPosition() > helperTester.ballYPosition() + 5);
        assertTrue(tester.ballYPosition() > dunk_moment[1] + 2);
        assertTrue(helperTester.ballYPosition() < dunk_moment[1] - 2);
        assertEquals(tester.ballXPosition(), helperTester.ballXPosition(), deltaError);

        //Balls will be in the initial position, after rebounding
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(5));
        assertEquals(GameModel.ModelState.LIVE, helperTester.noMotionDuringTime(5));
        assertEquals(tester.ballXPosition(), helperTester.ballXPosition(), deltaError);
        assertEquals(tester.ballYPosition(), helperTester.ballYPosition(), deltaError);
    }

    @Test
    public void diagonalJumpTest()
    {
        assertEquals(GameModel.ModelState.LIVE, tester.rotateRightDuringTime(0.5f));

        //Ball position before jump
        float[] init_pos = {tester.ballXPosition(), tester.ballYPosition()};

        //Jumped
        tester.ballJump();
        //Giving time for the ball to elevate
        tester.noMotionDuringTime(0.5f);

        assertTrue(init_pos[0] + 3 < tester.ballXPosition());
        assertTrue(init_pos[1] + 3 < tester.ballYPosition());

        //Checking the ball is still going up - but losing velocity (Vertical component)
        float[] other_pos = {tester.ballXPosition(), tester.ballYPosition()};
        tester.noMotionDuringTime(0.5f);

        assertTrue(other_pos[0] + 3 < tester.ballXPosition());
        assertFalse(other_pos[1] + 3 < tester.ballYPosition()); //Not as fast
        assertTrue(other_pos[1] + 1 < tester.ballYPosition());
    }

    GameTester anotherTester = new GameTester("maps/testmap1.tmx");

    @Test
    public void surroundedByWallsTest()
    {
        //Initial Ball position
        float[] init_pos = {anotherTester.ballXPosition(), anotherTester.ballYPosition()};

        //Rotating Right
        assertEquals(GameModel.ModelState.LIVE, anotherTester.rotateRightDuringTime(5));
        assertEquals(init_pos[0], anotherTester.ballXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.ballYPosition(), deltaError);

        //Rotating Left
        anotherTester.rotateLeftDuringTime(5);
        assertEquals(init_pos[0], anotherTester.ballXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.ballYPosition(), deltaError);

        ///Jumping and Dunking
        anotherTester.ballJump();
        anotherTester.noMotionDuringTime(0.5f);
        assertEquals(init_pos[0], anotherTester.ballXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.ballYPosition(), deltaError);

        anotherTester.ballJump();
        anotherTester.noMotionDuringTime(0.2f);
        anotherTester.ballJump();
        anotherTester.noMotionDuringTime(0.5f);
        anotherTester.ballJump();
        assertEquals(GameModel.ModelState.LIVE, anotherTester.noMotionDuringTime(0.1f));
        assertEquals(init_pos[0], anotherTester.ballXPosition(), deltaError);
        assertEquals(init_pos[1], anotherTester.ballYPosition(), deltaError);
    }
}