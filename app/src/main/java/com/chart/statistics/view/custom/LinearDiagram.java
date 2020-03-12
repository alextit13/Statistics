package com.chart.statistics.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.chart.statistics.model.DataHolder;
import com.chart.statistics.model.utils.State;

import java.util.Date;
import java.util.List;

public class LinearDiagram extends View {

    private List<State> list;
    private Paint paint;

    public LinearDiagram(Context context) {
        super(context);
        init();
    }

    public LinearDiagram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearDiagram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (list.isEmpty()) return;

        for (int i = 0; i <= list.size() - 1; i++) {
            State state = list.get(i);
            Rect rect = new Rect(
                    getStartCoordinate(i),
                    0,
                    getEndCoordinate(i),
                    getHeight()
            );
            paint.setColor(getColorByStateName(state));
            canvas.drawRect(rect, paint);
        }
    }

    private int getStartCoordinate(int position) {
        if (position == 0) {
            return 0;
        } else {
            long time = Long.valueOf(list.get(position).getId()); // 1070
            long timeFirstEvent = Long.valueOf(list.get(0).getId()); // 234
            long numMillisecondsInAllInterval = (new Date().getTime() - (timeFirstEvent));
            float px = getWidth() / (float) numMillisecondsInAllInterval;
            return (int) ((time - timeFirstEvent) * px);
        }
    }

    private int getEndCoordinate(int position) {
        if (position == list.size() - 1) {
            return getWidth();
        } else {
            return getStartCoordinate(position + 1);
        }
    }

    private @ColorInt
    int getColorByStateName(State state) {
        return DataHolder.newInstance().getStateColorByState(state);
    }

    public void setSourseData(List<State> stateList) {
        list = stateList;
        invalidate();
    }
}
