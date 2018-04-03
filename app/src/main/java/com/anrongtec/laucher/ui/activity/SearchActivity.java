package com.anrongtec.laucher.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.sos.SosBean;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.ApkUtil;
import com.anrongtec.laucher.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author huiliu
 */
public class SearchActivity extends ToolBarActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_main_search)
    EditText mTvMainSearch;
    @BindView(R.id.tv_easy_search)
    TextView mTvEasySearch;
    @BindView(R.id.tv_patrol_search)
    TextView mTvPatrolSearch;
    private int mType;
    private String mInfo;
    private String mString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setRightImgGone(false);
        initView();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mTvEasySearch.setOnClickListener(this);
        mTvPatrolSearch.setOnClickListener(this);
    }

    private void initView() {
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        mToolbarTitle.setText("搜索");
    }

    @Override
    public void onClick(View v) {
        mString = mTvMainSearch.getText().toString();
        switch (v.getId()) {
            case R.id.tv_search:
                if (mType == 1) {
                    toZhiYu(mInfo, 0);
                }
                break;
            case R.id.tv_patrol:

                break;
            case R.id.tv_easy_search:
                if (StringUtil.is18ByteIdCard(mString)) {
                    toZhiYu(mString, 0);
                } else if (StringUtil.isCarNumberNO(mString)) {
                    toZhiYu(mString, 1);
                } else {
                    showToastMessage("请检查身份证或者车牌输入是否错误");
                }
                break;
            case R.id.tv_patrol_search:
                if (StringUtil.is18ByteIdCard(mString)) {
                    toDragon(mString, "0");
                } else if (StringUtil.isCarNumberNO(mString)) {
                    toDragon(mString, "1");
                } else {
                    showToastMessage("请检查身份证或者车牌输入是否错误");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 跳转智羽
     *
     * @param index 0是人 1是车
     */
    private void toZhiYu(String id, int index) {
        boolean available = ApkUtil.isAvailable(this, "st.kxb.mosty.xlpc");
        if (available) {
                SosNewBean userInfo = UserService.getNewUserInfo(this);
                String userInfoName = userInfo.getName();
                String code = userInfo.getCode();
                Intent mIntent = new Intent();
                mIntent.putExtra("SYSTEM_USER_ID", code);//当前登录民警的警号
                mIntent.putExtra("SYSTEM_USER_NAME", userInfoName);//当前登录民警的姓名
                mIntent.putExtra("YWID", "2"); //你的业务ID
                mIntent.putExtra("YWKZZD1", ""); //你的业务扩展字段1
                mIntent.putExtra("YWKZZD2", ""); //你的业务扩展字段2
                mIntent.putExtra("YWKZZD3", ""); //你的业务扩展字段3
                mIntent.putExtra("YWKZZD4", ""); //你的业务扩展字段4
                mIntent.putExtra("YWKZZD5", ""); //你的业务扩展字段5
                mIntent.setAction("com.mosty.ydjw.xlpc.person.VIEW");
            if (index == 0) {
                mIntent.putExtra("SFZH", id);//(被核查人员)身份证
                mIntent.setAction("com.mosty.ydjw.xlpc.person.VIEW");
                startActivityForResult(mIntent, 10001);
            } else if (index == 1) {
                mIntent.putExtra("HPHM", id); //车牌号
                mIntent.putExtra("HPZL", "02"); //车牌种类
                mIntent.setAction("com.mosty.ydjw.xlpc.car.VIEW");
                startActivityForResult(mIntent, 10000);
            }

        } else {
            showToastMessage("请去应用仓库下载巡逻盘查应用");
        }
    }

    /**
     * 跳转巨龙
     *
     * @param id
     */
    private void toDragon(String id, String index) {
        boolean available = ApkUtil.isAvailable(this, "com.dragonsoft.nana.esou");
        if (available) {
            SosBean userInfo = UserService.getUserInfo(this);
            String identifier = userInfo != null ? userInfo.getIdentifier() : "";
            String name = userInfo != null ? userInfo.getName() : "";
            String code = userInfo != null ? userInfo.getCode() : "";
            String deptCode = userInfo != null ? userInfo.getDepcode() : "";
            try {
                Uri uri = Uri.parse("nana://");
                Intent intent = new Intent("com.dragon.nana.esou.share", uri);
                intent.putExtra("flag", "2");
                //数据类型0，人员 1车辆
                intent.putExtra("index", index);
                //查询内容
                intent.putExtra("cond", id);
                //登录人姓名
                intent.putExtra("userName", name);
                //登录人身份证号
                intent.putExtra("userSfzh", identifier);
                //登录人单位编号
                intent.putExtra("userDwbh", deptCode);
                startActivity(intent);
            } catch (Exception e) {
                showToastMessage("没有安装e搜");
            }
        } else {
            showToastMessage("请去应用仓库下载天地e搜应用");
        }

    }

    @OnClick({R.id.tv_easy_search, R.id.tv_patrol_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_easy_search:
                break;
            case R.id.tv_patrol_search:
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (10000 == requestCode || 10001 == requestCode) {
            //调用成功。。

        }
    }
}
