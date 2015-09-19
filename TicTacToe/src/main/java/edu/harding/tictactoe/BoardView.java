package edu.harding.tictactoe;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mordreth on 9/19/15.
 */
public class BoardView extends View {
    public static final int GRID_WIDH = 6;

    private Bitmap mHumanBitmap;
    private Bitmap mAndroidBitmap;

    private Paint mPaint;

    public BoardView(Context context) {
        super(context);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }


    public void initialize() {
        mAndroidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.x_img);
        mAndroidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_img);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int boardWidth = getWidth();
        int boardHeight = getHeight();

        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(GRID_WIDH);

        int cellWidth = boardWidth / 3;
        int cellHeight = boardHeight / 3;

        canvas.drawLine(cellWidth, 0, cellWidth, boardHeight, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardHeight, mPaint);

        canvas.drawLine(0, cellHeight, boardWidth, cellHeight, mPaint);
        canvas.drawLine(0, cellHeight * 2, boardWidth, cellHeight * 2, mPaint);
    }

}
