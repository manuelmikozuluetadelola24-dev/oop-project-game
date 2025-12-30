package com.oop.deepclean;
import java.awt.Image;
import java.awt.Rectangle;

public class TrashObject {
    private int x, y;
    private Rectangle bounds;
    private Image sprite;

    public TrashObject(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.bounds = new Rectangle(x, y, 40, 40);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Rectangle getBounds() { return bounds; }
    public Image getSprite() { return sprite; }
}
