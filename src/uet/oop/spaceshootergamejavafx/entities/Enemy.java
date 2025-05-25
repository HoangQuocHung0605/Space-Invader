package uet.oop.spaceshootergamejavafx.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// Add to module-info.java:
// requires javafx.controls;
// requires javafx.graphics;

/**
 * Skeleton for Enemy. Students must implement movement, rendering,
 * and death state without viewing the original implementation.
 */
public class Enemy extends GameObject {

    // Hitbox dimensions
    protected static final int WIDTH = 30;
    protected static final int HEIGHT = 30;

    // Movement speed
    public static double SPEED = 1;

    // Flag to indicate if enemy should be removed
    private boolean dead;
    private Image sprite;

    /**
     * Constructs an Enemy at the given coordinates.
     * @param x initial X position
     * @param y initial Y position
     */
    public Enemy(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        // TODO: load sprite if needed and initialize dead flag
        this.dead = false;
        try {
            this.sprite = new Image(getClass().getResource("/enemy.png").toString());
        } catch (Exception e) {
            System.out.println("Image not found" + e.getMessage());
            sprite = null;
        }
        // khởi tạo đối tượng Enemy, dùng khối try catch để lấy hình ảnh.
        // set tình trạng dead là false.

    }

    /**
     * Updates enemy position each frame.
     */
    @Override
    public void update() {
        // TODO: implement vertical movement by SPEED
        y+=SPEED;
        // đối tượng Enemy di chuyển dọc với SPEED đã set;
    }

    /**
     * Renders the enemy on the canvas.
     * @param gc the GraphicsContext to draw on
     */
    @Override
    public void render(GraphicsContext gc) {
        // TODO: draw sprite or fallback shape (e.g., colored rectangle)
        if (sprite != null) {
            gc.drawImage(sprite, x - WIDTH/2, y- WIDTH/2, WIDTH, HEIGHT);
        }
        else {
            gc.setFill(javafx.scene.paint.Color.RED);
            gc.fillRect(x - WIDTH/2, y - WIDTH/2, WIDTH, HEIGHT);
        }
    }

    /**
     * Returns the current width of the enemy.
     * @return WIDTH
     */
    @Override
    public double getWidth() {
        // TODO: return width
        return WIDTH;
    }

    /**
     * Returns the current height of the enemy.
     * @return HEIGHT
     */
    @Override
    public double getHeight() {
        // TODO: return height
        return HEIGHT;
    }

    /**
     * Marks this enemy as dead (to be removed).
     * @param dead true if enemy should be removed
     */
    public void setDead(boolean dead) {
        this.dead = dead;
        // TODO: update dead flag

        // Có thể làm hiệu ứng nổ hoặc tăng điểm.
    }

    /**
     * Checks if this enemy is dead.
     * @return true if dead, false otherwise
     */
    @Override
    public boolean isDead() {
        // TODO: return dead flag
        return dead;
    }
}
