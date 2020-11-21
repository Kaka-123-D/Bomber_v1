package Game;

import Entities.Enemy.Balloon;
import Entities.Enemy.Oneal;
import Entities.Entity;
import Entities.Mono.*;

import Entities.Player.Bomb;
import Graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Play extends Application {

    private GraphicsContext gc;
    private Canvas canvas;
    String fileMap = "res/levels/Level1.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Board board = new Board(fileMap);

        canvas = new Canvas(Sprite.SCALED_SIZE * board.getColumn(), Sprite.SCALED_SIZE * board.getRow());
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        KeyControl control = new KeyControl(board, scene);
        control.catchEvent();

        stage.setScene(control.scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (board.bomberMan != null) {
                    board.update();
                    if (board.bomberMan.imasu) {
                        control.xuLi();
                        board.bomberMan.updateEvent(control.xControl, control.yControl, control.imgPlayer);
                    }
                    board.render(gc, canvas);
                }
            }
        };

        timer.start();
    }
}
