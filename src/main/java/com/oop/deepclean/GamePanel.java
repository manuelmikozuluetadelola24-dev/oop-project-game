package com.oop.deepclean;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    
    private DeepCleanGame mainFrame;

    // Game Settings
    private boolean gameOver = false;
    private int score = 0;
    private int highScore = 0;
    private int timeLeft = 60; 
    private Timer countdownTimer;

    // Player State
    private int playerX = 487;
    private int playerY = 362;
    private final int playerSpeed = 6;
    private boolean mirror = false;

    // Animation State
    private Image[] rightFrames;
    private Image[] leftFrames;
    private int currentFrame = 0;
    private int animationDelay = 5;
    private int animationTimer = 0;

    // Environment Scrolling (Parallax effect on trash)
    private int backgroundOffset = 0;
    private final int scrollSpeed = 5;

    // Trash System
    private Image bottleImg, bagImg, canImg;
    private ArrayList<TrashObject> trashList = new ArrayList<>();
    private Random random = new Random();

    // Input Flags
    private boolean up, down, left, right;
    private Timer gameTimer;

    public GamePanel(DeepCleanGame mainFrame) {
        this.mainFrame = mainFrame;

        setFocusable(true); 
        loadAssets();
        setupControls();
        spawnTrash(10);
        
        // Auto-focus when this panel is shown
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                requestFocusInWindow();
                if(gameOver) resetGame();
            }
        });

        // Main Loop
        gameTimer = new Timer(16, this);
        gameTimer.start();

        // Timer Loop
        countdownTimer = new Timer(1000, e -> {
            if (!gameOver && isShowing()) { 
                timeLeft--;
                if (timeLeft <= 0) {
                    timeLeft = 0;
                    gameOver = true;
                    if (score > highScore) highScore = score;
                }
            }
        });
        countdownTimer.start();
    }

    private void resetGame() {
        score = 0;
        timeLeft = 60;
        gameOver = false;
        playerX = 487;
        playerY = 362;
        trashList.clear();
        spawnTrash(10);
        countdownTimer.restart();
        gameTimer.restart();
        repaint();
    }

    private void loadAssets() {
        try {
            rightFrames = new Image[4];
            leftFrames = new Image[4];

            rightFrames[0] = ImageIO.read(new File("Swim frame 1 (Right).png"));
            rightFrames[1] = ImageIO.read(new File("Swim frame 2 (Right).png"));
            rightFrames[2] = ImageIO.read(new File("Swim frame 3 (Right).png"));
            rightFrames[3] = ImageIO.read(new File("Swim frame 4 (Right).png"));

            leftFrames[0] = ImageIO.read(new File("Swim frame 1 (Left).png"));
            leftFrames[1] = ImageIO.read(new File("Swim frame 2 (Left).png"));
            leftFrames[2] = ImageIO.read(new File("Swim frame 3 (Left).png"));
            leftFrames[3] = ImageIO.read(new File("Swim frame 4(Left).png")); 

            bottleImg = ImageIO.read(new File("bottle.png"));
            bagImg = ImageIO.read(new File("plastic.png"));
            canImg = ImageIO.read(new File("can.png"));
            
        } catch (Exception e) {
            System.out.println("Error loading assets: " + e.getMessage());
        }
    }

    private void spawnTrash(int count) {
        int startX = (getWidth() > 0) ? getWidth() : 1024;
        Image[] choices = { bottleImg, bagImg, canImg };
        
        for (int i = 0; i < count; i++) {
            int x = backgroundOffset + startX + random.nextInt(1000);
            int y = random.nextInt(500) + 100;
            
            Image randomSprite = null;
            if (bottleImg != null) {
                randomSprite = choices[random.nextInt(3)];
            }
            trashList.add(new TrashObject(x, y, randomSprite));
        }
    }

    private void setupControls() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        addBinding(im, am, "W", "up");
        addBinding(im, am, "S", "down");
        addBinding(im, am, "A", "left");
        addBinding(im, am, "D", "right");

        im.put(KeyStroke.getKeyStroke("R"), "restart");
        am.put("restart", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { resetGame(); }
        });
        
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "menu");
        am.put("menu", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { 
                mainFrame.switchScreen(DeepCleanGame.TITLE_SCREEN); 
            }
        });
    }

    private void addBinding(InputMap im, ActionMap am, String key, String name) {
        im.put(KeyStroke.getKeyStroke(key), name + "Pressed");
        im.put(KeyStroke.getKeyStroke("released " + key), name + "Released");
        am.put(name + "Pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { setMove(name, true); }
        });
        am.put(name + "Released", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { setMove(name, false); }
        });
    }

    private void setMove(String name, boolean active) {
        if (name.equals("up")) up = active;
        if (name.equals("down")) down = active;
        if (name.equals("left")) { left = active; if (active) mirror = true; }
        if (name.equals("right")) { right = active; if (active) mirror = false; }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) { repaint(); return; }

        // Movement
        boolean isMoving = false;
        if (up && playerY > 50) { playerY -= playerSpeed; isMoving = true; }
        if (down && playerY < getHeight() - 150) { playerY += playerSpeed; isMoving = true; }
        if (left) { backgroundOffset -= scrollSpeed; isMoving = true; }
        if (right) { backgroundOffset += scrollSpeed; isMoving = true; }

        if (isMoving) {
            animationTimer++;
            if (animationTimer >= animationDelay) {
                currentFrame = (currentFrame + 1) % 4;
                animationTimer = 0;
            }
        } else {
            currentFrame = 0;
            animationTimer = 0;
        }

        // Collision
        Rectangle playerRect = new Rectangle(playerX, playerY, 100, 70);
        for (int i = 0; i < trashList.size(); i++) {
            TrashObject t = trashList.get(i);
            int screenX = t.getX() - backgroundOffset;
            if (playerRect.intersects(new Rectangle(screenX, t.getY(), t.getBounds().width, t.getBounds().height))) {
                trashList.remove(i);
                score += 10;
                spawnTrash(1);
                i--;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. Draw Background (GIF) from main frame
        g2d.drawImage(mainFrame.getSharedBackground(), 0, 0, getWidth(), getHeight(), this);

        // 2. Bubbles
        g2d.setColor(new Color(255, 255, 255, 60));
        for (int i = 0; i < 20; i++) {
            int bx = (i * 200 - (backgroundOffset % (getWidth() + 200)));
            if (bx < -10) bx += (getWidth() + 200);
            g2d.fillOval(bx, (i * 70) % getHeight(), 6, 6);
        }

        // 3. Trash
        for (TrashObject t : trashList) {
            int screenX = t.getX() - backgroundOffset;
            if (t.getSprite() != null) {
                g2d.drawImage(t.getSprite(), screenX, t.getY(), 40, 40, null);
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(screenX, t.getY(), 20, 20);
            }
        }

        // 4. Player
        Image spriteToDraw = null;
        if (mirror) {
            if (leftFrames[currentFrame] != null) spriteToDraw = leftFrames[currentFrame];
        } else {
            if (rightFrames[currentFrame] != null) spriteToDraw = rightFrames[currentFrame];
        }

        if (spriteToDraw != null) {
            g2d.drawImage(spriteToDraw, playerX, playerY, 160, 110, this);
        } else {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(playerX, playerY, 160, 110);
        }

        // 5. HUD
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + score, 20, 40);
        g2d.setColor(Color.YELLOW);
        g2d.drawString("High Score: " + Math.max(score, highScore), 20, 70);
        g2d.setColor(Color.WHITE);
        g2d.drawString("[ESC] Menu", 20, 100);

        if (timeLeft <= 10) g2d.setColor(Color.RED);
        else g2d.setColor(Color.WHITE);
        g2d.drawString("Time: " + timeLeft, getWidth() - 150, 40);

        // 6. Game Over
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new java.awt.BasicStroke(5));
            g2d.drawRect(getWidth()/2 - 200, getHeight()/2 - 120, 400, 240);
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(getWidth()/2 - 200, getHeight()/2 - 120, 400, 240);

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            String title = "TIME'S UP!";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(title)) / 2;
            g2d.drawString(title, x, getHeight()/2 - 40);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 25));
            String subMsg = "Final Score: " + score;
            fm = g2d.getFontMetrics();
            int x2 = (getWidth() - fm.stringWidth(subMsg)) / 2;
            g2d.drawString(subMsg, x2, getHeight()/2 + 10);
            
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            String restartMsg = "Press 'R' to Play Again";
            int x4 = (getWidth() - fm.stringWidth(restartMsg)) / 2;
            g2d.drawString(restartMsg, x4, getHeight()/2 + 90);
        }
    }
}
