package com.boat.boatmonitoring;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import static com.boat.boatmonitoring.R.drawable.ic_essence;
import static com.boat.boatmonitoring.R.drawable.ic_essence4;
import static com.boat.boatmonitoring.R.drawable.needle;
import static com.boat.boatmonitoring.R.drawable.needle6;

public class compass_view extends View {
    public float cap;
    public Paint paint;
    public compass_view(Context context) {
        super ( context );
    }

    public compass_view(Context context, @Nullable AttributeSet attrs) {
        super ( context, attrs );
        TypedArray a = context.getTheme ().obtainStyledAttributes (
                attrs,
                R.styleable.boatMonitoring,
                0, 0 );

        try {
            cap  = a.getFloat ( R.styleable.compas_view_cap, 0 );

        } finally {
            a.recycle ();
        }


    }

    public compass_view(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super ( context, attrs, defStyleAttr );



    }

    public compass_view(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super ( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw ( canvas );
        canvas.save ();
        paint = new Paint ( Paint.ANTI_ALIAS_FLAG );
        paint.setColor ( Color.WHITE );
        paint.setStrokeWidth ( 3f );
        paint.setStyle ( Paint.Style.STROKE );
        float cx = getWidth ()/2;
        float cy = getHeight ()/2;
        paint.setStrokeWidth ( 10f );
        float cercle = 150;
        canvas.drawCircle( cx, cy, cercle, paint);
        float recul;
        for (int ii=0; ii <36; ii++  )
        {
            recul = cercle-20;
            paint.setColor ( Color.BLACK );
            if (ii%9==0)
            {
                recul= cercle-60;
                paint.setStrokeWidth ( 6f );
                paint.setColor ( Color.GRAY );

            }
            else
                if (ii%3==0)
                {
                    recul= cercle-40;
                    paint.setStrokeWidth ( 4f );
                    paint.setColor ( Color.YELLOW );
                }
                else
                {
                    recul= cercle-20;
                    paint.setStrokeWidth ( 2f );
                    paint.setColor ( Color.WHITE );

                }
            canvas.drawLine (cx,cy-cercle,cx, cy-recul ,paint );
            canvas.rotate ( 10,cx,cy );
        }
        canvas.restore ();

        canvas.save ();
        paint.setColor ( Color.RED );
        paint.setStrokeWidth ( 1 );
        paint.setTextSize ( 30 );
        paint.setStyle ( Paint.Style.FILL );
        paint.setTextAlign ( Paint.Align.CENTER );
        paint.setColor ( Color.WHITE );
        float ecartLettre = 45;
        canvas.drawText ( "N",cx,cy-cercle-ecartLettre,paint );
        canvas.drawText ( "S",cx,cy+cercle+ecartLettre,paint );
        canvas.drawText ( "E",cx+cercle+ecartLettre,cy,paint );
        canvas.drawText ( "W",cx-cercle-ecartLettre,cy,paint );
        canvas.restore ();
/*        canvas.save ();
        Drawable iconEssence;
        iconEssence = ContextCompat.getDrawable(getContext (), ic_essence);
        iconEssence.setBounds((int)60, (int) 120, (int)180, (int)240);
        iconEssence.draw(canvas);*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 500;
        int desiredHeight = 1500;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }
}
