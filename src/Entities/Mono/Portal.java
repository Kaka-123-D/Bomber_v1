package Entities.Mono;

import Entities.Entity;
import Game.Board;
import Game.Play;
import javafx.scene.image.Image;

public class Portal extends Entity {

    public boolean nextLevel = false;

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (Board.enemyList.isEmpty()) {
            nextLevel = true;
        }
    }

    public void update(Board board) {
        if (nextLevel) {
            Play.fileMap = Play.fileMap.substring(0, 16) + String.valueOf(1 + Board.level);
            board = new Board(Play.fileMap);
        }
    }
}
