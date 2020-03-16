package com.chart.statistics.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.chart.statistics.model.utils.State;

import java.util.List;

public class CircleDiagram extends View {

    private List<State> data;
    private Paint paint;

    public CircleDiagram(Context context) {
        super(context);
        init();
    }

    public CircleDiagram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleDiagram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.drawArc();
        canvas.drawCircle(100, 100, 100, paint);
        canvas.drawArc(10, 10, 110, 110, 0, 90, true, paint);
    }

    public void setSourceData(List<State> list) {
        data = list;
        invalidate();
    }
}
