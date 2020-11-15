package Game;

import Entities.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public List<Entity> entityList = new ArrayList<>();

    public Board() {
    }

    public void update() {
        entityList.forEach(Entity::update);
    }

    public void render(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entityList.forEach(g -> g.render(gc));
    }
}
