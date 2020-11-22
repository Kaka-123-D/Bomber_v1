package Entities.Flame;

import Graphics.Sprite;
import javafx.scene.image.Image;

public class FlameLeft extends Flame {

    public boolean last;

    public FlameLeft(int x, int y, Image img, boolean last) {
        super(x, y, img);
        this.last = last;
    }

    @Override
    public void update() {
        setAnimate();
        timeImasu--;
        if (last) {
            img  =  Sprite.movingSprite(Sprite.explosion_horizontal_left_last1,
                    Sprite.explosion_horizontal_left_last2,
                    animate, 15).getFxImage();
        } else {
            img = Sprite.movingSprite(Sprite.explosion_horizontal1,
                    Sprite.explosion_horizontal2,
                    animate, 15).getFxImage();
        }
    }
}
