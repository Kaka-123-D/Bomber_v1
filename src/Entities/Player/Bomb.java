package Entities.Player;

import Entities.AnimateEntity;
import Entities.Enemy.Enemy;
import Entities.Entity;
import Entities.Flame.*;
import Entities.Mono.Brick;
import Entities.Mono.Grass;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class Bomb extends AnimateEntity {

    public int timeToExploded = 120; // 2s
    public int explodeTime = 30;
    public boolean allowEntry = true;
    public FlameUp flameUp = null;
    public FlameDown flameDown = null;
    public FlameRight flameRight = null;
    public FlameLeft flameLeft = null;
    public FlameCenter flameCenter = null;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public void updateBomb(List<Entity> entityList, List<Enemy> enemyList) {
        timeToExploded--;
        if (timeToExploded == 0) {

            flameCenter = new FlameCenter(x, y, Sprite.bomb_exploded.getFxImage());
            flameCenter.destroy(entityList);
            flameCenter.killEnemy(enemyList);

            if (checkEntity(x, y - 1, entityList) == null) {
                flameUp = new FlameUp(x, y - 1, Sprite.explosion_vertical_top_last.getFxImage());
                flameUp.destroy(entityList);
                flameUp.killEnemy(enemyList);
            }

            if (checkEntity(x, y + 1, entityList) == null) {
                flameDown = new FlameDown(x, y + 1, Sprite.explosion_vertical_down_last.getFxImage());
                flameDown.destroy(entityList);
                flameDown.killEnemy(enemyList);
            }

            if (checkEntity(x + 1, y, entityList) == null) {
                flameRight = new FlameRight(x + 1, y, Sprite.explosion_horizontal_right_last.getFxImage());
                flameRight.destroy(entityList);
                flameRight.killEnemy(enemyList);
            }

            if (checkEntity(x - 1, y, entityList) == null) {
                flameLeft = new FlameLeft(x - 1, y, Sprite.explosion_horizontal_left_last.getFxImage());
                flameLeft.destroy(entityList);
                flameLeft.killEnemy(enemyList);
            }
        }
        if (timeToExploded < 0) {
            explode();
            return;
        }
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 20).getFxImage();
        setAnimate();
    }

    public Entity checkEntity(int X, int Y, List<Entity> entityList) {
        for (int i = entityList.size() - 1; i >= 0 ; i--) {
            if (entityList.get(i).getX() == X && entityList.get(i).getY() == Y) {
                if (entityList.get(i) instanceof Grass
                || entityList.get(i) instanceof Bomb
                || entityList.get(i) instanceof Brick) return null;
                else return entityList.get(i);
            }
        }
        return null;
    }

    public void explode() {
        explodeTime--;
        if (explodeTime == 0) imasu = false;
        setAnimate();
        img = Sprite.movingSprite(Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 15).getFxImage();
        if(flameUp != null) flameUp.update();
        if(flameDown != null) flameDown.update();
        if(flameLeft != null) flameLeft.update();
        if(flameRight != null) flameRight.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (timeToExploded < 0) {
            if(flameRight != null && flameRight.timeImasu > 0) flameRight.render(gc);
            if(flameLeft != null && flameLeft.timeImasu > 0) flameLeft.render(gc);
            if(flameUp != null && flameUp.timeImasu > 0) flameUp.render(gc);
            if(flameDown != null && flameDown.timeImasu > 0)  flameDown.render(gc);
        }
    }
}
