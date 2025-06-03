package com.htu.tankgame;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class CCollider extends Group {
    private final boolean DEBUG_MODE = true;  // global switch

    private final String name;
    private final Rectangle shape;

    public CCollider(String name, Rectangle shape) {
        this.name = name;
        this.shape = shape;
        applyDebugStyle(DEBUG_MODE);
        this.getChildren().add(shape);
    }

    public String getName() {
        return name;
    }

    public void setPosition(double x, double y)
    {
        this.shape.setX(x);
        this.shape.setY(y);
    }

    public boolean intersects(@NotNull CCollider other) {
        return this.shape.localToScene(this.shape.getBoundsInLocal())
                .intersects(other.shape.localToScene(other.shape.getBoundsInLocal()));
    }


//    public String collidesWith(Iterable<CCollider> others) {
//        for (CCollider other : others) {
//            if (this != other && this.intersects(other)) {
//                return other.getTag(); // return the tag (name) of the collider hit
//            }
//        }
//        return null; // no collision
//    }


    private void applyDebugStyle(boolean debug) {
        if (debug) {
            shape.setStroke(Color.RED);
            shape.setStrokeWidth(2);
            shape.setFill(Color.TRANSPARENT);
        } else {
            shape.setStroke(null);
            shape.setFill(null);
        }
    }
    public Node getNode() {
        return shape;
    }

    public String getTag()
    {
        return this.name;
    }
}

