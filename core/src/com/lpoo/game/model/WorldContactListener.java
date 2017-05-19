package com.lpoo.game.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lpoo.game.model.controllers.BuoyancyController;
import com.lpoo.game.model.entities.BallModel;
import com.lpoo.game.model.entities.EntityModel;

/**
 * Created by andre on 04/05/2017.
 */

public class WorldContactListener implements ContactListener {
    // TODO
    // Map of categoryBits to functions

    private BuoyancyController buoyancyController;

    public WorldContactListener(BuoyancyController buoyancyController) {
        this.buoyancyController = buoyancyController;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getFilterData().categoryBits == EntityModel.BALL_BIT)
            ballBeginContact(fixA, fixB);
        else if (fixB.getFilterData().categoryBits == EntityModel.BALL_BIT)
            ballBeginContact(fixB, fixA);

        // Draft of collision detection with fluid sensor.
        // Assumes only one body of water and that it is the only sensor
        if (fixA.isSensor() && fixA.getFilterData().categoryBits == EntityModel.FLUID_BIT && fixB.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            System.err.println("-- in water --");
            buoyancyController.addBody(fixB);
        } else if (fixB.isSensor() && fixB.getFilterData().categoryBits == EntityModel.FLUID_BIT && fixA.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            System.err.println("-- in water --");
            buoyancyController.addBody(fixA);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getFilterData().categoryBits == EntityModel.BALL_BIT)
            ballEndContact(fixA, fixB);
        else if (fixB.getFilterData().categoryBits == EntityModel.BALL_BIT)
            ballEndContact(fixB, fixA);

        if (fixA.isSensor() && fixA.getFilterData().categoryBits == EntityModel.FLUID_BIT && fixB.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            System.err.println("-- out of water --");
            buoyancyController.removeBody(fixB);
        } else if (fixB.isSensor() && fixB.getFilterData().categoryBits == EntityModel.FLUID_BIT && fixA.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            System.err.println("-- out of water --");
            buoyancyController.removeBody(fixA);
        }
    }

    private void ballBeginContact(Fixture ball, Fixture other) {
        switch (other.getFilterData().categoryBits) {
            case EntityModel.GROUND_BIT:
//                ((BallModel) ball.getUserData()).setState(BallModel.State.LANDED);
                GameModel.getInstance().getBall().setState(BallModel.State.LANDED);
                break;
        }
    }

    private void ballEndContact(Fixture ball, Fixture other) {
        switch (other.getFilterData().categoryBits) {
            case EntityModel.GROUND_BIT:
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
