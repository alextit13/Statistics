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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LinearDiagram extends View {

    private static final int TOP_MARGIN = 80;
    private static final int TEXT_TITLE_SIZE = 40;
    private static final int TEXT_TIME_SIZE = 30;
    private static final int BOTTOM_TEXT_MARGIN = 30;
    private static final int DEFAULT_MARGINS = 16;

    private List<State> list;
    private Paint paint;
    private String timeFinish;
    private String nameTitle;

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
            Rect rect = new Rect(
                    getStartCoordinate(i),
                    TOP_MARGIN,
                    getEndCoordinate(i),
                    getHeight() / 2
            );
            paint.setColor(getColorByStateName());
            canvas.drawRect(rect, paint);
        }

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        // bottom horizontal line
        canvas.drawLine(0f, getHeight(), getWidth(), getHeight(), paint);
        // first vertical line
        canvas.drawLine(0f + paint.getStrokeWidth(), getHeight(), 0f + paint.getStrokeWidth(), getHeight() / 2, paint);
        // end vertical line
        canvas.drawLine(getWidth() - paint.getStrokeWidth(), getHeight(), getWidth() - paint.getStrokeWidth(), getHeight() / 2, paint);

        for (int i = 0; i < list.size() - 1; i++) {
            float startX = (getStartCoordinate(i) + getStartCoordinate(i + 1)) / 2;

            canvas.drawLine(
                    startX,
                    getHeight(),
                    startX,
                    getHeight() / 2,
                    paint
            );
        }
        // draw title
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setStrokeWidth(9f);
        titlePaint.setTextSize(TEXT_TITLE_SIZE);
        canvas.drawText(nameTitle, 0f, BOTTOM_TEXT_MARGIN, titlePaint);
        // draw start time
        Paint startTimePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setStrokeWidth(5f);
        titlePaint.setTextSize(TEXT_TIME_SIZE);
        canvas.drawText(getTimePattern(list.get(list.size() - 1).getId()), 0f, BOTTOM_TEXT_MARGIN + TEXT_TIME_SIZE + DEFAULT_MARGINS, titlePaint);
    }

    private String getTimePattern(String longTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
        Date date = new Date(Long.parseLong(longTime));
        return simpleDateFormat.format(date);
    }

    private int getStartCoordinate(int position) {
        if (position == 0) {
            return 0;
        } else {
            long time = Long.valueOf(list.get(position).getId());
            long timeFirstEvent = Long.valueOf(list.get(0).getId());
            long numMillisecondsInAllInterval = (Long.parseLong(timeFinish) - (timeFirstEvent));
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
    int getColorByStateName() {
        return DataHolder.newInstance().getRandomColor();
    }

    public void setSourceData(List<State> stateList, String timeObservationFinish) {
        list = stateList;
        timeFinish = timeObservationFinish;
        invalidate();
    }

    public void setTitle(String title) {
        nameTitle = title;
        invalidate();
    }
}
