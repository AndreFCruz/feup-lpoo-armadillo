package com.lpoo.game.test;

import com.lpoo.game.model.GameModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edgar on 03/06/2017.
 */

public class gameLogicTest extends GameTest{

    GameTester tester = new GameTester("maps/testmap0.tmx");

    float deltaError = 0.08f;

    @Test
    public void loadMapTest()
    {
        GameTester loadMap1 = new GameTester("maps/testmap3.tmx");
        GameTester loadMap2 = new GameTester("maps/map2.tmx");

        //No errors in both maps loading
        assertEquals(GameModel.ModelState.LIVE, loadMap1.noMotionDuringTime(2));
        assertEquals(GameModel.ModelState.LIVE, loadMap2.noMotionDuringTime(2));

        //TODO: Teste com mapa mau  a provar que da erro (?)
    }

    @Test
    public void scoreUpdateTest()
    {
        assertEquals(0f, tester.runTime(), deltaError);
        tester.noMotionDuringTime(5);

        assertEquals(5f, tester.runTime(), deltaError);
        tester.rotateLeftDuringTime(2);
        tester.noMotionDuringTime(4);

        assertEquals(11f, tester.runTime(), deltaError);
    }

    @Test
    public void winGameTest()
    {
        assertEquals(GameModel.ModelState.LIVE, tester.noMotionDuringTime(1));
        assertEquals(GameModel.ModelState.WON, tester.rotateRightDuringTime(2));

        float[] final_pos = { tester.ballXPosition(), tester.ballYPosition() };

        //After WON Position ball doesn't move
        assertEquals(GameModel.ModelState.WON, tester.rotateLeftDuringTime(3));
        tester.ballJump();
        assertEquals(GameModel.ModelState.WON, tester.rotateLeftDuringTime(2));
        assertEquals(final_pos[0], tester.ballXPosition(), deltaError);
        assertEquals(final_pos[1], tester.ballYPosition(), deltaError);
    }

    GameTester otherTester = new GameTester("maps/testmap2.tmx");

    @Test
    public void loseGameByFallTest()
    {
        assertEquals(GameModel.ModelState.LIVE, otherTester.noMotionDuringTime(1));
        float[] init_pos = { otherTester.ballXPosition(), otherTester.ballYPosition() };

        //Ball will fall and game is lost
        assertEquals(GameModel.ModelState.LIVE, otherTester.rotateRightDuringTime(8));
        assertEquals(GameModel.ModelState.LOST, otherTester.noMotionDuringTime(3)); //Ball falling
        assertTrue(init_pos[1] - 5 > otherTester.ballYPosition());

        float[] final_pos = { otherTester.ballXPosition(), otherTester.ballYPosition() };

        //After LOST Position ball doesn't move
        assertEquals(GameModel.ModelState.LOST, otherTester.rotateRightDuringTime(3));
        otherTester.ballJump();
        assertEquals(GameModel.ModelState.LOST, otherTester.rotateRightDuringTime(2));
        assertEquals(final_pos[0], otherTester.ballXPosition(), deltaError);
        assertEquals(final_pos[1], otherTester.ballYPosition(), deltaError);
    }

    @Test
    public void loseGameByWaterTest()
    {
        assertEquals(GameModel.ModelState.LIVE, otherTester.noMotionDuringTime(1));

        //Ball will touch Water and game is lost
        assertEquals(GameModel.ModelState.LOST, otherTester.rotateLeftDuringTime(5));

        float[] final_pos = { otherTester.ballXPosition(), otherTester.ballYPosition() };

        //After LOST Position ball doesn't move
        assertEquals(GameModel.ModelState.LOST, otherTester.rotateRightDuringTime(3));
        tester.ballJump();
        assertEquals(GameModel.ModelState.LOST, otherTester.rotateRightDuringTime(2));
        assertEquals(final_pos[0], otherTester.ballXPosition(), deltaError);
        assertEquals(final_pos[1], otherTester.ballYPosition(), deltaError);
    }
}
