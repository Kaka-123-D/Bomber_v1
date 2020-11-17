package Game;

import Entities.Player.Bomb;
import Graphics.Sprite;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.awt.event.KeyListener;

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
        int tmpX = (int) (board.bomberMan.getX() + (imgPlayer.getWidth()/3)) / Sprite.SCALED_SIZE;
        int tmpY = (int) (board.bomberMan.getY() + (imgPlayer.getHeight()/2)) / Sprite.SCALED_SIZE;
        xControl = 0;
        yControl = 0;
        if (goUp) {
            yControl = -3;
            //if (board.boardCheckMove[tmpY - 1][tmpX]) yControl = 0;
            imgPlayer = Sprite.player_up.getFxImage();
        }
        if (goDown) {
            yControl = 3;
            //if (board.boardCheckMove[tmpY + 1][tmpX]) yControl = 0;
            imgPlayer = Sprite.player_down.getFxImage();
        }
        if (goRight) {
            xControl = 3;
            //if (board.boardCheckMove[tmpY][tmpX + 1]) xControl = 0;
            imgPlayer = Sprite.player_right.getFxImage();
        }
        if (goLeft) {
            xControl = -3;
            //if (board.boardCheckMove[tmpY][tmpX - 1]) xControl = 0;
            imgPlayer = Sprite.player_left.getFxImage();
        }
        if (putBomb) {
            Bomb bomb = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
            board.entityList.add(bomb);
        }
    }
}
