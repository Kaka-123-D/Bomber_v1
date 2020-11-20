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
        if (checkMove[y][x - 1] == false) arr[0] = 1;

        if (checkMove[y][x + 1 + 32] == false) arr[1] = 1;

        if (checkMove[y - 1][x] == false
        && checkMove[y - 1][x + 31] == false) arr[2] = 1;

        if (checkMove[y + 1 + 32][x] == false
        &&checkMove[y + 32 + 1][x + 31] == false) arr[3] = 1;

        boolean check = false;
        for (int i = 0; i < 4; i++) {
            if (arr[i] == 1) {
                check = true;
                break;
            }
        }

        while (check == true) {
            int rd = random.nextInt(4);
            if (arr[rd] != 0) {
                if (timeMoveACell == 32) {
                    if (rd == 0) {
                        x--;
                        update();
                        checkPath = 0;
                    }
                    else if (rd == 1) {
                        x++;
                        update();
                        checkPath = 1;
                    }
                    else if (rd == 2) {
                        y--;
                        update();
                        checkPath = 2;
                    }
                    else if (rd == 3) {
                        y++;
                        update();
                        checkPath = 3; // trái, phải, trên, xuống
                    }
                    break;
                } else if (timeMoveACell == 0) {
                    timeMoveACell = 32;
                } else {
                    if (checkPath == 0) {
                        x--;
                        update();
                    }
                    else if (checkPath == 1) {
                        x++;
                        update();
                    }
                    else if (checkPath == 2) {
                        y--;
                        update();
                    }
                    else if (checkPath == 3) {
                        y++;
                        update();
                    }
                    break;
                }
            }
        }
    }
}
