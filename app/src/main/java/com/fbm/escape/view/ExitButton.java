package com.fbm.escape.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import com.fbm.escape.R;
import com.fbm.escape.activity.ExitActivity;


public class ExitButton extends androidx.appcompat.widget.AppCompatImageView {
    private Context context;

    public ExitButton(Context context) {
        super(context);
        init(context);
    }

    public ExitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        setImageResource(R.drawable.ic_exit_button);
        setOnClickListener(v -> startActivity());
    }

    private void startActivity() {
        Intent intent = new Intent(context, ExitActivity.class);
        context.startActivity(intent);
    }
}
