package com.chart.statistics.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.chart.statistics.model.DataHolder;

import java.util.HashMap;

public class ColorPatterns extends View {

    private static final int HORIZONTAL_MARGINS = 16;
    private static final int VERTICAL_MARGINS = 16;
    private static final int WIDTH_RECT = 150;
    private static final int HEIGHT_RECT = 100;

    private int lastBottomCoordinate = VERTICAL_MARGINS;
    private Paint paint;
    private Paint paintNames;

    public ColorPatterns(Context context) {
        super(context);
    }

    public ColorPatterns(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorPatterns(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawColors(canvas);
    }

    private void drawColors(Canvas canvas) {
        HashMap<String, Integer> colorMap = DataHolder.newInstance().getMapColor();
        if (colorMap == null || colorMap.isEmpty()) return;

        initPaint();

        String[] obj = colorMap.keySet().toArray(new String[0]);
        for (String name : obj) {
            Integer color = colorMap.get(name);

            // draw rects
            Rect rect = new Rect(
                    HORIZONTAL_MARGINS,
                    lastBottomCoordinate + VERTICAL_MARGINS,
                    WIDTH_RECT + HORIZONTAL_MARGINS,
                    HEIGHT_RECT + lastBottomCoordinate
            );

            lastBottomCoordinate = rect.bottom;

            if (color == null) return;
            paint.setColor(color);
            canvas.drawRect(rect, paint);

            // draw titles
            canvas.drawText(
                    " - " + name,
                    (WIDTH_RECT + (HORIZONTAL_MARGINS * 2)),
                    rect.bottom - (rect.height() / 2),
                    paintNames);
        }
    }

    private void initPaint(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        paintNames = new Paint();
        paintNames.setStyle(Paint.Style.STROKE);
        paintNames.setColor(Color.BLACK);
        paintNames.setTextSize(32f);
    }}
