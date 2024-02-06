package model;

import javafx.scene.shape.Polygon;

import java.util.List;
import java.util.Random;

public class Player implements Hittable{
    private double x, y;
    private double speed, speedX, speedY;
    private double direction = 180;
    private Polygon hitBox;
    private int weaponCountdown, shieldCountdown, duoCountdown, boosterCountdown, hyperJumpCountdown = 0;
    private boolean alive = true, isAccelerating = false, hasShield = false, duoBullets = false, booster = false;
    public static final double SHIP_SCALE = 25;
    Random random;

    public Player() {
        x = 512;
        y = 450;
        hitBox = new Polygon(x + SHIP_SCALE * Math.sin(direction / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos(direction / 180 * 3.141592654),
                x + SHIP_SCALE * Math.sin((direction + 150) / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos((direction + 150) / 180 * 3.141592654),
                x + SHIP_SCALE * Math.sin((direction + 210) / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos((direction + 210) / 180 * 3.141592654));

        random = new Random();
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

    public boolean isHit(Bullet b) {
        boolean hit = hitBox.intersects(b.getHitBox().getBoundsInParent()) && !hasShield;
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public void tick() {
        if (weaponCountdown > 0) {
            weaponCountdown--;
        } else {
            weaponCountdown = 8;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void resetDestroyed() {
        alive = true;
        x = 512;
        y = 450;
        speedX = 0;
        speedY = 0;
        direction = 180;
        hitBox = new Polygon(x + SHIP_SCALE * Math.sin(direction / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos(direction / 180 * 3.141592654),
                x + SHIP_SCALE * Math.sin((direction + 150) / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos((direction + 150) / 180 * 3.141592654),
                x + SHIP_SCALE * Math.sin((direction + 210) / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos((direction + 210) / 180 * 3.141592654));
        duoBullets = false;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return true;
    }

    public Polygon getHitBox() {
        return hitBox;
    }

    public boolean hasDuoBullets(){
        return duoBullets;
    }

    public Bullet fire() {
        Bullet b = null;
        if (weaponCountdown == 0) {
            b = new Bullet(x + 25 * Math.sin(direction / 180 * 3.141592654),
                    y + 25 * Math.cos(direction / 180 * 3.141592654), direction);
        }
        return b;
    }

    public Bullet fire(int i) {
        Bullet b = null;
        if (weaponCountdown == 0) {
            b = new Bullet(x + 25 * Math.sin((direction + 14 * i) / 180 * 3.141592654),
                    y + 22 * Math.cos((direction + 14 * i) / 180 * 3.141592654), direction);
        }
        return b;
    }

    public void turnLeft(){
        direction = (direction + 5) % 360;
    }

    public void turnRight(){
        direction = (direction - 5) % 360;
    }

    public void accelerate(){
        isAccelerating = true;
        speedX += 1.5 * Math.sin(direction / 180 * 3.141592654);
        speedY += 1.5 * Math.cos(direction / 180 * 3.141592654);
    }

    public void accelerateBooster(){
        isAccelerating = true;
        speedX += 4 * Math.sin(direction / 180 * 3.141592654);
        speedY += 4 * Math.cos(direction / 180 * 3.141592654);
    }

    public void notAccelerate(){
        isAccelerating = false;
    }

    public boolean isAccelerating() {
        return isAccelerating;
    }

    public void move() {
        x = (x + speedX / 30) % 1024;
        y = (y + speedY / 30) % 768;
        if (x <= 0) x += 1024;
        if (y <= 0) y += 768;

        hitBox = new Polygon(x + SHIP_SCALE * Math.sin(direction / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos(direction / 180 * 3.141592654),
                x + SHIP_SCALE * Math.sin((direction + 150) / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos((direction + 150) / 180 * 3.141592654),
                x + SHIP_SCALE * Math.sin((direction + 210) / 180 * 3.141592654),
                y + SHIP_SCALE * Math.cos((direction + 210) / 180 * 3.141592654));
        if (shieldCountdown > 0) shieldCountdown--;
        if (duoCountdown > 0) duoCountdown--;
        if (boosterCountdown > 0) boosterCountdown --;
        if (hyperJumpCountdown > 0) hyperJumpCountdown --;
        if (shieldCountdown <= 0) hasShield = false;
        if (duoCountdown <= 0) duoBullets = false;
        if (boosterCountdown <= 0) booster = false;
    }

    @Override
    public boolean canSplit() {
        return false;
    }

    public void destroy() {
        alive = false;
    }

    public double getSpeedX(){
        return speedX;
    }

    public double getSpeedY(){
        return speedY;
    }

    @Override
    public boolean isUFO() {
        return false;
    }

    public boolean hasShield() {
        return hasShield;
    }

    public void giveBonus(int type){
        if (type == 1) {
            hasShield = true;
            shieldCountdown = 360;
        }

        if (type == 0) {
            duoBullets = true;
            duoCountdown = 420;
        }

        if (type == 2){
            booster = true;
            boosterCountdown = 360;
        }

        if (type == -1) {
            hasShield = true;
            shieldCountdown = 240;
        }
    }

    public boolean hasBooster(){
        return booster;
    }

    public void hyperJump(List<FlyingRock> rocks) {
        if (hyperJumpCountdown > 0) return;
        boolean valid = false;
        double newX = x, newY = y;
        Polygon testHitBox;

        while(!valid) {
            newX = random.nextInt(800) + 100;
            newY = random.nextInt(500) + 100;
            testHitBox = new Polygon(newX + SHIP_SCALE * Math.sin(direction / 180 * 3.141592654),
                    newY + SHIP_SCALE * Math.cos(direction / 180 * 3.141592654),
                    newX + SHIP_SCALE * Math.sin((direction + 150) / 180 * 3.141592654),
                    newY + SHIP_SCALE * Math.cos((direction + 150) / 180 * 3.141592654),
                    newX + SHIP_SCALE * Math.sin((direction + 210) / 180 * 3.141592654),
                    newY + SHIP_SCALE * Math.cos((direction + 210) / 180 * 3.141592654));
            valid = true;
            for (FlyingRock r : rocks) {
                if (r.isIntersect(testHitBox)) valid = false;
            }
        }
        x = newX;
        y = newY;
        hyperJumpCountdown = 240;
    }
}
