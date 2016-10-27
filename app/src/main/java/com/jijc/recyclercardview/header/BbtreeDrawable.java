package com.jijc.recyclercardview.header;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.jijc.recyclercardview.R;

import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Description:
 * Created by jijc on 2016/8/30.
 * PackageName: com.jijc.recyclercardview.header
 */
public class BbtreeDrawable extends Drawable implements Animatable {

    private final Matrix mMatrix;
    private Context mContext;
    private View mParent;
    private Bitmap bmpGrassLand;
    private int mTotalDragDistance;

    public BbtreeDrawable(Context context, View parent){
        mContext = context;
        mParent = parent;
        mMatrix = new Matrix();
        PtrLocalDisplay.init(mContext);
        bmpGrassLand = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.bg_header_grass);
        mTotalDragDistance = PtrLocalDisplay.dp2px(50);
    }

    /**
     * Starts the drawable's animation.
     */
    @Override
    public void start() {

    }


    @Override
    public void stop() {

    }


    @Override
    public boolean isRunning() {
        return false;
    }


    @Override
    public void draw(Canvas canvas) {
        final int saveCount = canvas.save();
        canvas.drawBitmap(bmpGrassLand,mMatrix,null);
        canvas.restoreToCount(saveCount);
    }


    @Override
    public void setAlpha(int alpha) {

    }


    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public int getTotalDragDistance() {
        return mTotalDragDistance;
    }
}
