package com.getright.skin.skindemo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

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
