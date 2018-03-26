package com.anrongtec.laucher.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.focusperson.CooperPersonInfos;
import com.anrongtec.laucher.bean.focusperson.EKongPersonInfos;
import com.anrongtec.laucher.bean.message.AdmComparingInfo;
import com.anrongtec.laucher.util.BitmapUtil;
import com.anrongtec.laucher.widget.CustomToast;
import com.anrongtec.laucher.widget.SelectPopwindow;
import com.anrongtec.laucher.widget.ZoomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author huiliu
 */
public class ZoomImageActivity extends ToolBarActivity {

    @BindView(R.id.zi_photo)
    ZoomImageView mZiPhoto;
    @BindView(R.id.btn_show_pop)
    Button mBtnShowPop;
    private SelectPopwindow menuWindow;
    private String mRyzp;
    private AdmComparingInfo mMessage;
    private CooperPersonInfos mCooperMessage;
    private EKongPersonInfos mEKongMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        ButterKnife.bind(this);
        setRightImgGone(false);
        int flags = getIntent().getFlags();
        switch (flags){
            case 1:
                mMessage = getIntent().getParcelableExtra("data");
                if (mMessage != null) {
                    if (mMessage.getXp() != null) {
                        if (BitmapUtil.stringtoBitmap(mMessage.getXp()) != null) {
                            mZiPhoto.setImageBitmap(BitmapUtil.stringtoBitmap(mMessage.getXp()));
                        }
                    }
                }
                break;
            case 2:
                mEKongMessage = getIntent().getParcelableExtra("data");
                if (mEKongMessage != null) {
                    if (mEKongMessage.getXp() != null) {
                        if (BitmapUtil.stringtoBitmap(mEKongMessage.getXp()) != null) {
                            mZiPhoto.setImageBitmap(BitmapUtil.stringtoBitmap(mEKongMessage.getXp()));
                        }
                    }
                }
                break;
            case 3:
                mCooperMessage = getIntent().getParcelableExtra("data");
                if (mCooperMessage != null) {
                    if (mCooperMessage.getXp() != null) {
                        if (BitmapUtil.stringtoBitmap(mCooperMessage.getXp()) != null) {
                            mZiPhoto.setImageBitmap(BitmapUtil.stringtoBitmap(mCooperMessage.getXp()));
                        }
                    }
                }

                default:
                    break;
        }

    }

    /**
     * 为弹出窗口实现监听类
     */
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 实有人口查询
                case R.id.tv_use_reality_people_demand:
                    CustomToast.INSTANCE.showToast(getApplication(), "保存成功");
                    break;
                // 暂住人口查询
                case R.id.tv_use_stay_people_demand:
                    CustomToast.INSTANCE.showToast(getApplication(), "开发中。。。");
                    break;
                default:
                    break;
            }
        }
    };


    @OnClick({R.id.btn_show_pop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_show_pop:
                // 实例化SelectPicPopupWindow
                menuWindow = new SelectPopwindow(ZoomImageActivity.this, itemsOnClick);
                // 显示窗口
                menuWindow.showAtLocation(mBtnShowPop,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                // 设置layout在PopupWindow中显示的位置
                break;
            default:
                break;
        }
    }
}
