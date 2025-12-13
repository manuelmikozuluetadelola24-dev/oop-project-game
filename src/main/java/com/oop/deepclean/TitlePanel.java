package com.oop.deepclean;

import java.awt.*;
import javax.swing.*;

public class TitlePanel extends JPanel {
    private final Color PRIMARY_COLOR = new Color(0, 102, 204);
    private final Color SECONDARY_COLOR = new Color(102, 204, 255);
    private DeepCleanGame controller;

    public TitlePanel(DeepCleanGame controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("DeepClean", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 64));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Buttons
        JButton playBtn = createStyledButton("PLAY");
        JButton howToBtn = createStyledButton("HOW TO PLAY");
        JButton creditsBtn = createStyledButton("CREDITS");

        // Listeners
        playBtn.addActionListener(e -> controller.showScreen(DeepCleanGame.GAME_SCREEN));
        howToBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Clean the ocean floor!"));
        creditsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Created by You"));

        gbc.gridy = 1; add(playBtn, gbc);
        gbc.gridy = 2; add(howToBtn, gbc);
        gbc.gridy = 3; add(creditsBtn, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(PRIMARY_COLOR);
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(250, 50));
        button.setFocusPainted(false);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, 0, getHeight(), SECONDARY_COLOR);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}