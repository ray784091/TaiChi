package com.fbm.escape.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.fbm.escape.R;
import com.fbm.escape.activity.GameActivity;

public class StartButton extends androidx.appcompat.widget.AppCompatImageButton {

    private static final String TAG = StartButton.class.getName();

    private Context context;

    public StartButton(Context context) {
        super(context);
        init(context);
    }

    public StartButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StartButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setAnim();
        setImageResource(R.drawable.ic_start_button);
        setBackground(null);
        setScaleType(ScaleType.FIT_CENTER);
        setOnClickListener(v -> startActivity());
    }

    private void setAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(this, "rotation", 0, 359);
        anim.setRepeatCount(-1);
        anim.setInterpolator(new LinearInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(anim);
        set.setDuration(2000);
        set.start();
    }

    private void startActivity() {
        Log.d(TAG, "start game activity");
        Intent intent = new Intent(context, GameActivity.class);
        context.startActivity(intent);
    }
}
