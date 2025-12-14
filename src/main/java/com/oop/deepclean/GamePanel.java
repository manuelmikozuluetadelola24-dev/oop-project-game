package com.oop.deepclean;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel {
    private DeepCleanGame controller;

    public GamePanel(DeepCleanGame controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        // Top Label
        JLabel gameLabel = new JLabel("Ocean Floor Cleaning in Progress...", SwingConstants.CENTER);
        gameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameLabel.setForeground(Color.WHITE);
        add(gameLabel, BorderLayout.NORTH);

        // Back to Menu Button
        JButton backBtn = new JButton("Quit to Menu");
        backBtn.addActionListener(e -> controller.showScreen(DeepCleanGame.TITLE_SCREEN));
        add(backBtn, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Textures are placeholders for now because I suck at art
        // 0. Textures
        BufferedImage water;
        TexturePaint background_texture = null;
        try {
            File bkg_texture = new File("./src/main/java/com/oop/deepclean/resources/placeholder-background-sprite.png");
            System.out.println("" + System.getProperty("user.dir"));
            water = ImageIO.read(bkg_texture);
            background_texture = new TexturePaint( water, new Rectangle(this.getWidth(), this.getHeight()));
        } catch (IOException ex) {
            System.getLogger(GamePanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
        
        // 1. Water Gradient
        g2d.setPaint(background_texture);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 2. Sand Floor
        Color sandColor = new Color(210, 180, 140);
        g2d.setColor(sandColor);
        int sandHeight = (int)(getHeight() * 0.15);
        g2d.fillRect(0, getHeight() - sandHeight, getWidth(), sandHeight);
        
        // 3. Simple Seaweed Detail
        g2d.setColor(new Color(34, 139, 34));
        for(int i = 50; i < getWidth(); i += 100) {
            g2d.fillOval(i, getHeight() - sandHeight - 30, 15, 40);
        }
    }
}