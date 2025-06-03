package com.htu.tankgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.Random;

public class CEnemySpawner {
    private final Group root;
    private final CGameLoop gameLoop;
    private final Random random = new Random();

    private final double sceneWidth = 800;
    private final double sceneHeight = 600;

    public CEnemySpawner(Group root, CGameLoop gameLoop) {
        this.root = root;
        this.gameLoop = gameLoop;
        startSpawning();
    }

    private void startSpawning() {
        Timeline spawnTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> spawnEnemy()));
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnTimeline.play();
    }

    private void spawnEnemy() {
        // Random delay before next spawn
        int delay = 1 + random.nextInt(4); // 1 to 4 seconds
        Timeline delayTimeline = new Timeline(new KeyFrame(Duration.seconds(delay), e -> {
            double x = random.nextDouble() * (sceneWidth - 50);
            double y = random.nextDouble() * (sceneHeight - 50);

            CTank enemyTank = new CTank(); // assume default constructor
            enemyTank.setPosition(x, y);

            CEnemy enemy = new CEnemy(enemyTank);
            gameLoop.addEnemy(enemy);
        }));
        delayTimeline.play();
    }
}
