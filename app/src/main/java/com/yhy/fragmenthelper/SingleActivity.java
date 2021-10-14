package com.yhy.fragmenthelper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.yhy.fmhelper.FmHelper;
import com.yhy.fragmenthelper.single.FirstOnFm;

/**
 * Created by HongYi Yan on 2017/5/26 9:57.
 */
public class SingleActivity extends AppCompatActivity {

    private FmHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fm_container);

        mHelper = new FmHelper.Builder(this, R.id.fl_container)
                .animate(R.anim.helper_slide_in_right, R.anim.helper_slide_out_left)
                .build();

        mHelper.open(new FirstOnFm());
    }

    public void next(Fragment fm) {
        mHelper.open(fm);
    }

    @Override
    public void onBackPressed() {
        mHelper.back();
    }
}
