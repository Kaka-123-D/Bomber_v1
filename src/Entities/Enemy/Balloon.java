package Entities.Enemy;

import Entities.AnimateEntity;
import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Balloon extends AnimateEntity {

    public Balloon(int x, int y, Image img) {
        super(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE, img);
    }

    @Override
    public void update() {
        time--;
     }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
}
