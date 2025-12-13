package com.oop.deepclean;

import java.awt.*;
import javax.swing.*;

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

        // 1. Water Gradient
        Color surfaceColor = new Color(51, 204, 255);
        Color deepColor = new Color(0, 51, 102);
        GradientPaint watergp = new GradientPaint(0, 0, surfaceColor, 0, getHeight() * 0.85f, deepColor);
        g2d.setPaint(watergp);
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