package Entities.Flame;

import Graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.peer.ScrollbarPeer;

public class FlameUp extends Flame {

    public boolean last;

    public FlameUp(int x, int y, Image img, boolean last) {
        super(x, y, img);
        this.last = last;
    }

    @Override
    public void update() {
        setAnimate();
        timeImasu--;
        if (last) {
            img  =  Sprite.movingSprite(Sprite.explosion_vertical_top_last1,
                    Sprite.explosion_vertical_top_last2,
                    animate, 15).getFxImage();
        } else {
            img = Sprite.movingSprite(Sprite.explosion_vertical1,
                    Sprite.explosion_vertical2,
                    animate, 15).getFxImage();
        }

    }
}
