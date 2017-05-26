package com.yhy.fragmenthelper.single;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yhy.fragmenthelper.R;
import com.yhy.fragmenthelper.base.BaseFragment;

/**
 * Created by HongYi Yan on 2017/5/26 10:04.
 */
public class FirstFm extends BaseFragment {

    private TextView tvBack;
    private TextView tvNext;

    @Nullable
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_single_first, container, false);
        tvBack = (TextView) view.findViewById(R.id.tv_back);
        tvNext = (TextView) view.findViewById(R.id.tv_next);
        return view;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Bundle args = getArguments();
        if (isVisibleToUser && null != args) {
            String arg = args.getString("args");
            Toast.makeText(mActivity, "接收到参数：" + arg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(new SecondFm());
            }
        });
    }
}
