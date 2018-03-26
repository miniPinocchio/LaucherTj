package com.anrongtec.laucher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.AppModel;

import java.util.List;


/**
 * @author huiliu
 * @date 2017/9/30
 * @email liu594545591@126.com
 * @introduce
 */
public class MainTabAppAdapter extends RecyclerView.Adapter<MainTabAppAdapter.MainTabHolder> {
    private Context mContext;
    private List<AppModel> mModels;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    public MainTabAppAdapter(Context context, List<AppModel> models, CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener) {
        mContext = context;
        mModels = models;
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public List<AppModel> getModels() {
        return mModels;
    }

    public void setModels(List<AppModel> models) {
        mModels = models;
    }

    @Override
    public MainTabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_main_tab_app, parent, false);
        return new MainTabHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MainTabHolder holder, int position) {
        final AppModel appModel = mModels.get(position);
        holder.iv_tab_app.setImageDrawable(appModel.getLocalIcon());
        holder.tv_tab_app.setText(appModel.getAppName());
        if (appModel.isCheck()) {
            holder.cb_manager_apps.setChecked(true);
        } else {
            holder.cb_manager_apps.setChecked(false);
        }
        holder.cb_manager_apps.setTag(position);
        holder.cb_manager_apps.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    public int getItemCount() {
        return mModels.size() > 0 ? mModels.size() : 0;
    }

    class MainTabHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_main_tab_app;
        TextView tv_tab_app;
        ImageView iv_tab_app;
        CheckBox cb_manager_apps;

        public MainTabHolder(View itemView) {
            super(itemView);
            tv_tab_app = (TextView) itemView.findViewById(R.id.tv_main_tab_app);
            iv_tab_app = (ImageView) itemView.findViewById(R.id.iv_main_tab_app);
            rl_main_tab_app = (RelativeLayout) itemView.findViewById(R.id.rl_main_tab_app);
            cb_manager_apps = (CheckBox) itemView.findViewById(R.id.cb_manager_apps);
        }
    }
}
