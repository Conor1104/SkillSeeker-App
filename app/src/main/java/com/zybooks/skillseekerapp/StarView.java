package com.zybooks.skillseekerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class StarView extends View {
    private Paint paint;
    private boolean isSelected;
    private boolean isHalf;

    public StarView(Context context) {
        super(context);
        init();
    }

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFFFD700);//Gold
        isSelected = false;
        isHalf = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) / 2;
        int x = width / 2;
        int y = height / 2;

        StarPath starPath = new StarPath(x, y, size / 2, size, 5);
        if (isSelected) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(starPath, paint);
        } else if (isHalf) {
            // Draw half-star logic
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(starPath, paint); // Draw half star if needed
        } else {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(starPath, paint);
        }
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate(); //Redraws the view
    }

    public void setHalf(boolean isHalf) {
        this.isHalf = isHalf;
        invalidate(); // Redraws the view
    }
}
