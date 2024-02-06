package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Levels {
    private int currentLevel = 1;
    ArrayList<FlyingRock> rocks;
    ArrayList<UFO> UFOs;
    ArrayList<Bullet> newBullets;
    Random random = new Random();
    private int tick = 0;

    Levels (){
        rocks = new ArrayList<>();
        UFOs = new ArrayList<>();
        newBullets = new ArrayList<>();
        for (int i = 0; i < currentLevel + 3; i ++){
            int x = random.nextInt(1024);
            int y = random.nextInt(768);
            while (x > 300 && x < 500) x = random.nextInt(1024);
            while (y > 350 && y < 550) y = random.nextInt(768);
            int direction = random.nextInt(360);
            int type = random.nextInt(2);
            double speed = random.nextInt(4) + 2;
            rocks.add(new FlyingRock(x, y, direction, speed, type, 20));
        }
    }

    public void move(Player player){
        tick ++;
        List<FlyingRock> remove = new ArrayList<>();
        List<FlyingRock> add = new ArrayList<>();
        List<UFO> remove_UFO = new ArrayList<>();
        for (FlyingRock r : rocks) {
            if (r.isAlive()) r.move();
            else {
                if (r.canSplit()) {
                    double newDirection1 = random.nextInt(20);
                    double newDirection2 = - random.nextInt(20);

                    if (r.getScale() == 20) {
                        newDirection1 += r.getDirection() + 10;
                        newDirection2 += r.getDirection() - 10;
                        double newSpeed1 = r.getSpeed() * (random.nextDouble() + 1);
                        double newSpeed2 = r.getSpeed() * (random.nextDouble() + 1);
                        add.add(new FlyingRock(r.getX(), r.getY(), newDirection1, newSpeed1, random.nextInt(2),r.getScale() / 2));
                        add.add(new FlyingRock(r.getX(), r.getY(), newDirection2, newSpeed2, random.nextInt(2),r.getScale() / 2));

                    }

                    if (r.getScale() == 10) {
                        newDirection1 += r.getDirection() + 20;
                        newDirection2 += r.getDirection() - 20;
                        double newSpeed1 = r.getSpeed() * (random.nextDouble() + 1);
                        double newSpeed2 = r.getSpeed() * (random.nextDouble() + 1);
                        add.add(new FlyingRock(r.getX(), r.getY(), newDirection1, newSpeed1, random.nextInt(2),r.getScale() / 2));
                        add.add(new FlyingRock(r.getX(), r.getY(), newDirection2, newSpeed2, random.nextInt(2),r.getScale() / 2));
                    }
                }
                remove.add(r);
            }
        }
        rocks.addAll(add);
        rocks.removeAll(remove);
        for (UFO ufo: UFOs){
            newBullets.clear();
            if (ufo.isAlive()) ufo.move(getRocks());
            else remove_UFO.add(ufo);
            Bullet b = ufo.fire(player);
            if (b != null) {
                newBullets.add(b);
            }

        }
        UFOs.removeAll(remove_UFO);

        if(tick > 500){
            tick = 0;
            int r = random.nextInt(3);
            if (r == 0) UFOs.add(new UFO(0, random.nextInt(600) + 50, random.nextInt(180)));
            if (r == 1) UFOs.add(new UFO(1024, random.nextInt(600) + 50, random.nextInt(180) + 180));
        }

    }

    public int now(){
        return currentLevel;
    }

    public void up(){
        currentLevel ++;
        newLevel();
    }

    public List<FlyingRock> getRocks() {
        return rocks;
    }

    public List<UFO> getUFOs(){
        return UFOs;
    }

    public List<Hittable> getHittable(){
        ArrayList<Hittable> hits = new ArrayList<>(rocks);
        hits.addAll(UFOs);
        return hits;
    }

    public ArrayList<Bullet> newBullets() {
        return newBullets;
    }

    public void newLevel(){
        rocks = new ArrayList<>();
        for (int i = 0; i < currentLevel + 3; i ++){
            int x = random.nextInt(1024);
            int y = random.nextInt(768);
            while (x > 300 && x < 500 && y > 350 && y < 550) {
                x = random.nextInt(1024);
                y = random.nextInt(768);
            }
            int direction = random.nextInt(360);
            int type = random.nextInt(2);
            double speed = random.nextInt(4) + 2;
            rocks.add(new FlyingRock(x, y, direction, speed, type, 20));
        }

    }

}
