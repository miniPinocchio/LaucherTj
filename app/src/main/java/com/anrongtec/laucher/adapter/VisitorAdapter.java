package com.anrongtec.laucher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.AppModel;

import java.util.List;

/**
 * Created by huiliu on 2018/3/25.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorHolder> {
    private Context mContext;
    private List<AppModel> mModels;
    private View.OnClickListener mListener;

    public VisitorAdapter(Context context, List<AppModel> models,View.OnClickListener listener) {
        mContext = context;
        mModels = models;
        this.mListener = listener;
    }

    @Override
    public VisitorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_main_tab_app, parent, false);
        return new VisitorHolder(inflate);
    }

    @Override
    public void onBindViewHolder(VisitorHolder holder, int position) {
        final AppModel appModel = mModels.get(position);
        holder.iv_tab_app.setImageDrawable(appModel.getLocalIcon());
        holder.tv_tab_app.setText(appModel.getAppName());
        holder.cb_manager_apps.setVisibility(View.GONE);
        holder.rl_main_tab_app.setOnClickListener(mListener);
        holder.rl_main_tab_app.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mModels.size() > 0 ? mModels.size() : 0;
    }

    class VisitorHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_main_tab_app;
        TextView tv_tab_app;
        ImageView iv_tab_app;
        CheckBox cb_manager_apps;

        public VisitorHolder(View itemView) {
            super(itemView);
            tv_tab_app = (TextView) itemView.findViewById(R.id.tv_main_tab_app);
            iv_tab_app = (ImageView) itemView.findViewById(R.id.iv_main_tab_app);
            rl_main_tab_app = (RelativeLayout) itemView.findViewById(R.id.rl_main_tab_app);
            cb_manager_apps = (CheckBox) itemView.findViewById(R.id.cb_manager_apps);
        }
    }
}

