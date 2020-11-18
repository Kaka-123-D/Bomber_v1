package Entities.Player;

import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BomberMan extends Entity {

    private int speed = 2;

    public BomberMan(int x, int y, Image img) {
        super(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE, img);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public void updateEvent(int xControl, int yControl, Image img) {
        if (img != null)this.img = img;
        x = x + xControl;
        y = y + yControl;
    }
}
