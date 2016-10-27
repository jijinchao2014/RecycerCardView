package com.jijc.recyclercardview.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jijc.recyclercardview.R;
import com.jijc.recyclercardview.activity.BbtreeActivity;

import java.util.ArrayList;

/**
 * Description:
 * Created by jijc on 2016/9/2.
 * PackageName: com.jijc.recyclercardview.view
 */
public class OptionsPopWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;
    private View contentView;
    private ListView lvOptions;
    private ArrayList<String> options;
    private final OptionsAdapter adapter;
    private RelativeLayout rootView;
    private LinearLayout ll_item_root;

    public OptionsPopWindow(Context context, ArrayList<String> options){
        mContext=context;
        this.options=options;
        contentView= LayoutInflater.from(mContext).inflate(R.layout.layout_options_popup,null);
        lvOptions = (ListView) contentView.findViewById(R.id.lv_options);
        rootView = (RelativeLayout) contentView.findViewById(R.id.rl_popup_root);
        ll_item_root = (LinearLayout) contentView.findViewById(R.id.ll_item_root);
        rootView.setOnClickListener(this);

        adapter = new OptionsAdapter();
        lvOptions.setAdapter(adapter);


        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        //ColorDrawable dw = new ColorDrawable(0xffeeeeee);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.take_photo_anim);

        lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onPopupItemClick!=null){
                    onPopupItemClick.onPopupItemClick(parent,view,position,id);
                }
                dismiss();
            }
        });
    }

//    public void dismiss(){
//        if (this.isShowing()){
//            dismiss();
//        }
//    }
    private OnPopupItemClick onPopupItemClick;
    public void setOnPopupItemClick(OnPopupItemClick onPopupItemClick){
        this.onPopupItemClick = onPopupItemClick;
    }
    public interface OnPopupItemClick{
        void onPopupItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing() /*&& ListUtils.getSize(productList)>0*/) {

            //动态设置选项的高度
            resetPopupHeight();

            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            this.showAsDropDown(parent);

        } else {
            this.dismiss();
        }
    }

    /**
     * 动态设置Popupwindow的高度
     */
    private void resetPopupHeight() {
        View listItem = adapter.getView(0, null, lvOptions);
        listItem.measure(0, 0);
        int itemHeight=listItem.getMeasuredHeight();
        int totalHeight = 0;
        //当条目超过5时显示5个条目的高度，不足5个则有几个显示几个的高度
        if(adapter.getCount()<=5){
            totalHeight=itemHeight*adapter.getCount();
        }else{
            totalHeight=itemHeight*5;
        }
        DisplayMetrics ds = new DisplayMetrics();
        ((BbtreeActivity)mContext).getWindowManager().getDefaultDisplay().getMetrics(ds);

        ll_item_root.getLayoutParams().height=totalHeight;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        dismiss();
    }

    class OptionsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return options.size();
        }


        @Override
        public Object getItem(int position) {
            return options.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView = View.inflate(mContext, R.layout.item_option, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            String opt = options.get(position);
            if (position==1){
                holder.tvOption.setTextColor(mContext.getResources().getColor(R.color.color_92c659));
            }else {
                holder.tvOption.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            }
            holder.tvOption.setText(opt);
            return convertView;
        }
    }

    class ViewHolder{
        private View itemView;
        private TextView tvOption;
        public ViewHolder(View itemView){
            this.itemView=itemView;
            tvOption = (TextView) itemView.findViewById(R.id.tv_option);
        }
    }
}
