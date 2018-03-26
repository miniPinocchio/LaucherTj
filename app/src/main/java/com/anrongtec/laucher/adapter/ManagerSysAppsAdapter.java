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
import com.anrongtec.laucher.widget.CustomToast;

import java.util.List;

import static com.anrongtec.laucher.TjApp.getContext;

/**
 * @author huiliu
 * @date 2017/11/29
 * @email liu594545591@126.com
 * @introduce
 */
public class ManagerSysAppsAdapter extends RecyclerView.Adapter<ManagerSysAppsAdapter.ManagerSysViewHolder> {

    private Context mContext;
    private List<AppModel> mAppModels;
    private View.OnClickListener mOnClickListener;

    public ManagerSysAppsAdapter(Context context, List<AppModel> appModels, View.OnClickListener onClickListener) {
        mContext = context;
        mAppModels = appModels;
        mOnClickListener = onClickListener;
    }

    @Override
    public ManagerSysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_main_tab_apps, parent, false);
        return new ManagerSysViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ManagerSysViewHolder holder, int position) {
        final AppModel appModel = mAppModels.get(position);

        holder.iv_tab_app.setImageDrawable(appModel.getLocalIcon());
        holder.tv_tab_app.setText(appModel.getAppName());
        holder.rl_tab_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApkUtil.isInstalled(mContext, appModel.getAppPackName(), appModel.getLocalVersionCode())) {
                    ApkUtil.openAPP(getContext(), appModel.getAppPackName());
                } else {
                    CustomToast.INSTANCE.showToast(mContext, "未发现该应用，请下载");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppModels.size() > 0 ? mAppModels.size() : 0;
    }

    class ManagerSysViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_tab_app;
        TextView tv_tab_app;
        ImageView iv_tab_app;

        public ManagerSysViewHolder(View itemView) {
            super(itemView);
            tv_tab_app = (TextView) itemView.findViewById(R.id.tv_main_tab_app);
            iv_tab_app = (ImageView) itemView.findViewById(R.id.iv_main_tab_app);
            rl_tab_app = (RelativeLayout) itemView.findViewById(R.id.rl_main_tab_apps);
        }
    }
}
