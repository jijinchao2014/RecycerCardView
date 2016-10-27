package com.jijc.recyclercardview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jijc.recyclercardview.R;

/**
 * Description:
 * Created by jijc on 2016/8/29.
 * PackageName: com.jijc.recyclercardview.holder
 */
public abstract class ItemHolder extends RecyclerView.ViewHolder {
    public TextView tv_title;
    public ImageView iv_pic;
    public ImageView iv_pic1;
    public ImageView iv_pic2;
    public ImageView iv_pic3;
    public TextView tv_from;
    public TextView tv_num;
    public TextView tv_tip;
    public ItemHolder(View itemView) {
        super(itemView);
        this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        this.iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
        this.iv_pic1 = (ImageView) itemView.findViewById(R.id.iv_pic1);
        this.iv_pic2 = (ImageView) itemView.findViewById(R.id.iv_pic2);
        this.iv_pic3 = (ImageView) itemView.findViewById(R.id.iv_pic3);
        this.tv_from = (TextView) itemView.findViewById(R.id.tv_from);
        this.tv_num = (TextView) itemView.findViewById(R.id.tv_num);
        this.tv_tip = (TextView) itemView.findViewById(R.id.tv_tip);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initItemClick(getPosition());
            }
        });
    }
    public abstract void initItemClick(int position);
}
