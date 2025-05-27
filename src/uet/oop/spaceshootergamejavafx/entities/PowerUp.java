package uet.oop.spaceshootergamejavafx.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * Skeleton for PowerUp. Students must implement movement,
 * rendering, and state management.
 */
public class PowerUp extends GameObject {

    // Dimensions of the power-up
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    // Fall speed of the power-up
    private static final double SPEED = 0.5;

    // Flag indicating whether the power-up should be removed
    private boolean dead;
    private Image sprite;

    /**
     * Constructs a PowerUp at the given position.
     *
     * @param x initial X position
     * @param y initial Y position
     */
    public PowerUp(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        dead = false;

        try {
            this.sprite = new Image(getClass().getResource("/img/powerup.png").toString());
        } catch (Exception e) {
            System.out.println("Image not found: " + e.getMessage());
            this.sprite = null;
        }
    }

    /**
     * Updates power-up position each frame.
     */
    @Override
    public void update() {
        y += SPEED;
        if (y - HEIGHT > 600) {  // Assuming 600 is the height of the game screen
            dead = true;
        }
    }

    /**
     * Renders the power-up on the canvas.
     *
     * @param gc the GraphicsContext to draw on
     */
    @Override
    public void render(GraphicsContext gc) {
        if (sprite != null) {
            gc.drawImage(sprite, x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
        } else {
            gc.setFill(javafx.scene.paint.Color.YELLOW);
            gc.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
        }
    }

    /**
     * Returns the width of the power-up.
     *
     * @return WIDTH
     */
    @Override
    public double getWidth() {
        // TODO: return width
        return WIDTH;
    }

    /**
     * Returns the height of the power-up.
     *
     * @return HEIGHT
     */
    @Override
    public double getHeight() {
        // TODO: return height
        return HEIGHT;
    }

    /**
     * Checks whether the power-up should be removed.
     *
     * @return true if dead
     */
    @Override
    public boolean isDead() {
        // TODO: return dead flag
        return dead;
    }

    /**
     * Marks this power-up as dead (to be removed).
     *
     * @param dead true if should be removed
     */
    public void setDead(boolean dead) {
        // TODO: update dead flag
        this.dead = dead;
    }
}
