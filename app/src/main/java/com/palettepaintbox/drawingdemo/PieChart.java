package com.palettepaintbox.drawingdemo;

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


    public PieChart(View v, String[] data_names, int[] data_values, int[] color_values) {
        this.view = v;
        this.data_values = data_values;
        this.color_values = color_values;
        this.data_names = data_names;

        paint = new Paint();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //screen height
        int view_h = view.getHeight();

        int bar_start_x = 50;
        int bar_width = 20;
        int padding_bottom = 500;

        int left_edge = 0;
        int top_edge = 0;
        int right_edge = 400;
        int bottom_edge = 400;
        //chart area square
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

        float start_angle = 0;
        int i = 0;

        for (int datum : data_values) {
            if (datum == 0) continue;

            //calculate start & end angle for each data value
            float end_angle = value_sum == 0 ? 0 : 360 * datum / (float) value_sum;
            float new_start_angle = start_angle + end_angle;


            int current_color = color_values[i % color_values.length];
            paint.setColor(current_color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(0.5f);

            //gradient fill color
            LinearGradient linear_gradient = new LinearGradient(arc_bounds.left, arc_bounds.top, arc_bounds.right,arc_bounds.bottom, current_color, Color.WHITE, Shader.TileMode.CLAMP);
            paint.setShader(linear_gradient);

            //draw fill arc
            canvas.drawArc(arc_bounds, start_angle, end_angle, true, paint);

            Paint line_paint = new Paint();
            line_paint.setAntiAlias(true);
            line_paint.setStyle(Paint.Style.STROKE);
            line_paint.setStrokeJoin(Paint.Join.ROUND);
            line_paint.setStrokeCap(Paint.Cap.ROUND);
            line_paint.setStrokeWidth(0.5f);
            line_paint.setColor(Color.BLACK);

            //draw border arc
            canvas.drawArc(arc_bounds, start_angle, end_angle, true, line_paint);

            int bar_start_y = view_h-padding_bottom+(i-1)*2*bar_width;

            Rect bar_rect = new Rect(bar_start_x,bar_start_y,bar_start_x+bar_width,bar_start_y+bar_width);

            //draw legend box
            canvas.drawRect(bar_rect, paint);
            canvas.drawRect(bar_rect,line_paint);


            Paint text_paint = new Paint();
            text_paint.setAntiAlias(true);
            text_paint.setColor(current_color);
            text_paint.setTextSize(30);


            //draw legend text
            canvas.drawText(data_names[i], bar_start_x+2*bar_width, bar_start_y+bar_width, text_paint);

            start_angle = new_start_angle;
            i++;
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter color_filter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
