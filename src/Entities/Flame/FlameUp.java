package Entities.Flame;

import Graphics.Sprite;
import javafx.scene.image.Image;

public class FlameUp extends Flame {

    public FlameUp(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
        timeImasu--;
        img  =  Sprite.movingSprite(Sprite.explosion_vertical_top_last1,
                                    Sprite.explosion_vertical_top_last2,
                                    animate, 15).getFxImage();
    }
}
