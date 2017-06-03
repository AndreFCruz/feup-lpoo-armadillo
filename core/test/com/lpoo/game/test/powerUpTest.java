package com.lpoo.game.test;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edgar on 03/06/2017.
 */

public class powerUpTest {

    GameTester tester = new GameTester("maps/testmap3.tmx");

    float deltaError = 0.08f;

    @Test
    public void jumpPowerUpTest()
    {
        float initialJumpPower = tester.ballJumpPower();
        tester.rotateRightDuringTime(0.95f);

        assertTrue(initialJumpPower < tester.ballJumpPower());
        assertEquals(initialJumpPower * 1.2, tester.ballJumpPower(), deltaError);

        //For ball to stabilize
        tester.noMotionDuringTime(0.8f);

        //Jumping for confirmation
        GameTester helper = new GameTester("maps/testmap3.tmx");
        tester.ballJump();
        helper.ballJump();
        tester.noMotionDuringTime(1);
        helper.noMotionDuringTime(1);

        assertTrue(tester.ballYPosition() > helper.ballYPosition());
        //deltaError * 3 because of multiplication errors
        assertEquals(helper.ballYPosition() * 1.2, tester.ballYPosition(), deltaError * 3);
    }

    @Test
    public void densityPowerUpTest()
    {
        /*
        //Falta este power up? So aparece no random
        float initialDensity = tester.ballDensity();
        tester.rotateRightDuringTime(0.95f);

        System.out.print(initialDensity < tester.ballDensity());

        assertTrue(initialDensity < tester.ballDensity());
        assertEquals(initialDensity * 1.2, tester.ballDensity(), deltaError);*/
    }
    @Test
    public void velocityPowerUpTest()
    {

    }

    @Test
    public void gravityPowerUpTest()
    {

    }

    //TODO: Fazer como fazia no ogre, até gerar os 4? Com recurso a booleanos
    @Test
    public void randomPowerUpTest()
    {

    }

}
