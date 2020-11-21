package Entities.Enemy;

import Entities.AnimateEntity;
import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.image.Image;

public class Oneal extends Enemy {

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        //timeMoveACell--;
        setAnimate();
        if (!imasu) die();
    }

    public void die() {
        timeToRemove--;
        img = Sprite.oneal_dead.getFxImage();
        if (timeToRemove >= 25 && timeToRemove <= 45) img = Sprite.mob_dead1.getFxImage();
        else if (timeToRemove <= 25 && timeToRemove >= 10) img = Sprite.mob_dead2.getFxImage();
        else if (timeToRemove >= 0 && timeToRemove <= 10) img = Sprite.mob_dead3.getFxImage();
    }

    @Override
    public void updateMove(boolean[][] checkMove) {

    }

}
