package com.example.ejemplovistas;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class surface_view extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surface;
    private View background;
    private Button cambioColor;
    private android.graphics.Path path;
    private Paint mPaint = new Paint();
    private int[] colors = new int[]{Color.WHITE, Color.GREEN, Color.BLUE};
    private int colorActual = Color.WHITE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);

        cambioColor = findViewById(R.id.btnCambioColor);
        cambioColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indiceColor = new Random().nextInt(colors.length);
                colorActual = colors[indiceColor];
                cambiarColorFondo(colorActual);
            }
        });

        surface = (SurfaceView) findViewById(R.id.surfaceView);
        surface.setZOrderOnTop(true);
        surface.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surface.getHolder().addCallback(this);

        background = (View) findViewById(R.id.surfaceBackground);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(50);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        path = new Path();
        surface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float X = event.getX();
                float Y = event.getY();

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        path.reset();
                        path.moveTo(X, Y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        path.lineTo(X, Y);
                        break;
                    case MotionEvent.ACTION_UP:
                        path.lineTo(event.getX(),event.getY());
                        Canvas canvas1 = surface.getHolder().lockCanvas();
                        canvas1.drawPath(path, mPaint);
                        surface.getHolder().unlockCanvasAndPost(canvas1);
                        break;

                }
                if(path != null){
                    Canvas canvas = surface.getHolder().lockCanvas();
                    canvas.drawPath(path, mPaint);
                    surface.getHolder().unlockCanvasAndPost(canvas);
                }
                return true;
            }
        });
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    private void cambiarColorFondo(@ColorInt int color){
        if(background != null){
            background.setBackgroundColor(color);
        }
    }
}