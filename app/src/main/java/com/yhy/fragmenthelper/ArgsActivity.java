package com.yhy.fragmenthelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.yhy.fmhelper.FmHelper;
import com.yhy.fragmenthelper.R;
import com.yhy.fragmenthelper.single.FirstFm;

/**
 * Created by HongYi Yan on 2017/5/26 9:57.
 */
public class ArgsActivity extends AppCompatActivity {

    private FmHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fm_container);

        mHelper = new FmHelper.Builder(this, R.id.fl_container).build();

        mHelper.open(new FirstFm());
    }

    public void next(Fragment fm) {
        mHelper.open(fm);
    }

    @Override
    public void onBackPressed() {
        mHelper.back();
    }
}
