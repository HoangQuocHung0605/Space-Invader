package uet.oop.spaceshootergamejavafx.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;



/**
 * Skeleton for Player. Students must implement movement, rendering,
 * shooting, and health/state management.
 */
public class Player extends GameObject{

    // Hitbox dimensions
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    // Movement speed
    private static final double SPEED = 5;

    // Movement flags
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveForward;
    private boolean moveBackward;

    // Player health
    private int health;

    // State flag for removal
    private boolean dead;
    private Image sprite;

    /**
     * Constructs a Player at the given position.
     * @param x initial X position
     * @param y initial Y position
     */
    public Player(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        this.dead = false;
        try {
            sprite = new Image(getClass().getResource("/player.png").toString());
        } catch (Exception e) {
            sprite = null; // fallback nếu không có hình
        }

        // TODO: initialize health, dead flag, load sprite if needed
    }

    /**
     * Returns the width of the player.
     */
    @Override
    public double getWidth() {
        // TODO: return width
        return WIDTH;
    }

    /**
     * Returns the height of the player.
     */
    @Override
    public double getHeight() {
        // TODO: return height
        return HEIGHT;
    }

    /**
     * Returns current health of the player.
     */
    public int getHealth() {
        // TODO: return health
        return health;
    }

    /**
     * Sets player's health.
     */
    public void setHealth(int health) {
        // TODO: update health
        this.health = health;
    }

    /**
     * Updates player position based on movement flags.
     */
    @Override
    public void update() {

        // Implement movement logic
        if (moveLeft && x - SPEED > WIDTH / 2) {
            x -= SPEED;
        }
        if (moveRight && x + SPEED < 350 - WIDTH / 2) { // Assuming screen width is 800
            x += SPEED;
        }
        if (moveForward && y - SPEED > HEIGHT / 2) {
            y -= SPEED;
        }
        if (moveBackward && y + SPEED < 800 - HEIGHT / 2) { // Assuming screen height is 600
            y += SPEED;
        }
    }

    /**
     * Renders the player on the canvas.
     */
    @Override
    public void render(GraphicsContext gc) {

        // Draw the player sprite if available, else use a placeholder
        if (sprite != null) {
            gc.drawImage(sprite, x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
        } else {
            gc.setFill(javafx.scene.paint.Color.BLUE);
            gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
        }
    }

    /**
     * Sets movement flags.
     */
    public void setMoveLeft(boolean moveLeft) {
        // TODO: update moveLeft flag
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        // TODO: update moveRight flag
        this.moveRight = moveRight;
    }

    public void setMoveForward(boolean moveForward) {
        // TODO: update moveForward flag
        this.moveForward = moveForward;
    }

    public void setMoveBackward(boolean moveBackward) {
        // TODO: update moveBackward flag
        this.moveBackward = moveBackward;
    }

    /**
     * Shoots a bullet from the player.
     */
    public void shoot(List<GameObject> newObjects) {

        newObjects.add(new Bullet(x, y - HEIGHT / 2));
    }

    /**
     * Marks the player as dead.
     */
    public void setDead(boolean dead) {
        // TODO: update dead flag
        this.dead = dead;
    }

    /**
     * Checks whether the player is dead.
     */
    @Override
    public boolean isDead() {
        // TODO: return dead flag
        return dead;
    }
}
