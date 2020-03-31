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
    private static final int BOTTOM_MARGINS = 40;

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

        // draw main rects
        for (int i = 0; i < list.size(); i++) {
            Rect rect = new Rect(
                    getStartCoordinate(i),
                    TOP_MARGIN,
                    getEndCoordinate(i),
                    getHeight() / 2
            );
            paint.setColor(getColorByStateName(list.get(i)));
            canvas.drawRect(rect, paint);
        }

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        // bottom horizontal line
        canvas.drawLine(0f, getHeight() - BOTTOM_MARGINS, getWidth(), getHeight() - BOTTOM_MARGINS, paint);
        // first vertical line
        canvas.drawLine(0f + paint.getStrokeWidth(), getHeight() - BOTTOM_MARGINS, 0f + paint.getStrokeWidth(), getHeight() / 2, paint);
        // end vertical line
        canvas.drawLine(getWidth() - paint.getStrokeWidth(), getHeight() - BOTTOM_MARGINS, getWidth() - paint.getStrokeWidth(), getHeight() / 2, paint);
        // draw vertical lines
        Rect textTimeBounds = new Rect();
        Paint timePaint = new Paint();
        timePaint.setTextSize(24f);
        timePaint.setColor(Color.BLACK);
        timePaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < list.size() - 1; i++) {
            float startX = (getStartCoordinate(i) + getStartCoordinate(i + 1)) / 2;
            canvas.drawLine(
                    startX,
                    getHeight() - BOTTOM_MARGINS,
                    startX,
                    getHeight() / 2,
                    paint
            );
            // draw time below vertical lines
            String time = getTimePattern(list.get(i).getId());
            timePaint.getTextBounds(time, 0, time.length(), textTimeBounds);
            // canvas.drawText(time, startX, getHeight(), timePaint);
        }
        // draw title
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setStrokeWidth(9f);
        titlePaint.setTextSize(TEXT_TITLE_SIZE);
        canvas.drawText(nameTitle, 0f, BOTTOM_TEXT_MARGIN, titlePaint);
        // draw start time
        Paint startTimePaint = new Paint();
        startTimePaint.setColor(Color.BLACK);
        startTimePaint.setStrokeWidth(8f);
        startTimePaint.setTextSize(TEXT_TIME_SIZE);
        canvas.drawText(getDatePattern(list.get(0).getId()), 0f, BOTTOM_TEXT_MARGIN + TEXT_TIME_SIZE + DEFAULT_MARGINS, startTimePaint);
        // draw end time
        Paint endTimePaint = new Paint();
        endTimePaint.setColor(Color.BLACK);
        endTimePaint.setStrokeWidth(8f);
        endTimePaint.setTextSize(TEXT_TIME_SIZE);
        endTimePaint.setTextAlign(Paint.Align.RIGHT);
        Rect bounds = new Rect();
        String timeEnd = getDatePattern(list.get(list.size() - 1).getId());
        endTimePaint.getTextBounds(timeEnd, 0, timeEnd.length(), bounds);
        canvas.drawText(timeEnd,
                getWidth(),
                BOTTOM_TEXT_MARGIN + TEXT_TIME_SIZE + DEFAULT_MARGINS, endTimePaint);
    }

    private String getDatePattern(String longTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
        Date date = new Date(Long.parseLong(longTime));
        return simpleDateFormat.format(date);
    }

    private String getTimePattern(String longTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.ROOT);
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
    int getColorByStateName(State state) {
        return DataHolder.newInstance().getRandomColor(state);
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
