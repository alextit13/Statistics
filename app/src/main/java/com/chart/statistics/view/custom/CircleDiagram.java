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
    private String timeStart;
    private String timeFinish;

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

    float p = .0f;

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

        float flipAngle = getSweepAngle(timeStart, data.get(0).getId());

        paint.setColor(Color.GREEN);
        oval.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(oval, p, flipAngle, true, paint);
        p = flipAngle;

        for (int i = 0; i < data.size(); i++) {
            String timeCurrent = data.get(i).getId();
            String timePrevious;

            if (i == 0) {
                timePrevious = data.get(0).getId();
            } else {
                timePrevious = data.get(i - 1).getId();
            }

            paint.setColor(getColorByStateName(data.get(i)));
            flipAngle = getSweepAngle(timePrevious, timeCurrent);

            canvas.drawArc(oval, p, flipAngle, true, paint);
            p = p + flipAngle;
        }
        flipAngle = getSweepAngle(data.get(data.size() - 1).getId(), timeFinish);
        canvas.drawArc(oval, p, flipAngle, true, paint);
    }

    private float getSweepAngle(String previousTime, String currentTime) {
        float previous = getStartAngle(previousTime);
        float current = getStartAngle(currentTime);
        return current - previous;
    }

    private float getStartAngle(String time) {
        long previous = Long.parseLong(time);
        long start = Long.parseLong(timeStart);
        if (previous == start) return 0;
        long finish = Long.parseLong(timeFinish);
        return (float) ((360 * (previous - start)) / (finish - start));
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
        this.timeStart = timeObservationStart;
        data = states;
        this.timeFinish = timeObservationFinish;
        invalidate();
    }
}
