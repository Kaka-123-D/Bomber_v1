package Entities.Enemy;

import Entities.AnimateEntity;
import Entities.Entity;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Random;

public class Balloon extends Enemy {

    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        timeMoveACell--;
        setAnimate();
        if (!imasu) die();
    }
    public void die() {
        timeToRemove--;
        img = Sprite.balloom_dead.getFxImage();
        if (timeToRemove >= 25 && timeToRemove <= 45) img = Sprite.mob_dead1.getFxImage();
        else if (timeToRemove <= 25 && timeToRemove >= 10) img = Sprite.mob_dead2.getFxImage();
        else if (timeToRemove >= 0 && timeToRemove <= 10) img = Sprite.mob_dead3.getFxImage();
    }
}
