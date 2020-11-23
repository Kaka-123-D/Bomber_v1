package Game;

import Entities.Enemy.Balloon;
import Entities.Enemy.Enemy;
import Entities.Enemy.Oneal;
import Entities.Entity;
import Entities.Item.BombItem;
import Entities.Item.FlameItem;
import Entities.Item.Item;
import Entities.Item.SpeedItem;
import Entities.Mono.Brick;
import Entities.Mono.Grass;
import Entities.Mono.Portal;
import Entities.Mono.Wall;
import Entities.Player.Bomb;
import Entities.Player.BomberMan;
import Graphics.Sprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLClientInfoException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

    private int column;
    private int row;
    public int level;
    public List<Entity> entityList = new ArrayList<>();
    public static List<Enemy> enemyList = new ArrayList<>();
    public boolean[][] checkMovePlayer;
    public boolean[][] checkMoveEnemy;
    public static BomberMan bomberMan = new BomberMan(1, 1, Sprite.player_right.getFxImage());
    public static Portal portal;

    public Board(String fileName) {
        inputFromFile(fileName);
    }

    public void inputFromFile(String fileMap) {
        File file = new File(fileMap);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        level = scanner.nextInt();
        System.out.println(level);
        row = scanner.nextInt();
        column = scanner.nextInt();
        checkMovePlayer = new boolean[row * Sprite.SCALED_SIZE][column * Sprite.SCALED_SIZE];
        checkMoveEnemy = new boolean[row * Sprite.SCALED_SIZE][column * Sprite.SCALED_SIZE];
        String str = scanner.nextLine();

        for (int i = 0; i < row; i++){
            str = scanner.nextLine();
            for (int j = 0; j < column; j++) {
                char c = str.charAt(j);
                switch (c) {
                    case '#':
                        entityList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        changeCheckMovePlayer(i, j);
                        changeCheckMoveEnemy(i, j);
                        break;
                    case '*':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        entityList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        changeCheckMovePlayer(i, j);
                        changeCheckMoveEnemy(i, j);
                        break;
                    case 'x':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        portal = new Portal(j, i, Sprite.portal.getFxImage());
                        entityList.add(portal);
                        entityList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        changeCheckMovePlayer(i, j);
                        changeCheckMoveEnemy(i, j);
                        break;
                    case '1':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemyList.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
                        break;
                    case '2':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        enemyList.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    case 'b':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        entityList.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        entityList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        changeCheckMovePlayer(i, j);
                        changeCheckMoveEnemy(i, j);
                        break;
                    case 'f':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        entityList.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        entityList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        changeCheckMovePlayer(i, j);
                        changeCheckMoveEnemy(i, j);
                        break;
                    case 's':
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        entityList.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        entityList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        changeCheckMovePlayer(i, j);
                        changeCheckMoveEnemy(i, j);
                        break;
                    default:
                        entityList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                }
            }
        }
        entityList.add(bomberMan);
    }

    public void changeCheckMovePlayer(int r, int c) {
        for (int i = r * Sprite.SCALED_SIZE; i < (r + 1) * Sprite.SCALED_SIZE; i++) {
            for (int j = c * Sprite.SCALED_SIZE; j < (c + 1) * Sprite.SCALED_SIZE; j++) {
                if (!checkMovePlayer[i][j]) checkMovePlayer[i][j] = true;
                else checkMovePlayer[i][j] = false;
            }
        }
    }

    public void changeCheckMoveEnemy(int r, int c) {
        for (int i = r * Sprite.SCALED_SIZE; i < (r + 1) * Sprite.SCALED_SIZE; i++) {
            for (int j = c * Sprite.SCALED_SIZE; j < (c + 1) * Sprite.SCALED_SIZE; j++) {
                if (!checkMoveEnemy[i][j]) checkMoveEnemy[i][j] = true;
                else checkMoveEnemy[i][j] = false;
            }
        }
    }

    public Entity checkEntity(int X, int Y) {
        for (int i = entityList.size() - 1; i >= 0 ; i--) {
            if (entityList.get(i).getX() == X && entityList.get(i).getY() == Y) {
                if (entityList.get(i) instanceof Grass) return null;
                else return entityList.get(i);
            }
        }
        return null;
    }

    public void update() {

        checkNextLevel();

        // check bomber va chạm enemy
        if (checkEnemyKillPlayer()) bomberMan.imasu = false;

        // update mono, bomb, player, item
        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i) instanceof Bomb) {
                ((Bomb) entityList.get(i)).updateBomb(entityList, enemyList, bomberMan.getLengthFlame());
            }
            else entityList.get(i).update();
        }

        // update enemy
        for (int i = 0; i < enemyList.size(); i++) {
            if (!enemyList.get(i).imasu) enemyList.get(i).update();
            else enemyList.get(i).updateMove(checkMoveEnemy);
        }

    }

    public void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /* render mono, bomb, item */
        for (int i = 0; i < entityList.size(); i++) {
            if (checkRender(i)) entityList.get(i).render(gc);
            else i--;
        }

        /* render enemy */
        for (int i = 0; i < enemyList.size(); i++) {
            if (!checkRemoveEnemy(i)) enemyList.get(i).render(gc);
        }

        /* render player */
        if (bomberMan != null && bomberMan.timeReset != 0) bomberMan.render(gc);
    }

    public void checkNextLevel() {
        if (portal.nextLevel) {
            System.out.println(level);
            int X = (bomberMan.getX() + (3 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE;
            int Y = (bomberMan.getY() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
            if (X == portal.getX() && Y == portal.getY()) {
                entityList.clear();
                String fileMap = "res/levels/Level" + String.valueOf(level + 1) + ".txt";
                inputFromFile(fileMap);
                portal.nextLevel = false;
                bomberMan.setX(1);
                bomberMan.setY(1);
            }
        }
    }

    public boolean checkRender(int index) {
        if (checkRemovePlayer(index)) return false;
        if (checkRemoveBomb(index)) return false;
        if (checkRemoveBrick(index)) return false;
        if (checkRemoveItem(index)) return false;
        return true;
    }

    public boolean checkEnemyKillPlayer() {
        for (int i = 0; i < enemyList.size(); i++) {
            int enemyXUP = (enemyList.get(i).getX() + 4) / Sprite.SCALED_SIZE;
            int enemyYUP = (enemyList.get(i).getY() + 4) / Sprite.SCALED_SIZE;
            int enemyXDOWN = 1 + (enemyList.get(i).getX() - 4) / Sprite.SCALED_SIZE;
            int enemyYDOWN = 1 + (enemyList.get(i).getY() - 4) / Sprite.SCALED_SIZE;
            int playerX = (bomberMan.getX() + (3 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE;
            int playerY = (bomberMan.getY() + (3 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE;

            if (enemyXUP == playerX && enemyYUP == playerY) return true;
            if (enemyXDOWN == playerX && enemyYDOWN == playerY) return true;
        }
        return false;
    }

    public boolean checkRemoveEnemy(int index) {
        if ((enemyList.get(index)).timeToRemove <= 0) {
            enemyList.remove(index);
            return true;
        }
        return false;
    }

    public boolean checkRemovePlayer(int index) {
        if (entityList.get(index) instanceof BomberMan
                && ((BomberMan) entityList.get(index)).timeReset == 0) {
            entityList.remove(index);
            if (BomberMan.live != 0)
            {
                bomberMan = new BomberMan(1, 1, Sprite.player_right.getFxImage());
                entityList.add(bomberMan);
            }
            else bomberMan = null;
            return true;
        }
        return false;
    }

    public boolean checkRemoveItem(int index) {
        if (entityList.get(index) instanceof Item
                && entityList.get(index).isImasu() == false) {
            entityList.remove(index);
            return true;
        }
        return false;
    }

    public boolean checkRemoveBomb(int index) {
        if (entityList.get(index) instanceof Bomb && entityList.get(index).imasu == false) {
            if (!((Bomb) entityList.get(index)).allowEntry) {
                changeCheckMovePlayer(entityList.get(index).getY(), entityList.get(index).getX());
            }
            changeCheckMoveEnemy(entityList.get(index).getY(), entityList.get(index).getX());
            entityList.remove(index);
            bomberMan.setAmountBom(bomberMan.getAmountBom() + 1);
            return true;
        }
        return false;
    }

    public boolean checkRemoveBrick(int index) {
        if (entityList.get(index) instanceof Brick && ((Brick) entityList.get(index)).timeDestroy == 0) {
            changeCheckMovePlayer(entityList.get(index).getY(), entityList.get(index).getX());
            changeCheckMoveEnemy(entityList.get(index).getY(), entityList.get(index).getX());
            entityList.remove(index);
            return true;
        }
        return false;
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
