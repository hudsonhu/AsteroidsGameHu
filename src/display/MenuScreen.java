package display;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.GameCore;
import ucd.comp2011j.engine.Screen;

public class MenuScreen implements Screen {
    private final Canvas canvas;

    public MenuScreen() {
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
        gc.setFont(new Font("Georgia", 48));
        gc.setFill(Color.WHITE);
        gc.fillText("Welcome to Asteroid Game!", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 5.0);
        gc.setFont(new Font("Courier New", 24));
        gc.fillText("To start a new game  Press N", GameCore.SCREEN_WIDTH / 2.0, 4 * GameCore.SCREEN_HEIGHT / 10.0);
        gc.fillText("To see the controls  Press A", GameCore.SCREEN_WIDTH / 2.0, 5 * GameCore.SCREEN_HEIGHT / 10.0);
        gc.fillText("To see the High scores  Press H", GameCore.SCREEN_WIDTH / 2.0, 6 * GameCore.SCREEN_HEIGHT / 10.0);
        gc.fillText("To exit  Press X", GameCore.SCREEN_WIDTH / 2.0, 5 * GameCore.SCREEN_HEIGHT / 6.0);
    }
}
