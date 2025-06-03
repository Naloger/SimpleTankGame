package com.htu.tankgame;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CPlayer extends Group{

    private double mouseX, mouseY;
    private final CTank tank;
    public  List<CProjectile> projList = new ArrayList<>();
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private final double speed = 100;

    public CPlayer(CTank tank) {
        this.tank = tank;
        this.getChildren().add(tank);
    }

    public void onKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    public void onKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
        if (pressedKeys.isEmpty()) {
            tank.setMovingState(false);
        }
    }


    public void update(double dt) {
        // Direction update
        tank.lookAt(mouseX, mouseY);
        Point2D movement = Point2D.ZERO;

        if (pressedKeys.contains(KeyCode.W)) {
            movement = movement.add(tank.getDirectionVector());
        }
        if (pressedKeys.contains(KeyCode.S)) {
            movement = movement.subtract(tank.getDirectionVector());
        }
        if (pressedKeys.contains(KeyCode.SPACE)) {
            fire();
        }

        if (!movement.equals(Point2D.ZERO)) {
            movement = movement.normalize().multiply(speed * dt);
            tank.setMovingState(true);
            tank.setPosition(
                    tank.getTranslateX() + movement.getX(),
                    tank.getTranslateY() + movement.getY()
            );
        }
    }
    public Point2D getWorldPosition() {
        return new Point2D(tank.getTranslateX(), tank.getTranslateY());
    }


    public void onMouseMoved(double x, double y) {
        this.mouseX = x;
        this.mouseY = y;
    }
    public void fire() {
        CProjectile proj = new CProjectile();
        Point2D spawn = tank.getWorldPivotPosition();
        proj.setPosition(spawn.getX(), spawn.getY());
        proj.setDirection(tank.getDirectionVector());
        this.getChildren().add(proj);
        projList.addLast(proj);
    }

    public CCollider getCollider()
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

    public void takeDamage()
    {
        System.out.println("-1 Health");
        tank.setPosition(0,0);
    }
    public CTank getTank() {
        return tank;
    }
}

