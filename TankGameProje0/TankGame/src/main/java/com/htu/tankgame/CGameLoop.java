package com.htu.tankgame;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CGameLoop extends AnimationTimer {
    private final Group root;
    private final CPlayer player;
    private final List<CEnemy> enemies = new ArrayList<>();
    private Text scoreT = new Text(100,10,"Score : "+CEnemy.score);

    private long lastTime = 0;

    public CGameLoop(Group root, CPlayer player) {
        this.root = root;
        this.player = player;

        scoreT.setFill(Color.WHITE);

        this.root.getChildren().addAll(player,scoreT);
    }

    public void addEnemy(CEnemy enemy) {
        enemies.add(enemy);
        root.getChildren().add(enemy);
    }

@Override
public void handle(long now) {
    if (lastTime == 0) {
        lastTime = now;
        return;
    }

    double dt = (now - lastTime) / 1_000_000_000.0;

    player.update(dt);

    Point2D playerPos = player.getWorldPosition();

    for (CEnemy enemy : enemies) {
        enemy.update(dt, playerPos);

        // Update enemy projectiles
        List<CProjectile> enemyProjectiles = enemy.getProjList();
        enemyProjectiles.forEach(proj -> proj.update(dt));

        // Remove dead enemy projectiles
        enemyProjectiles.removeIf(proj -> {
            if (proj.isDead()) {
                root.getChildren().remove(proj);
                return true;
            }
            return false;
        });
    }

    // Update player projectiles
    player.projList.forEach(proj -> proj.update(dt));

    // Remove dead player projectiles
    player.projList.removeIf(proj -> {
        if (proj.isDead()) {
            root.getChildren().remove(proj);
            return true;
        }
        return false;
    });

    // Check player projectiles hitting enemies
    for (CProjectile projectile : player.projList) {
        for (CEnemy enemy : enemies) {
            if (projectile.getCollider().intersects(enemy.getEnemyCollider())) {
                System.out.println("HIT!");
                System.out.println("Score : "+CEnemy.score);
                scoreT.setText("Score : "+CEnemy.score);
                enemy.kill();
                projectile.markAsDead();
                break;
            }
        }
    }

    // Check enemy projectiles hitting player
    for (CEnemy enemy : enemies) {
        for (CProjectile projectile : enemy.getProjList()) {
            if (projectile.getCollider().intersects(player.getCollider())) {
                System.out.println("PLAYER HIT!");
                player.takeDamage();  // You need to implement this in your player
                projectile.markAsDead();
                break;
            }
        }
    }
    // Remove dead enemies from scene and list
    enemies.removeIf(enemy -> {
        if (enemy.isDead()) {
            root.getChildren().remove(enemy);
            return true;
        }
        return false;
    });

    lastTime = now;
}

}
