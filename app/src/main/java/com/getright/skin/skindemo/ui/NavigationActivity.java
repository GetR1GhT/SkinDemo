package com.getright.skin.skindemo.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintToolbar;
import com.getright.skin.skindemo.R;
import com.getright.skin.skindemo.base.BaseActivity;
import com.getright.skin.skindemo.utils.ThemeHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class NavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.header_image_view)
    ImageView headerImageView;
    @BindView(R.id.toolbar)
    TintToolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private int mCurrentTheme;
    private List<Fragment> fragments;
    private List<String> tabTitles;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_red) {
            mCurrentTheme = ThemeHelper.CARD_RED;
            changeTheme(mCurrentTheme);
        } else if (id == R.id.nav_green) {
            mCurrentTheme = ThemeHelper.CARD_GREEN;
            changeTheme(mCurrentTheme);
        } else if (id == R.id.nav_blue) {
            mCurrentTheme = ThemeHelper.CARD_BLUE;
            changeTheme(mCurrentTheme);
        } else if (id == R.id.nav_palette) {
            mCurrentTheme = ThemeHelper.CARD_PALETTE;
            changeTheme(mCurrentTheme);
        } else if (id == R.id.nav_animation) {
            startActivity(new Intent(this, VectorActivity.class));
        } else if (id == R.id.nav_exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeTheme(int currentTheme) {
        ThemeHelper.setTheme(this, currentTheme);
        ThemeUtils.refreshUI(this, new ThemeUtils.ExtraRefreshable() {
            @Override
            public void refreshGlobal(Activity activity) {
                //for global setting, just do once
                NavigationActivity context = NavigationActivity.this;
                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                setTaskDescription(taskDescription);
                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary_dark));
                collapseToolbar.setContentScrim(new ColorDrawable(ThemeUtils.getColorById(context, R.color.theme_color_primary_dark)));
                fab.setBackgroundTintList(ColorStateList.valueOf(ThemeUtils.getColorById(context, R.color.theme_color_primary_trans)));
                fab.setRippleColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                tabLayout.setSelectedTabIndicatorColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                tabLayout.setTabTextColors(ThemeUtils.getColorById(context, R.color.text_primary_color), ThemeUtils.getColorById(context, R.color.theme_color_primary));
            }

            @Override
            public void refreshSpecificView(View view) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    @Override
    protected void initViewAndListener() {
        mCurrentTheme = ThemeHelper.getTheme(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnack(view, "正在开发");
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        fab.setSize(FloatingActionButton.SIZE_MINI);
        collapseToolbar.setTitleEnabled(false);
        initTab();
        changeTheme(mCurrentTheme);
    }

    private void initTab() {
        tabTitles = new ArrayList<>();
        tabTitles.add("TAB1");
        tabTitles.add("TAB2");
        tabTitles.add("TAB3");
        fragments = new ArrayList<>();
        for (int i = 0; i < tabTitles.size(); i++) {
            fragments.add(new RecyclerViewFragment());
            tabLayout.addTab(tabLayout.newTab().setText(tabTitles.get(i)));
        }
        TabViewPagerAdapter tabViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int initContentView() {
        return R.layout.activity_navigation;
    }

    /**
     * Viewpager的适配器
     */
    private class TabViewPagerAdapter extends FragmentPagerAdapter {

        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }
}
