package utils;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.administrator.terminal.R;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class ImageTextView extends android.support.v7.widget.AppCompatTextView {
    private Drawable mDrawable;//设置的图片
    private int mScaleWidth; // 图片的宽度
    private int mScaleHeight;// 图片的高度
    private int mPosition;// 图片的位置 2上1左4下3右

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ImageTextView);

        mDrawable = typedArray.getDrawable(R.styleable.ImageTextView_drawable);
        mScaleWidth = typedArray
                .getDimensionPixelOffset(
                        R.styleable.ImageTextView_drawableWidth,
                        DensityUtils.dp2px(context,20));
        mScaleHeight = typedArray.getDimensionPixelOffset(
                R.styleable.ImageTextView_drawableHeight,
                DensityUtils.dp2px(context,20));
        mPosition = typedArray.getInt(R.styleable.ImageTextView_position, 3);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mDrawable != null) {
            //mDrawable.setBounds(0, 0, DensityUtils.dp2px(,mScaleWidth),
           //         DensityUtils.dp2px(context, mScaleHeight));
            mDrawable.setBounds(0, 0, mScaleWidth, mScaleHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mPosition) {
            case 1:
                this.setCompoundDrawables(mDrawable, null, null, null);
                break;
            case 2:
                this.setCompoundDrawables(null, mDrawable, null, null);
                break;
            case 3:
                this.setCompoundDrawables(null, null, mDrawable, null);
                break;
            case 4:
                this.setCompoundDrawables(null, null, null, mDrawable);
                break;
            default:

                break;
        }
    }

    /**
     * 设置左侧图片并重绘
     *
     * @param
     */
    public void setDrawableLeft(Drawable drawable) {
        this.mDrawable = drawable;
        invalidate();
    }

    /**
     * 设置左侧图片并重绘
     *
     * @param
     */
    public void setDrawableLeft(int drawableRes, Context context) {
        this.mDrawable = context.getResources().getDrawable(drawableRes);
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     *
     * @param
     */
    public void setDrawableRight(Drawable drawable) {
        this.mDrawable = drawable;
        this.mPosition = 3;
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     *
     * @param
     */
    public void setDrawableRight(int drawableRes, Context context) {
        this.mDrawable = context.getResources().getDrawable(drawableRes);
        this.mPosition = 3;
        invalidate();
    }

    public Drawable getmDrawable() {
        return mDrawable;
    }

    public void setmDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public int getmScaleWidth() {
        return mScaleWidth;
    }

    public void setmScaleWidth(int mScaleWidth) {
        this.mScaleWidth = mScaleWidth;
    }

    public int getmScaleHeight() {
        return mScaleHeight;
    }

    public void setmScaleHeight(int mScaleHeight) {
        this.mScaleHeight = mScaleHeight;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }
}