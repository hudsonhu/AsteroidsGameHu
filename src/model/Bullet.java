package model;

import javafx.scene.shape.Polygon;


public class Bullet implements Movable, Hittable{
    private double x,y;
    private final double speedX;
    private final double speedY;
    private int lifetime = 0;

    private boolean alive = true;
    private Polygon hitBox;

    public Bullet(double x, double y, double direction){
        this.x = x;
        this.y = y;
        speedX = Math.sin(direction / 180 * 3.141592654) * 8;
        speedY = Math.cos(direction / 180 * 3.141592654) * 8;

    }

    public void move() {
        x = (x + speedX) % 1024;
        y = (y + speedY) % 1024;
        if (x <= 0) x += 1024;
        if (y <= 0) y += 768;
        lifetime ++;
        hitBox = new Polygon(x-1.5,y-1.5,x+1.5,y-1.5,x+1.5,y+1.5,x-1.5,y+1.5);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isInsideRange(){
        return lifetime <= 70;
    }

    public boolean isHit(Bullet b) {
        boolean hit = hitBox.intersects(b.getHitBox().getBoundsInParent());
        if (hit) {
            alive = false;
            b.alive = false;
        }
        return false; //hit
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return false;
    }

    public Polygon getHitBox() {
        return hitBox;
    }

    public void destroy() {
        alive = false;
    }

    public boolean canSplit() {
        return false;
    }

    @Override
    public boolean isUFO() {
        return false;
    }
}
