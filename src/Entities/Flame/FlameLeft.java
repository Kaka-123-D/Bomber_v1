package Entities.Flame;

import Graphics.Sprite;
import javafx.scene.image.Image;

public class FlameLeft extends Flame {

    public FlameLeft(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
        timeImasu--;
        img  =  Sprite.movingSprite(Sprite.explosion_horizontal_left_last1,
                                    Sprite.explosion_horizontal_left_last2,
                                    animate, 15).getFxImage();
    }
}
