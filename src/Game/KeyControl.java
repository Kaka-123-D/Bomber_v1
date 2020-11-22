package Game;

import Entities.Item.BombItem;
import Entities.Item.FlameItem;
import Entities.Item.SpeedItem;
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
                    case UP:
                        goUp = true;
                        goDown = false;
                        break;
                    case DOWN:
                        goDown = true;
                        goUp = false;
                        break;
                    case LEFT:
                        goLeft  = true;
                        goRight = false;
                        break;
                    case RIGHT:
                        goRight  = true;
                        goLeft = false;
                        break;
                    case SPACE: putBomb = true; break;
                    default: break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goUp = false;
                        imgPlayer = Sprite.player_up.getFxImage();
                        break;
                    case DOWN:
                        goDown = false;
                        imgPlayer = Sprite.player_down.getFxImage();
                        break;
                    case LEFT:
                        goLeft  = false;
                        imgPlayer = Sprite.player_left.getFxImage();
                        break;
                    case RIGHT:
                        goRight  = false;
                        imgPlayer = Sprite.player_right.getFxImage();
                        break;
                    case SPACE:
                        putBomb = false;
                        break;
                    default: break;
                }
            }
        });
    }

    public void xuLi() {
        int X = board.bomberMan.getX();
        int Y = board.bomberMan.getY();
        int speed = board.bomberMan.getSpeed();
        xControl = 0;
        yControl = 0;

        if (putBomb && board.bomberMan.timeSpacePutBom == 0) showBomb();

        if (goLeft) {
            pressLeft(X, Y);

            /* Xử lí đi qua bom */
            int bomX = (X + speed + (5 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE;
            int notBomX = (X - speed + (5 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE;
            int bomY = (Y + 1 + Sprite.SCALED_SIZE / 8) / Sprite.SCALED_SIZE;
            int notBomY = bomY;

            xuLiDiQuaBom(bomX, bomY, notBomX, notBomY);

            eatItem(X, Y + 5);
        }

        if (goRight) {
            pressRight(X, Y);

            /* Xử lí đi qua bom */
            int bomX = (X - speed) / Sprite.SCALED_SIZE;
            int notBomX = (X + speed) / Sprite.SCALED_SIZE;
            int bomY = (Y + 1 + Sprite.SCALED_SIZE / 8) / Sprite.SCALED_SIZE;
            int notBomY = bomY;

            xuLiDiQuaBom(bomX, bomY, notBomX, notBomY);

            eatItem(X + (5 * Sprite.SCALED_SIZE) / 8, Y + 5);
        }

        if (goUp) {
            pressUp(X + xControl, Y);

            /* Xử lí đi qua bom */
            int bomX = (X + (3 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE;
            int notBomX = bomX;
            int bomY = (Y + Sprite.SCALED_SIZE + speed) / Sprite.SCALED_SIZE;
            int notBomY = (Y + Sprite.SCALED_SIZE - speed) / Sprite.SCALED_SIZE;

            xuLiDiQuaBom(bomX, bomY, notBomX, notBomY);

            eatItem(X + 4, Y);
        }

        if (goDown) {
            pressDown(X + xControl, Y);

            /* Xử lí đi qua bom */
            int bomX = (X + (3 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE;
            int notBomX = bomX;
            int bomY = (Y + Sprite.SCALED_SIZE / 8 - speed) / Sprite.SCALED_SIZE;
            int notBomY = (Y + Sprite.SCALED_SIZE / 8 + speed) / Sprite.SCALED_SIZE;

            xuLiDiQuaBom(bomX, bomY, notBomX, notBomY);

            eatItem(X + 4, Y + Sprite.SCALED_SIZE);
        }
    }

    public void xuLiDiQuaBom(int bomX, int bomY, int notBomX, int notBomY) {
        if (checkBomb(bomX, bomY) && !checkBomb(notBomX, notBomY)) {
            Bomb tmp = (Bomb) board.checkEntity(bomX, bomY);
            if (tmp.allowEntry) board.changeCheckMovePlayer(bomY, bomX);
            tmp.allowEntry = false;
        }
    }

    public void eatItem(int X, int Y) {
        if (!checkEatSpeed(X, Y)) {             // check ăn item speed
            if (!checkEatBomb(X, Y)) {          // check ăn item bomb
                if (!checkEatFlame(X, Y)) {      // check ăn item flame
                    return;                     // không phải item.
                }
            }
        }
    }

    public boolean checkEatFlame(int X, int Y) {
        if (checkFlameItem(X, Y)) {
            board.bomberMan.setLengthFlame(board.bomberMan.getLengthFlame() + 1);
            FlameItem flameItem = (FlameItem) board.checkEntity(X / Sprite.SCALED_SIZE, Y / Sprite.SCALED_SIZE);
            flameItem.setImasu(false);
            return true;
        }
        return false;
    }

    public boolean checkEatSpeed(int X, int Y) {
        if (checkSpeedItem(X, Y)) {
            board.bomberMan.setSpeed(board.bomberMan.getSpeed() * 2);
            SpeedItem speedItem = (SpeedItem) board.checkEntity(X / Sprite.SCALED_SIZE,Y / Sprite.SCALED_SIZE);
            speedItem.setImasu(false);
            return true;
        }
        return false;
    }

    public boolean checkEatBomb(int X, int Y) {
        if (checkBombItem(X, Y)) {
            board.bomberMan.setAmountBom(board.bomberMan.getAmountBom() + 1);
            BombItem bombItem
                    = (BombItem) board.checkEntity
                    (X / Sprite.SCALED_SIZE,
                            Y / Sprite.SCALED_SIZE);
            bombItem.setImasu(false);
            return true;
        }
        return false;
    }

    public boolean checkBomb(int X, int Y) {
        if (board.checkEntity(X, Y) instanceof Bomb) {
            return true;
        }
        return false;
    }

    public boolean checkBombItem(int X, int Y) {
        if (board.checkEntity(X / Sprite.SCALED_SIZE, Y / Sprite.SCALED_SIZE) instanceof BombItem) {
            return true;
        }
        return false;
    }

    public boolean checkSpeedItem(int X, int Y) {
        if (board.checkEntity(X / Sprite.SCALED_SIZE, Y / Sprite.SCALED_SIZE) instanceof SpeedItem) {
            return true;
        }
        return false;
    }

    public boolean checkFlameItem(int X, int Y) {
        if (board.checkEntity(X / Sprite.SCALED_SIZE, Y / Sprite.SCALED_SIZE) instanceof FlameItem) {
            return true;
        }
        return false;
    }

    public void showBomb() {
        int nBomb = board.bomberMan.getAmountBom();
        if (nBomb == 0) return;
        int tmpX =  (board.bomberMan.getX() + (5 * Sprite.SCALED_SIZE) / 16) / Sprite.SCALED_SIZE;
        int tmpY =  (board.bomberMan.getY() + (3 * Sprite.SCALED_SIZE) / 4) / Sprite.SCALED_SIZE;
        if (board.checkEntity(tmpX, tmpY) instanceof Bomb) return;
        Bomb bomb = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        board.entityList.add(bomb);
        board.changeCheckMoveEnemy(tmpY, tmpX);
        board.bomberMan.setAmountBom(nBomb - 1);
    }

    public void pressUp(int X, int Y) {

        yControl = -board.bomberMan.getSpeed();
        while (board.checkMovePlayer[Y + yControl][X + Sprite.SCALED_SIZE / 8]
                || board.checkMovePlayer[Y + yControl][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            yControl++;
            if (yControl > 0){
                yControl = 0;
                break;
            }
        }

        /* Nếu cả hai bên đầu đều bị chặn. */
        if (board.checkMovePlayer[Y - 1][X + Sprite.SCALED_SIZE / 8]
                &&  board.checkMovePlayer[Y - 1][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            yControl = 0;
        }

        /* bên phải đầu bị chặn, bên trái không bị chặn */
        if (!board.checkMovePlayer[Y - 1][X + Sprite.SCALED_SIZE / 8]
                && board.checkMovePlayer[Y - 1][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            if (!goRight && !goLeft) xControl = -1;
            yControl = 0;
        }

        /* bên phải đầu không bị chặn, bên trái bị chặn. */
        if (board.checkMovePlayer[Y - 1][X + Sprite.SCALED_SIZE / 8]
                && !board.checkMovePlayer[Y - 1][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            if (!goLeft && !goRight) xControl = 1;
            yControl = 0;
        }

        /* Nhún nhảy */
        sprite = Sprite.player_up;
        sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }

    public void pressDown(int X, int Y) {

        yControl = board.bomberMan.getSpeed();
        while (board.checkMovePlayer[Y + yControl + Sprite.SCALED_SIZE][X + Sprite.SCALED_SIZE / 8]
                || board.checkMovePlayer[Y + yControl + Sprite.SCALED_SIZE][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            yControl--;
            if (yControl < 0){
                yControl = 0;
                break;
            }
        }

        /* 2 bên chân đều bị chặn. */
        if (board.checkMovePlayer[Y + Sprite.SCALED_SIZE + 1][X + Sprite.SCALED_SIZE / 8]
                &&  board.checkMovePlayer[Y + Sprite.SCALED_SIZE + 1][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            yControl = 0;
        }

        /* bên chân phải bị chặn, chân trái không bị chặn. */
        if (!board.checkMovePlayer[Y + Sprite.SCALED_SIZE + 1][X + Sprite.SCALED_SIZE / 8]
                &&   board.checkMovePlayer[Y + Sprite.SCALED_SIZE + 1][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            if (!goRight && !goLeft) xControl = -1;
            yControl = 0;
        }

        /* bên chân phải không bị chặn, bên trái bị chặn. */
        if (board.checkMovePlayer[Y + Sprite.SCALED_SIZE + 1][X + Sprite.SCALED_SIZE / 8]
                && !board.checkMovePlayer[Y + Sprite.SCALED_SIZE + 1][X + (5 * Sprite.SCALED_SIZE) / 8]) {
            if (!goLeft && !goRight) xControl = 1;
            yControl = 0;
        }

        /* Nhún nhảy */
        sprite = Sprite.player_down;
        sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }

    public void pressLeft(int X, int Y) {
        xControl = -board.bomberMan.getSpeed();
        while (board.checkMovePlayer[Y + 1 + Sprite.SCALED_SIZE / 8][X + xControl]
                || board.checkMovePlayer[Y + Sprite.SCALED_SIZE - 1][X + xControl]) {
            xControl++;
            if (xControl > 0){
                xControl = 0;
                break;
            }
        }

        /* Mắt bị chặn, Tay bị chặn */
        if (board.checkMovePlayer[Y + Sprite.SCALED_SIZE / 4][X - 1]
                &&  board.checkMovePlayer[Y + (3 * Sprite.SCALED_SIZE) / 4][X - 1]) {
            xControl = 0;
        }

        /* Mắt bị chặn, tay không bị chặn. */
        else if (board.checkMovePlayer[Y + 1 + Sprite.SCALED_SIZE / 8][X - 1]
                && !board.checkMovePlayer[Y - 1 + Sprite.SCALED_SIZE][X - 1]) {
            //if (!board.checkMove[Y + (3 * Sprite.SCALED_SIZE) / 4][X - 1])
            yControl = 1;
            xControl = 0;
        }

        /* Mắt không bị chặn, tay bị chặn. */
        else if (!board.checkMovePlayer[Y + 1 + Sprite.SCALED_SIZE / 8][X - 1]
                &&   board.checkMovePlayer[Y + Sprite.SCALED_SIZE - 1][X - 1]) {
            //if (!board.checkMove[Y + (1 * Sprite.SCALED_SIZE) / 4][X - 1])
            yControl = -1;
            xControl = 0;
        }

        /* Nhún nhảy */
        sprite = Sprite.player_left;
        sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }

    public void pressRight(int X, int Y) {

        xControl = board.bomberMan.getSpeed();
        while (board.checkMovePlayer[Y + 1 + Sprite.SCALED_SIZE / 8][X + xControl + (5 * Sprite.SCALED_SIZE) / 8]
                || board.checkMovePlayer[Y + Sprite.SCALED_SIZE - 1][X + xControl + (5 * Sprite.SCALED_SIZE) / 8]) {
            xControl--;
            if (xControl < 0){
                xControl = 0;
                break;
            }
        }

        /* Mắt và tay bị chặn. */
        if (board.checkMovePlayer[Y + Sprite.SCALED_SIZE / 4][X + 1 + (5 * Sprite.SCALED_SIZE) / 8]
                &&  board.checkMovePlayer[Y + (3 * Sprite.SCALED_SIZE) / 4][X + 1 + (5 * Sprite.SCALED_SIZE) / 8]) {
            xControl = 0;
        }

        /* Mắt bị chặn, tay không bị chặn. */
        else if (board.checkMovePlayer[Y + 1 + Sprite.SCALED_SIZE / 8][X + 1 + (5 * Sprite.SCALED_SIZE) / 8]
                && !board.checkMovePlayer[Y + Sprite.SCALED_SIZE - 1][X + 1 +  (5 * Sprite.SCALED_SIZE) /  8]) {
            //if (!board.checkMove[Y + (3 * Sprite.SCALED_SIZE) / 4][X + 1 + (5 * Sprite.SCALED_SIZE) / 8])
            yControl = 1;
            xControl = 0;
        }

        /* Mắt không bị chặn, tay bị chặn. */
        else if (!board.checkMovePlayer[Y + 1 + Sprite.SCALED_SIZE / 8][X + 1 + (5 * Sprite.SCALED_SIZE) / 8]
                &&   board.checkMovePlayer[Y + Sprite.SCALED_SIZE - 1][X + 1 + (5 * Sprite.SCALED_SIZE) / 8]) {
            //if (!board.checkMove[Y + (1 * Sprite.SCALED_SIZE) / 4][X + 1 + (5 * Sprite.SCALED_SIZE) / 8])
            yControl = -1;
            xControl = 0;
        }

        /* Nhún nhảy */
        sprite = Sprite.player_right;
        sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, board.bomberMan.animate, 20);
        imgPlayer = sprite.getFxImage();
    }
}
