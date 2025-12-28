import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    // Player State
    private int playerX = 487;
    private int playerY = 362;
    private int score = 0;
    private final int playerSpeed = 6;
    private boolean mirror = false;

    // Environment Scrolling
    private int backgroundOffset = 0;
    private final int scrollSpeed = 5;

    // Trash System
    private Image bottleImg, bagImg, canImg;
    private ArrayList<TrashObject> trashList = new ArrayList<>();
    private Random random = new Random();

    // Input Flags
    private boolean up, down, left, right;
    private Timer gameTimer;

    // Player Animation
    private Image swimRight, swimLeft;

    public GamePanel() {
        setFocusable(true);
        loadAssets();
        setupControls();
        spawnTrash(10);
        gameTimer = new Timer(16, this);
        gameTimer.start();
    }

    private void loadAssets() {
        try {
            swimRight = ImageIO.read(new File("Swim_AnimationRight.png"));
            swimLeft = ImageIO.read(new File("Swim_AnimationLeft.png"));
            
            // Assets folder paths
            bottleImg = ImageIO.read(new File("assets/bottle.png"));
            bagImg = ImageIO.read(new File("assets/plastic_bag.png"));
            canImg = ImageIO.read(new File("assets/can.png"));
        } catch (Exception e) {
            System.out.println("Some assets not found. Using placeholders.");
        }
    }

    private void spawnTrash(int count) {
        int startX = (getWidth() > 0) ? getWidth() : 1024;
        Image[] choices = {bottleImg, bagImg, canImg};
        
        for (int i = 0; i < count; i++) {
            int x = backgroundOffset + startX + random.nextInt(1000);
            int y = random.nextInt(500) + 100;
            
            Image randomSprite = null;
            if (bottleImg != null || bagImg != null || canImg != null) {
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
        if (name.equals("left")) { left = active; if(active) mirror = true; }
        if (name.equals("right")) { right = active; if(active) mirror = false; }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (up && playerY > 50) playerY -= playerSpeed;
        if (down && playerY < getHeight() - 150) playerY += playerSpeed;
        if (left) backgroundOffset -= scrollSpeed;
        if (right) backgroundOffset += scrollSpeed;

        Rectangle playerRect = new Rectangle(playerX, playerY, 80, 50);
        for (int i = 0; i < trashList.size(); i++) {
            TrashObject t = trashList.get(i);
            int screenX = t.bounds.x - backgroundOffset;
            if (playerRect.intersects(new Rectangle(screenX, t.bounds.y, t.bounds.width, t.bounds.height))) {
                trashList.remove(i);
                score += 10;
                spawnTrash(1);
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(new Color(0, 51, 153));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Bubbles
        g2d.setColor(new Color(255, 255, 255, 60));
        for (int i = 0; i < 20; i++) {
            int bx = (i * 200 - (backgroundOffset % (getWidth() + 200)));
            g2d.fillOval(bx, (i * 70) % getHeight(), 4, 4);
        }

        // Trash
        for (TrashObject t : trashList) {
            int screenX = t.bounds.x - backgroundOffset;
            if (t.sprite != null) {
                g2d.drawImage(t.sprite, screenX, t.bounds.y, 40, 40, null);
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(screenX, t.bounds.y, 20, 20);
            }
        }

        // Diver
        Image currentFrame = mirror ? swimLeft : swimRight;
        if (currentFrame != null) {
            g2d.drawImage(currentFrame, playerX, playerY, 120, 80, this);
        } else {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(playerX, playerY, 120, 80);
        }

        // Score
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 20, 40);
    }
}