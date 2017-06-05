package com.yhy.fragmenthelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.yhy.fmhelper.FmHelper;
import com.yhy.fragmenthelper.single.FirstOnFm;

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

        Fragment fm = new FirstOnFm();
        Bundle arg = new Bundle();
        arg.putBoolean("isArgs", true);
        fm.setArguments(arg);
        mHelper.open(fm);
    }

    public void next(Fragment fm) {
        mHelper.open(fm);
    }

    @Override
    public void onBackPressed() {
        mHelper.back();
    }
}
