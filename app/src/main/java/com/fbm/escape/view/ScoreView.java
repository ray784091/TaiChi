package com.fbm.escape.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fbm.escape.R;
import com.fbm.escape.busmessage.ScoreMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ScoreView extends LinearLayout {

    private static final String TAG = ScoreView.class.getName();
    private TextView textView;

    public ScoreView(Context context) {
        super(context);
        init(context);
    }

    public ScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("ResourceAsColor")
    private void init(Context context) {
        EventBus.getDefault().register(this);

        textView = new TextView(context);
        textView.setTextSize(30);
        textView.setTextColor(R.color.black);
        setGravity(Gravity.CENTER);
        addView(textView);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void updateScore(Object object) {
        if (object instanceof ScoreMsg) {
            String score = String.valueOf(((ScoreMsg) object).getScore());
            Log.d(TAG, "update score =" + score);
            textView.setText(score);
        }
    }

}
