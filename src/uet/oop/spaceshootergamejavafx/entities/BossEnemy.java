package uet.oop.spaceshootergamejavafx.entities;

import com.sun.javafx.iio.ImageStorage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Skeleton for BossEnemy. Students must implement behavior
 * without viewing the original implementation.
 */
public class BossEnemy extends Enemy {

    // Health points of the boss
    private int health;

    // Hitbox dimensions
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    // Horizontal movement speed
    private double horizontalSpeed;
    private Image sprite;

    /**
     * Constructs a BossEnemy at the given coordinates.
     * @param x initial X position
     * @param y initial Y position
     */
    public BossEnemy(double x, double y) {
        super(x, y);
        this.health = 100;
        this.horizontalSpeed = 2;
        this.sprite = new Image(getClass().getResource("/boss.png").toString());

        // TODO: initialize health, speeds, and load resources
    }

    /**
     * Update boss's position and behavior each frame.
     */
    @Override
    public void update() {
        // TODO: implement vertical and horizontal movement
        x += horizontalSpeed;
        if (x <= WIDTH / 2 || x >= 800 - WIDTH / 2) {
            horizontalSpeed = -horizontalSpeed;
        }

    }

    /**
     * Inflicts damage to the boss.
     */
    public void takeDamage() {
        // TODO: decrement health, mark dead when <= 0
        health -= 5;
        if (health <= 0) {
            setDead(true);
        }
    }

    /**
     * Boss fires bullets towards the player.
     */
    public void shoot(List<EnemyBullet> enemyBullets) {
        // TODO: implement shooting logic (spawn EnemyBullet)
        double bulletX = x;
        double bulletY = y + HEIGHT /2;
        enemyBullets.add(new EnemyBullet(bulletX, bulletY));

    }

    /**
     * Render the boss on the canvas.
     * @param gc graphics context
     */
    @Override
    public void render(GraphicsContext gc) {

        // TODO: draw boss sprite or placeholder
        if (sprite != null) {
            gc.drawImage(sprite, x - WIDTH/2, y- WIDTH/2, WIDTH, HEIGHT);
        }
        else {
            gc.setFill(javafx.scene.paint.Color.RED);
            gc.fillRect(x - WIDTH/2, y - WIDTH/2, WIDTH, HEIGHT);
        }
    }
}
