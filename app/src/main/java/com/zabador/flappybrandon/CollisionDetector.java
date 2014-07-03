package com.zabador.flappybrandon;

import android.graphics.Bitmap;
import android.util.Log;

import com.zabador.model.Entity;

/**
 * Created by schneis on 7/2/14.
 */
public class CollisionDetector {

    private Entity brandon;
    private Entity pipeTop;
    private Entity pipeBottom;
    private int halfBrandonWidth;
    private int halfBrandonHeight;
    private int halfPipeWidth;
    private int halfPipeHeight;



    public CollisionDetector(Entity b, Entity pt, Entity pb) {
        brandon = b;
        pipeTop = pt;
        pipeBottom = pb;
        halfBrandonWidth = b.getBitmap().getWidth()/2;
        halfBrandonHeight = b.getBitmap().getHeight()/2;
        halfPipeWidth = pt.getBitmap().getWidth()/2;
        halfPipeHeight = pt.getBitmap().getHeight()/2;
    }

    public boolean collision() {

        int brandonLeftEdge = brandon.getX() - halfBrandonWidth;
        int brandonRightEdge = brandon.getX() + halfBrandonWidth;
        int brandonTop = brandon.getY() - halfBrandonHeight;
        int brandonBottom = brandon.getY() + halfBrandonHeight;

        int pipeRight = pipeTop.getX() + halfPipeWidth;
        int pipeLeft = pipeTop.getX() - halfPipeWidth;

        int pipeTopBottom = pipeTop.getY() + halfPipeHeight;
        int pipeBottomTop = pipeBottom.getY() - halfPipeHeight;



        if ((brandonRightEdge >= pipeLeft && brandonRightEdge <= pipeRight) || (brandonLeftEdge >= pipeLeft && brandonLeftEdge <= pipeRight)) {
            if (brandonTop <= pipeTopBottom)
                return true;
            else if (brandonBottom >= pipeBottomTop)
                return true;
        }

        return false;
    }
}
