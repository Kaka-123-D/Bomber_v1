package Entities.Mono;

import Entities.AnimateEntity;
import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.image.Image;

public class Brick extends AnimateEntity {

    public int timeDestroy = 60;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        score = 1;
    }

    @Override
    public void update() {
        setAnimate();
        if (imasu == false && timeDestroy > 0) {
            timeDestroy--;
            img  =  Sprite.movingSprite(Sprite.brick_exploded,
                                        Sprite.brick_exploded1,
                                        Sprite.brick_exploded2,
                                        animate, 20).getFxImage();
        }
    }
}
