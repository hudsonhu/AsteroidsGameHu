package model;

public interface Hittable{
	boolean isAlive();
	int getPoints();
	boolean isPlayer();
	boolean isUFO();
	boolean isHit(Bullet b);
	boolean canSplit();
	void destroy();
}

