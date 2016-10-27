package com.jijc.recyclercardview.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jijc.recyclercardview.R;
import com.jijc.recyclercardview.adapter.NewsDataAdapter;
import com.jijc.recyclercardview.utils.DataUtil;
import com.jijc.recyclercardview.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class StoreHouseActivity extends AppCompatActivity {

    @InjectView(R.id.rv_newsdata)
    RecyclerView rvNewsdata;
    @InjectView(R.id.store_house_ptr_frame)
    PtrFrameLayout ptrFrameLayout;
    private NewsDataAdapter adapter;
    private int disY;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_house);
        setTitle("StoreHouse风格");
        mContext=this;
        ButterKnife.inject(this);

        StoreHouseHeader header = new StoreHouseHeader(this);
        header.setPadding(0, UIUtils.dip2pixel(this,10), 0, UIUtils.dip2pixel(this,10));
        header.initWithString("Ji Jinchao",18);

        ptrFrameLayout.setDurationToCloseHeader(800);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);

        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                        Toast.makeText(mContext, "refreshComplete", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);

            }
        });
        rvNewsdata.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rvNewsdata.setHasFixedSize(true);
        adapter = new NewsDataAdapter(this, DataUtil.getNewsList().data.list);
        rvNewsdata.setAdapter(adapter);
    }
}
