package com.anrongtec.laucher.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anrongtec.laucher.R;

/**
 * @author huiliu
 * @date 2017/11/21
 * @email liu594545591@126.com
 * @introduce
 */
public class PopSearchInfo extends PopupWindow {
    private View mMenuView;

    public PopSearchInfo(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = context
                .getLayoutInflater();
        mMenuView = inflater.inflate(R.layout.select_popupwindow, null);


        TextView tv_search = (TextView) mMenuView.findViewById(R.id.tv_search);
        TextView tv_patrol = (TextView) mMenuView.findViewById(R.id.tv_patrol);
        tv_search.setOnClickListener(itemsOnClick);
        tv_patrol.setOnClickListener(itemsOnClick);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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
            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void showPopupWindow(View parent) {
        if (!isShowing()) {
            showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            dismiss();
        }
    }

}
