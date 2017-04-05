package com.example.administrator.terminal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import utils.ImageTextView;
import utils.LeftPopupWindow;
import utils.OrderUtils;
import utils.ScreenUtils;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class MainActivity extends Activity {
    @BindView(R.id.tv_home_video)
    ImageTextView tvHomeVideo;
    @BindView(R.id.tv_home_bd_guide)
    ImageTextView tvHomeBdGuide;
    @BindView(R.id.tv_home_data)
    ImageTextView tvHomeData;
    @BindView(R.id.tv_home_tools)
    ImageTextView tvHomeTools;
    @BindView(R.id.tv_home_bd)
    TextView tvHomeBd1;
    @BindView(R.id.tv_home_4g)
    TextView tvHome4g;
    @BindView(R.id.tv_home_570)
    TextView tvHome570;
    @BindView(R.id.tv_home_voip)
    TextView tvHomeVoip;
    @BindView(R.id.tv_home_signal_3g)
    ImageTextView tvHomeSignal3g;
    @BindView(R.id.tv_home_signal_bd)
    ImageTextView tvHomeSignalBd;
    @BindView(R.id.tv_home_signal_570)
    ImageTextView tvHomeSignal570;
    @BindView(R.id.tv_home_signal_voip)
    ImageTextView tvHomeSignalVoip;


    private LeftPopupWindow leftPopupWindow;
    private boolean is570Connecting = false;
    private static String baseUri = "http://192.168.2.67:8080/test/";
    private static boolean isExit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        ButterKnife.bind(this);
        Crouton.makeText(this, "登录成功！欢迎您", Style.INFO).show();
        is570Connecting = OrderUtils.do570Ping(baseUri);
        change570State(is570Connecting);
    }

    @Override
    protected void onResume() {
        super.onResume();
        is570Connecting = OrderUtils.do570Ping(baseUri);
        change570State(is570Connecting);
    }

    private void change570State(boolean isConnect) {
        if (isConnect) {
            tvHomeSignal570.setmDrawable(getResources().getDrawable(R.drawable.icon_sign_4));
//            tvHomeSignal570.setDrawableRight(
//                    getResources().getDrawable(R.drawable.icon_sign_4));
        }else {
            tvHomeSignal570.setmDrawable(getResources().getDrawable(R.drawable.icon_sign_disable));

//            tvHomeSignal570.setDrawableRight(
//                    getResources().getDrawable(R.drawable.icon_sign_disable));
        }
    }

    @OnClick({R.id.tv_home_video, R.id.tv_home_bd_guide, R.id.tv_home_data, R.id.tv_home_tools, R.id.tv_home_bd, R.id.tv_home_4g, R.id.tv_home_570, R.id.tv_home_voip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home_video:
                showPopupWindow(view, 1);
                break;
            case R.id.tv_home_bd_guide:
                showPopupWindow(view, 2);
                break;
            case R.id.tv_home_data:
                showPopupWindow(view, 3);
                break;
            case R.id.tv_home_tools:
                showPopupWindow(view, 4);
                break;
            case R.id.tv_home_570:
                Intent intent = new Intent(MainActivity.this, HomeDetailActivity.class);
                intent.putExtra("570state", is570Connecting);
                startActivity(intent);
                break;
        }
    }

    private void showPopupWindow(View view, int type) {
        if (leftPopupWindow == null) {
            int viewHeight = view.getHeight();
            int viewWidth = view.getWidth();
            int leftWidth = ScreenUtils.getScreenWidth(MainActivity.this) - viewWidth;
            leftPopupWindow = new LeftPopupWindow(MainActivity.this, leftWidth, viewHeight, type);
            leftPopupWindow.initPopupWindow();
        }
        leftPopupWindow.showLeftWindow(view, type);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Crouton.makeText(this, "再按一次返回键退出", Style.INFO).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.clearCroutonsForActivity(this);
    }
}
