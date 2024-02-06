package model;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class FlyingRock implements Hittable{
    private boolean alive;
    private double x, y;
    private final double speedX;
    private final double speedY;
    private final double direction;
    private double speed;
    private final int style;
    private final int scale;
    private Polygon hitbox;

    private final double[][] rockShape = {
            {0, +1, 1, +2, 2, +1.5, 1.3, -0.5, 2, -1.5, 0.5, -2, -0.8, -2, -1.5, -1.5, -2, 1, -1, +2},
            {0,-1.2,1,-2,2,-0.8,0.8,-0.4,2,0.5,0.7,1.8,-0.5,1.2,-0.6,1.8,-1.3,1,-1,0.2,-1.5,-0.8,-0.6,-2}};

    public FlyingRock(double x, double y, double direction, double speed, int style, int scale) {
        Random random = new Random();
        this.x = x;
        this.y = y;
        this.style = style;
        this.alive = true;
        this.direction = direction;
        this.speed = speed;
        this.scale = scale;
        speedX = speed / 4.0 * (Math.sin(direction / 180 * 3.141592654));
        speedY = speed / 4.0 * (Math.cos(direction / 180 * 3.141592654));
        double[] adjustedRockShape = new double[rockShape[style].length];

        for (int i = 0; i < rockShape[style].length; i += 2){
            adjustedRockShape[i] = rockShape[style][i] * scale + x;
            adjustedRockShape[i + 1] = rockShape[style][i + 1] * scale + y;
        }
        hitbox = new Polygon(adjustedRockShape);
    }

    public int getStyle() {
        return style;
    }

    public double[][] getRockShape() {
        return rockShape;
    }

    public boolean isHit(Bullet b) {
        boolean hit = b.getHitBox().intersects(hitbox.getBoundsInParent());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public boolean isHit(Player p) {
        boolean hit = p.getHitBox().intersects(hitbox.getBoundsInParent());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public boolean isIntersect(Polygon p) {
        return p.intersects(hitbox.getBoundsInParent());
    }

    public boolean isHit(UFO ufo) {
        boolean hit = ufo.getHitBox().intersects(hitbox.getBoundsInParent());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 500 / scale;
    }

    public boolean isPlayer() {
        return false;
    }

    public void move() {
        x = (x + speedX) % 1024;
        y = (y + speedY) % 768;
        if (x <= 0) x += 1024;
        if (y <= 0) y += 768;
        double[] adjustedRockShape = new double[rockShape[style].length];

        for (int i = 0; i < rockShape[style].length; i += 2){
            adjustedRockShape[i] = rockShape[style][i] * scale + x;
            adjustedRockShape[i + 1] = rockShape[style][i + 1] * scale + y;
        }
        hitbox = new Polygon(adjustedRockShape);
    }

    public Polygon getHitBox() {
        return hitbox;
    }

    public int getScale() {
        return scale;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection(){
        return direction;
    }

    public void destroy() {
        alive = false;
    }

    @Override
    public boolean canSplit() {
        return scale > 5;
    }

    @Override
    public boolean isUFO() {
        return false;
    }

    public double getSpeed(){
        return speed;
    }
}
