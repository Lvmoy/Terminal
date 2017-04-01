package com.example.administrator.terminal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.bluelinelabs.logansquare.LoganSquare;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import interfaces.OrderService;
import interfaces.TestService;
import pojo.ItemsItem;
import pojo.Response;
import pojo.order.Order;
import pojo.order.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_detail);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        vpHorizontalNtb.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                final View view = LayoutInflater.from(
                        getApplicationContext()).inflate(R.layout.layout_detail_item_570, null, false);

                container.addView(view);
                return view;
            }
        });

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

            }
        });

//        EditText etToCatIp = (EditText) findViewById(R.id.et_toCatIp);
//        String toCatIp = etToCatIp.getText().toString().trim();

//            findViewById(R.id.bt_catPing).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String baseUri2 = "http://192.168.2.55:8080/test/";
                String baseUri = "http://192.168.2.15:8080/MulityServer2/servlet/";
                String baseUri1 = "https://api.stackexchange.com/2.2/";
//                testRetrofit(baseUri2);
                testOrder(baseUri2);

            }
        },3000);

//                }
//            });
//        }
    }

    private void testOrder(String baseUri2) {
        Retrofit retrofit = new Retrofit.Builder( )
                .baseUrl(baseUri2)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();

        Order order = new Order();
        order.setId(1);
        order.setMachineType(2);
        order.setOrderName("query iso xx");
        order.setOrderType(2);
        order.setIp("172.22.1.22");
        order.setMachine_port("iso.3.6.1.2.1.4.20.1.1.172.22.1.22");

        OrderService orderService = retrofit.create(OrderService.class);
        Call<Result> call = orderService.reportOrder(order);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                    String extraString ;
                    Intent intent =new Intent(HomeDetailActivity.this, ShowActivity.class);
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            extraString = response.body().toString();
                        }else {
                            extraString = "repose.body() == null";
                        }

                    }else {
                        extraString = response.errorBody().toString();
                    }

                    intent.putExtra("json", extraString);
                    startActivity(intent);
                }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Intent intent =new Intent(HomeDetailActivity.this, ShowActivity.class);
                intent.putExtra("json", "error nothing received :" + t.toString());
                startActivity(intent);
            }
        });


    }

    private void testRetrofit(String baseUri1) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(LoganSquareConverterFactory.create())
                .baseUrl(baseUri1)
                .build();

        TestService testService = retrofit.create(TestService.class);
        Call<Response> call = testService.getAnswers();
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                String extraString ;
                Intent intent =new Intent(HomeDetailActivity.this, ShowActivity.class);
                if(response.isSuccessful()){
                    if(response.body() != null){
                        extraString = response.body().getItems().toString();
                    }else {
                        extraString = "repose.body() == null";
                    }

                }else {
                    extraString = response.errorBody().toString();
                }

                intent.putExtra("json", extraString);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Intent intent =new Intent(HomeDetailActivity.this, ShowActivity.class);
                intent.putExtra("json", "error nothing received :" + t.toString());
                startActivity(intent);
            }
        });
    }
}
