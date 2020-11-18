package Game;

import Entities.Player.Bomb;
import Graphics.Sprite;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.awt.event.KeyListener;
import java.sql.SQLClientInfoException;

public class KeyControl {

    int xControl = 0;
    int yControl = 0;
    Sprite sprite = Sprite.player_right;
    Image imgPlayer;
    boolean goUp, goDown, goLeft, goRight, putBomb;
    Board board;
    Scene scene;

    public KeyControl(Board board, Scene scene) {
        this.board = board;
        this.scene = scene;
    }

    public void catchEvent() {
        scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goUp = true; break;
                    case DOWN:  goDown = true; break;
                    case LEFT:  goLeft  = true; break;
                    case RIGHT: goRight  = true; break;
                    case SPACE: putBomb = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goUp = false; break;
                    case DOWN:  goDown = false; break;
                    case LEFT:  goLeft  = false; break;
                    case RIGHT: goRight  = false; break;
                    case SPACE: putBomb = false; break;
                }
            }
        });
    }

    public void xuLi() {
        int X = board.bomberMan.getX();
        int Y = board.bomberMan.getY();
        xControl = 0;
        yControl = 0;

        if (goUp) pressUp(X, Y);
        if (goDown) pressDown(X, Y);
        if (goLeft) pressLeft(X, Y);
        if (goRight) pressRight(X, Y);
        if (putBomb) showBomb();
    }

    public void showBomb() {
        int nBomb = board.bomberMan.getAmountBom();
        if (nBomb == 0) return;
        int tmpX = (int) (board.bomberMan.getX() + (imgPlayer.getWidth()/3)) / Sprite.SCALED_SIZE;
        int tmpY = (int) (board.bomberMan.getY() + (imgPlayer.getHeight()/2)) / Sprite.SCALED_SIZE;
        Bomb bomb = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        board.entityList.add(bomb);
        board.bomberMan.setAmountBom(nBomb - 1);
    }

    public void pressUp(int X, int Y) {

        yControl = -board.bomberMan.getSpeed();
        if (board.checkMove[Y + Sprite.SCALED_SIZE / 8][X + Sprite.SCALED_SIZE/8]
                && !board.checkMove[Y + Sprite.SCALED_SIZE / 8][X - 1 + ((5 * Sprite.SCALED_SIZE) / 8)]) {
            yControl = 0;
            if (!board.checkMove[Y + Sprite.SCALED_SIZE / 8][X + (8 * Sprite.SCALED_SIZE) / 16]) {
                xControl = board.bomberMan.getSpeed();
            }
        }
        else if (board.checkMove[Y + Sprite.SCALED_SIZE / 8][X - 1 + ((5 * Sprite.SCALED_SIZE) / 8)]) {
            yControl = 0;
            if (!board.checkMove[Y + Sprite.SCALED_SIZE / 8][X + (4 * Sprite.SCALED_SIZE) / 16]) {
                xControl = -board.bomberMan.getSpeed();
            }
        }

        /* Nhún nhảy */
        sprite = Sprite.player_up;
        sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }

    public void pressDown(int X, int Y) {

        yControl = board.bomberMan.getSpeed();
        if (board.checkMove[Y + Sprite.SCALED_SIZE][X + Sprite.SCALED_SIZE/8]
                && !board.checkMove[Y + Sprite.SCALED_SIZE][X - 1 + ((5 * Sprite.SCALED_SIZE) / 8)]) {
            yControl = 0;
            if (!board.checkMove[Y + Sprite.SCALED_SIZE][X + (8 * Sprite.SCALED_SIZE) / 16]) {
                xControl = board.bomberMan.getSpeed();
            }
        }
        else if (board.checkMove[Y + Sprite.SCALED_SIZE][X - 1 + ((5 * Sprite.SCALED_SIZE) / 8)]) {
            yControl = 0;
            if (!board.checkMove[Y + Sprite.SCALED_SIZE][X + (4 * Sprite.SCALED_SIZE) / 16]) {
                xControl = -board.bomberMan.getSpeed();
            }
        }

        /* Nhún nhảy */
        sprite = Sprite.player_down;
        sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }

    public void pressLeft(int X, int Y) {

        xControl = -board.bomberMan.getSpeed();
        if (board.checkMove[Y + Sprite.SCALED_SIZE / 4][X + 1]
                && !board.checkMove[Y + (15 * Sprite.SCALED_SIZE) / 16][X + 1]) {
            xControl = 0;
            if (!board.checkMove[Y + (3 * Sprite.SCALED_SIZE) / 4][X + 1]) {
                yControl = board.bomberMan.getSpeed();
            }
        }
        else if (board.checkMove[Y + (15 * Sprite.SCALED_SIZE) / 16][X + 1]) {
            xControl = 0;
            if (!board.checkMove[Y + Sprite.SCALED_SIZE / 4][X + 1]) {
                yControl = -board.bomberMan.getSpeed();
            }
        }

        /* Nhún nhảy */
        sprite = Sprite.player_left;
        sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }

    public void pressRight(int X, int Y) {
        xControl = board.bomberMan.getSpeed();
        if (board.checkMove[Y + Sprite.SCALED_SIZE / 4][X + (5 * Sprite.SCALED_SIZE) / 8]
                && !board.checkMove[Y + (15 * Sprite.SCALED_SIZE) / 16][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            xControl = 0;
            if (!board.checkMove[Y + (3 * Sprite.SCALED_SIZE) / 4][X + (5 * Sprite.SCALED_SIZE) / 8]) {
                yControl = board.bomberMan.getSpeed();
            }
        }
        else if (board.checkMove[Y + (15 * Sprite.SCALED_SIZE) / 16][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            xControl = 0;
            if (!board.checkMove[Y + (1 * Sprite.SCALED_SIZE) / 4][X + (5 * Sprite.SCALED_SIZE) / 8]) {
                yControl = -board.bomberMan.getSpeed();
            }
        }

        /* Nhún nhảy */
        sprite = Sprite.player_right;
        sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }
}
