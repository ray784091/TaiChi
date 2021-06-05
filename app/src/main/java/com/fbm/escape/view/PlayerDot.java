package com.fbm.escape.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.fbm.escape.busmessage.FingerMovingMsg;
import com.fbm.escape.interfaces.CustomSurfaceView;
import com.fbm.escape.utils.ScreenSize;

public class PlayerDot extends CustomSurfaceView {
    private final static String TAG = PlayerDot.class.getName();

    private final static float SAFE_DISTANCE = 10F;

    private float locationX;

    private float locationY;

    private float radius = 50;

    boolean isOverTop;

    boolean isOverBottom;

    boolean isOverLeft;

    boolean isOverRight;

    private Paint paint;

    public PlayerDot(Context context) {
        super(context);
    }

    @Override
    public void init(Context context) {
        Log.i(TAG, "PlayerDot init");

        locationX = ScreenSize.getWidth() / 2;
        locationY = (float) (ScreenSize.getHeight() * 0.2);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        canvas.drawCircle(locationX, locationY, radius, paint);
    }

    @Override
    public Paint getPaint() {
        paint = new Paint();
        setPaintColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        return paint;
    }

    public void setPaintColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void processEvent(Object object) {
        Log.i(TAG, "finger position received");
        if (object instanceof FingerMovingMsg) {
            updatePosition((FingerMovingMsg) object);
        }
    }

    @Override
    public float getSafeDistance() {
        return SAFE_DISTANCE;
    }

    public void updatePosition(FingerMovingMsg fingerMovingMsg) {
        isOverLeft = locationX - fingerMovingMsg.getDistanceX() - radius - SAFE_DISTANCE < 0;
        isOverRight = ScreenSize.getWidth() - locationX + fingerMovingMsg.getDistanceX() - radius - SAFE_DISTANCE < 0;
        isOverTop = locationY - fingerMovingMsg.getDistanceY() - radius - SAFE_DISTANCE < 0;
        isOverBottom = ScreenSize.getHeight() * 2 / 5 - locationY + fingerMovingMsg.getDistanceY() - radius - SAFE_DISTANCE < 0;

        if (!isOverLeft && !isOverRight) {
            locationX = locationX - fingerMovingMsg.getDistanceX();
        }
        if (!isOverTop && !isOverBottom) {
            locationY = locationY - fingerMovingMsg.getDistanceY();
        }
        setX(locationX);
        setY(locationY);
        Log.i(TAG, "location=" + locationX + " " + locationY);
    }
}
