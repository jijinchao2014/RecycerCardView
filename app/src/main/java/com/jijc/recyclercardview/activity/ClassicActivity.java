package com.jijc.recyclercardview.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jijc.recyclercardview.R;
import com.jijc.recyclercardview.adapter.NewsDataAdapter;
import com.jijc.recyclercardview.utils.DataUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ClassicActivity extends AppCompatActivity {

    @InjectView(R.id.rv_newsdata)
    RecyclerView rvNewsdata;
    @InjectView(R.id.rotate_header_grid_view_frame)
    PtrClassicFrameLayout rotateHeaderGridViewFrame;

    private Context mContext;
    private NewsDataAdapter adapter;
    private PtrClassicFrameLayout mPtrFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic);
        setTitle("经典下拉刷新");
        ButterKnife.inject(this);
        mContext=this;

        mPtrFrame = (PtrClassicFrameLayout)findViewById(R.id.rotate_header_grid_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        Toast.makeText(ClassicActivity.this, "refreshComplete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.0f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                // mPtrFrame.autoRefresh();
            }
        }, 100);
        // updateData();
        setupViews(mPtrFrame);

        rvNewsdata.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rvNewsdata.setHasFixedSize(true);
        adapter = new NewsDataAdapter(this, DataUtil.getNewsList().data.list);
        rvNewsdata.setAdapter(adapter);
    }
    protected void setupViews(final PtrClassicFrameLayout ptrFrame) {
        Toast.makeText(ClassicActivity.this, "setupViews", Toast.LENGTH_SHORT).show();
    }
}
