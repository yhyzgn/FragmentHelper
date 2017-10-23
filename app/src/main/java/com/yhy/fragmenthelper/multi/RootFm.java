package com.yhy.fragmenthelper.multi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.fmhelper.FmHelper;
import com.yhy.fragmenthelper.R;
import com.yhy.fragmenthelper.base.BaseFragment;
import com.yhy.fragmenthelper.single.FirstOnFm;

/**
 * Created by HongYi Yan on 2017/5/26 10:26.
 */
public class RootFm extends BaseFragment {

    private FmHelper mHelper;

    @Nullable
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_fm_root, container, false);
        mHelper = new FmHelper.Builder(this, R.id.fl_container).build();

        open(new FirstOnFm());

        return view;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
    }

    public void open(Fragment fm) {
        mHelper.open(fm);
    }

    public boolean back() {
        return mHelper.back();
    }
}
