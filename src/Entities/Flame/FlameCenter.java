package Entities.Flame;

import Graphics.Sprite;
import javafx.scene.image.Image;

public class FlameCenter extends Flame {

    public FlameCenter(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
        timeImasu--;
        img  =  Sprite.movingSprite(Sprite.bomb_exploded1,
                Sprite.bomb_exploded2, animate, 15).getFxImage();
    }
}
