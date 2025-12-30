package com.oop.deepclean;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

public class TitlePanel extends JPanel {
    private DeepCleanGame controller;
    
    public TitlePanel(DeepCleanGame controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("Deep Clean", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 64));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        JButton playbtn = createButton("Play");
        JButton helpbtn = createButton("How to Play");
        
        playbtn.addActionListener(e -> controller.switchScreen(DeepCleanGame.GAME_SCREEN));
        helpbtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Use WASD to move around, collect trash by touching them, collect as much trash before the timer runs out."));
        
        add(title);
        add(playbtn);
        add(helpbtn);
        
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button.setBackground(Color.white);
        button.setPreferredSize(new Dimension(600, 300));
        button.setFocusPainted(false);
        return button;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}