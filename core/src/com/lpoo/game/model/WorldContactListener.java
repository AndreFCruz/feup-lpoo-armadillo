package com.lpoo.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lpoo.game.model.controllers.BuoyancyController;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.game.model.entities.EntityModel.BALL_BIT;
import static com.lpoo.game.model.entities.EntityModel.FLUID_BIT;
import static com.lpoo.game.model.entities.EntityModel.GROUND_BIT;

/**
 * Created by andre on 04/05/2017.
 */

public class WorldContactListener implements ContactListener {
    private interface ContactHandler {
        void handle(Fixture fixA, Fixture fixB);
    }

    // Map of categoryBits to functions
    Map<Short, ContactHandler> beginContactFunctions = new HashMap<Short, ContactHandler>();
    Map<Short, ContactHandler> endContactFunctions = new HashMap<Short, ContactHandler>();

    public WorldContactListener() {
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

        beginContactFunctions.put(FLUID_BIT, new ContactHandler() {
            @Override
            public void handle(Fixture fluid, Fixture fixB) {
                if (fixB.getFilterData().categoryBits == BALL_BIT)
                    GameModel.getInstance().startLevel();
                else
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

    private void ballBeginContact(Fixture ball, Fixture other) {
        switch (other.getFilterData().categoryBits) {
            case GROUND_BIT:
//                ((BallModel) ball.getUserData()).setState(BallModel.State.LANDED);
                GameModel.getInstance().getBall().setState(BallModel.State.LANDED);
                break;
        }
    }

    private void ballEndContact(Fixture ball, Fixture other) {
        switch (other.getFilterData().categoryBits) {
            case GROUND_BIT:
//                ((BallModel) ball.getUserData()).setState(BallModel.State.FLYING);
                GameModel.getInstance().getBall().setState(BallModel.State.FLYING);
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
