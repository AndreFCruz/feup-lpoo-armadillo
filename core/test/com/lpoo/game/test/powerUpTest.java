package com.lpoo.game.test;

import org.junit.Test;

import static com.lpoo.game.model.GameModel.ModelState.LIVE;
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
        float initialJumpPower = tester.getBallJumpPower();
        tester.rotateRightDuringTime(0.95f);

        assertTrue(initialJumpPower < tester.getBallJumpPower());
        assertEquals(initialJumpPower * 1.2, tester.getBallJumpPower(), deltaError);

        //For ball to stabilize
        tester.noMotionDuringTime(0.8f);

        //Jumping for confirmation
        GameTester helper = new GameTester("maps/testmap3.tmx");
        tester.ballJump();
        helper.ballJump();
        tester.noMotionDuringTime(1);
        helper.noMotionDuringTime(1);

        assertTrue(tester.getBallYPosition() + 5> helper.getBallYPosition());
    }

    @Test
    public void velocityPowerUpTest()
    {
        float initialAcceleration = tester.getBallAcceleration();
        tester.rotateRightDuringTime(1);
        //Stabilize ball
        tester.noMotionDuringTime(3);

        assertTrue(initialAcceleration < tester.getBallAcceleration());

        //For ball to stabilize
        assertEquals(LIVE, tester.noMotionDuringTime(0.8f));

        //Competing with other ball for confirmation
        GameTester helper = new GameTester("maps/testmap0.tmx");
        float helperInitialX = helper.getBallXPosition();
        float testerInitialX = tester.getBallXPosition();

        tester.rotateLeftDuringTime(2);
        helper.rotateLeftDuringTime(2);

        assertTrue(tester.getPowerUpRatio() * (helperInitialX - helper.getBallXPosition()) < testerInitialX - tester.getBallXPosition());
    }

    GameTester gravityTester = new GameTester("maps/testmap4.tmx");

    @Test
    public void gravityPowerUpTest()
    {
        float noGravityY = gravityTester.getBallYPosition();

        //Catching the Power Up and switching gravity
        gravityTester.rotateRightDuringTime(1);
        //Giving time for ball to stabilize
        gravityTester.noMotionDuringTime(3);

        assertTrue(noGravityY + 5 < gravityTester.getBallYPosition() );
        float gravityY = gravityTester.getBallYPosition();

        //Checking if ball Y changed
        gravityTester.noMotionDuringTime(5);
        assertEquals(gravityY, gravityTester.getBallYPosition(), deltaError);

        //Switching Gravity again
        gravityTester.rotateRightDuringTime(1);//, GameModel.ModelState.LIVE);
        //Stabilizing the ball
        gravityTester.noMotionDuringTime(3);

        assertTrue(gravityY - 5 > gravityTester.getBallYPosition());
    }

    @Test (timeout = 1000)
    public void randomPowerUpTest()
    {
        boolean jumpPowerUp = false, accelerationUp = false, densityUp = false, jumpPowerDown = false, accelerationDown = false, densityDown = false;

        while ( !jumpPowerUp || !jumpPowerDown || !accelerationUp || !accelerationDown || !densityUp || !densityDown) {

            GameTester randomTester = new GameTester("maps/testmap5.tmx");
            float initialAcceleration = randomTester.getBallAcceleration();
            float initialJumpPower = randomTester.getBallJumpPower();
            float initialDensity = randomTester.getBallDensity();

            randomTester.rotateRightDuringTime(0.5f);
            randomTester.noMotionDuringTime(1);

            if (initialJumpPower < randomTester.getBallJumpPower())
                jumpPowerUp = true;

            else if (initialJumpPower > randomTester.getBallJumpPower())
                jumpPowerDown = true;

            else if (initialAcceleration < randomTester.getBallAcceleration())
                accelerationUp = true;

            else if (initialAcceleration > randomTester.getBallAcceleration())
                accelerationDown = true;

            else if (initialDensity < randomTester.getBallDensity())
                densityUp = true;

            else if (initialDensity > randomTester.getBallDensity())
                densityDown = true;

            else
                fail ("Random Power Up's behaviour is unexpected!");

        }
    }

}
