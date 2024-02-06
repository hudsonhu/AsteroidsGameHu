package model;

import display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameCore implements Game {
    private int playerLives;
    private int playerScore;
    private boolean pause = true;
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    private Player player;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private ArrayList<Bonus> bonuses;
    private ArrayList<Hittable> targets;
    private Levels level;
    private final PlayerListener listener;
    private int newLife = 0;
    Random random = new Random();

    public GameCore(PlayerListener listener) {
        this.listener = listener;
        startNewGame();
    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public void updateGame() {
        if (!isPaused()) {
            player.tick();
            targets.clear();
            targets.addAll(level.getHittable());
            targets.add(player);
            playerBullets();
            enemyBullets.addAll(level.newBullets());
            enemyBullets();
            movePlayer();
            playerCrash();
            level.move(player);
            bonusTick();
        }
    }

    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pause = !pause;
            listener.resetPause();
        }
    }

    @Override
    public void startNewGame() {
        targets = new ArrayList<>();
        playerLives = 3;
        playerScore = 0;
        playerBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        bonuses = new ArrayList<>();
        player = new Player();
        player.giveBonus(-1);
        level = new Levels();
        targets.clear();
        targets.addAll(level.getHittable());
        enemyBullets.addAll(level.newBullets());
        targets.add(player);
    }


    public Player getShip() {
        return player;
    }

    public int getLives() {
        return playerLives;
    }

    @Override
    public boolean isLevelFinished() {
        return targets.size() <= 1;
    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        player.giveBonus(-1);
        playerBullets = new ArrayList<>();
    }

    @Override
    public void moveToNextLevel() {
        level.up();
        player.giveBonus(-1);
        updateGame();
    }

    @Override
    public boolean isGameOver() {
        return playerLives <= 0;
    }

    private void movePlayer() {
        // called 60 times per second
        if (listener.isPressingFire()) {
            if (player.hasDuoBullets()) {
                Bullet b1 = player.fire(-1);
                Bullet b2 = player.fire(1);
                if (b1 != null) playerBullets.add(b1);
                if (b2 != null) playerBullets.add(b2);
            } else {
                Bullet b = player.fire();
                if (b != null) {
                    playerBullets.add(b);
                }
            }
        }
        if (listener.isPressingLeft()) {
            player.turnLeft();
        }
        if (listener.isPressingRight()) {
            player.turnRight();
        }
        if (listener.isPressingShift()) {
            player.hyperJump(level.getRocks());
        }
        if (listener.isPressingUp()) {
            if (player.hasBooster()) player.accelerateBooster();
            else player.accelerate();
        } else {
            player.notAccelerate();
        }
        player.move();
    }

    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        return bullets;
    }

    public List<UFO> getUFOs() {
        return level.getUFOs();
    }

    private void playerBullets() {
        List<Bullet> remove = new ArrayList<>();
        for (Bullet playerBullet : playerBullets) {
            if (playerBullet.isAlive() && playerBullet.isInsideRange()) {
                playerBullet.move();
                for (Hittable t : targets) {
                    if (t != playerBullet && !t.isPlayer()) {
                        if (t.isHit(playerBullet)) {
                            t.destroy();
                            giveBonus(playerBullet.getX(), playerBullet.getY());
                            playerScore += t.getPoints();
                            if (playerScore / 10000 > newLife) {
                                playerLives++;
                                newLife++;
                            }
                            playerBullet.destroy();
                        }
                    }
                }
            } else {
                remove.add(playerBullet);
            }
        }
        playerBullets.removeAll(remove);
    }

    private void enemyBullets() {
        List<Bullet> remove = new ArrayList<>();
        for (Bullet b : enemyBullets) {
            if (b.isAlive() && b.isInsideRange()) {
                b.move();
                for (Hittable t : targets) {
                    if (t != b && !t.isUFO()) {
                        if (t.isHit(b)) {
                            if (t.isPlayer()) {
                                playerLives--;
                                pause = true;
                            }
                            b.destroy();
                        }
                    }
                }
            } else {
                remove.add(b);
            }
        }
        enemyBullets.removeAll(remove);
    }

    public void playerCrash() {
        List<FlyingRock> rocks = level.getRocks();
        List<UFO> ufos = level.getUFOs();

        for (FlyingRock r : rocks) {
            if (!player.hasShield()) {
                if (r.isHit(player)) {
                    playerLives--;
                    player.destroy();
                    player.notAccelerate();
                    pause = true;
                }
                for (UFO u : ufos) {
                    if (r.isHit(u)) {
                        u.destroy();
                    }
                }
            }
        }
        for (UFO u : ufos) {
            if (!player.hasShield()) {
                if (u.isHit(player)) {
                    playerLives--;
                    player.destroy();
                    player.notAccelerate();
                    pause = true;
                }
            }
        }

        ArrayList<Bonus> hitB = new ArrayList<>();
        for (Bonus b : bonuses) {
            if (b.isHit(player)) {
                player.giveBonus(b.getBonusType());
                hitB.add(b);
            }
        }
        bonuses.removeAll(hitB);
    }

    public List<FlyingRock> getRocks() {
        return level.getRocks();
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public int getCurrentLevel() {
        return level.now();
    }

    public void giveBonus(double x, double y) {
        int magicNumber = random.nextInt(10);
        if (magicNumber < 3) {
            bonuses.add(new Bonus(x, y, magicNumber));
        }
    }

    public void bonusTick(){
        ArrayList<Bonus> remove = new ArrayList<>();
        for(Bonus b : bonuses){
            if (!b.tick()) remove.add(b);
        }
        bonuses.removeAll(remove);
    }
}
