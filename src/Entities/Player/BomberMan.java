package Entities.Player;

import Entities.AnimateEntity;
import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BomberMan extends AnimateEntity {

    private int speed = 3;
    private int amountBom = 1;

    public BomberMan(int x, int y, Image img) {
        super(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE, img);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAmountBom() {
        return amountBom;
    }

    public void setAmountBom(int amountBom) {
        this.amountBom = amountBom;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public void updateEvent(int xControl, int yControl, Image img) {
        setAnimate();
        if (img != null)this.img = img;
        x = x + xControl;
        y = y + yControl;
    }
}
