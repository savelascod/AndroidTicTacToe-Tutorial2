package edu.harding.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by mordreth on 9/19/15.
 */
public class BoardView extends View {
    public static final int GRID_WIDH = 6;

    private Bitmap mHumanBitmap;
    private Bitmap mAndroidBitmap;

    public BoardView(Context context) {
        super(context);
    }
}
