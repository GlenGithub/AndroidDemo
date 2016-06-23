package com.littlejie.ui.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.littlejie.R;
import com.littlejie.base.Core;

/**
 * Created by Lion on 2016/6/23.
 */
public class BaseImageView extends ImageView {

    protected int mRadius = 0;
    private int mImageResId;
    private boolean mStateful;

    public BaseImageView(Context context, int resId) {
        super(context);
        mImageResId = resId;
        setImage(mImageResId);
    }

    public BaseImageView(Context context, int resId, boolean stateful) {
        super(context);
        mImageResId = resId;
        mStateful = stateful;
        setImage(mImageResId);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setScaleType(ScaleType.FIT_XY);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseImageView);
        mImageResId = a.getResourceId(R.styleable.BaseImageView_defaltImage, 0);
        mStateful = a.getBoolean(R.styleable.BaseImageView_stateful, true);

        mRadius = a.getDimensionPixelSize(R.styleable.BaseImageView_radius, 0);

        boolean custom = a.getBoolean(R.styleable.BaseImageView_customImage, false);
        if (custom) {
            mImageResId = a.getResourceId(R.styleable.BaseImageView_imageSrc, 0);
        }

        setImage(mImageResId);
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }

    public void setImage(int resId) {
        if (mStateful) {
            setImageResource(resId);
        } else {
            if (resId == 0) {
                setImageResource(0);
            } else {
                String res = "drawable://" + resId;
                if (mRadius != 0) {
                    Core.setMemCachedImage(res, this, mImageResId, mRadius);
                } else {
                    Core.setMemCachedImage(res, this, mImageResId);
                }
            }
        }
    }

    public void setImage(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (mRadius != 0) {
            Core.setDiskCachedImage(url, this, mImageResId, mRadius);
        } else {
            Core.setDiskCachedImage(url, this, mImageResId);
        }
    }

    public void setImage(String url, Core.OnImageLoadListener listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (mRadius != 0) {
            Core.setDiskCachedImage(url, this, mImageResId, mRadius, listener);
        } else {
            Core.setDiskCachedImage(url, this, mImageResId, listener);
        }
    }

    private String getDrawableRes(String res) {
        if (TextUtils.isEmpty(res)) {
            return null;
        }

        int index = res.lastIndexOf('/');
        if (index != -1) {
            return "R.drawable." + res.substring(index + 1, res.length());
        }
        return null;
    }
}
