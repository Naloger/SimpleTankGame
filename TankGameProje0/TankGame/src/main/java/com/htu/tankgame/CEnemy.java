package com.htu.tankgame;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class CEnemy extends Group {

    public static int score = 0;
    private double timeSinceLastShot = 0.0;
    private final double fireInterval = 2.0;  // seconds between shots; you can randomize this if you want
    private boolean isDead = false;


    private final CTank tank;
    private final double speed = 60; // slower than player
    private final double detectionRadius = 200;

    private final List<CProjectile> projList = new ArrayList<>();

    public CEnemy(CTank tank) {
        this.tank = tank;
        this.getChildren().add(tank);
    }

    public void update(double dt, Point2D playerPos) {
        Point2D enemyPos = new Point2D(tank.getTranslateX(), tank.getTranslateY());
        double distanceToPlayer = enemyPos.distance(playerPos);

        if (distanceToPlayer < detectionRadius) {
            // Look at player
            tank.lookAt(playerPos.getX(), playerPos.getY());

            // Move towards player
            Point2D movement = tank.getDirectionVector()
                    .normalize()
                    .multiply(speed * dt);

            tank.setMovingState(true);
            tank.setPosition(
                    tank.getTranslateX() + movement.getX(),
                    tank.getTranslateY() + movement.getY()
            );

            // Fire at intervals if in range
            timeSinceLastShot += dt;
            if (timeSinceLastShot >= fireInterval) {
                fire();
                timeSinceLastShot = 0;
            }
        } else {
            tank.setMovingState(false);
            // Reset timer so enemy doesn't fire immediately once in range
            timeSinceLastShot = 0;
        }
    }


    public CCollider getEnemyCollider()
    {
    CCollider original = tank.getCollider();
    Rectangle bounds = new Rectangle(
            original.getNode().getBoundsInLocal().getWidth(),
            original.getNode().getBoundsInLocal().getHeight()
    );

    Point2D scenePos = original.getNode().localToScene(
            original.getNode().getBoundsInLocal().getMinX(),
            original.getNode().getBoundsInLocal().getMinY()
    );

    bounds.setX(scenePos.getX());
    bounds.setY(scenePos.getY());

    return new CCollider(original.getTag(), bounds);
}

    public List<CProjectile> getProjList() {
        return projList;
    }

    public void kill() {
        isDead = true;

        // Optional: stop movement
        tank.setMovingState(false);

        // Optional: hide or remove visuals
        this.setVisible(false);

        // Optional: clear any projectiles it owns
        projList.clear(); // assuming you have this list in your CEnemy

        // Optional: remove all children if you want to fully detach visuals
        this.getChildren().clear();
        score++;
    }

    public void fire() {
        CProjectile proj = new CProjectile();
        Point2D spawn = tank.getWorldPivotPosition();
        proj.setPosition(spawn.getX(), spawn.getY());
        proj.setDirection(tank.getDirectionVector());
        this.getChildren().add(proj);
        projList.addLast(proj);
    }
    //Used in CGameLoop
    public boolean isDead() {
        return isDead;
    }

    public CTank getTank() {
        return tank;
    }
}
