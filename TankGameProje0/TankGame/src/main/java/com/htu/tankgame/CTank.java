package com.htu.tankgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CTank extends Group {

    private static final boolean DEBUG_MODE = true;

    private final ImageView sprite;
    private final ImageView sprite2;
    private final CCollider collider;

    private final Group bodyGroup;
    private final Point2D origin = new Point2D(0, 0);
    private final Point2D projectilePivotOffset = new Point2D(30, 15);

    private final Circle pivotMarker;

    private Point2D dir;
    private boolean movingState;
    private boolean showingFirstSprite = true;

    private final Timeline spriteSwapTimeline;


    public CTank(ImageView sprite, ImageView sprite2) {
        this.sprite = sprite;
        this.sprite2 = sprite2;

        sprite.setVisible(true);   // <-- Add this
        sprite2.setVisible(false); // Hide second initially

        // Collider
        Rectangle collShape = new Rectangle(0, 0, 30, 30);
        this.collider = new CCollider("tank", collShape);

        // Visuals
        this.bodyGroup = new Group();
//        sprite.setTranslateX(0);
//        sprite.setTranslateY(0);
//        sprite2.setTranslateX(0);
//        sprite2.setTranslateY(0);

//
//        collider.setTranslateX(0);
//        collider.setTranslateY(0);



        bodyGroup.getChildren().addAll(sprite, sprite2, collider);
        bodyGroup.setTranslateX(-origin.getX());
        bodyGroup.setTranslateY(-origin.getY());
        this.getChildren().add(bodyGroup);

        // Direction
        this.dir = new Point2D(1, 0);

        // Pivot marker
        pivotMarker = new Circle(5, Color.RED);
        pivotMarker.setMouseTransparent(true);
        this.getChildren().add(pivotMarker);
        updatePivotMarker();

        // Sprite swap animation
        this.spriteSwapTimeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
            if (movingState) {
                swapSprites();
            } else {
                resetSprite();
            }
        }));
        spriteSwapTimeline.setCycleCount(Timeline.INDEFINITE);
        spriteSwapTimeline.play();
    }
    public CTank() {
        this(
                new ImageView("C:\\CalismaAlani\\CodingJava\\EmreninProje0\\TankGame\\src\\main\\resources\\com\\htu\\tankgame\\yellowTank1.png"),
                new ImageView("C:\\CalismaAlani\\CodingJava\\EmreninProje0\\TankGame\\src\\main\\resources\\com\\htu\\tankgame\\yellowTank2.png")
        );
    }



    public void setPosition(double x, double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void setMovingState(boolean movingState) {
        this.movingState = movingState;
    }

    public void lookAt(double mouseX, double mouseY) {
        Point2D tankWorldPos = new Point2D(getTranslateX(), getTranslateY());
        this.dir = new Point2D(mouseX - tankWorldPos.getX(), mouseY - tankWorldPos.getY()).normalize();
        updateRotation();
    }

    public void updateRotation() {
        double angle = Math.toDegrees(Math.atan2(dir.getY(), dir.getX()));
        this.setRotate(angle);
    }

    private void updatePivotMarker() {
        double angleRad = Math.toRadians(this.getRotate());
        double sin = Math.sin(angleRad);
        double cos = Math.cos(angleRad);

        double relX = projectilePivotOffset.getX();
        double relY = projectilePivotOffset.getY();

        double rotatedX = -(relX * sin);
        double rotatedY = (relY * cos);

        pivotMarker.setCenterX(rotatedX + 40);  // adjust if offset needed
        pivotMarker.setCenterY(rotatedY);
    }

    private void swapSprites() {
        sprite.setVisible(!showingFirstSprite);
        sprite2.setVisible(showingFirstSprite);
        showingFirstSprite = !showingFirstSprite;
    }

    private void resetSprite() {
        sprite.setVisible(true);
        sprite2.setVisible(false);
        showingFirstSprite = true;
    }

    public Point2D getDirectionVector() {
        return dir;
    }

    public Point2D getWorldPivotPosition() {
        return this.localToScene(pivotMarker.getCenterX(), pivotMarker.getCenterY());
    }

    public Point2D getPivotInParentCoords() {
        return this.localToParent(pivotMarker.getCenterX(), pivotMarker.getCenterY());
    }

    public void update(double dt) {
        // Optional movement logic
    }

    public CCollider getCollider() {
        return this.collider;
    }
}
