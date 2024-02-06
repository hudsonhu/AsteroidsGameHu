package display;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.GameCore;
import ucd.comp2011j.engine.Score;
import ucd.comp2011j.engine.ScoreKeeper;
import ucd.comp2011j.engine.Screen;

public class ScoreScreen implements Screen {
    private final ScoreKeeper scoreKeeper;
    private final Canvas canvas;

    public ScoreScreen(ScoreKeeper sc) {
        this.scoreKeeper = sc;
        this.canvas = new Canvas(GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
    }
    public Canvas getCanvas(){
        return canvas;
    }

    public void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Courier New", 36));
        gc.setFill(Color.WHITE);
        gc.fillText("Asteroid Game Hall of Fame", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 5.0);

        Score[] scores = scoreKeeper.getScores();
        gc.setFont(new Font("Courier New", 20));
        gc.setTextAlign(TextAlignment.LEFT);
        for (int i = 0; i < scores.length; i++) {
            Score score = scores[i];
            gc.fillText(score.getName(), 2 * GameCore.SCREEN_WIDTH / 6.5, 256 + i * 32);
            gc.fillText("" + score.getScore(), 4 * GameCore.SCREEN_WIDTH / 6.4, 256 + i * 32);
        }
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Courier New", 28));
        gc.fillText("Press M Back to main menu", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 10.0 * 9);
    }
}