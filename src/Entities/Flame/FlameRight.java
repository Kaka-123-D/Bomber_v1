package Entities.Flame;

import Graphics.Sprite;
import javafx.scene.image.Image;

public class FlameRight extends Flame {

    public FlameRight(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
        timeImasu--;
        img  =  Sprite.movingSprite(Sprite.explosion_horizontal_right_last1,
                                    Sprite.explosion_horizontal_right_last2,
                                    animate, 15).getFxImage();
    }
}
