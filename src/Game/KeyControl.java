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
    Image img = null;
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
        xControl = 0;
        yControl = 0;
        img = null;
        if (goUp) {
            yControl = -3;
            img = Sprite.player_up.getFxImage();
        }
        if (goDown) {
            yControl = 3;
            img = Sprite.player_down.getFxImage();
        }
        if (goRight) {
            xControl = 3;
            img = Sprite.player_right.getFxImage();
        }
        if (goLeft) {
            xControl = -3;
            img = Sprite.player_left.getFxImage();
        }
        if (putBomb) {
            Bomb bomb = new Bomb(board.bomberMan.getX() / 32,
                    board.bomberMan.getY() / 32,
                    Sprite.bomb.getFxImage());
            board.entityList.add(bomb);
        }
    }
}
