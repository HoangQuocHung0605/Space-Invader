package uet.oop.spaceshootergamejavafx.entities;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.awt.*;
import java.awt.Button;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static jdk.internal.org.jline.terminal.Terminal.MouseTracking.Button;

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

    private Stage primaryStage;


    // TODO: Declare UI labels, lists of GameObjects, player, root Pane, Scene, Stage(Done)

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        Pane menuRoot = createMenu();
        Scene menuScene = new Scene(menuRoot, WIDTH, HEIGHT);

        primaryStage.setTitle("Space Shooter");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);
        primaryStage.show();

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
        Pane losingPane = new Pane();
        losingPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
        //them hieu ung fade in
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5),losingPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        //Label "Game over"
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: red; -fx-font-weight: bold;");
        gameOverLabel.setLayoutX(WIDTH / 2 - 100);
        gameOverLabel.setLayoutY(HEIGHT / 2 - 100);
        //Label diem so
        Label finalScoreLabel = new Label("Final Score: " + score);
        finalScoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        finalScoreLabel.setLayoutX(WIDTH / 2 - 80);
        finalScoreLabel.setLayoutY(HEIGHT / 2 - 30);
        // nut play again
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        playAgainButton.setLayoutX(WIDTH / 2 - 100);
        playAgainButton.setLayoutY(HEIGHT / 2 + 20);
        playAgainButton.setOnAction(e -> restartGame());
        // nut back to menu
        Button menuButton = new Button("Back to Menu");
        menuButton.setStyle("-fx-font-size: 16px; -fx-background-color: #f44336; -fx-text-fill: white;");
        menuButton.setLayoutX(WIDTH / 2 - 100);
        menuButton.setLayoutY(HEIGHT / 2 + 70);
        menuButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(createMenu(), WIDTH, HEIGHT));
        });

        losingPane.getChildren().addAll(gameOverLabel, finalScoreLabel, playAgainButton, menuButton);
        root.getChildren().add(losingPane);
    }


    private void restartGame() {
        // TODO: reset gameObjects, lives, score and switch back to game scene
    }

    private void resetGame() {
        gameRunning = false;
        showLosingScreen();
    }

    private void initEventHandlers(Scene scene) {
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


    }

    private Pane createMenu() {
        // TODO: build and return main menu pane with styled buttons
        Pane menuRoot = new Pane();
        menuRoot.setStyle("-fx-background-color: black;");

        Label title = new Label("SPACE SHOOTER");
        title.setStyle("-fx-font-size: 36px; -fx-text-fill: white;");
        title.setLayoutX(WIDTH / 2.0 - 120);
        title.setLayoutY(100);

        Button startButton = new Button("Start Game");
        startButton.setLayoutX(WIDTH / 2.0 - 50);
        startButton.setLayoutY(250);
        startButton.setOnAction(e -> startGame());

        Button instructionsButton = new Button("Instructions");
        instructionsButton.setLayoutX(WIDTH / 2.0 - 50);
        instructionsButton.setLayoutY(300);
        instructionsButton.setOnAction(e -> showInstructions());

        menuRoot.getChildren().addAll(title, startButton, instructionsButton);
        return menuRoot;
    }

   private void showInstructions() {
       //tao 1 stage moi de huong dan
       Stage instructionsStage = new Stage();
       instructionsStage.initOwner(primaryStage);
       instructionsStage.setTitle("How to play");
       // noi dung huong dan
       Label instructionsLabel = new Label(
               "SPACE SHOOTER - INSTRUCTIONS\n\n" +
                       "• Use ARROW KEYS to move your ship:\n" +
                       "   ↑ (UP): Move forward\n" +
                       "   ↓ (DOWN): Move backward\n" +
                       "   ← (LEFT): Move left\n" +
                       "   → (RIGHT): Move right\n\n" +
                       "• Press SPACE to shoot bullets.\n\n" +
                       "• Avoid enemies and their bullets.\n" +
                       "• Collect power-ups for bonuses.\n\n" +
                       "GOAL: Survive as long as possible and defeat the boss!"
       );
       //tuy chinh giao dien
       instructionsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
       instructionsLabel.setWrapText(true);

       Button closeButton = new Button("Close");
       closeButton.setOnAction(e -> instructionsStage.close());

       VBox layout = new VBox(20, instructionsLabel, closeButton);
       layout.setAlignment(Pos.CENTER);
       layout.setPadding(new Insets(20));
       layout.setStyle("-fx-background-color: #333;");

       Scene instructionsScene = new Scene(layout, 400, 400);
       instructionsStage.setScene(instructionsScene);
       instructionsStage.show();
   }


    private void showTempMessage(String message, double x, double y, double duration) {
        // TODO: show temporary on-screen message for duration seconds
    }

    private void startGame() {
        gameRunning = true;
        score = 0;
        numLives = 3;

        // Khởi tạo root và canvas
        root = new Pane();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Khởi tạo các label
        scoreLabel = new Label("Score: " + score);
        livesLabel = new Label("Lives: " + numLives);
        messageLabel = new Label();

        scoreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        livesLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        messageLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: yellow;");

        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);
        livesLabel.setLayoutX(WIDTH - 80);
        livesLabel.setLayoutY(10);
        messageLabel.setLayoutX(WIDTH / 2.0 - 100);
        messageLabel.setLayoutY(HEIGHT / 2.0);

        root.getChildren().addAll(canvas, scoreLabel, livesLabel, messageLabel);

        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        // Khởi tạo player và danh sách
        player = new Player(WIDTH / 2.0, HEIGHT - 50);
        gameObjects.clear();
        bullets.clear();
        enemies.clear();
        powerUps.clear();
        enemyBullets.clear();
        gameObjects.add(player);

        // Xử lý bàn phím
        initEventHandlers(scene);

        // Game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            }
        };
        timer.start();
    }

}
