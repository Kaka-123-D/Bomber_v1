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
    Image imgPlayer = Sprite.player_right.getFxImage();
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
        /* Làm mượt đi lên */
        if (goUp) {
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
            imgPlayer = Sprite.player_up.getFxImage();
        }

        /* Làm mượt đi xuống */
        if (goDown) {
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
            imgPlayer = Sprite.player_down.getFxImage();
        }

        /* Làm mượt đi sang trái */
        if (goLeft) {
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
            imgPlayer = Sprite.player_left.getFxImage();
        }

        /* Làm mượt đi sang phải */
        if (goRight) {
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
            imgPlayer = Sprite.player_right.getFxImage();
        }

        /* Đặt bom */
        if (putBomb) {
            int tmpX = (int) (board.bomberMan.getX() + (imgPlayer.getWidth()/3)) / Sprite.SCALED_SIZE;
            int tmpY = (int) (board.bomberMan.getY() + (imgPlayer.getHeight()/2)) / Sprite.SCALED_SIZE;
            Bomb bomb = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
            board.entityList.add(bomb);
        }
    }
}
