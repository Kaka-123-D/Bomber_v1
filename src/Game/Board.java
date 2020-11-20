package Game;

import Entities.AnimateEntity;
import Entities.Enemy.Balloon;
import Entities.Enemy.Oneal;
import Entities.Entity;
import Entities.Mono.Brick;
import Entities.Mono.Grass;
import Entities.Mono.Portal;
import Entities.Mono.Wall;
import Entities.Player.Bomb;
import Entities.Player.BomberMan;
import Graphics.Sprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLClientInfoException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Board {

    private int level;
    private int column;
    private int row;
    public List<Entity> entityList = new ArrayList<>();
    public List<AnimateEntity> enemies = new ArrayList<>();
    public boolean[][] checkMove;
    public BomberMan bomberMan = new BomberMan(1, 1, Sprite.player_right.getFxImage());

    public Board(String fileName) {

        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        level = scanner.nextInt();
        row = scanner.nextInt();
        column = scanner.nextInt();
        checkMove = new boolean[row * Sprite.SCALED_SIZE][column * Sprite.SCALED_SIZE];
        String str = scanner.nextLine();

        for (int i = 0; i < row; i++){
            str = scanner.nextLine();
            for (int j = 0; j < column; j++) {
                char c = str.charAt(j);
                switch (c) {
                    case '#':
                        entityList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        changeCheck(i, j);
                        break;
                    case '*':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        entityList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        changeCheck(i, j);
                        break;
                    case 'x':
                        entityList.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        entityList.add(new Brick(j, i, Sprite.wall.getFxImage()));
                        changeCheck(i, j);
                        break;
                    case '1':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemies.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
                        break;
                    case '2':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    default:
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                }
            }
        }

    }

    public void changeCheck(int r, int c) {
        for (int i = r * Sprite.SCALED_SIZE; i < (r + 1) * Sprite.SCALED_SIZE; i++) {
            for (int j = c * Sprite.SCALED_SIZE; j < (c + 1) * Sprite.SCALED_SIZE; j++) {
                if (!checkMove[i][j]) checkMove[i][j] = true;
            }
        }
    }

    public void update() {
        entityList.forEach(Entity::update);
        for (int i = 0; i < enemies.size(); i++) {
            Random random = new Random();
            AnimateEntity a = enemies.get(i);
            int[] arr = new int[4];
            if (checkMove[a.getY()][a.getX() - 1] == false) arr[0] = 1;
            if (checkMove[a.getY()][a.getX() + 1 + 32] == false) arr[1] = 1;
            if (checkMove[a.getY() - 1][a.getX()] == false
                && checkMove[a.getY() - 1][a.getX() + 31] == false) arr[2] = 1;
            if (checkMove[a.getY() + 1 + 32][a.getX()] == false
                    &&checkMove[a.getY() + 32 + 1][a.getX() + 31] == false) arr[3] = 1;
            while (true) {
                int rd = random.nextInt(4);
                if (arr[rd] != 0) {
                    if (a.time == 32) {
                        if (rd == 0) {
                            a.setX(a.getX() - 1);
                            a.update();
                            a.setCheckPath(0);
                        }
                        else if (rd == 1) {
                            a.setX(a.getX() + 1);
                            a.update();
                            a.setCheckPath(1);
                        }
                        else if (rd == 2) {
                            a.setY(a.getY() - 1);
                            a.update();
                            a.setCheckPath(2);
                        }
                        else if (rd == 3) {
                            a.setY(a.getY() + 1);
                            a.update();
                            a.setCheckPath(3);
                        }
                        break;
                    } else if (a.time == 0) {
                        a.time = 32;
                    } else {
                        if (a.getCheckPath() == 0) {
                            a.setX(a.getX() - 1);
                            a.update();
                        }
                        else if (a.getCheckPath() == 1) {
                            a.setX(a.getX() + 1);
                            a.update();
                        }
                        else if (a.getCheckPath() == 2) {
                            a.setY(a.getY() - 1);
                            a.update();
                        }
                        else if (a.getCheckPath() == 3) {
                            a.setY(a.getY() + 1);
                            a.update();
                        }
                        break;
                    }
                }
            }
        }
    }

    public void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).render(gc);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(gc);
        }
        bomberMan.render(gc);
    }

    public Board() {
    }

    public Board(int level, int column, int row) {
        this.level = level;
        this.column = column;
        this.row = row;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
