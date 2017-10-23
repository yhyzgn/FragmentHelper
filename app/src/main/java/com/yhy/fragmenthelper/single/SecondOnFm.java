package com.yhy.fragmenthelper.single;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yhy.fmhelper.manager.FmCbManager;
import com.yhy.fmhelper.callback.OnFmCallBack;
import com.yhy.fragmenthelper.R;
import com.yhy.fragmenthelper.base.BaseFragment;

/**
 * Created by HongYi Yan on 2017/5/26 10:13.
 */
public class SecondOnFm extends BaseFragment implements OnFmCallBack {

    private TextView tvBack;
    private TextView tvNext;
    private boolean isArgs;

    @Nullable
    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_single_second, container, false);
        tvBack = (TextView) view.findViewById(R.id.tv_back);
        tvNext = (TextView) view.findViewById(R.id.tv_next);
        return view;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if (null != getArguments()) {
            isArgs = getArguments().getBoolean("isArgs");
        }
        FmCbManager.getInstance().registFmCallBack(this, this);
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
                open(new ThirdOnFm());
            }
        });
    }

    @Override
    public void onResult(Bundle args) {
        if (isArgs && null != args) {
            String arg = args.getString("args");
            Toast.makeText(mActivity, "接收到参数：" + arg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Bundle getResult() {
        if (!isArgs) {
            return null;
        }
        Bundle args = new Bundle();
        args.putString("args", "第2页回传的参数");
        return args;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FmCbManager.getInstance().unRegistFmCallBack(this, this);
    }
}
