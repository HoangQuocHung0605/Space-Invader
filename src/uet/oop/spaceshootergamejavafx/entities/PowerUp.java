package uet.oop.spaceshootergamejavafx.entities;

import javafx.scene.canvas.GraphicsContext;

/**
 * Skeleton for PowerUp. Students must implement movement,
 * rendering, and state management.
 */
public class PowerUp extends GameObject {

    // Dimensions of the power-up
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    // Fall speed of the power-up
    private static final double SPEED = 2;

    // Flag indicating whether the power-up should be removed
    private boolean dead;

    /**
     * Constructs a PowerUp at the given position.
     * @param x initial X position
     * @param y initial Y position
     */
    public PowerUp(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        // TODO: initialize dead flag, load sprite if needed
        // Khởi tạo cờ dead - power-up bắt đầu ở trạng thái sống
        this.dead = false;
    }

    /**
     * Updates power-up position each frame.
     */
    @Override
    public void update() {
        // TODO: move power-up vertically by SPEED
        // Di chuyển power-up theo chiều dọc với tốc độ SPEED
        this.y += SPEED;

        // Kiểm tra nếu power-up đã rơi ra khỏi màn hình
        if (this.y > 800) {
            this.dead = true;
        }
    }

    /**
     * Renders the power-up on the canvas.
     * @param gc the GraphicsContext to draw on
     */
    @Override
    public void render(GraphicsContext gc) {
        // TODO: draw sprite or fallback (e.g., colored rectangle)
        // Vẽ hình chữ nhật màu vàng cho power-up
        gc.setFill(javafx.scene.paint.Color.YELLOW);
        gc.fillRect(x, y, WIDTH, HEIGHT);

        // Thêm viền để dễ nhìn hơn
        gc.setStroke(javafx.scene.paint.Color.ORANGE);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, WIDTH, HEIGHT);
    }


    /**
     * Returns the width of the power-up.
     * @return WIDTH
     */
    @Override
    public double getWidth() {
        // TODO: return width
        return WIDTH;
    }

    /**
     * Returns the height of the power-up.
     * @return HEIGHT
     */
    @Override
    public double getHeight() {
        // TODO: return height
        return HEIGHT;
    }

    /**
     * Checks whether the power-up should be removed.
     * @return true if dead
     */
    @Override
    public boolean isDead() {
        // TODO: return dead flag
        return dead;
    }

    /**
     * Marks this power-up as dead (to be removed).
     * @param dead true if should be removed
     */
    public void setDead(boolean dead) {
        // TODO: update dead flag
        this.dead = dead;
    }
}
