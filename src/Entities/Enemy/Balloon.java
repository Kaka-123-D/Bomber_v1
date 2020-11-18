package Entities.Enemy;

import Entities.AnimateEntity;
import Entities.Entity;
import javafx.scene.image.Image;

public class Balloon extends AnimateEntity {

    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
    }
}
