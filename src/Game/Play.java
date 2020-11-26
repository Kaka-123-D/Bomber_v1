package Game;

import Audio.Music;
import Graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Play extends Application {

    public static Group root = new Group();
    private GraphicsContext gc;
    private Canvas canvas;
    public String fileMap = "res/levels/Level1.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Board board = new Board(fileMap);

        canvas = new Canvas(Sprite.SCALED_SIZE * board.getColumn(), Sprite.SCALED_SIZE * board.getRow() + 50);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);                             // root[0] = canvas

        addTextToGroup(50, 32, "", 30);             // root[1] = time
        addTextToGroup(430, 32, "", 30);            // root[2] = score
        addTextToGroup(800, 32, "", 30);            // root[3] = lives
        addTextToGroup(330, 250, "Stage 1", 100);   // root[4] = level

        Scene scene = new Scene(root);

        KeyControl control = new KeyControl(board, scene);
        control.catchEvent();

        stage.setScene(control.scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                board.update();
                if (board.bomberMan.imasu && Board.timeOpen < 0) {
                    control.xuLi();
                    board.bomberMan.updateEvent(control.xControl, control.yControl, control.imgPlayer);
                }
                board.render(gc, canvas);
            }
        };
        timer.start();
    }

    public static void addTextToGroup(int x, int y, String name, int size) {
        Font font = Font.font("System", FontWeight.BOLD, size);
        Text text = new Text(x, y, name);
        text.setFill(Color.WHITE);
        text.setFont(font);
        root.getChildren().add(text);
    }
}
