package com.example.administrator.terminal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapter.HomeViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import devlight.io.library.ntb.NavigationTabBar;
import interfaces.OrderService;
import pojo.order.DataItem;
import pojo.order.Order;
import pojo.order.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import utils.OrderUtils;
import utils.edittextvalidator.widget.FormEditText;

/**
 * Created by Lvmoy on 2017/3/29 0029.
 * Mode: - - !
 */

public class HomeDetailActivity extends Activity {
    @BindView(R.id.vp_horizontal_ntb)
    ViewPager vpHorizontalNtb;
    @BindView(R.id.ntb_horizontal)
    NavigationTabBar ntbHorizontal;
    @BindView(R.id.wrapper_ntb_horizontal)
    FrameLayout wrapperNtbHorizontal;


    private FormEditText etToCatIp;
    private FormEditText etToWanIp;
    private FormEditText etToLanIp;
    private TextView btCatPing;
    private TextView btWanPing;
    private TextView btLanPing;

    private EditText etSendHz;
    private EditText etSendBps;
    private EditText etReceiveHz;
    private EditText etReceiveBps;

    private TextView btOk;

    private View currentView = null;
    //    private HashMap<Integer, View > viewHashMap = new HashMap<>();
    private HomeViewPagerAdapter viewPagerAdapter;
    private List<View> viewList = new ArrayList<>();
    private List<DataItem> dataItems  = new ArrayList<>();

    private boolean is570Connecting = false;
    private boolean isDataShowing = false;
    private boolean isChildThreadRunning = true;
    private static final String baseUri2 = "http://192.168.2.67:8080/test/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_detail);
        ButterKnife.bind(this);
        initUI();
        doPing();
        is570Connecting = getIntent().getBooleanExtra("570state", false);
        if(is570Connecting){
            Thread thread  = new Thread(new Runnable() {
                @Override
                public void run() {
                    doSetOrQuery(dataItems);
                }
            });
            thread.start();
        }
        else {
            Crouton.makeText(this, "对不起，570设备没有连接，不支持此项服务。", Style.ALERT).show();
        }

        doOpenQuery();

    }

    private void doOpenQuery() {
        while (!isChildThreadRunning && !isDataShowing){
            if (viewList != null && viewList.get(3) != null) {

                View view = viewList.get(3);
//                btOk = (TextView) view.findViewById(R.id.bt_ok);
                etSendHz = (EditText) view.findViewById(R.id.et_sendHz);
                etSendBps = (EditText) view.findViewById(R.id.et_sendbps);
                etReceiveHz = (EditText) view.findViewById(R.id.et_receivehz);
                etReceiveBps = (EditText) view.findViewById(R.id.et_receivebps);

//                btOk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
                        if(dataItems != null){
                            etSendHz.setText(dataItems.get(0).getValue());
                            etSendBps.setText(dataItems.get(1).getValue());
                            etReceiveHz.setText(dataItems.get(2).getValue());
                            etReceiveBps.setText(dataItems.get(3).getValue());
                        }else {
                            Crouton.makeText(HomeDetailActivity.this, "连接570设备错误!", Style.ALERT).show();
                        }
//                    }
//                });
            }
            isDataShowing = true;
        }
    }

    private void doSetOrQuery(List<DataItem> dataItemList) {
        dataItemList.clear();
        if(! isDataShowing){
            doAllQuery(dataItemList);
            if(dataItemList != null){
                if(dataItemList.size() > 0){
                    isChildThreadRunning = false;
                }

            }

        }
    }

    private void doAllQuery(List<DataItem> dataItemsList) {
        OrderUtils.doAllQuery(baseUri2, dataItemsList);
    }

    private void initUI() {
        viewList.add(View.inflate(getApplicationContext(), R.layout.layout_detail_item_common, null));
        viewList.add(View.inflate(getApplicationContext(), R.layout.layout_detail_item_3g, null));
        viewList.add(View.inflate(getApplicationContext(), R.layout.layout_detail_item_bd, null));
        viewList.add(View.inflate(getApplicationContext(), R.layout.layout_detail_item_570,null));
        viewList.add(View.inflate(getApplicationContext(), R.layout.layout_detail_item_voip, null));
        viewPagerAdapter = new HomeViewPagerAdapter(viewList);
        vpHorizontalNtb.setAdapter(viewPagerAdapter);


        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_home_detail_common),
                        Color.parseColor(colors[0]))
                        .title(getString(R.string.home_detail_common))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_home_detail_3g),
                        Color.parseColor(colors[1]))
                        .title(getString(R.string.home_detail_3g))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_home_detail_bd),
                        Color.parseColor(colors[2]))
                        .title(getString(R.string.home_detail_bd))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_home_detail_570),
                        Color.parseColor(colors[3]))
                        .title(getString(R.string.home_detail_570))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_home_detail_voip),
                        Color.parseColor(colors[4]))
                        .title(getString(R.string.home_detail_voip))
                        .build()
        );

        ntbHorizontal.setModels(models);
        ntbHorizontal.setViewPager(vpHorizontalNtb, 3);
        ntbHorizontal.setIconSizeFraction(0.5f);

        ntbHorizontal.post(new Runnable() {
            @Override
            public void run() {
                final View viewPager = findViewById(R.id.vp_horizontal_ntb);
                ((ViewGroup.MarginLayoutParams) viewPager.getLayoutParams()).topMargin =
                        (int) -ntbHorizontal.getBadgeMargin();
                viewPager.requestLayout();
            }
        });

        ntbHorizontal.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                switch (index) {
                    case 3:

                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void doPing() {
//        final View layout = LayoutInflater.from(
//                getApplicationContext()).inflate(R.layout.layout_detail_item_570, null, false);
//        currentView = layout;
//        viewHashMap.put(3, layout);

        if (viewList != null && viewList.get(3) != null) {

            View view = viewList.get(3);
            etToCatIp = (FormEditText) view.findViewById(R.id.et_toCatIp);
            etToWanIp = (FormEditText) view.findViewById(R.id.et_toWanIp);
            etToLanIp = (FormEditText) view.findViewById(R.id.et_toLanIp);

            btCatPing = (TextView) view.findViewById(R.id.bt_catPing);
            btWanPing = (TextView) view.findViewById(R.id.bt_WanPing);
            btLanPing = (TextView) view.findViewById(R.id.bt_LanPing);



            etToCatIp.setFocusable(true);
            etToCatIp.requestFocus();
            etToCatIp.setTextIsSelectable(true);
            etToCatIp.setSelection(8);

            etToWanIp.setFocusable(true);
            etToWanIp.requestFocus();
            etToWanIp.setTextIsSelectable(true);
            etToWanIp.setSelection(8);

            etToLanIp.setFocusable(true);
            etToLanIp.requestFocus();
            etToLanIp.setTextIsSelectable(true);
            etToLanIp.setSelection(8);
            final InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            etToCatIp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });

            etToWanIp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });

            etToLanIp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });

            EditText editText = (EditText) view.findViewById(R.id.et_sendHz);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            btCatPing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(HomeDetailActivity.this, R.style.UserAvatarDialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    TextView textView = new TextView(HomeDetailActivity.this);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                    textView.setTextColor(getResources().getColor(R.color.colorOrange));
                    textView.setText("努力ping中，请稍等...");
                    dialog.setContentView(textView);
                    dialog.show();
                    if (etToCatIp.testValidity()) {
                        String catIp = etToCatIp.getText().toString();
                        if(OrderUtils.doCatPing(catIp)){
                            String result = OrderUtils.doPingForInfo(catIp);
                            dialog.hide();
                            textView.setTextColor(getResources().getColor(R.color.colorLightWhite));
                            textView.setText(result);
                            dialog.show();
                            Crouton.makeText(HomeDetailActivity.this, "恭喜，网络通畅!", Style.INFO).show();
                        }else
                        {
                            String result = OrderUtils.doPingForInfo(catIp);

                            textView.setText(result);
                            dialog.show();
                            Crouton.makeText(HomeDetailActivity.this, "对不起，网络不通!请检查地址", Style.ALERT).show();

                        }
                    }
                }
            });
            btWanPing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etToWanIp.testValidity()) {
                        String wanIp = etToWanIp.getText().toString();
                        if(OrderUtils.doCatPing(wanIp)){
                            Crouton.makeText(HomeDetailActivity.this, "恭喜，网络通畅!", Style.INFO).show();
                        }else
                        {
                            Crouton.makeText(HomeDetailActivity.this, "对不起，，网络不通!请检查地址", Style.ALERT).show();

                        }
                    }
                }
            });
            btLanPing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etToLanIp.testValidity()) {
                        String lanIp = etToLanIp.getText().toString();
                        if(OrderUtils.doCatPing(lanIp)){
                            Crouton.makeText(HomeDetailActivity.this, "恭喜，网络通畅!", Style.INFO).show();
                        }else
                        {
                            Crouton.makeText(HomeDetailActivity.this, "对不起，网络不通!请检查地址", Style.ALERT).show();

                        }
                    }
                }
            });

        } else {
            Crouton.makeText(HomeDetailActivity.this, "viewHashMap = null", Style.ALERT).show();

        }


    }

    private boolean is570Connected() {
        return is570Connecting;
    }

//    private void testOrder(String baseUri2) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUri2)
//                .addConverterFactory(LoganSquareConverterFactory.create())
//                .build();
//
//        Order order = new Order();
//        order.setId(1);
//        order.setMachineType(2);
//        order.setOrderName("query iso xx");
//        order.setOrderType(2);
//        order.setIp("172.22.1.22");
//        order.setMachine_port("iso.3.6.1.2.1.4.20.1.1.172.22.1.22");
//
//        OrderService orderService = retrofit.create(OrderService.class);
//        Call<Result> call = orderService.reportOrder(order);
//
//        call.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
//                String extraString;
//                Intent intent = new Intent(HomeDetailActivity.this, ShowActivity.class);
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        extraString = response.body().toString();
//                    } else {
//                        extraString = "repose.body() == null";
//                    }
//
//                } else {
//                    extraString = response.errorBody().toString();
//                }
//
//                intent.putExtra("json", extraString);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                Intent intent = new Intent(HomeDetailActivity.this, ShowActivity.class);
//                intent.putExtra("json", "error nothing received :" + t.toString());
//                startActivity(intent);
//            }
//        });
//
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.clearCroutonsForActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isDataShowing = false;
        finish();
    }
}
