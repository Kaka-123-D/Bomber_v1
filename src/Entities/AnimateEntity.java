package Entities;

import javafx.scene.image.Image;

public abstract class AnimateEntity extends Entity {

    public int animate = 0;
    public final int maxAnimate = 7500;
    protected int checkPath = -1;
    public int time = 32;

    public AnimateEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    public void setAnimate() {
        if (animate < maxAnimate) {
            animate++;
        }
        else {
            animate = 0;
        }
    }

    public int getCheckPath() {
        return checkPath;
    }

    public void setCheckPath(int checkPath) {
        this.checkPath = checkPath;
    }
}
