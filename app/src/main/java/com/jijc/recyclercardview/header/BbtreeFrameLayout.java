package com.jijc.recyclercardview.header;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Description:
 * Created by jijc on 2016/8/30.
 * PackageName: com.jijc.recyclercardview.header
 */
public class BbtreeFrameLayout extends PtrFrameLayout {
    private BbtreeHeaderView mPtrClassicHeader;

    public BbtreeFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public BbtreeFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BbtreeFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new BbtreeHeaderView(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    public BbtreeHeaderView getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}
