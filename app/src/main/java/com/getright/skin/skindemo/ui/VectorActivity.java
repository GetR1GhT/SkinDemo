package com.getright.skin.skindemo.ui;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintToolbar;
import com.getright.skin.skindemo.R;
import com.getright.skin.skindemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shaoshuai on 2017/3/13.
 */

public class VectorActivity extends BaseActivity {
    @BindView(R.id.arrow_iv)
    ImageView arrowIv;
    @BindView(R.id.toolbar)
    TintToolbar toolbar;
    @BindView(R.id.square_iv)
    ImageView squareIv;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.search_iv)
    ImageView searchIv;

    @Override
    protected void initViewAndListener() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_vector;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.arrow_iv, R.id.square_iv, R.id.search_iv})
    public void onClick(View view) {
        Drawable drawable = arrowIv.getDrawable();
        switch (view.getId()) {
            case R.id.arrow_iv:
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                break;
            case R.id.square_iv:
                drawable = squareIv.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                break;
            case R.id.search_iv:
                drawable = searchIv.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                break;
        }
    }

}
