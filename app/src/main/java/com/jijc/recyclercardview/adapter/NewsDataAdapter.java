package com.jijc.recyclercardview.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jijc.recyclercardview.R;
import com.jijc.recyclercardview.bean.NewsRequest;
import com.jijc.recyclercardview.holder.ItemHolder;
import com.jijc.recyclercardview.holder.LoadMoreHolder;
import com.jijc.recyclercardview.holder.NoMoreHolder;
import com.jijc.recyclercardview.utils.DisplayImageOptionsCfg;
import com.jijc.recyclercardview.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Description:
 * Created by jijc on 2016/8/29.
 * PackageName: com.jijc.recyclercardview.adapter
 */
public class NewsDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_ONEPIC = 0;
    public static final int ITEM_MUTPIC = 1;
    public static final int ITEM_LOADMORE = 2;
    public static final int ITEM_NOMORE = 3;
    private ArrayList<NewsRequest.ReData.Recomment> mRecomments;
    private Context mContext;
    private RecyclerView mRecylerView;
    private int totalItemCount;
    private int lastVisibleItemPosition;
    private boolean isLoading;

    public NewsDataAdapter() {
    }

    public NewsDataAdapter(Context context,ArrayList<NewsRequest.ReData.Recomment> recomments) {
        mContext=context;
        mRecomments=recomments;

    }

    public NewsDataAdapter(Context context,ArrayList<NewsRequest.ReData.Recomment> recomments,RecyclerView recyclerView) {
        mContext=context;
        mRecomments=recomments;
        mRecylerView = recyclerView;
        if(mRecylerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            mRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    Log.i("jijinchao","totalItemCount:"+totalItemCount+"---------lastVisibleItemPosition:"+lastVisibleItemPosition);
                    if (!isLoading&&lastVisibleItemPosition==totalItemCount-1){
                        Log.w("jijinchao","+++++++++++++++++++");
                        if (mOnLoadMoreListener!=null){
                            mOnLoadMoreListener.loadMore(recyclerView);
                        }
                        isLoading=true;
                    }
                }
            });
        }
    }

    public void setLoaded(){
        isLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = null;
        if (viewType == ITEM_LOADMORE){
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.footer_loadmore, parent, false);
            return new LoadMoreHolder(view);
        }
        if (viewType == ITEM_NOMORE){
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.footer_nomore, parent, false);
            return new NoMoreHolder(view);
        }
        if (viewType == ITEM_ONEPIC){
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.item_onepic_card, parent, false);

        }else if (viewType == ITEM_MUTPIC){
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.item_mutpic_card, parent, false);
        }
        return new ItemHolder(view) {
            @Override
            public void initItemClick(int position) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position,viewType);
                }
            }
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsRequest.ReData.Recomment recomment = mRecomments.get(position);
        int viewType=getItemViewType(position);
        if (viewType==ITEM_ONEPIC){
            loadCommon(holder, recomment);
            Object object=((ItemHolder)holder).iv_pic.getTag();
            if(object==null||!TextUtils.equals((CharSequence) object,recomment.picUrl[0])){
                ImageLoader.loadImageAsync(((ItemHolder)holder).iv_pic, recomment.picUrl[0], DisplayImageOptionsCfg.getInstance().getOptions(R.mipmap.default_small_ugc));
                ((ItemHolder)holder).iv_pic.setTag(recomment.picUrl[0]);
            }
        }else if (viewType == ITEM_MUTPIC){
            loadCommon(holder, recomment);
            if (recomment.picUrl.length == 1) {
                ((ItemHolder)holder).iv_pic1.setVisibility(View.VISIBLE);
                ((ItemHolder)holder).iv_pic2.setVisibility(View.INVISIBLE);
                ((ItemHolder)holder).iv_pic3.setVisibility(View.INVISIBLE);

                loadPic1(recomment.picUrl[0], ((ItemHolder)holder));

            } else if (recomment.picUrl.length == 2) {
                ((ItemHolder)holder).iv_pic1.setVisibility(View.VISIBLE);
                ((ItemHolder)holder).iv_pic2.setVisibility(View.VISIBLE);
                ((ItemHolder)holder).iv_pic3.setVisibility(View.INVISIBLE);

                loadPic2(recomment, ((ItemHolder)holder));

            } else {
                ((ItemHolder)holder).iv_pic1.setVisibility(View.VISIBLE);
                ((ItemHolder)holder).iv_pic2.setVisibility(View.VISIBLE);
                ((ItemHolder)holder).iv_pic3.setVisibility(View.VISIBLE);
                loadPic3(recomment, ((ItemHolder)holder));
            }
        }

    }

    private void loadCommon(RecyclerView.ViewHolder holder, NewsRequest.ReData.Recomment recomment) {
        if (holder instanceof ItemHolder){
            ((ItemHolder)holder).tv_title.setText(recomment.title);
            if (!TextUtils.isEmpty(recomment.stick)) {
                ((ItemHolder)holder).tv_tip.setVisibility(View.VISIBLE);
                ((ItemHolder)holder).tv_tip.setText(recomment.stick);
            } else {
                ((ItemHolder)holder).tv_tip.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(recomment.sourceFrom)) {
                ((ItemHolder)holder).tv_from.setVisibility(View.VISIBLE);
                ((ItemHolder)holder).tv_from.setText(recomment.sourceFrom);
            } else {
                ((ItemHolder)holder).tv_from.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(recomment.goodNum)) {
                if (!TextUtils.isEmpty(recomment.stick)){
                    ((ItemHolder)holder).tv_num.setVisibility(View.GONE);
                }else {
                    ((ItemHolder)holder).tv_num.setVisibility(View.VISIBLE);
                    ((ItemHolder)holder).tv_num.setText(recomment.goodNum+"赞");
                }
            } else {
                ((ItemHolder)holder).tv_num.setVisibility(View.GONE);
            }
        }
    }

    /**加载三张图片*/
    private void loadPic3(NewsRequest.ReData.Recomment recomment, ItemHolder holder) {
        loadPic2(recomment, holder);
        Object object3=holder.iv_pic3.getTag();
        if(object3==null||!TextUtils.equals((CharSequence) object3,recomment.picUrl[2])){
            ImageLoader.loadImageAsync(holder.iv_pic3, recomment.picUrl[2], DisplayImageOptionsCfg.getInstance().getOptions(R.mipmap.default_small_ugc));
            holder.iv_pic3.setTag(recomment.picUrl[2]);
        }
    }

    /**加载两张图片*/
    private void loadPic2(NewsRequest.ReData.Recomment recomment, ItemHolder holder) {
        loadPic1(recomment.picUrl[0], holder);
        Object object2=holder.iv_pic2.getTag();
        if(object2==null||!TextUtils.equals((CharSequence) object2,recomment.picUrl[1])){
            ImageLoader.loadImageAsync(holder.iv_pic2, recomment.picUrl[1], DisplayImageOptionsCfg.getInstance().getOptions(R.mipmap.default_small_ugc));
            holder.iv_pic2.setTag(recomment.picUrl[1]);
        }
    }

    /**加载一张图片*/
    private void loadPic1(String b, ItemHolder holder) {
        Object object1=holder.iv_pic1.getTag();
        if(object1==null|| !TextUtils.equals((CharSequence) object1, b)){
            ImageLoader.loadImageAsync(holder.iv_pic1, b, DisplayImageOptionsCfg.getInstance().getOptions(R.mipmap.default_small_ugc));
            holder.iv_pic1.setTag(b);
        }
    }

    @Override
    public int getItemCount() {
        return mRecomments.size();
    }

    @Override
    public int getItemViewType(int position) {
        NewsRequest.ReData.Recomment recomment = mRecomments.get(position);
        if (recomment==null){
            return ITEM_LOADMORE;
        }
        int picType = recomment.picType;
        if (picType == 1) {
            return ITEM_ONEPIC;
        } else if (picType == 2) {
            return ITEM_MUTPIC;
        }else if (picType == 0){
            return ITEM_NOMORE;
        }
        return -1;
    }

    private OnItemClickListener onItemClickListener;
    public void setOnClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(int position, int viewType);
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public interface OnLoadMoreListener{
        void loadMore(RecyclerView recyclerView);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        mOnLoadMoreListener = onLoadMoreListener;
    }
}
