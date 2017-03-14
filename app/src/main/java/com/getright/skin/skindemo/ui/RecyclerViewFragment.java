package com.getright.skin.skindemo.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.getright.skin.skindemo.R;
import com.getright.skin.skindemo.utils.ThemeHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shaoshuai on 2017/3/13.
 */

public class RecyclerViewFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private List<String> contents;
    private @ColorInt int color;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 初始化view
     */
    private void initView() {
        //假数据
        contents = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            contents.add("item");
        }
        //com.github.CymChad:BaseRecyclerViewAdapterHelper:2.8.6

        final BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_fragment_recycler, contents) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.item_content, item + helper.getAdapterPosition());
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // 下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addData(contents);
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                        changeTheme(ThemeHelper.getTheme(getActivity()));
                    }
                }, 1000);
            }
        });
        changeTheme(ThemeHelper.getTheme(getActivity()));
    }

    /**
     * 主题切换
     * @param currentTheme 当前主题
     */
    private void changeTheme(int currentTheme) {
        ThemeHelper.setTheme(getActivity(), currentTheme);
        ThemeUtils.refreshUI(getActivity(), new ThemeUtils.ExtraRefreshable() {
            @Override
            public void refreshGlobal(Activity activity) {
                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(getActivity(), android.R.attr.colorPrimary));
                getActivity().setTaskDescription(taskDescription);
                color = ThemeUtils.getColorById(getContext(),R.color.theme_color_primary);
                refreshLayout.setColorSchemeColors(color);
            }

            @Override
            public void refreshSpecificView(View view) {

            }
        });

    }

}
