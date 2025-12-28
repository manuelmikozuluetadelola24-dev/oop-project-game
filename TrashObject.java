import java.awt.Image;
import java.awt.Rectangle;

public class TrashObject {
    public Rectangle bounds;
    public Image sprite;

    public TrashObject(int x, int y, Image sprite) {
        // Defines the hitbox and visual for the trash
        this.bounds = new Rectangle(x, y, 40, 40);
        this.sprite = sprite;
    }
}