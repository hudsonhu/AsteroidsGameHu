package model;

import javafx.scene.shape.Polygon;

public class Bonus {
    private final double x;
    private final double y;
    private final Polygon hitbox;
    private boolean alive = true;
    private final int bonusType;
    private int lifeTime = 300;

    Bonus(double x, double y, int bonusType){
        this.x = x;
        this.y = y;
        this.bonusType = bonusType;
        hitbox = new Polygon(x-10,y-10,x+10,y-10,x+10,y+10,x-10,y+10);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getBonusType() {
        return bonusType;
    }

    public boolean isHit(Player p) {
        boolean hit = p.getHitBox().intersects(hitbox.getBoundsInParent());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public boolean tick() {
        lifeTime --;
        return lifeTime >= 0;
    }

}
