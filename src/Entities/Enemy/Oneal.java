package Entities.Enemy;

import Entities.AnimateEntity;
import Entities.Entity;
import javafx.scene.image.Image;

public class Oneal extends AnimateEntity {

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
    }
}
