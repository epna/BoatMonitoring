package com.boat.boatmonitoring;



import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import static com.boat.boatmonitoring.R.drawable.ic_consoinst;
import static com.boat.boatmonitoring.R.drawable.ic_essence4;
import static com.boat.boatmonitoring.R.drawable.ic_rmpmotor;

public class view_dashboard extends View {
    float speed, gasLevel, tourmMinuteB,tourmMinuteT,consoInstB,consoInstT,batterieB,batterieT,temperatureB,temeratureT,heureMoteursB,heureMoteursT;
    Paint paint = new Paint ();
    Bitmap mBitmap, mBitmap2;
    final int colorStrokeRectangle = Color.parseColor("#4BB2F9");
    final int colorFillRectangle =  Color.parseColor("#80212121");
    public view_dashboard(Context context) {
        super ( context );
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_essence);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_rmpmotor);
    }

    public view_dashboard(Context context, AttributeSet attrs) {
        super ( context, attrs );
        TypedArray a = context.getTheme ().obtainStyledAttributes (
                attrs,
                R.styleable.boatMonitoring,
                0, 0 );

        try {
            speed = a.getFloat ( R.styleable.boatMonitoring_speed, 0 );
            gasLevel = a.getFloat ( R.styleable.boatMonitoring_gasLevel, 0 );
            tourmMinuteB= a.getFloat ( R.styleable.boatMonitoring_tourMinuteB, 0 );
            tourmMinuteT= a.getFloat ( R.styleable.boatMonitoring_tourMinuteT, 0 );
            consoInstB= a.getFloat ( R.styleable.boatMonitoring_consoInstB, 0 );
            consoInstT = a.getFloat ( R.styleable.boatMonitoring_consoInstT, 0 );
            batterieB = a.getFloat ( R.styleable.boatMonitoring_batterieB, 11 );
            batterieT= a.getFloat ( R.styleable.boatMonitoring_batterieT, 11 );
            temperatureB = a.getFloat ( R.styleable.boatMonitoring_temperatureB, 0 );
            temeratureT= a.getFloat ( R.styleable.boatMonitoring_temperatureT, 0 );
            heureMoteursB = a.getFloat ( R.styleable.boatMonitoring_heureMoteurB, 0 );
            heureMoteursT = a.getFloat ( R.styleable.boatMonitoring_heureMoteurT, 0 );


        } finally {
            a.recycle ();
        }


    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw ( canvas );
        paint = new Paint ( Paint.ANTI_ALIAS_FLAG );

        drawGaugeSpeed ( canvas, paint, speed, gasLevel );
        drawRPM (canvas,consoInstB,consoInstT,tourmMinuteB,tourmMinuteT );
        drawThermometer ( canvas, temperatureB,temeratureT,batterieB,batterieT,heureMoteursB,heureMoteursT);
        drawBattery(canvas, batterieT, batterieB);


    }
    public void drawBattery(Canvas mcanvas, float batterieT, float batterieB)
    {
        // rectangle essence
        float xcenter = xx(25);
        float ycenter = yy(85 );

        /// Arc de cercle batterie Babord
        paint.setStrokeWidth ( 30f  );
        paint.setColor ( Color.BLACK );
        float mr4 = 150;   // marge du R4
        float lr4 = xcenter - mr4;
        float tr4 = ycenter - mr4;
        float rr4 = xcenter + mr4;
        float br4 = ycenter + mr4;
        float xc4 = ((rr4 - lr4) / 2) + lr4;
        float yc4 = ((br4 - tr4) / 2) + tr4;
        final RectF rectf4 = new RectF ( lr4, tr4, rr4, br4 );
        paint.setStyle ( Paint.Style.STROKE );
        paint.setColor ( Color.RED );
        paint.setStrokeCap ( Paint.Cap.BUTT );
        mcanvas.drawArc ( rectf4, 180f, 20f, false, paint );
        paint.setColor ( Color.YELLOW );
        mcanvas.drawArc ( rectf4, 200f, 50f, false, paint );
        paint.setColor ( Color.RED );
        mcanvas.drawArc ( rectf4, 250f, 20f, false, paint );
        mcanvas.save ();
        paint.setColor ( Color.DKGRAY );
        paint.setStrokeWidth ( 3f );
        for (int i = 0; i <= 10; i++) {
            mcanvas.drawLine ( xc4 - 165, yc4, xc4 - 135, yc4, paint );
            mcanvas.rotate ( 90 / 10, xc4, yc4 );

        }
        mcanvas.restore ();

        // pointeur voltage battarie Babord
        mcanvas.save ();
        paint.setStrokeWidth(8f);
        if (batterieB > 16f) batterieB = 16f;
        if (batterieB < 11f) batterieB = 11f;
        paint.setColor ( Color.WHITE );
        mcanvas.rotate(90f*(batterieB-11)/5f, xc4, yc4);
        mcanvas.drawLine(xc4-150, yc4, xc4-120, yc4, paint);
        paint.setStrokeWidth(3f);
        mcanvas.drawLine(xc4-120, yc4, xc4+40, yc4, paint);
        paint.setStyle ( Paint.Style.FILL_AND_STROKE );
        paint.setColor ( Color.WHITE );
        mcanvas.drawCircle ( xc4, yc4, 15, paint );
        mcanvas.restore ();


        /// Texte volt babord et heures moteur
        drawText ( mcanvas,paint,String.valueOf ( batterieB ) + " V",xc4,yc4+80,60,6f, Paint.Style.FILL, Paint.Align.CENTER,colorStrokeRectangle );
        drawText ( mcanvas,paint,String.valueOf ( heureMoteursB ) + " h moteur",xc4,yc4+200,30,6f, Paint.Style.FILL, Paint.Align.CENTER,colorStrokeRectangle );

        // batterie Tribord
        mcanvas.save ();
        xcenter = xx(75);
        paint.setStrokeWidth ( 30f  );
        paint.setColor ( Color.BLACK );
        mr4 = 150;   // marge du R4
        lr4 = xcenter - mr4;
        rr4 = xcenter + mr4;
        xc4 = ((rr4 - lr4) / 2) + lr4;
        yc4 = ((br4 - tr4) / 2) + tr4;
        RectF rectfT = new RectF ( lr4, tr4, rr4, br4 );
        paint.setStyle ( Paint.Style.STROKE );
        paint.setColor ( Color.RED );
        paint.setStrokeCap ( Paint.Cap.BUTT );
        mcanvas.drawArc ( rectfT, 270f, 20f, false, paint );
        paint.setColor ( Color.YELLOW );
        mcanvas.drawArc ( rectfT, 290f, 50f, false, paint );
        paint.setColor ( Color.RED );
        mcanvas.drawArc ( rectfT, 340f, 20f, false, paint );
        mcanvas.save ();

        paint.setStrokeWidth ( 3f );
        for (int i = 0; i <= 10; i++) {
            paint.setColor ( Color.BLACK );
            mcanvas.drawLine ( xc4 + 165, yc4, xc4 + 135, yc4, paint );
            mcanvas.rotate ( -90 / 10, xc4, yc4 );
        }
        mcanvas.restore ();


        mcanvas.save ();
        paint.setStrokeWidth(8f);
        paint.setColor ( Color.WHITE );
        if (batterieT > 16f) batterieT = 16f;
        if (batterieT < 11f) batterieT = 11f;
        mcanvas.rotate(-90f*(batterieT-11)/5f, xc4, yc4);
        mcanvas.drawLine(xc4+150, yc4, xc4+120, yc4, paint);
        paint.setStrokeWidth(3f);
        mcanvas.drawLine(xc4+120, yc4, xc4-40, yc4, paint);
        paint.setStyle ( Paint.Style.FILL_AND_STROKE );

        mcanvas.drawCircle ( xc4, yc4, 15, paint );
        mcanvas.restore ();

        drawText ( mcanvas,paint,
                String.valueOf ( batterieT ) + " V",
                xc4,
                yc4+80,
                60,
                6f,
                Paint.Style.FILL,
                Paint.Align.LEFT,
                colorStrokeRectangle );



        drawText ( mcanvas,paint,
                String.valueOf ( heureMoteursT ) + " h moteur",
                xc4,
                yc4+200,
                30,
                6f,
                Paint.Style.FILL,
                Paint.Align.LEFT,
                colorStrokeRectangle );

    }
    public void drawGaugeSpeed(Canvas mcanvas, Paint mpaint,float mspeed, float mgasLevel ) {

        int arcCenterX = mcanvas.getWidth () / 2;
        int arcCenterY = (int) yy ( 23f );
        int maxSpeed = 30;
        mcanvas.save ();
        paint.setStyle ( Paint.Style.STROKE );
        paint.setStrokeCap ( Paint.Cap.ROUND );
        paint.setColor(colorStrokeRectangle);
        paint.setStrokeWidth ( 15f );
        final RectF rect1 = new RectF ( 50, 50, mcanvas.getWidth ()-50, arcCenterY + 380 );
        mcanvas.drawRoundRect ( rect1, 25, 25, paint );

        paint.setStyle ( Paint.Style.FILL );
        paint.setStrokeCap ( Paint.Cap.ROUND );
        paint.setColor(colorFillRectangle);
        mcanvas.drawRoundRect ( rect1, 25, 25, paint );
        mcanvas.restore ();


        //
        // Petit rectangle
        mcanvas.save();
        float lpr = arcCenterX + 50;
        float tpr = arcCenterY + 50;
        float rpr = arcCenterX + 330;
        float bpr = arcCenterY + 330;
        paint.setStrokeWidth ( 6f );
        final RectF rectf3 = new RectF ( lpr, tpr, rpr, bpr );
        paint.setColor(colorStrokeRectangle);
        paint.setStyle ( Paint.Style.STROKE );
        mcanvas.drawRoundRect ( rectf3, 25, 25, paint );

        Drawable iconEssence = getResources().getDrawable( ic_essence4);
        iconEssence.setBounds((int)lpr+20, (int) bpr-120, (int)lpr+110, (int)bpr-20);
        iconEssence.draw(mcanvas);
        drawText ( mcanvas,paint,String.valueOf ( gasLevel ) + " L",lpr+180,bpr-40,40,6f, Paint.Style.FILL, Paint.Align.CENTER,colorStrokeRectangle );
        mcanvas.restore ();


        /// Arc de cercle essence
        mcanvas.save ();
        paint.setStrokeWidth ( 10f );
        paint.setColor ( Color.BLACK );
        float mr4 = 40;   // marge du R4
        float lr4 = lpr + mr4;
        float tr4 = tpr + mr4;
        float rr4 = rpr - mr4;
        float br4 = bpr - mr4;
        float xc4 = ((rr4 - lr4) / 2) + lr4;
        float yc4 = ((br4 - tr4) / 2) + tr4;
        final RectF rectf4 = new RectF ( lr4, tr4, rr4, br4 );
        paint.setColor ( Color.YELLOW );
        paint.setStyle ( Paint.Style.STROKE );
        mcanvas.drawArc ( rectf4, 180f, 180f, false, paint );
        mcanvas.save ();
        paint.setColor ( Color.DKGRAY );
        paint.setStrokeWidth ( 3f );
        for (int i = 0; i <= 10; i++) {
            mcanvas.drawLine ( xc4 - 95, yc4, xc4 - 105, yc4, paint );
            mcanvas.rotate ( 180f / 10, xc4, yc4 );

        }
        mcanvas.restore ();


        mcanvas.save ();
        paint.setStyle ( Paint.Style.FILL_AND_STROKE );
        paint.setColor ( Color.WHITE );
        mcanvas.drawCircle ( xc4, yc4, 10, paint );
        paint.setStrokeWidth(8f);

        mcanvas.rotate(180f*gasLevel/500f, xc4, yc4);
        mcanvas.drawLine(xc4-60, yc4, xc4-80, yc4, paint);
        paint.setStrokeWidth(3f);
        mcanvas.drawLine(xc4-60, yc4, xc4, yc4, paint);
        mcanvas.restore ();





        /// Arc de cercle gris
        mcanvas.save ();
        paint.setStrokeWidth ( 30f );
        paint.setColor ( Color.LTGRAY );
        paint.setStyle ( Paint.Style.STROKE );
        final RectF arcBounds = new RectF ( arcCenterX - 300, arcCenterY - 300, arcCenterX + 300, arcCenterY + 300 );
        mcanvas.drawArc ( arcBounds, 90f, 270f, false, paint );

        /// arc de cercle vitesse
        final int[] couleurs = new int[]{
                Color.rgb ( 128, 255, 32 ), // vert pomme
                Color.rgb ( 255, 128, 32 ), // orange
                Color.rgb ( 0, 0, 255 ) // bleu
        };
        final float[] positions = new float[]{0.0f, 0.5f, 1.0f};
        Shader shader = new LinearGradient ( 0, 0, 10, 0,
                couleurs, positions, Shader.TileMode.CLAMP );
        paint.setShader ( shader );
        mcanvas.drawArc ( arcBounds, 90f, mspeed / maxSpeed * 270, false, paint );
        paint.setShader ( null );
        mcanvas.restore();

        drawText ( mcanvas,paint, String.valueOf ( mspeed ) + " knts",arcCenterX,arcCenterY,100,5f, Paint.Style.FILL, Paint.Align.CENTER,colorStrokeRectangle);

        // Draw the pointers
        mcanvas.save ();
        final int totalNoOfPointers = 30;
        final int pointerMaxHeight = 30;
        final int pointerMinHeight = 5;
        int startX = arcCenterX;
        int startY = arcCenterY + 275;
        paint.setStrokeWidth ( 5f );
        paint.setStrokeCap ( Paint.Cap.ROUND );
        paint.setColor ( colorStrokeRectangle );
        int pointerHeight;
        for (int i = 0; i <= totalNoOfPointers; i++) {
            if (i % 5 == 0) {
                pointerHeight = pointerMaxHeight;
            } else {
                pointerHeight = pointerMinHeight;
            }
            mcanvas.drawLine ( startX, startY, startX, startY - pointerHeight, paint );
            mcanvas.rotate ( 270f / totalNoOfPointers, arcCenterX, arcCenterY );
        }
        mcanvas.restore ();

    }
    public void drawThermometer(Canvas mcanvas, float temperatureB, float temperatureT, float  batterieB, float batterieT,float heureMoteursB, float heureMoteursT )
    {

        // REctangle initial thermometre  heures moteur
        int  startColor = Color.YELLOW;
        int  endColor = Color.RED;
        float pieceNumber = 10;
        float widthB = 100f;
        float spacing = 15f;
        float heightB = 15f;
        float lpr =  50;
        float tpr = yy(72);
        float rpr = mcanvas.getWidth ()  -50;
        float bpr = yy(97);
        paint.setStrokeWidth ( 15f );
        final RectF rectfDT = new RectF ( lpr, tpr, rpr, bpr );
        paint.setColor(colorStrokeRectangle);
        paint.setStyle ( Paint.Style.STROKE );
        mcanvas.drawRoundRect ( rectfDT, 25, 25, paint );
        paint.setStyle ( Paint.Style.FILL );
        paint.setStrokeCap ( Paint.Cap.ROUND );
        paint.setColor(colorFillRectangle);
        mcanvas.drawRoundRect ( rectfDT, 25, 25, paint );

        //thermemetre Babord
        float top = yy(92);
        float left = xx(42) ;
        paint.setStrokeWidth ( 3 );
        paint.setStyle (Paint.Style.STROKE  );
        for (int iii = 0; iii < pieceNumber; iii++) {
            int rectColorG = getColor ( startColor, endColor, (float) iii / pieceNumber );
            paint.setColor ( rectColorG );
            if (iii*10 >= temperatureB) paint.setStyle (Paint.Style.STROKE  ); else paint.setStyle (Paint.Style.FILL  );
            float pbottom = top - (iii * (heightB + spacing));
            float ptop = top - heightB - (iii * (heightB + spacing));
            RectF carre1 = new RectF ( left-(widthB/2), ptop, left + (widthB/2), pbottom );
            mcanvas.drawRoundRect ( carre1, 25, 25, paint );
        }


        drawText ( mcanvas,paint, String.valueOf ( temperatureB )+" °C",left,top+70f,45,6f, Paint.Style.FILL, Paint.Align.CENTER,colorStrokeRectangle );
        // thermometre tribord
        top = yy(92);
        left = xx(58) ;
        paint.setStrokeWidth ( 3 );

        for (int iii = 0; iii < pieceNumber; iii++) {
            int rectColorG = getColor ( startColor, endColor, (float) iii / pieceNumber );
            paint.setColor ( rectColorG );
            if (iii*10 >= temperatureT) paint.setStyle (Paint.Style.STROKE  ); else paint.setStyle (Paint.Style.FILL  );
            float pbottom = top - (iii * (heightB + spacing));
            float ptop = top - heightB - (iii * (heightB + spacing));
            RectF carre1 = new RectF ( left-(widthB/2), ptop, left + (widthB/2), pbottom );
            mcanvas.drawRoundRect ( carre1, 25, 25, paint );
        }

        drawText ( mcanvas,paint,String.valueOf ( temperatureT )+" °C",left,top+70f,45,6f, Paint.Style.FILL, Paint.Align.CENTER,colorStrokeRectangle );
    }
    public void drawRPM (Canvas mcanvas , float consoInstB, float consoInstT, float tourMinuteB, float tourMinuteT ) {


        //

        final float maxConso = 50;
        final float maxTourMinute=50;
        final float maxLargeur = 70;
        // REctangle initial RPM et conso int.
        float lpr =  50;
        float tpr = yy(45);
        float rpr = mcanvas.getWidth ()  -50;
        float bpr = yy(70);
        paint.setStrokeWidth ( 15f );
        final RectF rectf3 = new RectF ( lpr, tpr, rpr, bpr );
        paint.setColor(colorStrokeRectangle);
        paint.setStyle ( Paint.Style.STROKE );
        mcanvas.drawRoundRect ( rectf3, 25, 25, paint );
        paint.setStyle ( Paint.Style.FILL );
        paint.setStrokeCap ( Paint.Cap.ROUND );
        paint.setColor(colorFillRectangle);
        mcanvas.drawRoundRect ( rectf3, 25, 25, paint );


        // RPM
        float xsrpmb = mcanvas.getWidth ()/2;
        float ysrpmb = yy(53);
        paint.setStrokeWidth ( 2f );
        paint.setColor(colorStrokeRectangle);
        float margeCentrale = 100;
        float ecartLigne = 5;
        for (int ii = 0; ii == maxLargeur || ii < tourMinuteB *maxLargeur/maxTourMinute; ii++) {
            // block special
            paint.setStyle ( Paint.Style.STROKE );
            int rectColor = getColor ( Color.YELLOW, Color.RED, (float) ii / maxLargeur );
            paint.setColor ( rectColor );
            mcanvas.drawLine (
                    xsrpmb-(ii*ecartLigne)-margeCentrale,
                    ysrpmb,
                    xsrpmb-(ii*ecartLigne)-margeCentrale,
                    (float) (ysrpmb - Math.pow ( 01.05f, ii*1.4))-40,
                    paint );
        }
        for (int ii = 0;ii == maxLargeur || ii < tourMinuteT *maxLargeur/maxTourMinute; ii++) {
            // block special
            paint.setStyle ( Paint.Style.STROKE );
            int rectColor = getColor ( Color.YELLOW, Color.RED, (float) ii / maxLargeur );
            paint.setColor ( rectColor );
            mcanvas.drawLine (
                    xsrpmb+(ii*ecartLigne)+margeCentrale,
                    ysrpmb,
                    xsrpmb+(ii*ecartLigne)+margeCentrale,
                    (float) (ysrpmb - Math.pow ( 01.05f, ii*1.4))-40,
                    paint );
        }


        Drawable iconRPM = getResources().getDrawable( ic_rmpmotor);
        iconRPM.setBounds((mcanvas.getWidth ()/2)-70, (int) tpr+50, (mcanvas.getWidth ()/2)+70, (int)tpr+200);
        iconRPM.draw(mcanvas);

        /// RPM
        drawText ( mcanvas,paint, String.valueOf ( tourMinuteB ) + " *100 T/m",xsrpmb-margeCentrale,ysrpmb+55,45,6f, Paint.Style.FILL, Paint.Align.RIGHT,colorStrokeRectangle);
        drawText ( mcanvas,paint, String.valueOf ( tourMinuteT ) + " *100 T/m",xsrpmb+margeCentrale,ysrpmb+55,45,6f, Paint.Style.FILL, Paint.Align.LEFT,colorStrokeRectangle);



        /// COnso instantanée
        //float xsrpmb = mcanvas.getWidth ()/2;

        ysrpmb = yy(63);
        paint.setStrokeWidth ( 2f );
        paint.setColor(colorStrokeRectangle);
        //float margeCentrale = 100;
        //float ecartLigne = 5;
        for (int ii = 0; ii == maxLargeur || ii < consoInstB *maxLargeur/maxConso; ii++) {
            // block special
            paint.setStyle ( Paint.Style.STROKE );
            int rectColor = getColor ( Color.BLUE, Color.BLACK, (float) ii / maxLargeur );
            paint.setColor ( rectColor );
            mcanvas.drawLine (
                    xsrpmb-(ii*ecartLigne)-margeCentrale,
                    ysrpmb,
                    xsrpmb-(ii*ecartLigne)-margeCentrale,
                    (float) (ysrpmb - Math.pow ( 01.05f, ii*1.4))-40,
                    paint );
        }
        for (int ii = 0; ii == maxLargeur || ii < consoInstT *maxLargeur/maxConso ; ii++) {
            // block special
            paint.setStyle ( Paint.Style.STROKE );
            int rectColor = getColor ( Color.BLUE, Color.BLACK, (float) ii / maxLargeur );
            paint.setColor ( rectColor );
            mcanvas.drawLine (
                    xsrpmb+(ii*ecartLigne)+margeCentrale,
                    ysrpmb,
                    xsrpmb+(ii*ecartLigne)+margeCentrale,
                    (float) (ysrpmb - Math.pow ( 01.05f, ii*1.4))-40,
                    paint );
        }


        Drawable iconConso = getResources().getDrawable( ic_consoinst);
        iconConso.setBounds((mcanvas.getWidth ()/2)-70, (int) tpr+300, (mcanvas.getWidth ()/2)+70, (int)tpr+440);
        iconConso.draw(mcanvas);

        /// Textes consommationn instantanée

        drawText ( mcanvas,paint,String.valueOf ( consoInstB ) + " L/h",xsrpmb-margeCentrale,ysrpmb+55,45,6f, Paint.Style.FILL, Paint.Align.RIGHT,colorStrokeRectangle);
        drawText ( mcanvas,paint,String.valueOf ( consoInstT ) + " L/h",xsrpmb+margeCentrale,ysrpmb+55,45,6f, Paint.Style.FILL, Paint.Align.LEFT,colorStrokeRectangle);



    }
    public float xx(float x )
    {
        return getWidth () *x/100 ;
    }
    public float yy(float y )
    {
        return getHeight () *y/100 ;
    }
    private int getColor(int c0, int c1, float p) {
        int a = ave ( Color.alpha ( c0 ), Color.alpha ( c1 ), p );
        int r = ave ( Color.red ( c0 ), Color.red ( c1 ), p );
        int g = ave ( Color.green ( c0 ), Color.green ( c1 ), p );
        int b = ave ( Color.blue ( c0 ), Color.blue ( c1 ), p );
        return Color.argb ( a, r, g, b );
    }
    private int ave(int src, int dst, float p) {
        return src + java.lang.Math.round ( p * (dst - src) );
    }

    public void drawText ( Canvas mCanvas, Paint mPaint, String mText,float xText, float yText, int textSize, float mStrokeWidth, Paint.Style mStyle,  Paint.Align mAlign, int mColor )
    {
        mCanvas.save ();
        mPaint.setTextSize ( textSize );
        mPaint.setStrokeWidth ( mStrokeWidth );
        mPaint.setColor(mColor);
        mPaint.setTextAlign ( mAlign  );
        mPaint.setStyle ( mStyle);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Typeface typeface = getResources ().getFont ( R.font.digital );
            mPaint.setTypeface ( typeface );
        }
        mCanvas.drawText ( mText, xText , yText , mPaint );
        mCanvas.restore();
    }

}
