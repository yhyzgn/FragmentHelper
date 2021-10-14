package com.yhy.fragmenthelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvSingle;
    private TextView tvMulti;
    private TextView tvArgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSingle = (TextView) findViewById(R.id.tv_single);
        tvMulti = (TextView) findViewById(R.id.tv_multi);
        tvArgs = (TextView) findViewById(R.id.tv_args);

        tvSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SingleActivity.class);
            }
        });

        tvMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MultiActivity.class);
            }
        });

        tvArgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(ArgsActivity.class);
            }
        });
    }

    private void start(Class<?> target) {
        startActivity(new Intent(this, target));
    }
}
