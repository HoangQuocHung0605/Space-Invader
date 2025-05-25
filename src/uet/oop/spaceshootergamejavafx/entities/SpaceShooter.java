package uet.oop.spaceshootergamejavafx.entities;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Skeleton for SpaceShooter. Students must implement game loop,
 * spawning, collision checks, UI, and input handling.
 */
public class SpaceShooter extends Application {

    public static final int WIDTH = 350;
    public static final int HEIGHT = 800;
    public static int numLives = 3;

    //Gán luôn gí trị các biến để tránh lỗi khi ử dụng ngay.
    private int score = 0;
    private boolean bossExists = false;
    private boolean reset = false;
    private boolean levelUpShown = false;
    private boolean gameRunning = false;

    // tạo biến giao diện và khung vẽ pane.
    private Pane root;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext gc;

    // tạo đôi tượng player và các ôi tượng game còn lại.
    private Player player;
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<GameObject> newObjects = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private List<EnemyBullet> enemyBullets = new ArrayList<>();

    //UI labels.
    private Label scoreLabel;
    private Label livesLabel;
    private Label messageLabel;


    // TODO: Declare UI labels, lists of GameObjects, player, root Pane, Scene, Stage(Done)

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // TODO: initialize primaryStage, scene, canvas, UI labels, root pane
        //tạo pane và canvas.
        root = new Pane();
        canvas = new Canvas(WIDTH, HEIGHT);
        //Lấy đối tượng GraphicsContext từ Canvas, cho phép thực hiện các thao tác vẽ (draw) lên Canvas
        gc = canvas.getGraphicsContext2D();

        //set các Label.
        scoreLabel = new Label("Score" + score);
        livesLabel = new Label("Lives: " + numLives);
        messageLabel = new Label();

        //gán cỡ chữ và màu chữ cho Label.
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        livesLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        messageLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: yellow;");

        //set vị trí label.
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);
        livesLabel.setLayoutX(WIDTH - 80);
        livesLabel.setLayoutY(10);
        messageLabel.setLayoutX(WIDTH / 2.0 - 100);
        messageLabel.setLayoutY(HEIGHT / 2.0);

        // Thêm tất cả các thành phần (canvas và 3 nhãn) vào Pane root, để hiển thị trên cửa sổ.
        root.getChildren().addAll(canvas, scoreLabel, livesLabel, messageLabel);

        //Tạo một Scene chứa root, kích thước bằng với WIDTH và HEIGHT đã định nghĩa.
        scene = new Scene(root, WIDTH, HEIGHT);

        // gán scence và ddăt Title
        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Shooter");

        primaryStage.setResizable(false);
        primaryStage.show();


        // thao tasc up,down,right left khi nhap tu ban phim.
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> player.setMoveLeft(true);
                case RIGHT -> player.setMoveRight(true);
                case DOWN -> player.setMoveBackward(true);
                case UP -> player.setMoveForward(true);
                case SPACE -> player.shoot(bullets); // Assuming `shoot` method adds bullet to bullets list
            }
        });

        // set lai false khi tha phim.
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT -> player.setMoveLeft(false);
                case RIGHT -> player.setMoveRight(false);
                case DOWN -> player.setMoveBackward(false);
                case UP -> player.setMoveForward(false);
            }
        });
        
        // TODO: initialize gameObjects list with player


        // TODO: create menu and switch to menu scene
        // TODO: set up AnimationTimer game loop and start it
        // TODO: show primaryStage
    }

    // Game mechanics stubs

    private void spawnEnemy() {
        // TODO: implement enemy and boss spawn logic based on score
    }

    private void spawnPowerUp() {
        // TODO: implement power-up spawn logic
    }

    private void spawnBossEnemy() {
        // TODO: implement boss-only spawn logic
    }

    private void checkCollisions() {
        // TODO: detect and handle collisions between bullets, enemies, power-ups, player
    }

    private void checkEnemiesReachingBottom() {
        // TODO: handle enemies reaching bottom of screen (reduce lives, respawn, reset game)
    }

    // UI and game state methods

    private void showLosingScreen() {
        // TODO: display Game Over screen with score and buttons
    }

    private void restartGame() {
        // TODO: reset gameObjects, lives, score and switch back to game scene
    }

    private void resetGame() {
        // TODO: stop game loop and call showLosingScreen
    }

    private void initEventHandlers(Scene scene) {
        // TODO: set OnKeyPressed and OnKeyReleased for movement and shooting
    }

    private Pane createMenu() {
        // TODO: build and return main menu pane with styled buttons
        return new Pane();
    }

    private void showInstructions() {
        // TODO: display instructions dialog
    }

    private void showTempMessage(String message, double x, double y, double duration) {
        // TODO: show temporary on-screen message for duration seconds
    }

    private void startGame() {
        // TODO: set gameRunning to true and switch to game scene
    }
}
