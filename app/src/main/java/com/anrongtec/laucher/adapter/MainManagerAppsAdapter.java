package com.anrongtec.laucher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.AppModel;
import com.anrongtec.laucher.util.ApkUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.widget.CustomToast;

import java.util.List;

/**
 * @author huiliu
 * @date 2017/12/7
 * @email liu594545591@126.com
 * @introduce
 */
public class MainManagerAppsAdapter extends RecyclerView.Adapter<MainManagerAppsAdapter.MainManagerAppsHolder> {
    private final int APP_NAME_LENGTH = 4;
    private Context mContext;
    private List<AppModel> mModels;

    public MainManagerAppsAdapter(Context context, List<AppModel> models) {
        mContext = context;
        mModels = models;
    }

    @Override
    public MainManagerAppsAdapter.MainManagerAppsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_main_manager_app, parent, false);
        return new MainManagerAppsAdapter.MainManagerAppsHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MainManagerAppsAdapter.MainManagerAppsHolder holder, int position) {
        final AppModel appModel = mModels.get(position);

        holder.iv_tab_app.setImageDrawable(appModel.getLocalIcon());
        /**
         * 保留4个字 名字
         */
        String appName = appModel.getAppName();
        String substring = null;
        StringBuilder buffer = new StringBuilder();
        if (appName.length() > APP_NAME_LENGTH) {
            substring = appName.substring(0, 4);
            buffer.append(substring).append("...");
            holder.tv_tab_app.setText(buffer.toString());
        }else {
            holder.tv_tab_app.setText(appName);
        }
        holder.rl_main_tab_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApkUtil.isAvailable(mContext, appModel.getAppPackName())) {
                    ApkUtil.openAPP(mContext, appModel.getAppPackName());
                    LogUtil.d(appModel.getAppPackName());
                } else {
                    CustomToast.INSTANCE.showToast(mContext, "未发现该应用，请去应用仓库下载");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModels.size() > 0 ? mModels.size() : 0;
    }

    class MainManagerAppsHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_main_tab_app;
        TextView tv_tab_app;
        ImageView iv_tab_app;

        public MainManagerAppsHolder(View itemView) {
            super(itemView);
            tv_tab_app = (TextView) itemView.findViewById(R.id.tv_main_manager_app);
            iv_tab_app = (ImageView) itemView.findViewById(R.id.iv_main_manager_app);
            rl_main_tab_app = (RelativeLayout) itemView.findViewById(R.id.rl_main_manager_app);
        }
    }


}
