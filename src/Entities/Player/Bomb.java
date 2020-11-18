package Entities.Player;

import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.image.Image;

public class Bomb extends Entity {
    private int time = 60;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        time--;
        if(time == 0) {
            img = Sprite.grass.getFxImage();
        }
    }
}
