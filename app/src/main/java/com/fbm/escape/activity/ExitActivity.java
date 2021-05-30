package com.fbm.escape.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fbm.escape.R;

public class ExitActivity extends BaseActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        init();
        setDialogSize();
    }

    private void init() {
        findViewById(R.id.negative_button).setOnClickListener(v -> finish());
        findViewById(R.id.positive_button).setOnClickListener(v -> finishAffinity());
        imageView = findViewById(R.id.bg_iv);
    }

    private void setDialogSize() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int length = (int) (metric.widthPixels * 0.7);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = length;
        layoutParams.width = length;
        imageView.setLayoutParams(layoutParams);
    }
}