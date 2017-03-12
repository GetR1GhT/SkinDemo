package com.getright.skin.skindemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shaoshuai on 2017/3/10.
 */

public class ThemeHelper {
    private static final String CURRENT_THEME = "theme_current";

    public static final int CARD_RED = 0x1;
    public static final int CARD_GREEN = 0x2;
    public static final int CARD_BLUE = 0x3;
    public static final int CARD_WOOD = 0x4;
    public static final int CARD_LIGHT = 0x5;
    public static final int CARD_THUNDER = 0x6;
    public static final int CARD_SAND = 0x7;
    public static final int CARD_FIREY = 0x8;
    public static final int CARD_PALETTE = 0x9;

    public static SharedPreferences getSharePreference(Context context) {
        return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE);
    }

    public static void setTheme(Context context, int themeId) {
        getSharePreference(context).edit()
                .putInt(CURRENT_THEME, themeId)
                .commit();
    }

    public static int getTheme(Context context) {
        return getSharePreference(context).getInt(CURRENT_THEME, CARD_RED);
    }

    public static boolean isDefaultTheme(Context context) {
        return getTheme(context) == CARD_RED;
    }

    public static String getName(int currentTheme) {
        switch (currentTheme) {
            case CARD_RED:
                return "THE RED";
            case CARD_BLUE:
                return "THE BLUE";
            case CARD_GREEN:
                return "THE GREEN";
            case CARD_PALETTE:
                return "THE PALETTE";
        }
        return "THE RETURN";
    }
}
