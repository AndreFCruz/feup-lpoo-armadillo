package com.lpoo.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lpoo.game.GameServices;
import com.lpoo.game.Armadillo;
import com.lpoo.game.model.controllers.BuoyancyController;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.model.entities.Hittable;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.game.model.entities.EntityModel.BALL_BIT;
import static com.lpoo.game.model.entities.EntityModel.FLUID_BIT;
import static com.lpoo.game.model.entities.EntityModel.GROUND_BIT;
import static com.lpoo.game.model.entities.EntityModel.HITTABLE_BIT;


/**
 * Class responsible for handling all the contacts happening in the game's world.
 */
public class WorldContactListener implements ContactListener {

    /**
     * Interface for Contact Handlers.
     */
    private interface ContactHandler {
        void handle(Fixture fixA, Fixture fixB);
    }

    /**
     * Map of categoryBits to functions, for when the contact begins.
     */
    private Map<Short, ContactHandler> beginContactFunctions = new HashMap<>();
    /**
     * Map of categoryBits to functions, for when the contact ends.
     */
    private Map<Short, ContactHandler> endContactFunctions = new HashMap<>();

    /**
     * The GameModel of the current game being played by the User.
     */
    private final GameModel model;

    /**
     * World Contact Listener's constructor.
     * It adds contact handlers for the game elements.
     *
     * @param model The GameModel of the current game being played by the User.
     */
    WorldContactListener(final GameModel model) {
        this.model = model;

        addBallContactHandlers();
        addFluidContactHandlers();
        addHittableContactHandlers();
    }

    /**
     * Adds a fluids contact handlers to the game elements.
     */
    private void addFluidContactHandlers() {
        beginContactFunctions.put(FLUID_BIT, new ContactHandler() {
            @Override
            public void handle(Fixture fluid, Fixture fixB) {
                if (fixB.getFilterData().categoryBits == BALL_BIT) {
                    model.setLost();
                    logWaterAchievement();
                } else
                    ((BuoyancyController) fluid.getBody().getUserData()).addBody(fixB);
            }
        });
        endContactFunctions.put(FLUID_BIT, new ContactHandler() {
            @Override
            public void handle(Fixture fluid, Fixture fixB) {
                ((BuoyancyController) fluid.getBody().getUserData()).removeBody(fixB);
            }
        });
    }

    /**
     * Increments Water Achievement if possible.
     */
    private void logWaterAchievement() {
        try {
            GameServices gameServices = ((Armadillo) (Gdx.app.getApplicationListener())).getGameServices();
            gameServices.unlockAchievement(gameServices.getWaterAchievementID());
        } catch (java.lang.ClassCastException e) {
            System.err.println("Application listener not of type game."); }
    }

    /**
     * Adds Ball Contact Handlers to the game elements.
     */
    private void addBallContactHandlers() {
        beginContactFunctions.put(BALL_BIT, new ContactHandler() {
            @Override
            public void handle(Fixture ball, Fixture fixB) {
                ballBeginContact(ball, fixB);
            }
        });
        endContactFunctions.put(BALL_BIT, new ContactHandler() {
            @Override
            public void handle(Fixture ball, Fixture fixB) {
                ballEndContact(ball, fixB);
            }
        });
    }

    /**
     * Adds hittable contact handlers to all the hittable bodies.
     */
    private void addHittableContactHandlers() {
        beginContactFunctions.put(HITTABLE_BIT, new ContactHandler() {
            @Override
            public void handle(Fixture fixA, Fixture fixB) {
                if (!(fixB.getBody().getUserData() instanceof EntityModel))
                    return;

                ((Hittable) fixA.getBody().getUserData()).onHit((BallModel) fixB.getBody().getUserData());
            }
        });
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        ContactHandler handler;
        handler = beginContactFunctions.get(fixA.getFilterData().categoryBits);
        if (handler != null)
            handler.handle(fixA, fixB);

        handler = beginContactFunctions.get(fixB.getFilterData().categoryBits);
        if (handler != null)
            handler.handle(fixB, fixA);

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        ContactHandler handler;
        handler = endContactFunctions.get(fixA.getFilterData().categoryBits);
        if (handler != null)
            handler.handle(fixA, fixB);

        handler = endContactFunctions.get(fixB.getFilterData().categoryBits);
        if (handler != null)
            handler.handle(fixB, fixA);

    }

    /**
     * Setting the Ball's state upon begin contact with specific fixtures.
     *
     * @param ball  The ball's fixture.
     * @param other The fixture the ball begins contact with
     */
    private void ballBeginContact(Fixture ball, Fixture other) {
        switch (other.getFilterData().categoryBits) {
            case GROUND_BIT:
                ((BallModel) ball.getBody().getUserData()).setState(BallModel.State.LANDED);
                break;
        }
    }

    /**
     * Setting the Ball's state upon ending contact with specific fixtures.
     *
     * @param ball  The ball's fixture.
     * @param other The fixture the ball ends contact with
     */
    private void ballEndContact(Fixture ball, Fixture other) {
        switch (other.getFilterData().categoryBits) {
            case GROUND_BIT:
                ((BallModel) ball.getBody().getUserData()).setState(BallModel.State.FLYING);
                break;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
