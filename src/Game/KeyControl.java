package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyControl implements KeyListener {

    private boolean[] keys = new boolean[90]; // VK_W = 87 -> 90 là đủ.
    public boolean UP;
    public boolean DOWN;
    public boolean LEFT;
    public boolean RIGHT;
    public boolean SPACE;

    public void update() {
        UP = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        DOWN = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        LEFT = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        RIGHT = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        SPACE = keys[KeyEvent.VK_SPACE];
    }

    @Override
    public void keyTyped(KeyEvent event) {}

    @Override
    public void keyPressed(KeyEvent event) {
        keys[event.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keys[event.getKeyCode()] = false;

    }
}
