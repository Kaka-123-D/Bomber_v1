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
}
