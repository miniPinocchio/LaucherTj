package com.anrongtec.laucher.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anrongtec.laucher.R;


/**
 *
 * @author huiliu
 * @date 2017/10/11
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class SelectPopwindow extends PopupWindow{
    private View mMenuView;
    public SelectPopwindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.select_popupwindows,null);


        TextView tv_use_reality_people_demand = (TextView) mMenuView.findViewById(R.id.tv_use_reality_people_demand);
        TextView tv_use_stay_people_demand = (TextView) mMenuView.findViewById(R.id.tv_use_stay_people_demand);

        TextView tv_cancel = (TextView) mMenuView.findViewById(R.id.tv_cancel);
        //取消按钮
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        tv_use_reality_people_demand.setOnClickListener(itemsOnClick);  //实有人口查询
        tv_use_stay_people_demand.setOnClickListener(itemsOnClick);  //暂住人口查询
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}

