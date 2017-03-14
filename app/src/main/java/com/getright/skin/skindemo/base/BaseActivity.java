package com.getright.skin.skindemo.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.getright.skin.skindemo.R;
import com.getright.skin.skindemo.utils.AppManager;

import butterknife.ButterKnife;

/**
 * Created by Shaoshuai on 2017/3/12.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        ButterKnife.bind(this);
        initViewAndListener();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    /**
     * 做一些初始化设置和监听的工作
     */
    protected abstract void initViewAndListener();

    /**
     * 设置View
     *
     * @return R.layout.xxx
     */
    public abstract int initContentView();

    /**
     * SnackBar
     *
     * @param view
     * @param content 输入要显示的内容
     */
    public void showSnack(View view, String content) {
        if (!TextUtils.isEmpty(content)) {
            Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.theme_color_primary_dark));
            snackbar.show();
        }
    }

    /**
     * 启动带动画的Activity
     *
     * @param cla .class
     */
    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    /**
     * 软键盘工具
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
