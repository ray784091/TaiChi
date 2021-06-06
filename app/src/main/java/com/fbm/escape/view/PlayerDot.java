package com.fbm.escape.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.fbm.escape.busmessage.FingerMovingMsg;
import com.fbm.escape.entity.Location;
import com.fbm.escape.interfaces.CustomSurfaceView;

public class PlayerDot extends CustomSurfaceView {
    private final static String TAG = PlayerDot.class.getName();

    private float radius = 50;

    private Paint paint;

    private FingerMovingMsg fingerMovingMsg;

    private boolean isFingerMoving;

    public PlayerDot(Context context) {
        super(context);
    }

    public PlayerDot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        Log.i(TAG, "PlayerDot init");
        paint = new Paint();
        setPaintColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    public void render(Canvas canvas, Location location) {
        canvas.drawCircle(location.getX(), location.getY(), radius, paint);
    }

    public FingerMovingMsg getFingerMovingMsg() {
        if (!isFingerMoving) {
            fingerMovingMsg = null;
        }
        isFingerMoving = false;
        return fingerMovingMsg;
    }

    public void setPaintColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void processEvent(Object object) {
        Log.i(TAG, "finger position received");
        if (object instanceof FingerMovingMsg) {
            isFingerMoving = true;
            fingerMovingMsg= (FingerMovingMsg) object;
        }
    }
}
