package com.oop.deepclean;

import java.awt.*;
import javax.swing.*;

public class DeepCleanGame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;

    public static final String TITLE_SCREEN = "Title";
    public static final String GAME_SCREEN = "Game";

    public DeepCleanGame() {
        setTitle("DeepClean");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Add the panels to the card layout
        mainContainer.add(new TitlePanel(this), TITLE_SCREEN);
        mainContainer.add(new GamePanel(this), GAME_SCREEN);

        add(mainContainer);
    }

    // Method to switch screens
    public void showScreen(String screenName) {
        cardLayout.show(mainContainer, screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeepCleanGame().setVisible(true));
    }
}