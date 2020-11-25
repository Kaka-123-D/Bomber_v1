package Entities.Enemy;

import Entities.AnimateEntity;
import Entities.Entity;
import Game.Board;
import Graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

public class Oneal extends Enemy {

    public Oneal(int x, int y, Image img) {
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
        img = Sprite.oneal_dead.getFxImage();
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
            // Biến check xem boss có đuổi theo người hay ko?
            boolean checkBossVsMan = false;
            // Check xem người có đang xuất hiện trên cùng một cột với boss ko?
            if ((Board.bomberMan.getX() >= (x / 32) * 32
                    && Board.bomberMan.getX() <= ((x / 32) + 1) * 32
                    && Board.bomberMan.imasu)
                    || (Board.bomberMan.getX() + 31 >= (x / 32) * 32
                    && Board.bomberMan.getX() + 31 <= ((x / 32) + 1) * 32
                    && Board.bomberMan.imasu)) {
                // Biến check xem trên đường đi đó có bomb, tường hay ko?
                boolean checkBombVsWall = false;
                if (y < Board.bomberMan.getY()) {
                    for (int i = y; i <= Board.bomberMan.getY(); i++) {
                        if (checkMove[i][x] == true) {
                            checkBombVsWall = true;
                            break;
                        }
                    }
                } else {
                    for (int i = Board.bomberMan.getY(); i <= y; i++) {
                        if (checkMove[i][x] == true) {
                            checkBombVsWall = true;
                            break;
                        }
                    }
                }
                if (!checkBombVsWall) {
                    // Tính kc giữa người và boss
                    int kcBossY = y - Board.bomberMan.getY();
                    // Nếu boss ở trên người và có thể đi xuống dưới được
                    if (kcBossY < 0 && arr[3] == 1) {
                        // Tăng tốc độ
                        speed = 2;
                        setAnimate();
                        // Hoạt hoạc của boss
                        img = Sprite.movingSprite(Sprite.oneal_right1,
                                Sprite.oneal_right2,
                                Sprite.oneal_right3,
                                animate, 20).getFxImage();
                        // Boss di chuyển xuống dưới
                        y = y + kc_down;
                        // Lưu hướng di chuyển lại
                        checkPath = 3;
                        // Lưu thời gian di chuyển lại
                        timeMoveACell = (((y + 32) / 32 + 1) * 32) - (y + 32);
                        checkBossVsMan = true;
                        break;
                    }
                    // Nếu boss ở dưới người và có thể đi lên dưới được
                    else if (kcBossY > 0 && arr[2] == 1) {
                        // Tăng tốc độ
                        speed = 2;
                        setAnimate();
                        // Hoạt hoạc của boss
                        img = Sprite.movingSprite(Sprite.oneal_left1,
                                Sprite.oneal_left2,
                                Sprite.oneal_left3,
                                animate, 20).getFxImage();
                        // Boss di chuyển lên trên.
                        y = y - kc_up;
                        // Lưu hướng di chuyển lại
                        checkPath = 2;
                        // Lưu thời gian di chuyển lại
                        timeMoveACell = y - (y / 32) * 32;
                        checkBossVsMan = true;
                        break;
                    }
                }
            }
            // Check xem người có đang xuất hiện trên cùng một hàng với boss ko?
            if ((Board.bomberMan.getY() >= (y / 32) * 32
                    && Board.bomberMan.getY() <= ((y / 32) + 1) * 32
                    && Board.bomberMan.imasu)
                    || (Board.bomberMan.getY() + 31 >= (y / 32) * 32
                    && Board.bomberMan.getY() + 31 <= ((y / 32) + 1) * 32
                    && Board.bomberMan.imasu)) {
                // Biến check xem trên đường đi đó có bomb, tường hay ko?
                boolean checkBombVsWall = false;
                if (x < Board.bomberMan.getX()) {
                    for (int i = x; i <= Board.bomberMan.getX(); i++) {
                        if (checkMove[y][i]  == true) {
                            checkBombVsWall = true;
                            break;
                        }
                    }
                } else {
                    for (int i = Board.bomberMan.getX(); i <= x; i++) {
                        if (checkMove[y][i]  == true) {
                            checkBombVsWall = true;
                            break;
                        }
                    }
                }
                if (!checkBombVsWall) {
                    // Tính kc giữa người và boss
                    int kcBossX = x - Board.bomberMan.getX();
                    // Nếu boss ở bên trái người người và có thể đi sang phải được.
                    if (kcBossX < 0 && arr[1] == 1) {
                        // Tăng tốc độ
                        speed = 2;
                        setAnimate();
                        // Hoạt họa của boss
                        img = Sprite.movingSprite(Sprite.oneal_right1,
                                Sprite.oneal_right2,
                                Sprite.oneal_right3,
                                animate, 20).getFxImage();
                        // Di chuyển sang phải
                        x = x + kc_right;
                        // Lưu hướng đi lại.
                        checkPath = 1;
                        // Lưu thời gian đi lại.
                        timeMoveACell = ((x + 32) / 32 + 1) * 32 - (x + 32);
                        checkBossVsMan = true;
                        break;
                    }
                    // Nếu boss ở bên phải người người và có thể đi sang trái được.
                    else if (kcBossX > 0 && arr[0] == 1) {
                        // Tăng tốc.
                        speed = 2;
                        setAnimate();
                        // Hoạt họa của boss
                        img = Sprite.movingSprite(Sprite.oneal_left1,
                                Sprite.oneal_left2,
                                Sprite.oneal_left3,
                                animate, 20).getFxImage();
                        // Di chuyển sang trái
                        x = x - kc_left;
                        // Lưu hướng di chuyển lại
                        checkPath = 0;
                        // Lưu thời gian di chuyển lại
                        timeMoveACell = x - (x / 32) * 32;
                        checkBossVsMan = true;
                        break;
                    }
                }
            }
            if (!checkBossVsMan) {
                // Giảm tốc độ
                speed = 1;
                // Chọn bất kì 1 trong 4 hướng để đi
                int rd = random.nextInt(4);
                if (arr[rd] != 0) {
                    // Nếu vị trí nằm bắt đầu một ô và có thể đi hướng mới
                    if (timeMoveACell == 32 && checkNewRun) {
                        if (rd == 0) {
                            setAnimate();
                            img = Sprite.movingSprite(Sprite.oneal_left1,
                                    Sprite.oneal_left2,
                                    Sprite.oneal_left3,
                                    animate, 20).getFxImage();
                            // Đi sang trái
                            x = x - kc_left;
                            // Giảm thời gian đi.
                            timeMoveACell = timeMoveACell - kc_left;
                            // Lưu hướng di.
                            checkPath = 0;
                        } else if (rd == 1) {
                            setAnimate();
                            img = Sprite.movingSprite(Sprite.oneal_right1,
                                    Sprite.oneal_right2,
                                    Sprite.oneal_right3,
                                    animate, 20).getFxImage();
                            // Đi sang phải.
                            x = x + kc_right;
                            // Giảm thời gian đi.
                            timeMoveACell = timeMoveACell - kc_right;
                            // Lưu hướng đi.
                            checkPath = 1;
                        } else if (rd == 2) {
                            setAnimate();
                            img = Sprite.movingSprite(Sprite.oneal_left1,
                                    Sprite.oneal_left2,
                                    Sprite.oneal_left3,
                                    animate, 20).getFxImage();
                            // Đi lên trên.
                            y = y - kc_up;
                            // Giảm thời gian đi.
                            timeMoveACell = timeMoveACell - kc_up;
                            // Lưu hướng đi.
                            checkPath = 2;
                        } else if (rd == 3) {
                            setAnimate();
                            img = Sprite.movingSprite(Sprite.oneal_right1,
                                    Sprite.oneal_right2,
                                    Sprite.oneal_right3,
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
                            img = Sprite.movingSprite(Sprite.oneal_left1,
                                    Sprite.oneal_left2,
                                    Sprite.oneal_left3,
                                    animate, 20).getFxImage();
                            // Đi sang trái.
                            x = x - kc_left;
                            // Giảm thời gian đi.
                            timeMoveACell = timeMoveACell - kc_left;
                        } else if (checkPath == 1) {
                            setAnimate();
                            img = Sprite.movingSprite(Sprite.oneal_right1,
                                    Sprite.oneal_right2,
                                    Sprite.oneal_right3,
                                    animate, 20).getFxImage();
                            // Đi sang phải.
                            x = x + kc_right;
                            //Giảm thời gian đi.
                            timeMoveACell = timeMoveACell - kc_right;
                        } else if (checkPath == 2) {
                            setAnimate();
                            img = Sprite.movingSprite(Sprite.oneal_left1,
                                    Sprite.oneal_left2,
                                    Sprite.oneal_left3,
                                    animate, 20).getFxImage();
                            // Đi lên trên.
                            y = y - kc_up;
                            // Giảm thời gian đi.
                            timeMoveACell = timeMoveACell - kc_up;
                        } else if (checkPath == 3) {
                            setAnimate();
                            img = Sprite.movingSprite(Sprite.oneal_right1,
                                    Sprite.oneal_right2,
                                    Sprite.oneal_right3,
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
//        System.out.print(" ");
//        for (int j = 0; j < checkMove[0].length; j++) {
//            System.out.print(j);
//        }
//        System.out.println();
//        for (int i = 0; i < checkMove.length; i++) {
//            System.out.print(i);
//            for (int j = 0; j < checkMove[0].length; j++) {
//                if (checkMove[i][j] == false) {
//                    System.out.print(" ");
//                } else {
//                    System.out.print("*");
//                }
//            }
//            System.out.println();
//        }
    }
}
