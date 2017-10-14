package com.palettepaintbox.drawingdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class PieChart extends Drawable {

    private Paint paint;
    private int data_values[];
    private int color_values[];
    private String data_names[];
    private View view;


    public PieChart(Context c, View v, String[] data_names, int[] data_values, int[] color_values) {
        this.view = v;
        this.data_values = data_values;
        this.color_values = color_values;
        this.data_names = data_names;

        paint = new Paint();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //screen width & height
        int view_w = view.getWidth();
        int view_h = view.getHeight();

        int left_edge = 0;
        int top_edge = 0;
        int right_edge = 400;
        int bottom_edge = 400;
        //chart area rectangle
        RectF arc_bounds = new RectF(
                left_edge,
                top_edge,
                right_edge,
                bottom_edge
        );

        int value_sum = 0;
        //sum of data values
        for (int datum : data_values)
            value_sum += datum;

        float startAngle = 0;
        int i = 0;

        for (int datum : data_values) {
            if (datum == 0) continue;

            //calculate start & end angle for each data value
            float endAngle = value_sum == 0 ? 0 : 360 * datum / (float) value_sum;
            float newStartAngle = startAngle + endAngle;


            int flickr_pink = color_values[i % color_values.length];
            paint.setColor(flickr_pink);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(0.5f);

            //gradient fill color
            LinearGradient linearGradient = new LinearGradient(arc_bounds.left, arc_bounds.top, arc_bounds.right,arc_bounds.bottom, flickr_pink, Color.WHITE, Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);

            //draw fill arc
            canvas.drawArc(arc_bounds, startAngle, endAngle, true, paint);

            Paint linePaint = new Paint();
            linePaint.setAntiAlias(true);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeJoin(Paint.Join.ROUND);
            linePaint.setStrokeCap(Paint.Cap.ROUND);
            linePaint.setStrokeWidth(0.5f);
            linePaint.setColor(Color.BLACK);

            //draw border arc
            canvas.drawArc(arc_bounds, startAngle, endAngle, true, linePaint);

            int barStartX = 50;
            int barWidth = 20;
            int padding_bottom = 20;
            int barStartY = view_h-padding_bottom+(i-1)*2*barWidth;

            Rect barRect = new Rect(barStartX,barStartY,barStartX+barWidth,barStartY+barWidth);

            //draw legend box
            canvas.drawRect(barRect, paint);
            canvas.drawRect(barRect,linePaint);


            Paint textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(30);

            //draw legend text
            canvas.drawText(data_names[i], barStartX+2*barWidth, barStartY+barWidth, textPaint);

            startAngle = newStartAngle;
            i++;
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
