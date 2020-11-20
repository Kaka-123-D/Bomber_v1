package Entities.Enemy;

import Entities.AnimateEntity;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.xml.parsers.SAXParser;

public abstract class Enemy extends AnimateEntity {

    public Enemy(int x, int y, Image img) {
        super(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
}
