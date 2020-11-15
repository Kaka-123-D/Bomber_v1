package Game;

import Entities.Entity;
import Entities.Item.BombItem;
import Entities.Mono.*;
import Entities.Enemy.*;

import Entities.Player.BomberMan;
import Graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Play extends Application {

    public static final int WIDTH = 31;//20
    public static final int HEIGHT = 13;//15

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };

        timer.start();

        createMono();
        // createCharacter();
    }

    public void createMono() {
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
        Entity objectE = null;
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
                            object = new Wall(count - 2, k, Sprite.wall.getFxImage());
                            break;
                        case ' ':
                            object = new Grass(count - 2, k, Sprite.grass.getFxImage());
                            break;
                        case '*':
                            object = new Brick(count - 2, k, Sprite.brick.getFxImage());
                            break;
                        case 'x':
                            object = new Portal(count - 2, k, Sprite.portal.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(count - 2, k, Sprite.brick.getFxImage());
                            break;
                        case 'p':
                            objectE = new BomberMan(count - 2, k, Sprite.player_right.getFxImage());
                            object = new Grass(count - 2, k, Sprite.grass.getFxImage());
                            break;
                        case '1':
                            objectE = new Balloon(count - 2, k, Sprite.balloom_right1.getFxImage());
                            object = new Grass(count - 2, k, Sprite.grass.getFxImage());
                            break;
                        case '2':
                            objectE = new Oneal(count - 2, k, Sprite.oneal_right1.getFxImage());
                            object = new Grass(count - 2, k, Sprite.grass.getFxImage());
                            break;
                        default:
                            object = new BombItem(count - 2, k, Sprite.bomb_1.getFxImage());
                    }
                    if (object != null) stillObjects.add(object);
                    object = null;
                    if (objectE != null) entities.add(objectE);
                    objectE = null;
                }
            }
        }

    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
