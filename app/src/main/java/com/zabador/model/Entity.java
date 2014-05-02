/**
 * 
 */
package com.zabador.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Entity {

	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
    private int xv; // x velocity
    private int yv; // y velocity

	
	public Entity(Bitmap bitmap, int x, int y, int xv, int yv) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
        this.xv = xv;
        this.yv = yv;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
    public int getXv() {
        return xv;
    }
    public void setXv(int xv) {
        this.xv = xv;
    }
    public int getYv() {
        return yv;
    }
    public void setYv(int yv) {
        this.yv = yv;
    }

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	public void update() {
			x += (getXv());
			y += (getYv());
	}
}
