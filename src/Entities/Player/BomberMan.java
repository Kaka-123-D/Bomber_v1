package Entities.Player;

import Entities.AnimateEntity;
import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BomberMan extends AnimateEntity {

    private int speed = 3 + (Sprite.SCALED_SIZE / 16) - 1;
    private int lengthFlame = 1;
    private int amountBom = 15;
    public int timeSpacePutBom = 0;
    public int timeReset = 60;
    public int timeNoDie = 90;
    public static int live = 3; // 3 mạng

    public BomberMan(int x, int y, Image img) {
        super(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE, img);
    }

    public int getLengthFlame() {
        return lengthFlame;
    }

    public void setLengthFlame(int lengthFlame) {
        this.lengthFlame = lengthFlame;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed > 0) this.speed = speed;
    }

    public int getAmountBom() {
        return amountBom;
    }

    public void setAmountBom(int amountBom) {
        if (this.amountBom > amountBom) timeSpacePutBom = 10;
        this.amountBom = amountBom;
    }

    @Override
    public void setX(int x) {
        this.x = x * Sprite.SCALED_SIZE;
    }

    @Override
    public void setY(int y) {
        this.y = y * Sprite.SCALED_SIZE;
    }

    @Override
    public void update() {
        if (timeNoDie > 0) timeNoDie--;

        setAnimate();
        if (!imasu) {
            timeReset--;
            img = Sprite.movingSprite(Sprite.player_dead1,
                                    Sprite.player_dead2,
                                    Sprite.player_dead3,
                                    animate, 120).getFxImage();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y + 50);
    }

    public void updateEvent(int xControl, int yControl, Image img) {
        if (timeSpacePutBom > 0) timeSpacePutBom--;
        if (img != null && imasu) {
            if (timeNoDie > 0 && timeNoDie % 10 > 5) this.img = null;
            else this.img = img;
        }
        x = x + xControl;
        y = y + yControl;
    }
}
