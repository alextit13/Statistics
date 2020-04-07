package com.chart.statistics.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.chart.statistics.model.DataHolder;
import com.chart.statistics.model.utils.State;

import java.util.List;

public class CircleDiagram extends View {

    private static final float STROKE_WIDTH = 3f;

    private List<State> data;
    private Paint paint;
    private String timeFin;
    private String timeSt;

    private RectF oval;

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
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setStyle(Paint.Style.FILL);
    }

    float startAngle = .0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data.isEmpty()) return;

        int height = getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = (int) ((height / 2) - (STROKE_WIDTH / 2));

        if (oval == null)
            oval = new RectF();

        paint.setColor(Color.GRAY);
        oval.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(oval, startAngle, 360, true, paint);

        for (int i = 0; i < data.size(); i++) {
            paint.setColor(getColorByStateName(data.get(i)));
            float swipeAngle = getAngle(data.get(i).getId()) - startAngle;
            canvas.drawArc(oval, startAngle, swipeAngle, true, paint);
            startAngle += swipeAngle;
        }
    }

    private float getAngle(String time){
        long timeStart = Long.parseLong(timeSt);
        long timeCurrent = Long.parseLong(time);
        long timeEnd = Long.parseLong(timeFin);

        long numMillisecondsInAllInterval = timeEnd - timeStart;
        float degreesInOneMillisecond = 360 / (float) numMillisecondsInAllInterval;

        return (timeCurrent - timeStart) * degreesInOneMillisecond;
    }

    private @ColorInt
    int getColorByStateName(State state) {
        return DataHolder.newInstance().getRandomColor(state);
    }

    public void setSourceData(
            List<State> states,
            String timeObservationStart,
            String timeObservationFinish
    ) {
        this.timeFin = timeObservationStart;
        data = states;
        this.timeSt = timeObservationFinish;
        invalidate();
    }
}
