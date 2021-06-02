package com.fbm.escape.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fbm.escape.R;
import com.fbm.escape.busmessage.EventMsg;
import com.fbm.escape.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BackgroundView extends ConstraintLayout implements SensorEventListener {
    private static final String TAG = BackgroundView.class.getName();

    private Context context;

    private ViewGroup baseView;

    private SensorManager sensorManager;

    public BackgroundView(Context context) {
        super(context);
        init(context, null);
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BackgroundView);
            boolean isEarthquakeOpen = typedArray.getBoolean(R.styleable.BackgroundView_earthquake_open, false);
            if (isEarthquakeOpen) {
                EventBus.getDefault().register(this);
            }
            typedArray.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.view_main_background, this, true);
        baseView = findViewById(R.id.base_view);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = false)
    public void processEvent(Object object) {
        if (object instanceof EventMsg) {
            switch (((EventMsg) object).getEventCode()) {
                case EventMsg.EVENT_CODE_EARTHQUAKE: {
                    Log.i(TAG, "do earthquake");
                    earthquake();
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void earthquake() {
        int x = baseView.getLeft();
        int y = baseView.getTop();

        ObjectAnimator animX = ObjectAnimator.ofFloat(baseView, "translationX", x, x - 4, x, x + 4, x);
        animX.setRepeatCount(3);

        ObjectAnimator animY = ObjectAnimator.ofFloat(baseView, "translationY", y, y - 4, y, y + 4, y);
        animY.setRepeatCount(3);

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] times = {0, 5, 45, 5, 45};

        AnimatorSet set = new AnimatorSet();
        set.play(animX).with(animY);
        set.setDuration(100);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (PreferenceUtils.isVibratorOpen()) {
                    vibrator.vibrate(times, 1);
                    Log.i(TAG, "earthquake start");
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                vibrator.cancel();
                Log.i(TAG, "earthquake end");
            }
        });
        set.start();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "X=" + event.values[0] + " Y=" + event.values[1]);
        for(int i=0;i<baseView.getChildCount();i++){

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        Log.i(TAG, "onVisibilityChanged");
        if(visibility==INVISIBLE){
            Log.i(TAG, "unregisterListener");
            sensorManager.unregisterListener(this);
        }else {
            Log.i(TAG, "registerListener");
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}
