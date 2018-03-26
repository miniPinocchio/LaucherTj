package com.anrongtec.laucher.manager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.bean.AppModel;
import com.anrongtec.laucher.util.ApkUtil;
import com.anrongtec.laucher.util.DialogUtil;
import com.anrongtec.laucher.util.FileUtils;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.util.Utils;
import com.anrongtec.laucher.widget.CustomToast;
import com.lzy.okhttpserver.download.DownloadInfo;
import com.lzy.okhttpserver.download.DownloadManager;
import com.lzy.okhttpserver.download.DownloadService;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 商店  检测更新管理类
 *
 * @author cxy
 */
public class CheckNewVersionManager {

    private ProgressBar bar;
    private TextView tv_size, tv_total;
    private Context mContext;

    private static CheckNewVersionManager instance;

    public static synchronized CheckNewVersionManager getInstance() {
        if (instance == null) {
            instance = new CheckNewVersionManager();
        }
        return instance;
    }


    /**
     * 检查更新
     */
    public void checkVersion(final Context context, final boolean isShowToast, Callback<String> callback) {
        int json =Utils.getVersionCode(context);
        LogUtil.e("检查更新" + json);
        TjApp.getRetrofit().getUpdate("").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    AppModel appModel = new AppModel();
                    checkDownload(appModel,context);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CustomToast.INSTANCE.showToast(context,"更新失败");
            }
        });
    }


    /**
     * 检测是否需要下载(已经下载完成的话则直接提示安装)
     *
     * @param app
     * @param context
     */
    private void checkDownload(AppModel app, Context context) {
        //app.getUrl()
        DownloadInfo info = DownloadService.getDownloadManager().getDownloadInfo("");
        if (info == null || info.getState() != DownloadManager.FINISH) {
            download(app, context);
        } else {
            if (FileUtils.isExist(info.getTargetPath())) {
                ApkUtil.install(context, new File(info.getTargetPath()));
            } else {
                download(app, context);
            }
        }
    }


    /**
     * 下载 , 并显示dialog进度框
     *
     * @param app
     * @param context
     */
    private void download(AppModel app, Context context) {

        View v = LayoutInflater.from(context).inflate(R.layout.dialog_downloading_view, null);
        bar = (ProgressBar) v.findViewById(R.id.progress_dialog);
        tv_size = (TextView) v.findViewById(R.id.tv_dialog_current_size);
        tv_total = (TextView) v.findViewById(R.id.tv_dialog_total_size);
        tv_total.setText(Utils.byte2Mega(app.getSize()));

        final Dialog dialog = DialogUtil.createViewDialog(context, "下载更新", 0, v, false, null, true, null);

        APPDownloadManager.addTaskNew(context, app, new APPDownloadManager.AppDownloadListener() {
            @Override
            public void progress(DownloadInfo downloadInfo) {
                tv_size.setText(Utils.byte2Mega(downloadInfo.getDownloadLength()));
                bar.setProgress((int) (downloadInfo.getProgress() * 100));
            }

            @Override
            public void finish(DownloadInfo downloadInfo) {
                dialog.cancel();
            }

            @Override
            public void error(DownloadInfo downloadInfo, String errorMsg, Exception e) {

            }
        });
    }


    /**
     * 转换为appmodel对象
     *
     * @param data
     * @return
     */
//    private AppModel UpgradeToAppModel(Upgrade data, Context context) {
//        AppModel app = new AppModel();
//        app.setAppName("警务商店");
//        app.setAppPackName(Util.getPackageName(context));
//        app.setVersionDesc(data.getVersionDesc());
//        app.setRemoteVersionCode(data.getExpVersionCode());
//        app.setRemoteVersionName(data.getExpVersion());
//        app.setSize(data.getExpSize());
//        app.setUrl(data.getExpFileId());
//        return app;
//    }
}
