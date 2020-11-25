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
        // Mảng kiểm tra hướng có đi được ko 1: là đi được, 0: là ko đi được
        // arr[0] là left, 1 là right, 2 là up, 3 là down
        int[] arr = new int[4];
        // Khởi tạo tốc độ ban đầu = tốc độ mặc định
        int kc_left = speed;
        while (true) {
            // 31 là kc từ góc trên đến góc dưới
            // check góc trên trái và góc dưới trái xem có đi được k?
            if (checkMove[y][x - kc_left] == false
                    && checkMove[y + 31][x - kc_left] == false) {
                //Nếu như boss không nằm trọn trong 1 trống thì sẽ chạy đến khi nằm trọn trong 1 ô trống
                if (x % 32 != 0) {
                    // (x - kc_left):là tọa độ chuẩn bị đi tới.
                    // ((x / 32) * 32):là điểm giới hạn ta sẽ đi được tới
                    if (x - kc_left >= (x / 32) * 32) {
                        arr[0] = 1;
                        break;
                    }
                } else {
                    arr[0] = 1;
                    break;
                }
            }
            // Giảm tốc độ xuống nếu ko thể đi qua.
            kc_left--;
            // Nếu ko thể đi qua thì break.
            if (kc_left == 0) break;
        }

        // Khởi tạo tốc độ ban đầu = tốc độ mặc định
        int kc_right = speed;
        while (true) {
            // check góc trên phải và góc dưới phải xem có đi được ko?
            // 31 là kc từ góc trên đến góc dưới
            if (checkMove[y][x + kc_right + 31] == false
                    && checkMove[y + 31][x + kc_right + 31] == false) {
                // (x + 31 + kc_right):là tọa độ chuẩn bị đi tới.
                // (((((x + 31) / 32) + 1) * 32) - 1):là điểm giới hạn ta sẽ đi được tới
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
            // Giảm tốc độ xuống nếu ko thể đi qua.
            kc_right--;
            // Nếu ko thể đi qua thì break.
            if (kc_right == 0) break;
        }

        // Khởi tạo tốc độ ban đầu = tốc độ mặc định
        int kc_up = speed;
        while (true) {
            // 31 là kc từ góc trên đến góc dưới
            // check góc trên trái và góc trên phải xem có đi được ko?
            if (checkMove[y - kc_up][x] == false
                    && checkMove[y - kc_up][x + 31] == false) {
                // (y - kc_up):là tọa độ chuẩn bị đi tới.
                // ((y / 32) * 32):là điểm giới hạn ta sẽ đi được tới
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
            // Giảm tốc độ xuống nếu ko thể đi qua.
            kc_up--;
            // Nếu ko thể đi qua thì break.
            if (kc_up == 0) break;
        }

        // Khởi tạo tốc độ ban đầu = tốc độ mặc định
        int kc_down = speed;
        while (true) {
            // 31 là kc từ góc trên đến góc dưới
            // check góc trái dưới và góc phải dưới xem có đi được ko?
            if (checkMove[y + kc_down + 31][x] == false
                    && checkMove[y + 31 + kc_down][x + 31] == false) {
                if (y % 32 != 0) {
                    // (y + kc_down + 31):là tọa độ chuẩn bị đi tới.
                    // (((((y + 31) / 32) + 1) * 32) - 1):là điểm giới hạn ta sẽ đi được tới
                    if (y + kc_down + 31 <= ((((y + 31) / 32) + 1) * 32) - 1) {
                        arr[3] = 1;
                        break;
                    }
                } else {
                    arr[3] = 1;
                    break;
                }
            }
            // Giảm tốc độ xuống nếu ko thể đi qua.
            kc_down--;
            // Nếu ko thể đi qua thì break.
            if (kc_down == 0) break;
        }

        // Biến check xem có hướng đi nào đi được hay ko?
        boolean checkEmpty = false;
        for (int i = 0; i < 4; i++) {
            if (arr[i] == 1) {
                checkEmpty = true;
                break;
            }
        }
        // Biến check xem có phải đi sang một hướng đi mới hay ko, = true là được đi hướng mới, = false là vẫn đi hướng cũ
        boolean checkNewRun = true;
        while (checkEmpty == true) {
            // Chọn bất kì 1 trong 4 hướng để đi
            int rd = random.nextInt(4);
            if (arr[rd] != 0) {
                // Nếu vị trí nằm bắt đầu một ô và có thể đi hướng mới
                if (timeMoveACell == 32 && checkNewRun) {
                    if (rd == 0) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        // Đi sang trái
                        x = x - kc_left;
                        // Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_left;
                        // Lưu hướng di.
                        checkPath = 0;
                    } else if (rd == 1) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        // Đi sang phải.
                        x = x + kc_right;
                        // Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_right;
                        // Lưu hướng đi.
                        checkPath = 1;
                    } else if (rd == 2) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        // Đi lên trên.
                        y = y - kc_up;
                        // Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_up;
                        // Lưu hướng đi.
                        checkPath = 2;
                    } else if (rd == 3) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        // Đi xuống dưới.
                        y = y + kc_down;
                        // Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_down;
                        // Lưu hướng đi.
                        checkPath = 3;
                    }
                    break;
                }
                // Nếu sau khi đi hết 1 ô
                else if (timeMoveACell == 0) {
                    // Nếu vẫn là đường đi cũ thì vẫn đi tiếp
                    if ((arr[0] == 1 && arr[1] == 1 && arr[2] == 0 && arr[3] == 0)
                            || (arr[0] == 0 && arr[1] == 0 && arr[2] == 1 && arr[3] == 1)) {
                        timeMoveACell = 32;
                        checkNewRun = false;
                    }
                    // Nếu không phải đường đi cũ thì sẽ đi hướng mới.
                    else {
                        timeMoveACell = 32;
                    }
                }
                // Đi theo hướng cũ.
                else {
                    if (checkPath == 0) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        // Đi sang trái.
                        x = x - kc_left;
                        // Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_left;
                    } else if (checkPath == 1) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        // Đi sang phải.
                        x = x + kc_right;
                        //Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_right;
                    } else if (checkPath == 2) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_left1,
                                Sprite.balloom_left2,
                                Sprite.balloom_left3,
                                animate, 20).getFxImage();
                        // Đi lên trên.
                        y = y - kc_up;
                        // Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_up;
                    } else if (checkPath == 3) {
                        setAnimate();
                        img = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2,
                                Sprite.balloom_right3,
                                animate, 20).getFxImage();
                        // Đi xuống dưới.
                        y = y + kc_down;
                        // Giảm thời gian đi.
                        timeMoveACell = timeMoveACell - kc_down;
                    }
                    break;
                }
            }
        }
    }
}
