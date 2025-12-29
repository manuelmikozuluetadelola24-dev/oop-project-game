import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DeepClean extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public DeepClean() {
        setTitle("DeepClean Game");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add the GamePanel to the container
        mainContainer.add(new GamePanel(), "Game");
        add(mainContainer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeepCleanGame().setVisible(true));
    }
}
