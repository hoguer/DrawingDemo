package com.palettepaintbox.drawingdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //main layout
        setContentView(R.layout.activity_main);

        //pie chart parameters
        int data_values[] = {20,10,25,5,15,25};
        int color_values[] = {Color.MAGENTA, Color.RED, Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN};
        String item_names[] = { "Grape", "Strawberry Rhubarb", "Key Lime", "Blueberry", "Lemon","Other"};

        //get the ImageView
        ImageView imageView = (ImageView) findViewById(R.id.image_placeholder);

        //create pie chart Drawable and set it to ImageView
        PieChart pieChart = new PieChart(imageView, item_names, data_values, color_values);
        imageView.setImageDrawable(pieChart);
    }
}
