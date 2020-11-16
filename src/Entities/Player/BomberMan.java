package Entities.Player;

import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.image.Image;

public class BomberMan extends Entity {

    public BomberMan(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
    }

    public void updateEvent(int xControl, int yControl, Image img) {
        if (img != null)this.img = img;
        if (x < Sprite.SCALED_SIZE + 1 && xControl < 0) {
            return;
        }
        if (y < Sprite.SCALED_SIZE + 1 && yControl < 0) {
            return;
        }
        x = x + xControl;
        y = y + yControl;
    }
}
