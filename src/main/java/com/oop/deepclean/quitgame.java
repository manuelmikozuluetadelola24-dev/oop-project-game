public JButton createQuitButton() {
    JButton button = new JButton("EXIT GAME");
    
    button.setFont(new Font("Arial", Font.BOLD, 20));
    button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    button.setBackground(Color.white);
    button.setPreferredSize(new Dimension(600, 300));
    button.setFocusPainted(false);

    // The logic to close the application
    button.addActionListener(e -> System.exit(0));

    return button;
}
