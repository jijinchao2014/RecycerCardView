package com.jijc.recyclercardview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.jijc.recyclercardview.R;

/**
 * Description:
 * Created by jijc on 2016/8/31.
 * PackageName: com.jijc.recyclercardview.holder
 */
public class NoMoreHolder extends RecyclerView.ViewHolder {

    private LinearLayout llFooter;
    public NoMoreHolder(View itemView) {
        super(itemView);
        this.llFooter = (LinearLayout) itemView.findViewById(R.id.ll_footer);
    }
}
