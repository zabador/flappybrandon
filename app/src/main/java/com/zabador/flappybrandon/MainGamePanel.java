/**
 * 
 */
package com.zabador.flappybrandon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.zabador.model.Entity;
import com.zabador.model.components.Speed;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Entity brandon;
    private Context context;
    private WindowManager wm;
    private Display display;
    private Point size;
    private int width;
    private int height;
    private int actionBarHeight;

	public MainGamePanel(Context context) {
		super(context);
        this.context = context;
        this.size = new Point();

        this.wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.display = wm.getDefaultDisplay();

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }        display.getSize(size);

        this.width = size.x;
        this.height = size.y - actionBarHeight * 2;
        // Calculate ActionBar height

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create brandon and load bitmap
		brandon = new Entity(BitmapFactory.decodeResource(getResources(), R.drawable.brandonup), 50, 50);
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the brandon
			brandon.handleActionDown((int) event.getX(), (int) event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			if (brandon.isTouched()) {
				// the brandon was picked up and is being dragged
				brandon.setX((int)event.getX());
				brandon.setY((int)event.getY());
            }

		} if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (brandon.isTouched()) {
				brandon.setTouched(false);
			}
		}
		return true;
	}

	public void render(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        canvas.drawBitmap(scaledBitmap, 0, 0, null);
		brandon.draw(canvas);
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		// check collision with right wall if heading right
		if (brandon.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& brandon.getX() + brandon.getBitmap().getWidth() / 2 >= width) {
		}
		// check collision with left wall if heading left
		if (brandon.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& brandon.getX() - brandon.getBitmap().getWidth() / 2 <= 0) {
		}
		// check collision with bottom wall if heading down
		if (brandon.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
				&& brandon.getY() + brandon.getBitmap().getHeight() / 2 >= height) {
		}
		// check collision with top wall if heading up
		if (brandon.getSpeed().getyDirection() == Speed.DIRECTION_UP
				&& brandon.getY() - brandon.getBitmap().getHeight() / 2 <= 0) {
		}
		// Update the lone brandon
		brandon.update();
	}

}
