package Entities.Flame;

import Entities.AnimateEntity;
import Entities.Enemy.Enemy;
import Entities.Entity;
import Entities.Item.BombItem;
import Entities.Item.Item;
import Entities.Item.SpeedItem;
import Entities.Mono.Grass;
import Entities.Mono.Wall;
import Entities.Player.Bomb;
import Entities.Player.BomberMan;
import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public abstract class Flame extends AnimateEntity {

    public int timeImasu = 30;

    public Flame(int x, int y, Image img) {
        super(x, y, img);
    }

    public void destroy(List<Entity> entityList) {
        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).getX() == x && entityList.get(i).getY() == y
                    && !(entityList.get(i) instanceof Wall)
                    && !(entityList.get(i) instanceof Grass)
                    && !(entityList.get(i) instanceof Bomb)
                    && !(entityList.get(i) instanceof Item)) {
                entityList.get(i).imasu = false;
            }

            /* player tự hủy */
            else if (entityList.get(i) instanceof BomberMan) {
                int X = entityList.get(i).getX();
                int Y = entityList.get(i).getY();

                /* Góc trên bên trái */
                if ((X + 4) / Sprite.SCALED_SIZE == x
                        && (Y + 8) / Sprite.SCALED_SIZE == y) {
                    entityList.get(i).imasu = false;
                }

                /* Góc dưới bên trái */
                else if ((X + 4) / Sprite.SCALED_SIZE == x
                        && ((Y - 4) + Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE == y) {
                    entityList.get(i).imasu = false;
                }

                /* Góc trên bên phải */
                else if ((X - 4 + (5 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE == x
                        && (Y + 8) / Sprite.SCALED_SIZE == y) {
                    entityList.get(i).imasu = false;
                }

                /* Góc dưới bên phải */
                else if ((X - 4 + (5 * Sprite.SCALED_SIZE) / 8) / Sprite.SCALED_SIZE == x
                        && ((Y - 4) + Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE == y) {
                    entityList.get(i).imasu = false;
                }
            }
        }
    }
    public void killEnemy(List<Enemy> enemyList) {
        for (int i = 0; i < enemyList.size(); i++) {
            int X = enemyList.get(i).getX();
            int Y = enemyList.get(i).getY();

            /* góc trên bên trái */
            if ((X + 4) / Sprite.SCALED_SIZE == x && (Y + 4) / Sprite.SCALED_SIZE == y) {
                enemyList.get(i).imasu = false;
            }

            /* góc dưới bên trái */
            if ((X + 8) / Sprite.SCALED_SIZE == x
                    && (Y - 4 + Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE == y) {
                enemyList.get(i).imasu = false;
            }

            /* góc trên bên phải */
            else if ((X - 4 + Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE == x
                    && (Y + 4) / Sprite.SCALED_SIZE == y) {
                enemyList.get(i).imasu = false;
            }

            /* góc dưới bên phải */
            else if ((X - 8 + Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE == x
                    && (Y - 4 + Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE == y) {
                enemyList.get(i).imasu = false;
            }
        }
    }
}
