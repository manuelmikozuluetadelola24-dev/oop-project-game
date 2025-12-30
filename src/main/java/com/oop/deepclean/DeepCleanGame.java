package com.oop.deepclean;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DeepCleanGame extends JFrame {
    
    // --- Constants for Screen Switching ---
    public static final String TITLE_SCREEN = "Main Menu";
    public static final String GAME_SCREEN = "Game";    

    // --- Main Frame Components ---
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    // Shared Background Image (The GIF)
    // NOTE: Ensure "underwater.gif" is in your project folder!
    private Image sharedBackground;

    public DeepCleanGame() {
        // Load the shared resource once here
        sharedBackground = new ImageIcon("underwater.gif").getImage();

        setTitle("DeepClean Game");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Add the screens to the container, passing 'this' instance to them
        // so they can talk back to the main frame (to switch screens or get images)
        mainContainer.add(new TitlePanel(this), TITLE_SCREEN);
        mainContainer.add(new GamePanel(this), GAME_SCREEN);
        
        add(mainContainer);
    }

    // Public getter so panels can draw the background
    public Image getSharedBackground() {
        return sharedBackground;
    }

    public void switchScreen(String screen) {
        cardLayout.show(mainContainer, screen);
        
        // Ensure the game panel gets focus for keyboard input when switched to
        if (screen.equals(GAME_SCREEN)) {
            for (Component comp : mainContainer.getComponents()) {
                if (comp instanceof GamePanel) {
                    comp.requestFocusInWindow();
                    // Optional: Reset game state if entering for the first time or re-entering
                    // ((GamePanel) comp).resetGame(); 
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeepCleanGame().setVisible(true));
    }
}
