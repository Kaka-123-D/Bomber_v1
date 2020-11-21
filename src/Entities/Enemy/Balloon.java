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
        //timeMoveACell--;
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

    @Override
    public void updateMove(boolean[][] checkMove) {
        Random random = new Random();
        int[] arr = new int[4];
        int kc_left = speed;
        while (true) {
            if (checkMove[y][x - kc_left] == false
                    && checkMove[y + 31][x - kc_left] == false) {
                if (x % 32 != 0) {
                    if (x - kc_left >= (x / 32) * 32) {
                        arr[0] = 1;
                        break;
                    }
                } else {
                    arr[0] = 1;
                    break;
                }
            }
            kc_left--;
            if (kc_left == 0) break;
        }
        int kc_right = speed;
        while (true) {
            if (checkMove[y][x + kc_right + 31] == false
                    && checkMove[y + 31][x + kc_right + 31] == false) {
                if (x % 32 != 0) {
                    if (x + 31 + kc_right <= ((((x + 31) / 32) + 1) * 32) - 1) {
                        arr[1] = 1;
                        break;
                    }
                } else {
                    arr[1] = 1;
                    break;
                }
            }
            kc_right--;
            if (kc_right == 0) break;
        }
        int kc_up = speed;
        while (true) {
            if (checkMove[y - kc_up][x] == false
                    && checkMove[y - kc_up][x + 31] == false) {
                if (y % 32 != 0) {
                    if (y - kc_up >= (y / 32) * 32) {
                        arr[2] = 1;
                        break;
                    }
                } else {
                    arr[2] = 1;
                    break;
                }
            }
            kc_up--;
            if (kc_up == 0) break;
        }
        int kc_down = speed;
        while (true) {
            if (checkMove[y + kc_down + 31][x] == false
                    && checkMove[y + 31 + kc_down][x + 31] == false) {
                if (y % 32 != 0) {
                    if (y + kc_down + 31 <= ((((y + 31) / 32) + 1) * 32) - 1) {
                        arr[3] = 1;
                        break;
                    }
                } else {
                    arr[3] = 1;
                    break;
                }
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
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        x = x - kc_left;
                        //update();
                        timeMoveACell = timeMoveACell - kc_left;
                        checkPath = 0;
                    }
                    else if (rd == 1) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        x = x + kc_right;
                        //update();
                        timeMoveACell = timeMoveACell - kc_right;
                        checkPath = 1;
                    }
                    else if (rd == 2) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        y = y - kc_up;
                        //update();
                        timeMoveACell = timeMoveACell - kc_up;
                        checkPath = 2;
                    }
                    else if (rd == 3) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        y = y + kc_down;
                        //update();
                        timeMoveACell = timeMoveACell - kc_down;
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
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        x = x - kc_left;
                        //update();
                        timeMoveACell = timeMoveACell - kc_left;
                    }
                    else if (checkPath == 1) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        x = x + kc_right;
                        //update();
                        timeMoveACell = timeMoveACell - kc_right;
                    }
                    else if (checkPath == 2) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        y = y - kc_up;
                        //update();
                        timeMoveACell = timeMoveACell - kc_up;
                    }
                    else if (checkPath == 3) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        y = y + kc_down;
                        //update();
                        timeMoveACell = timeMoveACell - kc_down;
                    }
                    break;
                }
            }
        }
    }
}
