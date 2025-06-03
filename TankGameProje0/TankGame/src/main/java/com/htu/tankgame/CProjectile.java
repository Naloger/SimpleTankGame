package com.htu.tankgame;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CProjectile extends Group {
    private double timeAlive = 0.0;
    private boolean isDead = false;
    private final CCollider collider;
    private double speed;
    public Point2D direction = new Point2D(1, 0);

    public CProjectile()
    {
        Rectangle collShape = new Rectangle(0,0,15,15);
        this.collider = new CCollider("projectile",collShape);
        this.speed = 200;
        ImageView sprite = new ImageView("C:\\CalismaAlani\\CodingJava\\EmreninProje0\\TankGame\\src\\main\\resources\\com\\htu\\tankgame\\bullet.png");

        double projX = sprite.getTranslateX();
        double projY = sprite.getTranslateY();

        this.getChildren().addAll(sprite,collider);  // <--1
    }

    public void setPosition(double x, double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

//    public void setDirection(Point2D dir) {
//        this.direction = dir.normalize();
//    }
    public void update(double dt) {
        if (isDead) return;

        double dx = direction.getX() * speed * dt;
        double dy = direction.getY() * speed * dt;
        this.setTranslateX(this.getTranslateX() + dx);
        this.setTranslateY(this.getTranslateY() + dy);

        timeAlive += dt;
        if (timeAlive >= 8.0) {
            markAsDead();
        }

    }

    public CCollider getCollider()
    {
        return this.collider;
    }

    public void markAsDead() {
        this.isDead = true;
        this.setVisible(false);  // hide immediately
    }

    public boolean isDead() {
        return isDead;
    }

//    public void initialize(Point2D position, Point2D direction) {
//        setPosition(position.getX(), position.getY());
//        setDirection(direction); // this sets the angle
//    }

    public void setDirection(Point2D dir) {
        this.direction = dir.normalize();
        double angle = Math.toDegrees(Math.atan2(direction.getY(), direction.getX()));
        this.setRotate(angle); // rotate the projectile group
    }

}

