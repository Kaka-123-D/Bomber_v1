package Entities.Enemy;

import Entities.AnimateEntity;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.xml.parsers.SAXParser;
import java.util.Random;

public abstract class Enemy extends AnimateEntity {

    public int timeMoveACell = Sprite.SCALED_SIZE;
    public int timeToRemove = 60;
    protected int checkPath = -1;
    public int speed = 2;

    public int getCheckPath() {
        return checkPath;
    }

    public void setCheckPath(int checkPath) {
        this.checkPath = checkPath;
    }

    public Enemy(int x, int y, Image img) {
        super(x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void updateMove(boolean[][] checkMove);
}
