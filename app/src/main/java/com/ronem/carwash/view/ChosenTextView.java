package com.ronem.carwash.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by ram on 7/31/17.
 */

public class ChosenTextView extends android.support.v7.widget.AppCompatTextView {
    public ChosenTextView(Context context) {
        super(context);
        initializeFontFace(context);
    }

    private void initializeFontFace(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/chosenebold.ttf");
        this.setTypeface(font);
    }

    public ChosenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeFontFace(context);
    }

    public ChosenTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeFontFace(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
