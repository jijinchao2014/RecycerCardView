package com.jijc.recyclercardview.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jijc.recyclercardview.R;
import com.jijc.recyclercardview.adapter.NewsDataAdapter;
import com.jijc.recyclercardview.bean.NewsRequest;
import com.jijc.recyclercardview.header.BbtreeFrameLayout;
import com.jijc.recyclercardview.utils.DataUtil;
import com.jijc.recyclercardview.view.DoubleClickTextView;
import com.jijc.recyclercardview.view.OptionsPopWindow;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class BbtreeActivity extends AppCompatActivity implements NewsDataAdapter.OnItemClickListener, NewsDataAdapter.OnLoadMoreListener {

    @InjectView(R.id.rv_newsdata)
    RecyclerView rvNewsdata;
    @InjectView(R.id.rotate_header_grid_view_frame)
    BbtreeFrameLayout mPtrFrame;
    @InjectView(R.id.tv_sub)
    TextView tvSub;
    @InjectView(R.id.tv_hot)
    TextView tvHot;
    @InjectView(R.id.v_options)
    View vOptions;
    @InjectView(R.id.ll_sub)
    LinearLayout llSub;
    @InjectView(R.id.ll_hot)
    LinearLayout llHot;

    private Context mContext;
    private NewsDataAdapter adapter;
    private ArrayList<NewsRequest.ReData.Recomment> recomments = new ArrayList<>();
    private ArrayList<String> options = new ArrayList<>();
    private ArrayList<String> options1 = new ArrayList<>();

    private OptionsPopWindow popWindow;
    private OptionsPopWindow popWindow1;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbtree);
        ButterKnife.inject(this);

        setTitle("智慧树下拉刷新");
        ButterKnife.inject(this);
        mContext = this;

        options.add("全部");
        options.add("课题研究");
        options.add("园长课程");
        options.add("园所活动");
        options.add("教学计划");
        options.add("幼小衔接");
        options.add("常见疾病");
        options.add("营养健康");

        options1.add("最新");
        options1.add("最热");

        initOptionsState(tvSub,R.mipmap.icon_green_down);
        initOptionsState(tvHot,R.mipmap.icon_gray_down);


        popWindow = new OptionsPopWindow(mContext, options);
        popWindow1 = new OptionsPopWindow(mContext, options1);

        popWindow.setOnPopupItemClick(new OptionsPopWindow.OnPopupItemClick() {
            @Override
            public void onPopupItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(BbtreeActivity.this, "data:"+options.get(position), Toast.LENGTH_SHORT).show();
                tvSub.setText(options.get(position));
            }
        });

        popWindow1.setOnPopupItemClick(new OptionsPopWindow.OnPopupItemClick() {
            @Override
            public void onPopupItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(BbtreeActivity.this, "data:"+options1.get(position), Toast.LENGTH_SHORT).show();
                tvHot.setText(options1.get(position));
            }
        });

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                initOptionsState(tvSub,R.mipmap.icon_green_down);
            }
        });

        popWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                initOptionsState(tvHot,R.mipmap.icon_gray_down);
            }
        });

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Log.i("jijinchao", "=============onRefreshBegin");
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        recomments.clear();
                        recomments.addAll(DataUtil.getNewsListNew().data.list);
                        adapter.notifyDataSetChanged();
                    }
                }, 4000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(2.0f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.0f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(400);
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
        recomments.addAll(DataUtil.getNewsList().data.list);
        adapter = new NewsDataAdapter(this, recomments, rvNewsdata);
        adapter.setOnClickListener(this);
        adapter.setOnLoadMoreListener(this);
        rvNewsdata.setAdapter(adapter);
    }

    private void initOptionsState(TextView view,int iconResId){
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconResId, 0);
        view.setCompoundDrawablePadding(10);
    }

    protected void setupViews(final BbtreeFrameLayout ptrFrame) {
        Toast.makeText(mContext, "setupViews", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position, int viewType) {
        Toast.makeText(BbtreeActivity.this, "position:" + position + ",viewType:" + (viewType == NewsDataAdapter.ITEM_ONEPIC ? "item_onepic" : "item_mutpic"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore(final RecyclerView recylerView) {
        recomments.add(null);
        adapter.notifyDataSetChanged();
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                recomments.remove(recomments.size() - 1);
                adapter.notifyDataSetChanged();
                if (index<=3){
                    recomments.addAll(DataUtil.getNewsListMore().data.list);
                    adapter.notifyDataSetChanged();
                    adapter.setLoaded();
                }else {
                    NewsRequest.ReData.Recomment recomment = new NewsRequest().new ReData().new Recomment();
                    recomment.picType = 0;
                    recomments.add(recomment);
                    adapter.notifyDataSetChanged();
                    adapter.setLoaded();
                    handler.sendEmptyMessageDelayed(1000, 1000);
                }
                index++;
//                if (DataUtil.getNewsListNone().data.list.size() == 0) {
//                    NewsRequest.ReData.Recomment recomment = new NewsRequest().new ReData().new Recomment();
//                    recomment.picType = 0;
//                    recomments.add(recomment);
//                    adapter.notifyDataSetChanged();
//                    adapter.setLoaded();
//                    handler.sendEmptyMessageDelayed(1000, 1000);
//                } else {
//                    recomments.addAll(DataUtil.getNewsListMore().data.list);
//                    adapter.notifyDataSetChanged();
//                    adapter.setLoaded();
//                }

            }
        }, 3000);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            recomments.remove(recomments.size() - 1);
//            adapter.notifyDataSetChanged();
        }
    };

    @OnClick({R.id.ll_sub, R.id.ll_hot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sub:
                if (popWindow1.isShowing())
                    popWindow1.dismiss();
                initOptionsState(tvSub,R.mipmap.icon_green_up);

                popWindow.showPopupWindow(vOptions);
                break;
            case R.id.ll_hot:
                if (popWindow.isShowing())
                    popWindow.dismiss();
                initOptionsState(tvHot,R.mipmap.icon_gray_up);
                popWindow1.showPopupWindow(vOptions);
                break;
        }
    }
}
