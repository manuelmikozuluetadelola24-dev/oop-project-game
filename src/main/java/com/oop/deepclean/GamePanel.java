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
        BufferedImage water, sand, seaweed;
        TexturePaint water_texture = null;
        TexturePaint sand_texture = null;
        TexturePaint seaweed_texture = null;
        try {
            File wtr_texture_file = new File("./src/main/java/com/oop/deepclean/resources/placeholder-background-sprite.png");
            File snd_texture_file = new File("./src/main/java/com/oop/deepclean/resources/placeholder-sand-sprite.png");
            File swd_texture_file = new File("./src/main/java/com/oop/deepclean/resources/placeholder-seaweed-sprite.png");
            water = ImageIO.read(wtr_texture_file);
            water_texture = new TexturePaint( water, new Rectangle(this.getWidth(), this.getHeight()));
            sand =  ImageIO.read(snd_texture_file);
            sand_texture = new TexturePaint( sand, new Rectangle(this.getWidth(), this.getHeight()));
            seaweed = ImageIO.read(swd_texture_file);
            seaweed_texture = new TexturePaint( seaweed, new Rectangle(this.getWidth(), this.getHeight()));
        } catch (IOException ex) {
            System.getLogger(GamePanel.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
        
        // 1. Water Gradient
        g2d.setPaint(water_texture);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 2. Sand Floor
        g2d.setPaint(sand_texture);
        int sandHeight = (int)(getHeight() * 0.15);
        g2d.fillRect(0, getHeight() - sandHeight, getWidth(), sandHeight);
        
        // 3. Simple Seaweed Detail
        g2d.setPaint(seaweed_texture);
        for(int i = 50; i < getWidth(); i += 100) {
            g2d.fillOval(i, getHeight() - sandHeight - 30, 15, 40);
        }
    }
}