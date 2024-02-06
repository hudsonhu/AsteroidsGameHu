package display;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerListener {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean fire;
    private boolean pause;
    private boolean shift;

    public void resetPause() {
        pause = false;
    }

    public boolean isPressingLeft() {
        return left;
    }

    public boolean isPressingRight() {
        return right;
    }

    public boolean isPressingUp() {
        return up;
    }

    public boolean isPressingFire() {
        return fire;
    }

    public boolean hasPressedPause() {
        return pause;
    }

    public boolean isPressingShift() {
        return shift;
    }

    public void setListeners(Scene s) {
        s.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.LEFT) {
                    left = true;
                }
                if (e.getCode() == KeyCode.RIGHT) {
                    right = true;
                }
                if (e.getCode() == KeyCode.SPACE) {
                    fire = true;
                }
                if (e.getCode() == KeyCode.P) {
                    pause = true;
                }
                if (e.getCode() == KeyCode.UP) {
                    up = true;
                }
                if (e.getCode() == KeyCode.SHIFT) {
                    shift = true;
                }
            }
        });
        s.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.LEFT) {
                    left = false;
                }
                if (e.getCode() == KeyCode.RIGHT) {
                    right = false;
                }
                if (e.getCode() == KeyCode.SPACE) {
                    fire = false;
                }
                if (e.getCode() == KeyCode.UP) {
                    up = false;
                }
                if (e.getCode() == KeyCode.SHIFT) {
                    shift = false;
                }
            }
        });

    }
}
