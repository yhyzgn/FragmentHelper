package com.yhy.fragmenthelper.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.fragmenthelper.SingleActivity;
import com.yhy.fragmenthelper.ArgsActivity;
import com.yhy.fragmenthelper.multi.RootFm;

/**
 * Created by HongYi Yan on 2017/5/26 10:00.
 */
public abstract class BaseFragment extends Fragment {
    protected FragmentActivity mActivity;
    protected Fragment mParent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == mActivity) {
            mActivity = getActivity();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (null == mActivity) {
            mActivity = (FragmentActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mParent = getParentFragment();

        initData(savedInstanceState);

        initListener();
    }

    @Nullable
    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    protected void initListener() {
    }

    public void open(Fragment fm) {
        if (null != mParent && mParent instanceof RootFm) {
            ((RootFm) mParent).open(fm);
        } else {
            if (mActivity instanceof SingleActivity) {
                ((SingleActivity) mActivity).next(fm);
            } else if (mActivity instanceof ArgsActivity) {
                ((ArgsActivity) mActivity).next(fm);
            }
        }
    }

    public boolean onBackPressed() {
        if (null != mParent && mParent instanceof RootFm) {
            return ((RootFm) mParent).back();
        } else {
            mActivity.onBackPressed();
        }
        return false;
    }
}
