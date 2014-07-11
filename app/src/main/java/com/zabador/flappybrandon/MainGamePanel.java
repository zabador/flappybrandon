package com.zabador.flappybrandon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.zabador.App;
import com.zabador.model.Entity;

import java.util.Random;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Entity brandon, bottonPipe, topPipe;
    private Context context;
    private WindowManager wm;
    private Display display;
    private Point size;
    private int width;
    private int height;
    private int actionBarHeight;
    private int brandonMovement;
    private int pipeMovement;
    private final int GAP = 250;
    private boolean gameStarted, gameOver;
    private CollisionDetector cd;
    private int score = 0;
    private Paint paint;
    private final float TEXTSIZE = 50.0f;
    private SoundHelper sh;

	public MainGamePanel(Context context) {
		super(context);
        this.context = context;
        this.size = new Point();
        this.sh = new SoundHelper(context);
        sh.dia();

        this.wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        this.paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(getDPFromPixels(TEXTSIZE));

        this.display = wm.getDefaultDisplay();

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }        display.getSize(size);

        this.width = size.x;
        this.height = size.y - actionBarHeight;


        gameStarted = false;
        gameOver = false;

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create brandon and load bitmap
        this.brandonMovement = 15;
		brandon = new Entity(BitmapFactory.decodeResource(getResources(), R.drawable.brandonup), 100, 500, 0, 0);

        // set up the pipes
        Bitmap pipes = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipe);
        Bitmap scaledPipes = Bitmap.createScaledBitmap(pipes, 100, height - GAP, true);
        this.pipeMovement = -15;
        topPipe = new Entity(scaledPipes, width + 500, 200, pipeMovement, 0);
        bottonPipe = new Entity(scaledPipes, width + 500,
                topPipe.getY() + topPipe.getBitmap().getHeight() + GAP, pipeMovement, 0);


        this.cd = new CollisionDetector(brandon, topPipe, bottonPipe, height);
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
        thread.setRunning(false);
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
        Log.i(TAG, "onTouchEvent");
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, "action down");
            brandonMovement = -35;
            gameStarted = true;
            if(!gameOver)
                sh.flap();
        }

		return true;
	}

	public void render(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        try {
            canvas.drawBitmap(scaledBitmap, 0, 0, null);
            brandon.draw(canvas);
            topPipe.draw(canvas);
            bottonPipe.draw(canvas);
            canvas.drawText("Score: " + score, 15, 50, paint);
            if(gameOver) {
                Paint gameOver = new Paint();
                gameOver.setColor(Color.RED);
                gameOver.setTextSize(getDPFromPixels(100.0f));
                gameOver.setTextAlign(Paint.Align.CENTER);

                RectF bounds = new RectF(0, 0, width, height);
                canvas.drawText("Game Over", bounds.centerX(), bounds.centerY(), gameOver);
            }
        }catch(Exception e) {
            // TODO stop thread gracefully
        }

	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {

        if(cd.collision()) {
            thread.setRunning(false);
            gameOver = true;
            sh.howembarrasing();
            if(score > App.getScore())
                App.saveScore(score);
        }

		// check collision with right wall if heading right
//		if (brandon.getX() + brandon.getBitmap().getWidth() / 2 >= width)
//		// check collision with left wall if heading left
//		if (brandon.getX() - brandon.getBitmap().getWidth() / 2 <= 0)
//		// check collision with bottom wall if heading down
//		if (brandon.getY() + brandon.getBitmap().getHeight() / 2 >= height)
//		// check collision with top wall if heading up
//	    if (brandon.getY() - brandon.getBitmap().getHeight() / 2 <= 0) {

        if (gameStarted && !gameOver) {
            brandonMovement = brandonMovement + 4;
            if (brandonMovement >= 0) {
                brandon.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brandondown));
            }
            else {
                brandon.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brandonup));
            }
            if (brandonMovement > 30) {
                brandonMovement = 30;
            }
            brandon.setYv(brandonMovement);

            brandon.update();

            if (bottonPipe.getX() <= 0) {
                sh.ding();
                score++;
                redrawPipes();
            }
            bottonPipe.update();
            topPipe.update();

        }
	}

    public void redrawPipes() {
        Random randomY = new Random();

        int y = randomY.nextInt(height - 500);
        bottonPipe.setX(width + bottonPipe.getBitmap().getWidth());
        topPipe.setX(width + topPipe.getBitmap().getWidth());
        topPipe.setY(y - topPipe.getBitmap().getHeight() + 450);
        bottonPipe.setY(topPipe.getY() + topPipe.getBitmap().getHeight() + GAP);

        Log.i(TAG, "bottom pipe = " + bottonPipe.getY());
        Log.i(TAG, "random y = " + y);
        Log.i(TAG, "top pipe height = " + topPipe.getBitmap().getHeight());
    }
    public float getDPFromPixels(float pixels) {
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        switch(metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                pixels = pixels * 0.75f;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                //pixels = pixels * 1.0f;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                pixels = pixels * 1.5f;
                break;
        }
        return pixels;
    }


}