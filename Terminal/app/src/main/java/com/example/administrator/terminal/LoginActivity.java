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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import utils.edittextvalidator.widget.FormEditText;

/**
 * Created by Lvmoy on 2017/3/28 0028.
 * Mode: - - !
 */

public class LoginActivity extends Activity {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_login_address)
    FormEditText etLoginAddress;
    @BindView(R.id.et_login_user)
    FormEditText etLoginUser;
    @BindView(R.id.et_login_psd)
    FormEditText etLoginPsd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        String content = null;
        content = "192.168.";
        etLoginAddress.setText(content);
        etLoginAddress.setSelection(content.length());
        content = "passenger";
        etLoginUser.setText(content);
        etLoginUser.setSelection(content.length());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        FormEditText[] allFields	= { etLoginAddress, etLoginUser ,etLoginPsd };
        boolean allValid = true;
        boolean isIpValid = true;
        isIpValid = allFields[0].testValidity();
        for (FormEditText field: allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {
            Intent intent = new Intent(this, MainActivity.class);
            int successFlag = 1;
            intent.addFlags(successFlag);
            startActivity(intent);
            // YAY
        } else if(!isIpValid){
        Crouton.makeText(LoginActivity.this, "请填写正确的登录IP地址！", Style.ALERT).show();
            // EditText are going to appear with an exclamation mark and an explicative message.
        }
        else {
            Crouton.makeText(LoginActivity.this, "请填写完整信息！", Style.ALERT).show();

        }
//        if(etLoginAddress.getText().toString().equals("" )|| etLoginUser.getText().toString().equals("")|| etLoginPsd.getText().toString().equals("")){
//        }else {
//
//        }

    }


//    @OnClick({R.id.et_login_address, R.id.et_login_user, R.id.et_login_psd})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.et_login_address:
//
//                break;
//            case R.id.et_login_user:
//                break;
//            case R.id.et_login_psd:
//                break;
//        }
//    }


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
