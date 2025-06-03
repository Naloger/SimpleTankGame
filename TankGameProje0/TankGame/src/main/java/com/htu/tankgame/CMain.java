package com.htu.tankgame;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CMain extends Application {
    @Override
    public void start(Stage primaryStage)
    {

        Group grp = new Group();

        ImageView tankIm = new ImageView("C:\\CalismaAlani\\CodingJava\\EmreninProje0\\TankGame\\src\\main\\resources\\com\\htu\\tankgame\\whiteTank1.png");
        ImageView tankIm2 = new ImageView("C:\\CalismaAlani\\CodingJava\\EmreninProje0\\TankGame\\src\\main\\resources\\com\\htu\\tankgame\\whiteTank2.png");
        ImageView tankEn = new ImageView("C:\\CalismaAlani\\CodingJava\\EmreninProje0\\TankGame\\src\\main\\resources\\com\\htu\\tankgame\\yellowTank1.png");
        ImageView tankEn2 = new ImageView("C:\\CalismaAlani\\CodingJava\\EmreninProje0\\TankGame\\src\\main\\resources\\com\\htu\\tankgame\\yellowTank2.png");


        CPlayer player = new CPlayer(new CTank(tankIm,tankIm2));
        //CEnemy enemy = new CEnemy(new CTank(tankEn,tankEn2));

        CGameLoop gameLoop = new CGameLoop(grp, player);
        CEnemySpawner spawner = new CEnemySpawner(grp,gameLoop );

        //gameLoop.addEnemy(enemy);
        gameLoop.start();


        Scene scene = new Scene(grp, 800, 600, Color.BLACK); // Size: 800x600


        // Input eventi burada bağlanmalı
        scene.setOnMouseMoved(e -> player.onMouseMoved(e.getX(), e.getY()));
        scene.setOnKeyPressed(player::onKeyPressed);
        scene.setOnKeyReleased(player::onKeyReleased);

        primaryStage.setTitle("TankGame");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }}