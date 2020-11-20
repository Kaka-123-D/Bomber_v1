package Entities.Flame;

import Graphics.Sprite;
import javafx.scene.image.Image;

public class FlameDown extends Flame {

    public FlameDown(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
        timeImasu--;
        img  =  Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                                    Sprite.explosion_vertical_down_last1,
                                    Sprite.explosion_vertical_down_last2,
                                    animate, 15).getFxImage();
    }
}
