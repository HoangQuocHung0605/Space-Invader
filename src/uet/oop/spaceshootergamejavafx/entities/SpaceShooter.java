package uet.oop.spaceshootergamejavafx.entities;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


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
    private List<BossEnemy> bossEnemies = new ArrayList<>();

    //UI labels.
    private Label scoreLabel;
    private Label livesLabel;
    private Label messageLabel;

    private Stage primaryStage;

    private Image backgroundImage = new Image(getClass().getResource("/img/back_2.png").toString());
    private AnimationTimer gameTimer;

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


    private long lastEnemySpawnTime = 0;
    private static final long ENEMY_SPAWN_INTERVAL = 1_000_000_000; // 1 giây

    private void spawnEnemy() {
        long now = System.nanoTime();
        if ( !bossExists && now - lastEnemySpawnTime >= ENEMY_SPAWN_INTERVAL) {
            double enemyWidth = 40;
            double margin = 20; // khoảng cách an toàn từ mép trái/phải

            double x = margin + Math.random() * (WIDTH - 2 * margin - enemyWidth);
            Enemy enemy = new Enemy(x, -50);
            enemies.add(enemy);
            lastEnemySpawnTime = now;
        }

        if (score > 100 && !bossExists) {
            bossExists = true;
            spawnBossEnemy();
        }
    }


    private long lastPowerUpSpawnTime = 0;
    private static final long POWERUP_SPAWN_INTERVAL = 15_000_000_000L; // 15 giây

    private void spawnPowerUp() {
        long now = System.nanoTime();
        if (now - lastPowerUpSpawnTime >= POWERUP_SPAWN_INTERVAL) {
            double x = Math.random() * (WIDTH - 30);
            PowerUp powerUp = new PowerUp(x, -30);
            powerUps.add(powerUp);
            lastPowerUpSpawnTime = now;
        }
    }


    private void spawnBossEnemy() {
        BossEnemy boss = new BossEnemy(WIDTH / 2.0 - 50, 150);
        bossEnemies.add(boss);
        System.out.printf("BOSS spawned\n");
    }

    private void checkCollisions() {
        for (Bullet bullet : bullets) {
            if (bullet.isDead()) continue;

            for (Enemy enemy : enemies) {
                if (enemy.isDead()) continue;

                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bullet.setDead(true);
                    enemy.setDead(true);
                    score += 10;
                    break; // Bullet chỉ trúng 1 enemy
                }
            }
        }

// 1b. Bullet va chạm với BossEnemy
        for (Bullet bullet : bullets) {
            if (bullet.isDead()) continue;

            for (BossEnemy boss : bossEnemies) {
                if (boss.isDead()) continue;

                if (bullet.getBounds().intersects(boss.getBounds())) {
                    bullet.setDead(true);
                    boss.takeDamage();

                    if (boss.isDead()) {
                        score += 100;
                        bossExists = false;
                        gameRunning = false;
                        showTempMessage("You Win!", WIDTH / 2.0 - 50, HEIGHT / 2.0, 2);

                        // Chuyển sang menu sau 2 giây (trùng với thời gian hiển thị message)
                        PauseTransition delay = new PauseTransition(Duration.seconds(2));
                        delay.setOnFinished(e -> primaryStage.setScene(new Scene(createMenu(), WIDTH, HEIGHT)));
                        delay.play();
                    }

                    break;
                }
            }
        }

        // 2. EnemyBullet va chạm Player
        for (EnemyBullet enemyBullet : enemyBullets) {
            if (enemyBullet.isDead() || player.isDead()) continue;

            if (enemyBullet.getBounds().intersects(player.getBounds())) {
                enemyBullet.setDead(true);
                numLives--;
                if (numLives <= 0) {
                    player.setDead(true);
                    resetGame();
                } else {
                    // Bạn có thể thêm hiệu ứng hoặc hồi sinh tạm thời player
                }
            }
        }

        // 3. Player va chạm PowerUp
        for (PowerUp powerUp : powerUps) {
            if (powerUp.isDead() || player.isDead()) continue;

            if (powerUp.getBounds().intersects(player.getBounds())) {
                powerUp.setDead(true);
                // Áp dụng hiệu ứng power-up cho player
                score += 20;
                numLives = Math.min(numLives + 1, 5); // max 5
            }
        }

        // 4. Enemy va chạm Player (va chạm trực tiếp)
        for (Enemy enemy : enemies) {
            if (enemy.isDead() || player.isDead()) continue;

            if (enemy.getBounds().intersects(player.getBounds())) {
                enemy.setDead(true);
                numLives--;
                if (numLives <= 0) {
                    player.setDead(true);
                    resetGame();
                }
            }
        }

        // Có thể thêm các va chạm khác nếu cần (ví dụ enemyBullet va chạm powerUp,...)

    }


    private void checkEnemiesReachingBottom() {
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.getY() >= HEIGHT) {
                enemiesToRemove.add(enemy);
                numLives--; // trừ mạng khi enemy vượt đáy
                if (numLives <= 0) {
                    resetGame(); // gọi reset khi hết mạng
                    break;
                }
            }
        }
        enemies.removeAll(enemiesToRemove);
    }


    // UI and game state methods

    private void showLosingScreen() {
        VBox losingLayout = new VBox(20);
        losingLayout.setAlignment(Pos.CENTER);
        losingLayout.setPrefSize(WIDTH, HEIGHT);
        losingLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");

// Hiệu ứng fade in
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), losingLayout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

// Label "Game Over"
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: red; -fx-font-weight: bold;");

// Label điểm số
        Label finalScoreLabel = new Label("Final Score: " + score);
        finalScoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

// Nút "Play Again"
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        playAgainButton.setOnAction(e -> restartGame());

// Nút "Back to Menu"
        Button menuButton = new Button("Back to Menu");
        menuButton.setStyle("-fx-font-size: 16px; -fx-background-color: #f44336; -fx-text-fill: white;");
        menuButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(createMenu(), WIDTH, HEIGHT));
        });

// Thêm các thành phần vào VBox
        losingLayout.getChildren().addAll(gameOverLabel, finalScoreLabel, playAgainButton, menuButton);

// Thêm VBox vào root
        root.getChildren().add(losingLayout);

    }

    private void restartGame() {
        if (gameTimer != null) {
            gameTimer.stop(); // Dừng AnimationTimer cũ
        }
        root.getChildren().clear();
        // Reset các biến trạng thái
        score = 0;
        numLives = 3;
        bossExists = false;
        gameRunning = true;
        // Khởi động lại game
        startGame();
        // TODO: reset gameObjects, lives, score and switch back to game scene
    }

    private void resetGame() {
        gameRunning = false;
        showLosingScreen();
        if (gameTimer != null) {
            gameTimer.stop(); // Dừng vòng lặp game hiện tại
        }
        // TODO: stop game loop and call showLosingScreen
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

        Button closeButton = new Button("Close");
        closeButton.setLayoutX(WIDTH / 2.0 - 50);
        closeButton.setLayoutY(350);
        closeButton.setOnAction(e -> Platform.exit());

        menuRoot.getChildren().addAll(title, startButton, instructionsButton, closeButton);
        return menuRoot;
    }

    private void showInstructions() {
        // TODO: display instructions dialog
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
        Label tempLabel = new Label(message);
        tempLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: yellow; -fx-font-weight: bold;");
        tempLabel.setLayoutX(x);
        tempLabel.setLayoutY(y);

        root.getChildren().add(tempLabel);

        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(e -> root.getChildren().remove(tempLabel));
        pause.play();
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
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameRunning) return;
                if (now - lastEnemySpawnTime >= ENEMY_SPAWN_INTERVAL) {
                    spawnEnemy(); // bạn tự viết hàm này
                    lastEnemySpawnTime = now;
                }

                // Spawn PowerUp
                if (now - lastPowerUpSpawnTime >= POWERUP_SPAWN_INTERVAL) {
                    spawnPowerUp(); // bạn tự viết hàm này
                    lastPowerUpSpawnTime = now;
                }

                // Cập nhật
                player.update();

                // Cập nhật enemies, chỉ với enemy chưa chết
                enemies.removeIf(Enemy::isDead); // Xóa enemy đã chết
                for (Enemy e : enemies) {
                    if (!e.isDead()) {
                        e.update();
                    }
                }

                // Cập nhật bullets, xóa bullet đã chết
                bullets.removeIf(Bullet::isDead);
                for (Bullet b : bullets) {
                    if (!b.isDead()) {
                        b.update();
                    }
                }

                // Tương tự với enemyBullets và powerUps
                enemyBullets.removeIf(EnemyBullet::isDead);
                for (EnemyBullet eb : enemyBullets) {
                    if (!eb.isDead()) {
                        eb.update();
                    }
                }

                powerUps.removeIf(PowerUp::isDead);
                for (PowerUp p : powerUps) {
                    if (!p.isDead()) {
                        p.update();
                    }
                }

                bossEnemies.removeIf(BossEnemy :: isDead);
                for (BossEnemy boss : bossEnemies) {
                    if (!boss.isDead()) {
                        boss.update();

                        // Kiểm tra thời gian bắn
                        if (now - boss.getLastShootTime() >= boss.getShootInterval()) {
                            boss.shoot(enemyBullets);
                            boss.setLastShootTime(now);
                        }
                    }
                }

                // Kiểm tra va chạm
                checkCollisions();

                // Kiểm tra enemy tới đáy màn hình
                checkEnemiesReachingBottom();

                // Vẽ lại màn hình
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                gc.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT);

                if (!player.isDead()) {
                    player.render(gc);
                }

                for (Enemy e : enemies) {
                    if (!e.isDead()) {
                        e.render(gc);
                    }
                }

                for (Bullet b : bullets) {
                    if (!b.isDead()) {
                        b.render(gc);
                    }
                }

                for (EnemyBullet eb : enemyBullets) {
                    if (!eb.isDead()) {
                        eb.render(gc);
                    }
                }

                for (PowerUp p : powerUps) {
                    if (!p.isDead()) {
                        p.render(gc);
                    }
                }

                for (BossEnemy boss : bossEnemies) {
                    if (!boss.isDead()) {
                        boss.render(gc);
                    }
                }

                // Cập nhật UI
                scoreLabel.setText("Score: " + score);
                livesLabel.setText("Lives: " + numLives);
            }
        };
        gameTimer.start();
    }



}
