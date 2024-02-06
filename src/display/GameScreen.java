package display;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.*;
import ucd.comp2011j.engine.Screen;

public class GameScreen implements Screen {
    private final GameCore game;
    private final Canvas canvas;
    private int tick = 0;
    private int b_tick = 0;

    public Canvas getCanvas() {
        return canvas;
    }

    public GameScreen(GameCore game) {
        this.game = game;
        this.canvas = new Canvas(GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
    }

    private void drawShape(GraphicsContext gc, Player p) {
        double x = p.getX();
        double y = p.getY();
        double direction = p.getDirection();
        double[] x_coords = new double[]{Math.sin(direction / 180 * 3.141592654),
                Math.sin((direction + 150) / 180 * 3.141592654),
                Math.sin((direction + 210) / 180 * 3.141592654)};
        double[] y_coords = new double[]{Math.cos(direction / 180 * 3.141592654),
                Math.cos((direction + 150) / 180 * 3.141592654),
                Math.cos((direction + 210) / 180 * 3.141592654)};
        double[] x_adjusted = new double[x_coords.length];
        double[] y_adjusted = new double[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Player.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Player.SHIP_SCALE;
        }
        gc.setStroke(Color.WHITE);
        gc.strokePolygon(x_adjusted, y_adjusted, x_adjusted.length);

        if (p.isAccelerating()) {
            int fireHeight = 7;
            tick++;
            if (p.hasBooster()) fireHeight = 14;
            if (tick <= 4) {
                double[] x_fire = new double[]{fireHeight * Math.sin((direction - 180) / 180 * 3.141592654),
                        5 * Math.sin((direction + 165) / 180 * 3.141592654),
                        5 * Math.sin((direction + 195) / 180 * 3.141592654)};
                double[] y_fire = new double[]{fireHeight * Math.cos((direction - 180) / 180 * 3.141592654),
                        5 * Math.cos((direction + 165) / 180 * 3.141592654),
                        5 * Math.cos((direction + 195) / 180 * 3.141592654)};
                double[] x_fire_adjusted = new double[x_coords.length];
                double[] y_fire_adjusted = new double[y_coords.length];
                for (int i = 0; i < x_coords.length; i++) {
                    x_fire_adjusted[i] = x + x_fire[i] * 5;
                    y_fire_adjusted[i] = y + y_fire[i] * 5;
                }
                if (tick <= 2) {
                    gc.setFill(Color.RED);
                    gc.fillPolygon(x_fire_adjusted, y_fire_adjusted, x_fire_adjusted.length);
                } else {
                    gc.setStroke(Color.RED);
                    gc.strokePolygon(x_fire_adjusted, y_fire_adjusted, x_fire_adjusted.length);
                }
            } else tick = 0;
        }

        if (p.hasShield()) {
            gc.setStroke(Color.WHITE);
            gc.strokeOval(x - 45, y - 45, 90, 90);
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeOval(x - 47, y - 47, 94, 94);
        }
    }

    private void drawShape(GraphicsContext gc, Bullet b) {
        gc.setFill(Color.YELLOWGREEN);
        // gc.fillRect(b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
        gc.fillOval(b.getX() - 1.5, b.getY() - 1.5, 3, 3);
    }

    private void drawShape(GraphicsContext gc, Bonus b) {
        gc.setFill(Color.YELLOW);
        gc.setStroke(Color.YELLOW);
        if (b_tick < 20) gc.fillOval(b.getX() - 10, b.getY() - 10, 20, 20);
        else gc.strokeOval(b.getX() - 10, b.getY() - 10, 20, 20);
    }

    private void drawShape(GraphicsContext gc, FlyingRock rock) {
        gc.setStroke(Color.WHITE);
        double x = rock.getX();
        double y = rock.getY();
        int style = rock.getStyle();
        double[][] rockShape = rock.getRockShape();
        int scale = rock.getScale();
        double[] x_adjusted = new double[rockShape[style].length / 2];
        double[] y_adjusted = new double[rockShape[style].length / 2];
        for (int i = 0; i < rockShape[style].length; i += 2) {
            x_adjusted[i / 2] = rockShape[style][i] * scale + x;
            y_adjusted[i / 2] = rockShape[style][i + 1] * scale + y;
        }
        gc.strokePolygon(x_adjusted, y_adjusted, x_adjusted.length);
    }


    private void drawShape(GraphicsContext gc, UFO ufo) {
        gc.setStroke(Color.WHITE);
        double x = ufo.getX();
        double y = ufo.getY();
        int scale = ufo.getScale();
        double[][] ufoShape = {{-1.8, 0, -1, -1.8, 1, -1.8, 1.8, 0}, {-3, 1, -1.8, 0, 1.8, 0, 3, 1}, {-1.8, 2, -3, 1, 3, 1, 1.8, 2}};
        for (double[] shape : ufoShape) {
            double[] x_adjusted = new double[shape.length / 2];
            double[] y_adjusted = new double[shape.length / 2];
            for (int i = 0; i < shape.length; i += 2) {
                x_adjusted[i / 2] = shape[i] * scale + x;
                y_adjusted[i / 2] = shape[i + 1] * scale + y;
            }
            gc.strokePolygon(x_adjusted, y_adjusted, x_adjusted.length);
        }
    }


    public void paint() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
        if (game != null) {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
            gc.setFill(Color.WHITESMOKE);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setTextBaseline(VPos.TOP);
            gc.setFont(new Font("Courier New", 24));
            gc.fillText("Lives: " + game.getLives(), 10, 10);
            gc.fillText("Level: " + game.getCurrentLevel(), 10, 40);
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.fillText("Score: " + game.getPlayerScore(), GameCore.SCREEN_WIDTH - 10, 10);
            drawShape(gc, game.getShip());


            for (Bullet bullet : game.getBullets()) {
                drawShape(gc, bullet);
            }
            for (FlyingRock s : game.getRocks()) {
                drawShape(gc, s);
            }
            for (UFO ufo : game.getUFOs()) {
                drawShape(gc, ufo);
            }
            for (Bonus bonus : game.getBonuses()) {
                drawShape(gc, bonus);
            }

            if ((game.isPaused() || !game.isPlayerAlive()) && game.getLives() > 0) {
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setTextBaseline(VPos.CENTER);
                gc.setFont(new Font("Courier New", 64));
                gc.setFill(Color.WHITE);
                gc.fillText("PAUSED", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 2.4);
                gc.setFont(new Font("Courier New", 24));
                gc.fillText("Press 'p' to continue", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 2.0);

            } else if (!game.isPlayerAlive() && game.getLives() == 0) {
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setTextBaseline(VPos.CENTER);
                gc.setFont(new Font("Courier New", 48));
                gc.setFill(Color.WHITE);
                gc.fillText("GAME OVER", GameCore.SCREEN_WIDTH / 2.0, GameCore.SCREEN_HEIGHT / 2.0);
            }

            b_tick++;
            if (b_tick >= 40) b_tick = 0;
        }
    }
}
