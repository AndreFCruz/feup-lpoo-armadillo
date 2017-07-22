package com.lpoo.game.test;

import com.lpoo.game.model.GameModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.ShapeModel;

import org.junit.Test;

import static com.lpoo.game.model.GameModel.ModelState.LIVE;
import static com.lpoo.game.model.GameModel.ModelState.LOST;
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
        assertEquals(LIVE, loadMap1.noMotionDuringTime(2));
        assertEquals(LIVE, loadMap2.noMotionDuringTime(2));

        //Testing several Model Components
        assertNotNull(loadMap1.model.getBallModel());
        assertNotNull(loadMap1.model.getWorld());
        assertNotNull(loadMap1.model.getMap());
        assertNotNull(loadMap1.model.getEntityModels());

        for (int i = 0; i < loadMap2.model.getShapeModels().size; ++i) {
            ShapeModel shape = loadMap2.model.getShapeModels().get(i);
            assertNotNull(shape.getType());
            assertNotNull(shape.getShape());
            assertTrue(-360 <= shape.getAngle());
            assertTrue(shape.getAngle() <= 360);
            assertNotNull(shape.getX());
            assertNotNull(shape.getY());
        }

        for (int i = 0; i < loadMap2.model.getEntityModels().size; ++i) {
            EntityModel entity = loadMap2.model.getEntityModels().get(i);
            assertTrue(-360 <= entity.getAngle());
            assertTrue(entity.getAngle() <= 360);
            assertTrue(entity.getType() == EntityModel.ModelType.BALL ||
                       entity.getType() == EntityModel.ModelType.BOX ||
                       entity.getType() == EntityModel.ModelType.POWERUP_GRAVITY ||
                       entity.getType() == EntityModel.ModelType.POWERUP_JUMP ||
                       entity.getType() == EntityModel.ModelType.POWERUP_VELOCITY ||
                       entity.getType() == EntityModel.ModelType.POWERUP_RANDOM);
        }

        assertEquals(LIVE, loadMap1.model.getState());
        assertEquals(LIVE, loadMap2.model.getState());

        tester.model.dispose();

        //Testing Model detects bad maps
        assertTrue(GameTester.loadBadMap("maps/testmap8.tmx"));
    }

    @Test
    public void scoreUpdateTest()
    {
        assertEquals(0f, tester.getRunTime(), deltaError);
        tester.noMotionDuringTime(5);

        assertEquals(5f, tester.getRunTime(), deltaError);
        tester.rotateLeftDuringTime(2);
        tester.noMotionDuringTime(4);

        assertEquals(11f, tester.getRunTime(), deltaError);
    }

    @Test
    public void winGameTest()
    {
        assertEquals(LIVE, tester.noMotionDuringTime(1));
        assertEquals(GameModel.ModelState.WON, tester.rotateRightDuringTime(2));

        float[] final_pos = { tester.getBallXPosition(), tester.getBallYPosition() };

        //After WON Position ball doesn't move
        assertEquals(GameModel.ModelState.WON, tester.rotateLeftDuringTime(3));
        tester.ballJump();
        assertEquals(GameModel.ModelState.WON, tester.rotateLeftDuringTime(2));
        assertEquals(final_pos[0], tester.getBallXPosition(), deltaError);
        assertEquals(final_pos[1], tester.getBallYPosition(), deltaError);
    }

    GameTester otherTester = new GameTester("maps/testmap2.tmx");

    @Test
    public void loseGameByFallTest()
    {
        assertEquals(LIVE, otherTester.noMotionDuringTime(1));
        float[] init_pos = { otherTester.getBallXPosition(), otherTester.getBallYPosition() };

        //Ball will fall and game is lost
        assertEquals(LIVE, otherTester.rotateRightDuringTime(1));
        assertEquals(LIVE, otherTester.noMotionDuringTime(2));
        assertEquals(LOST, otherTester.rotateRightDuringTime(2)); //Ball falling
        assertTrue(init_pos[1] - 5 > otherTester.getBallYPosition());

        float[] final_pos = { otherTester.getBallXPosition(), otherTester.getBallYPosition() };

        //After LOST Position ball doesn't move
        assertEquals(LOST, otherTester.rotateRightDuringTime(3));
        otherTester.ballJump();
        assertEquals(LOST, otherTester.rotateRightDuringTime(2));
        assertEquals(final_pos[0], otherTester.getBallXPosition(), deltaError);
        assertEquals(final_pos[1], otherTester.getBallYPosition(), deltaError);
    }

    @Test
    public void loseGameByWaterTest()
    {
        assertEquals(LIVE, otherTester.noMotionDuringTime(1));

        //Ball will touch Water and game is lost
        assertEquals(LOST, otherTester.rotateLeftDuringTime(5));

        float[] final_pos = { otherTester.getBallXPosition(), otherTester.getBallYPosition() };

        //After LOST Position ball doesn't move
        assertEquals(LOST, otherTester.rotateRightDuringTime(3));
        tester.ballJump();
        assertEquals(LOST, otherTester.rotateRightDuringTime(2));
        assertEquals(final_pos[0], otherTester.getBallXPosition(), deltaError);
        assertEquals(final_pos[1], otherTester.getBallYPosition(), deltaError);
    }

    @Test
    public void pauseGameTest()
    {
        //Starting ball motion
        tester.rotateRightDuringTime(1);
        float xPosition = tester.getBallXPosition();

        //Pause Game, even if called, ball wont move
        tester.pauseGame();

        tester.noMotionDuringTime(3);
        assertEquals(xPosition, tester.getBallXPosition(), deltaError);

        //Game unpaused, ball will move
        tester.pauseGame();
        tester.noMotionDuringTime(3);

        assertNotEquals(xPosition, tester.getBallXPosition(), deltaError);
    }
}
