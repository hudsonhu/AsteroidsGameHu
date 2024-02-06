package display;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.GameCore;
import ucd.comp2011j.engine.Screen;

public class AboutScreen implements Screen {
    private final boolean menu = false;
    private final Canvas canvas;
    private PlayerListener listener;

    public AboutScreen() {
        canvas = new Canvas(GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Courier New", 36));
        gc.setFill(Color.WHITE);
        gc.fillText("Keyboard Controls", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 5.0);

        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Courier New", 20));
        int start = 256;
        int gap = 48;
        gc.fillText("Accelerate", GameCore.SCREEN_WIDTH / 4.0, start);
        gc.fillText("UP arrow", 4 * GameCore.SCREEN_WIDTH / 6.0 - 30, start);
        gc.fillText("Move Left", GameCore.SCREEN_WIDTH / 4.0, start + gap);
        gc.fillText("LEFT arrow", 4 * GameCore.SCREEN_WIDTH / 6.0 - 30, start + gap);
        gc.fillText("Move Right", GameCore.SCREEN_WIDTH / 4.0, start + 2 * gap);
        gc.fillText("RIGHT arrow", 4 * GameCore.SCREEN_WIDTH / 6.0 - 30, start + 2 * gap);
        gc.fillText("Fire", GameCore.SCREEN_WIDTH / 4.0, start + 3 * gap);
        gc.fillText("SPACE bar", 4 * GameCore.SCREEN_WIDTH / 6.0 - 30, start + 3 * gap);
        gc.fillText("Hyperspace Jump", GameCore.SCREEN_WIDTH / 4.0, start + 4 * gap);
        gc.fillText("L/R SHIFT", 4 * GameCore.SCREEN_WIDTH / 6.0 - 30, start + 4 * gap);
        gc.fillText("Play/Pause", GameCore.SCREEN_WIDTH / 4.0, start + 5 * gap);
        gc.fillText("p", 4 * GameCore.SCREEN_WIDTH / 6.0 - 30, start + 5 * gap);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Courier New", 28));
        gc.fillText("Press M Back to main menu", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 10.0 * 9);
    }
}
