package com.jijc.recyclercardview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jijc.recyclercardview.R;

/**
 * Description:
 * Created by jijc on 2016/8/31.
 * PackageName: com.jijc.recyclercardview.holder
 */
public class LoadMoreHolder extends RecyclerView.ViewHolder {

    public LinearLayout llFooter;
    public TextView tvLoadMore;
    public ProgressBar progressBar;
    public LoadMoreHolder(View itemView) {
        super(itemView);
        this.llFooter = (LinearLayout) itemView.findViewById(R.id.ll_footer);
        this.tvLoadMore = (TextView) itemView.findViewById(R.id.tv_loadmore);
        this.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}
