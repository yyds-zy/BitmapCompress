package com.yyds.bitmapcompress;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/25
 * Describe:
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = false;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
