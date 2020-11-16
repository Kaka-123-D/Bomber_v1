package Game;

import Entities.Entity;
import Entities.Player.Bomb;
import Entities.Player.BomberMan;
import Graphics.Sprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public List<Entity> entityList = new ArrayList<>();
    public BomberMan bomberMan = new BomberMan(1, 1, Sprite.player_right.getFxImage());


    public Board() {
    }

    public void update() {
        entityList.forEach(Entity::update);
    }

    public void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).render(gc);
        }
        bomberMan.render(gc);
    }
}
