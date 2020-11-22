package Entities.Player;

import Entities.AnimateEntity;
import Entities.Enemy.Enemy;
import Entities.Entity;
import Entities.Flame.*;
import Entities.Item.Item;
import Entities.Mono.Brick;
import Entities.Mono.Grass;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimateEntity {

    public int timeToExploded = 120; // 2s
    public int explodeTime = 30;
    public boolean allowEntry = true;
    public List<Flame> flameUp = new ArrayList<>();
    public List<Flame> flameDown = new ArrayList<>();
    public List<Flame> flameRight = new ArrayList<>();
    public List<Flame> flameLeft = new ArrayList<>();
    public FlameCenter flameCenter = null;

    boolean checkUp = true;
    boolean checkDown = true;
    boolean checkRight = true;
    boolean checkLeft = true;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public void updateBomb(List<Entity> entityList, List<Enemy> enemyList, int length) {
        timeToExploded--;
        if (timeToExploded == 0) {

            flameCenter = new FlameCenter(x, y, Sprite.bomb_exploded.getFxImage());

            for (int i = 0; i < length - 1; i ++) {
                createFlameUp(x, y - 1 - i, entityList, false);
                createFlameDown(x, y + 1 + i, entityList, false);
                createFlameRight(x + 1 + i, y, entityList, false);
                createFlameLeft(x - 1 - i, y, entityList, false);
            }
            int i = length - 1;
            createFlameUp(x, y - 1 - i, entityList, true);
            createFlameDown(x, y + 1 + i, entityList, true);
            createFlameRight(x + 1 + i, y, entityList, true);
            createFlameLeft(x - 1 - i, y, entityList, true);
        }
        if (timeToExploded < 0) {
            explode(entityList, enemyList);
            return;
        }
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 20).getFxImage();
        setAnimate();
    }

    public void createFlameUp(int X, int Y, List<Entity> entityList, boolean last) {

        if (checkUp && checkEntity(X, Y, entityList) instanceof Brick) {
            if (last) {
                flameUp.add(new FlameUp(X, Y, Sprite.explosion_vertical_top_last.getFxImage(), true));
            } else {
                flameUp.add(new FlameUp(X, Y, Sprite.explosion_vertical_top_last.getFxImage(), false));
            }
            checkUp = false;
        }
        else if (checkEntity(X, Y, entityList) != null) checkUp = false;
        else if (checkUp) {
            if (last) {
                flameUp.add(new FlameUp(X, Y, Sprite.explosion_vertical_top_last.getFxImage(), true));
            } else {
                flameUp.add(new FlameUp(X, Y, Sprite.explosion_vertical_top_last.getFxImage(), false));
            }
        }
    }

    public void createFlameDown(int X, int Y, List<Entity> entityList, boolean last) {

        if (checkDown && checkEntity(X, Y, entityList) instanceof Brick) {
            if (last) {
                flameDown.add(new FlameDown(X, Y, Sprite.explosion_vertical_down_last.getFxImage(), true));
            } else {
                flameDown.add(new FlameDown(X, Y, Sprite.explosion_vertical_down_last.getFxImage(), false));
            }
            checkDown = false;
        }
        else if (checkEntity(X, Y, entityList) != null) checkDown = false;
        else if (checkDown) {
            if (last) {
                flameDown.add(new FlameDown(X, Y, Sprite.explosion_vertical_down_last.getFxImage(), true));
            } else {
                flameDown.add(new FlameDown(X, Y, Sprite.explosion_vertical_down_last.getFxImage(), false));
            }
        }
    }

    public void createFlameRight(int X, int Y, List<Entity> entityList, boolean last) {
        if (checkRight && checkEntity(X, Y, entityList) instanceof Brick) {
            if (last) {
                flameRight.add(new FlameRight(X, Y, Sprite.explosion_horizontal_right_last.getFxImage(), true));
            }
            else {
                flameRight.add(new FlameRight(X, Y, Sprite.explosion_horizontal_right_last.getFxImage(), false));
            }
            checkRight = false;
        }
        else if (checkEntity(X, Y, entityList) != null) checkRight = false;
        else if (checkRight) {
            if (last) {
                flameRight.add(new FlameRight(X, Y, Sprite.explosion_horizontal_right_last.getFxImage(), true));
            }
            else {
                flameRight.add(new FlameRight(X, Y, Sprite.explosion_horizontal_right_last.getFxImage(), false));
            }
        }
    }

    public void createFlameLeft(int X, int Y, List<Entity> entityList, boolean last) {
        if (checkLeft && checkEntity(X, Y, entityList) instanceof Brick) {
            if (last) {
                flameLeft.add(new FlameLeft(X, Y, Sprite.explosion_horizontal_left_last.getFxImage(), true));
            } else {
                flameLeft.add(new FlameLeft(X, Y, Sprite.explosion_horizontal_left_last.getFxImage(), false));
            }
            checkLeft = false;
        }
        else if (checkEntity(X, Y, entityList) != null) checkLeft = false;
        else if (checkLeft) {
            if (last) {
                flameLeft.add(new FlameLeft(X, Y, Sprite.explosion_horizontal_left_last.getFxImage(), true));
            } else {
                flameLeft.add(new FlameLeft(X, Y, Sprite.explosion_horizontal_left_last.getFxImage(), false));
            }
        }
    }

    public Entity checkEntity(int X, int Y, List<Entity> entityList) {
        for (int i = entityList.size() - 1; i >= 0 ; i--) {
            if (entityList.get(i).getX() == X && entityList.get(i).getY() == Y) {
                if (entityList.get(i) instanceof Grass
                 || entityList.get(i) instanceof Bomb) return null;
                else return entityList.get(i);
            }
        }
        return null;
    }

    public void explode(List<Entity> entityList, List<Enemy> enemyList) {
        explodeTime--;
        if (explodeTime == 0) imasu = false;

        flameCenter.destroy(entityList);
        flameCenter.killEnemy(enemyList);
        destroy(flameUp, entityList, enemyList);
        destroy(flameDown, entityList, enemyList);
        destroy(flameLeft, entityList, enemyList);
        destroy(flameRight, entityList, enemyList);

        setAnimate();
        img = Sprite.movingSprite(Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 15).getFxImage();

        update(flameUp);
        update(flameDown);
        update(flameRight);
        update(flameLeft);
        flameCenter.update();
    }

    public void destroy(List<Flame> flame, List<Entity> entityList, List<Enemy> enemyList) {
        if (flame != null) {
            for (int i = 0; i < flame.size(); i++) {
                flame.get(i).destroy(entityList);
                flame.get(i).killEnemy(enemyList);
            }
        }
    }

    public void update(List<Flame> flame) {
        if (flame != null) {
            for (int i = 0; i < flame.size(); i++) {
                flame.get(i).update();
            }
        }
    }

    public void renderFlame(GraphicsContext gc, List<Flame> flame) {
        if (flame == null) return;
        for (int i = 0; i < flame.size(); i++) {
            if (flame.get(i).timeImasu > 0) flame.get(i).render(gc);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (timeToExploded < 0) {
            renderFlame(gc, flameUp);
            renderFlame(gc, flameDown);
            renderFlame(gc, flameLeft);
            renderFlame(gc, flameRight);
        }
    }
}
