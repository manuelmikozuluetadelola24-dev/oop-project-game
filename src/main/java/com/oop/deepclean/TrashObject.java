package com.oop.deepclean;

import java.awt.Image;
import java.awt.Rectangle;

public class TrashObject {
    public int x, y;
    public Rectangle bounds;
    public Image sprite;

    public TrashObject(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.bounds = new Rectangle(x, y, 40, 40);
    }
}
