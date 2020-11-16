package Game;

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

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        Board board = createMapFromFile();
        KeyControl control = new KeyControl(board, scene);
        control.catchEvent();

        stage.setScene(control.scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                board.update();
                control.xuLi();
                board.bomberMan.updateEvent(control.xControl, control.yControl, control.img);
                board.render(gc, canvas);
            }
        };

        timer.start();
    }

    public Board createMapFromFile() {
        Board board = new Board();

        File file = new File("res/levels/Level1.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int count = 0;
        String str = "";
        Entity object = null;
        while (scanner.hasNextLine()) {
            str = scanner.nextLine();
            count++;
            if (count == 1) continue;
            else if (count == 15) break;
            else {
                for (int k = 0; k < str.length(); k++) {
                    char c = str.charAt(k);
                    switch (c) {
                        case '#':
                            object = new Wall(k, count - 2, Sprite.wall.getFxImage());
                            break;
                        case ' ':
                            object = new Grass(k, count - 2, Sprite.grass.getFxImage());
                            break;
                        case '*':
                            object = new Brick(k, count - 2, Sprite.brick.getFxImage());
                            break;
                        case 'x':
                            object = new Portal(k, count - 2, Sprite.portal.getFxImage());
                            board.entityList.add(object);
                            object = new Brick(k, count - 2, Sprite.brick.getFxImage());
                            break;
                        default:
                            object = new Grass(k, count - 2, Sprite.grass.getFxImage());
                            break;
                    }
                    board.entityList.add(object);
                }
            }
        }
        return board;
    }
}
