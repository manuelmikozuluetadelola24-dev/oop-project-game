package com.oop.deepclean;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DeepCleanGame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public static final String TITLE_SCREEN = "Main Menu";
    public static final String GAME_SCREEN = "Game";    
    
    public DeepCleanGame() {
        setTitle("DeepClean Game");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Add the separated GamePanel to the frame
        mainContainer.add(new TitlePanel(this), TITLE_SCREEN);
        mainContainer.add(new GamePanel(), GAME_SCREEN);
        add(mainContainer);
    }

    public void switchScreen(String screen) {
        cardLayout.show(mainContainer, screen);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeepCleanGame().setVisible(true));
    }
}
