package com.example.administrator.terminal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lvmoy on 2017/3/30 0030.
 * Mode: - - !
 */

public class ShowActivity extends Activity {

    @BindView(R.id.tv_show)
    TextView tvShow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);
        ButterKnife.bind(this);

        tvShow.setText(getIntent().getStringExtra("json"));
    }


}
