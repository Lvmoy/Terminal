package com.example.administrator.terminal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Lvmoy on 2017/3/28 0028.
 * Mode: - - !
 */

public class LoginActivity extends Activity {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_login_address)
    EditText etLoginAddress;
    @BindView(R.id.et_login_user)
    EditText etLoginUser;
    @BindView(R.id.et_login_psd)
    EditText etLoginPsd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        if(etLoginAddress.getText().toString().equals("" )|| etLoginUser.getText().toString().equals("")|| etLoginPsd.getText().toString().equals("")){
            Crouton.makeText(LoginActivity.this, "填写内容不能为空！", Style.ALERT).show();
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            int successFlag = 1;
            intent.addFlags(successFlag);
            startActivity(intent);
        }

    }

    @OnClick({R.id.et_login_address, R.id.et_login_user, R.id.et_login_psd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_login_address:
                break;
            case R.id.et_login_user:
                break;
            case R.id.et_login_psd:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.clearCroutonsForActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
