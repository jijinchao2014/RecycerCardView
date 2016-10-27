package com.jijc.recyclercardview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.jijc.recyclercardview.R;
import com.jijc.recyclercardview.adapter.NewsDataAdapter;
import com.jijc.recyclercardview.header.RentalsSunHeaderView;
import com.jijc.recyclercardview.utils.DataUtil;
import com.jijc.recyclercardview.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.rv_newsdata)
    RecyclerView rvNewsdata;
    @InjectView(R.id.store_house_ptr_frame)
    PtrFrameLayout ptrFrameLayout;
    private NewsDataAdapter adapter;
    private int disY;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        ButterKnife.inject(this);
        //1.StoreHouse显示格式
//        StoreHouseHeader header = new StoreHouseHeader(this);
//        header.setPadding(0, UIUtils.dip2pixel(this,10), 0, UIUtils.dip2pixel(this,10));
//        header.initWithString("Ji Jinchao",18);

        //2.Material显示格式
//        MaterialHeader header = new MaterialHeader(mContext);
//        int[] colors = getResources().getIntArray(R.array.google_colors);
//        header.setColorSchemeColors(colors);
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, UIUtils.dip2pixel(this,10), 0, UIUtils.dip2pixel(this,10));
//        header.setPtrFrameLayout(ptrFrameLayout);
//        ptrFrameLayout.setPinContent(true); //该方法设置内容不随手指滚动（可不设置）

        //3.RentalsSun显示格式
        RentalsSunHeaderView header = new RentalsSunHeaderView(mContext);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, UIUtils.dip2pixel(this,10), 0, UIUtils.dip2pixel(this,10));
        header.setUp(ptrFrameLayout);


        ptrFrameLayout.setDurationToCloseHeader(1500);
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
                        Toast.makeText(MainActivity.this, "refreshComplete", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);

            }
        });
        rvNewsdata.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rvNewsdata.setHasFixedSize(true);
        adapter = new NewsDataAdapter(this, DataUtil.getNewsList().data.list);
        rvNewsdata.setAdapter(adapter);
        rvNewsdata.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                disY+=dy;
                Log.i("jijinchao","-------dx:"+dx+"-------dy:"+dy+"----------disY:"+disY+"---top:"+recyclerView.getTop());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_bbtree:
                startActivity(new Intent(mContext,BbtreeActivity.class));
                break;
            case R.id.action_classic:
                startActivity(new Intent(mContext,ClassicActivity.class));
                break;
            case R.id.action_material:
                startActivity(new Intent(mContext,MaterialActivity.class));
                break;
            case R.id.action_storehouse:
                startActivity(new Intent(mContext,StoreHouseActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
