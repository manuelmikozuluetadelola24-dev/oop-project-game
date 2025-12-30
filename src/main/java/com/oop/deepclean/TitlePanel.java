package com.oop.deepclean;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {
    
    private DeepCleanGame mainFrame;

    public TitlePanel(DeepCleanGame mainFrame) {
        this.mainFrame = mainFrame; // Keep reference to switch screens
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Title Label
        JLabel title = new JLabel("Deep Clean", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 64));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        // Buttons
        JButton playbtn = createButton("Play");
        JButton helpbtn = createButton("How to Play");
        JButton quitbtn = createButton("Quit Game");
        
        // Button Actions
        playbtn.addActionListener(e -> mainFrame.switchScreen(DeepCleanGame.GAME_SCREEN));
        
        helpbtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Use WASD to move around, collect trash by touching them,\ncollect as much trash as possible before the timer runs out."));

        quitbtn.addActionListener(e -> System.exit(0));
        
        // Layout (Adding components with spacing)
        add(Box.createVerticalGlue()); // Pushes everything to center vertically
        add(title);
        add(Box.createVerticalStrut(40));
        add(playbtn);
        add(Box.createVerticalStrut(20));
        add(helpbtn);
        add(Box.createVerticalStrut(20));
        add(quitbtn);
        add(Box.createVerticalGlue());
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        // Make button bigger
        button.setMaximumSize(new Dimension(200, 50));
        return button;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mainFrame.getSharedBackground(), 0, 0, getWidth(), getHeight(), this);
    }
}
