package Entities.Player;

import Entities.AnimateEntity;
import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.image.Image;

public class Bomb extends AnimateEntity {

    public int explosionTime = 90; // 1.5s

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        explosionTime--;
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 15).getFxImage();
        setAnimate();
    }

    public void explode() {

    }
}
