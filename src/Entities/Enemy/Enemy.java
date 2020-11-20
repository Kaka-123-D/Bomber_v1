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
    public int speed = 3;

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

    public void updateMove(boolean[][] checkMove) {
        Random random = new Random();
        int[] arr = new int[4];
        int kc_left = speed;
        while (true) {
            if (checkMove[y + Sprite.SCALED_SIZE/8][x - kc_left] == false
            && checkMove[y + Sprite.SCALED_SIZE - Sprite.SCALED_SIZE/8][x - kc_left] == false) {
                arr[0] = 1;
                break;
            }
            kc_left--;
            if (kc_left == 0) break;
        }
        int kc_right = speed;
        while (true) {
            if (checkMove[y + Sprite.SCALED_SIZE/8][x + kc_right + Sprite.SCALED_SIZE] == false
            && checkMove[y + Sprite.SCALED_SIZE - Sprite.SCALED_SIZE/8][x + kc_right + Sprite.SCALED_SIZE] == false) {
                arr[1] = 1;
                break;
            }
            kc_right--;
            if (kc_right == 0) break;
        }
        int kc_up = speed;
        while (true) {
            if (checkMove[y - kc_up][x + Sprite.SCALED_SIZE/8] == false
                    && checkMove[y - kc_up][x + Sprite.SCALED_SIZE - Sprite.SCALED_SIZE/8] == false) {
                arr[2] = 1;
                break;
            }
            kc_up--;
            if (kc_up == 0) break;
        }
        int kc_down = speed;
        while (true) {
            if (checkMove[y + kc_down + Sprite.SCALED_SIZE][x + Sprite.SCALED_SIZE/8] == false
                    &&checkMove[y + Sprite.SCALED_SIZE + kc_down][x + Sprite.SCALED_SIZE - Sprite.SCALED_SIZE/8] == false) {
                arr[3] = 1;
                break;
            }
            kc_down--;
            if (kc_down == 0) break;
        }
        boolean check = false;
        for (int i = 0; i < 4; i++) {
            if (arr[i] == 1) {
                check = true;
                break;
            }
        }
        boolean newR = true;
        while (check == true) {
            int rd = random.nextInt(4);
            if (arr[rd] != 0) {
                if (timeMoveACell == 32 && newR) {
                    if (rd == 0) {
                        x = x - kc_left;
                        update();
                        checkPath = 0;
                    }
                    else if (rd == 1) {
                        x = x + kc_right;
                        update();
                        checkPath = 1;
                    }
                    else if (rd == 2) {
                        y = y - kc_up;
                        update();
                        checkPath = 2;
                    }
                    else if (rd == 3) {
                        y = y + kc_down;
                        update();
                        checkPath = 3; // trái, phải, trên, xuống
                    }
                    break;
                } else if (timeMoveACell == 0) {
                    if ((arr[0] == 1 && arr[1] == 1 && arr[2] == 0 && arr[3] == 0)
                    ||(arr[0] == 0 && arr[1] == 0 && arr[2] == 1 && arr[3] == 1)) {
                        timeMoveACell = 32;
                        newR = false;
                    }
                    else {
                        timeMoveACell = 32;
                    }
                } else {
                    if (checkPath == 0) {
                        x = x - kc_left;
                        update();
                    }
                    else if (checkPath == 1) {
                        x = x + kc_right;
                        update();
                    }
                    else if (checkPath == 2) {
                        y = y - kc_up;
                        update();
                    }
                    else if (checkPath == 3) {
                        y = y + kc_down;
                        update();
                    }
                    break;
                }
            }
        }
    }
}
