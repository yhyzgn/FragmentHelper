package com.yhy.fragmenthelper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.yhy.fmhelper.FmHelper;
import com.yhy.fragmenthelper.multi.RootFm;

/**
 * Created by HongYi Yan on 2017/5/26 9:57.
 */
public class MultiActivity extends AppCompatActivity {

    private FmHelper mHelper;
    private RootFm mFm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fm_container);

        mHelper = new FmHelper.Builder(this, R.id.fl_container).build();

        mFm = new RootFm();
        open(mFm);
    }

    public void open(Fragment fm) {
        mHelper.open(fm);
    }

    @Override
    public void onBackPressed() {
        //如果只有一个根布局，不能直接调用mHelper.back()，这样只会直接结束RootFm，并不会将RootFm中的Fragment逐个退出
        //需要将返回事件交给根Fragment，即RootFm才能实现
        boolean canBack = mFm.back();

        //如果有多个根布局的话，需要根据canBack来判断当前根中的Fragment是否能在继续退出了，不能时才退出当前根
        if (!canBack) {
            mHelper.back();
        }
    }
}
