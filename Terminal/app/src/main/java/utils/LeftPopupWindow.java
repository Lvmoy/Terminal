package utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.terminal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class LeftPopupWindow extends PopupWindow implements View.OnClickListener{
    private Context context = null;
//    private View contentView = null;
    private Handler handler = new Handler();
    private int width;
    private int height;
    private int type;   //1-3 2-2 3-1 4-2

    private ImageTextView imageTextViewOne;
    private ImageTextView imageTextViewTwo;
    private ImageTextView imageTextViewThree;

    private List<String> packageName = new ArrayList<String>();
    private List<String> appName = new ArrayList<>();


    private SparseIntArray typeArray = new SparseIntArray();
    private SparseIntArray typeNumArray = new SparseIntArray();

    public LeftPopupWindow(Context context, int width, int height, int type) {
        super(context);
        this.context = context;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public void initPopupWindow(){
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        contentView = layoutInflater.inflate(R.layout.layout_home_popupwindow, null);
//        this.setContentView(contentView);
        //包名
        packageName.add("org.avcon.mob");
        packageName.add("net.orbitingpluto.android.ipcalc");
        packageName.add("com.Eonsoft.satfinder");
        packageName.add("com.netfeige");
        packageName.add("com.zwxt.compass.terminal");
        packageName.add("com.polycom.cmad.mobile.android");
        packageName.add("org.videolan.vlc");
        packageName.add("bocc.telecom.txb");

        //app名
        appName.add("手机会议");
        appName.add("ipcal");
        appName.add("寻星计算器");
        appName.add("飞鸽传书");
        appName.add("北斗终端");
        appName.add("Video");
        appName.add("视频监控");
        appName.add("超级工程师");

        //适配用
        typeArray.append(1, R.id.tv_home_video);
        typeArray.append(2, R.id.tv_home_bd_guide);
        typeArray.append(3, R.id.tv_home_data);
        typeArray.append(4, R.id.tv_home_tools);

        typeNumArray.append(1, 3);
        typeNumArray.append(2, 2);
        typeNumArray.append(3, 1);
        typeNumArray.append(4, 2);
        //calcu 实际pupop 宽高
        width = width + 3;
        height = height + 4;

        this.setWidth(width);
        this.setHeight(height);
        //弹出可点
        this.setTouchable(true);
        this.setFocusable(true);
        //外部点击结果
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.PopupWindowAnimation);
    }

    public void showLeftWindow(View view, int type){
        final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_pupop, null);
//        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorTransparent));
        this.type = type;
        setContentView(linearLayout);
        final int x = (int)view.getX() + view.getWidth() - 2;

        if(isShowing()){
            closeAnimation(linearLayout, type, x);
        }
        showAnimation(linearLayout, type, x);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
//        int x = (int)DensityUtils.px2dp(context, location[0]);
        int viewY = (int)DensityUtils.px2dp(context, location[1]);
        int y = ScreenUtils.getScreenHeight(context) - viewY;
        showAtLocation(view, Gravity.TOP, x, viewY);
    }

    private void showAnimation(ViewGroup linearLayout, final int type, final int x) {
        linearLayout = switchLayout(linearLayout, type);
        int childCount = getVisibleChildCount(linearLayout);
        for(int i = 0; i<childCount; i++){
            final View childView = linearLayout.getChildAt(i);
            childView.setOnClickListener(this);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    childView.setVisibility(View.VISIBLE);
                    //icon X 方向偏移量计算  n = (3 - typeNum) n * 1/2 * (type - typeNum)
                    int n = typeNumArray.get(type);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(childView, "translationX", 0 , (float) (width / (3 * Math.pow(n, 2))) );
                    fadeAnim.setDuration(300);
                    KickBackAnimator kickBackAnimator = new KickBackAnimator();
                    kickBackAnimator.setDuration(150);
                    fadeAnim.setEvaluator(kickBackAnimator);
                    fadeAnim.start();
                }
            }, i * 50);
        }
    }



    private void closeAnimation(ViewGroup linearLayout, final int type, final int x) {
        linearLayout = switchLayout(linearLayout, type);
        final int childCount = getVisibleChildCount(linearLayout);
        for(int i = 0; i < childCount; i++){
            final View child = linearLayout.getChildAt(i);
            child.setOnClickListener(this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    int n = typeNumArray.get(type);
                    ValueAnimator fadeAnim;
                    if(childCount == 3){
                        fadeAnim = ObjectAnimator.ofFloat(child, "translationX", 0, 0);

                    }else {
                    fadeAnim = ObjectAnimator.ofFloat(child, "translationX", 0, (float) (width / (3 * Math.pow(n, 2))));
                    }
                    fadeAnim.setDuration(200);
                    KickBackAnimator kickBackAnimator = new KickBackAnimator();
                    kickBackAnimator.setDuration(100);
                    fadeAnim.setEvaluator(kickBackAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            child.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                }
            }, (childCount - i - 1) * 30);

            if(child.getId() != typeArray.get(type)){
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        dismiss();
                    }
                }, (childCount-i) * 30 + 80);
            }
        }

    }

    private LinearLayout switchLayout(ViewGroup viewGroup, int type) {
        LinearLayout linearLayout = (LinearLayout) viewGroup;

        Resources resources = context.getResources();
        Drawable drawable ;
        imageTextViewOne = (ImageTextView) linearLayout.findViewById(R.id.pup_one);
        imageTextViewTwo = (ImageTextView) linearLayout.findViewById(R.id.pup_two);
        imageTextViewThree = (ImageTextView) linearLayout.findViewById(R.id.pup_three);
        switch (type){
            case 1:
                imageTextViewOne.setText("POLYCOM");
                imageTextViewOne.setVisibility(View.VISIBLE);
                imageTextViewOne.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_polycom);
                imageTextViewOne.setmDrawable(drawable);

                imageTextViewTwo.setText("AVCON");
                imageTextViewTwo.setVisibility(View.VISIBLE);
                imageTextViewTwo.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_avcon);
                imageTextViewTwo.setmDrawable(drawable);

                imageTextViewThree.setText("视屏监控");
                imageTextViewThree.setVisibility(View.VISIBLE);
                imageTextViewThree.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_monitor);
                imageTextViewThree.setmDrawable(drawable);
                break;
            case 2:
                imageTextViewOne.setText("北斗");
                imageTextViewOne.setVisibility(View.VISIBLE);
                imageTextViewOne.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_bd);
                imageTextViewOne.setmDrawable(drawable);

                imageTextViewTwo.setText("星远北斗");
                imageTextViewTwo.setVisibility(View.VISIBLE);
                imageTextViewTwo.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_xybd);
                imageTextViewTwo.setmDrawable(drawable);

                imageTextViewThree.setVisibility(View.GONE);
                break;
            case 3:
                imageTextViewOne.setText("飞鸽传书");
                imageTextViewOne.setVisibility(View.VISIBLE);
                imageTextViewOne.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_ipmsg);
                imageTextViewOne.setmDrawable(drawable);

                imageTextViewTwo.setVisibility(View.GONE);

                imageTextViewThree.setVisibility(View.GONE);
                break;
            case 4:
                imageTextViewOne.setText("超级工程师");
                imageTextViewOne.setVisibility(View.VISIBLE);
                imageTextViewOne.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_suengineer);
                imageTextViewOne.setmDrawable(drawable);

                imageTextViewTwo.setText("Ip计算器");
                imageTextViewTwo.setVisibility(View.VISIBLE);
                imageTextViewTwo.setOnClickListener(this);
                drawable = resources.getDrawable(R.drawable.icon_home_ipcalc);
                imageTextViewTwo.setmDrawable(drawable);


                imageTextViewThree.setVisibility(View.GONE);
                break;
            default:
                break;

        }
        drawable = null;
        return linearLayout;
    }

    private int getVisibleChildCount(ViewGroup linearLayout) {
        int visibleChildCount = 0;
        for (int i = 0; i < linearLayout.getChildCount(); i ++){
            View child = linearLayout.getChildAt(i);
            if(child.getVisibility() == View.VISIBLE || child.getVisibility() == View.GONE){
                visibleChildCount ++;
            }
        }
        return visibleChildCount;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pup_one:
                switch (type){
                    case 1:
                        openApp(packageName.get(5));
                        break;
                    case 2:
                        openApp(packageName.get(4));
                        break;
                    case 3:
                        openApp(packageName.get(3));
                        break;
                    case 4:
                        openApp(packageName.get(7));
                        break;
                }
                break;
            case R.id.pup_two:
                switch (type) {
                    case 1:
                        openApp(packageName.get(0));
                        break;
                    case 2:
                        openApp(packageName.get(2));
                        break;
                    case 3:
                        break;
                    case 4:
                        openApp(packageName.get(1));
                        break;
                }
            case R.id.pup_three:
                switch (type){
                    case 1:
                        openApp(packageName.get(6));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            default:
                break;
        }
    }

    private void openApp(String packageName) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("tag", packageName + "is not found");
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null ) {
            String tempPackageName = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            //singletask mode
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(tempPackageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }
}
