package model;

import javafx.scene.shape.Polygon;

import java.util.List;

import static java.lang.Math.abs;

public class UFO implements Hittable{
    private boolean alive = true;
    private double x, y;
    private double speedX, speedY;
    private double direction;
    private int point;
    private int tick;

    private Polygon hitbox;

    private final int scale = 6;
    private final double t = 2;
    final double speed = 5;

    private final double[] UFOShape = {-1,-1.8,1,-1.8,1.8,0,3,1,1.8,2,-1.8,2,-3,1,-1.8,0};

    public UFO(double x, double y, double direction) {
        this.x = x;
        this.y = y;
        this.alive = true;
        this.direction = direction;
        speedX = speed / 4.0 * (Math.sin(direction / 180 * 3.141592654));
        speedY = speed / 4.0 * (Math.cos(direction / 180 * 3.141592654));
        double[] adjustedShape = new double[UFOShape.length];

        for (int i = 0; i < UFOShape.length; i += 2){
            adjustedShape[i] = UFOShape[i] * scale + x;
            adjustedShape[i + 1] = UFOShape[i + 1] * scale + y;
        }
        hitbox = new Polygon(adjustedShape);
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

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 200;
    }

    public boolean isPlayer() {
        return false;
    }

    public void move(List<FlyingRock> rocks) {
        tick = (tick + 1) % 180;

        for (FlyingRock rock : rocks){
            if (abs(rock.getX() - x) * abs(rock.getX() - x) + abs(rock.getY() - y) * abs(rock.getY() - y) < 10000) {
                direction = rock.getDirection() + 90;
            }
        }
        speedX = speed / 4.0 * (Math.sin(direction / 180 * 3.141592654));
        speedY = speed / 4.0 * (Math.cos(direction / 180 * 3.141592654));
        x = (x + speedX) % 1024;
        y = (y + speedY) % 768;
        if (x <= 0) x += 1024;
        if (y <= 0) y += 768;
        double[] adjustedRockShape = new double[UFOShape.length];

        for (int i = 0; i < UFOShape.length; i += 2){
            adjustedRockShape[i] = UFOShape[i] * scale + x;
            adjustedRockShape[i + 1] = UFOShape[i + 1] * scale + y;
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

    public Bullet fire(Player player) {
        Bullet b = null;
        if (tick % 179 == 0) { //179
            // fire
            double dx = player.getX() - x;
            double dy = player.getY() - y;
            double bulletDirection = Math.atan2(dy + t * player.getSpeedY(), dx + t * player.getSpeedX()) + 3.1415926;
            bulletDirection = -bulletDirection / 3.1415926 * 180 - 90;
            b = new Bullet(x, y, bulletDirection);
        }
        return b;
    }

    @Override
    public boolean canSplit() {
        return scale > 5;
    }

    @Override
    public boolean isUFO() {
        return true;
    }
}
