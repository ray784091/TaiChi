package com.fbm.escape.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.fbm.escape.app.MyApplication.getContext;

/**
 * 2021-05-09
 */
public class PreferenceUtils {

    private static final String PREFERENCE_NAME = "com.fbm.escape.preference_name";

    private static final String SP_KEY_BEST_SCORE = "best_score";

    private static SharedPreferences preference = getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);


    public static int getBestScore() {
       return preference.getInt(SP_KEY_BEST_SCORE,0);
    }

    /**
     * Save best score
     * @param score Score
     */
    public static void saveBestScore(int score) {
        if(score>getBestScore()){
            SharedPreferences.Editor editor=preference.edit();
            editor.putInt(SP_KEY_BEST_SCORE,score);
            editor.apply();
        }
    }

}
