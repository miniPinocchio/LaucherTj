package com.anrongtec.laucher.manager;

import android.content.Context;

import com.anrongtec.laucher.bean.AppModel;
import com.lzy.okhttpserver.download.DownloadInfo;
import com.lzy.okhttpserver.download.DownloadManager;
import com.lzy.okhttpserver.download.DownloadService;
import com.lzy.okhttpserver.download.db.DownloadDBManager;

import java.util.List;


/**
 * 下载管理类
 * 
 * 其实管理的是okhttp的下载管理类
 * 
 * @author cxy
 *
 */
public class APPDownloadManager {
	
	/**
	 * 获取downloadManager对象
	 * @return
	 */
	public static DownloadManager getManager(){
		return DownloadService.getDownloadManager();
	}
	
	


	/**
	 * 发送一个 显示正在下载数量的广播
	 * @param context
	 */
	public static void sendShowDownloadBroadcost(Context context) {
//		Intent intent = new Intent();
//		intent.setAction(Constant.Action.APP_DOWNLOAD);
////		intent.putExtra(Constant.DATA, getDownloadTaskCount());
//		context.sendBroadcast(intent);
	}
	
	/**
	 * 添加下载任务, 监听是管理类自定义的监听(用于统一管理下载, 因为第三方的监听无法直接管理)
	 * @param context
	 * @param app
	 * @param listener	
	 */
	public static void addTaskNew(final Context context, AppModel app, final AppDownloadListener listener){
//		GetRequest request = new GetRequest(Util.getDownloadUrl(app.getUrl()));
//		request.params(DDApp.dApp.getBaseInfo());
//		getManager().addTask(app.getAppName(), app.getUrl(), request, new DownloadListener() {
//			@Override
//			public void onProgress(DownloadInfo downloadInfo) {
//				listener.progress(downloadInfo);
//			}
//			@Override
//			public void onFinish(DownloadInfo downloadInfo) {
//				//下载完成后提示安装
//				if (FileUtils.isExist(downloadInfo.getTargetPath())) {
//					ApkUtil.install(context, new File(downloadInfo.getTargetPath()));
//				}
//				listener.finish(downloadInfo);
//			}
//			@Override
//			public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
//				listener.error(downloadInfo, errorMsg, e);
//			}
//		});
	}
	
	public interface AppDownloadListener {
		void progress(DownloadInfo downloadInfo);
		void finish(DownloadInfo downloadInfo);
		void error(DownloadInfo downloadInfo, String errorMsg, Exception e);
	}
	
	/**
	 * 移除任务
	 */
	public static void removeTask(String taskKey){
		getManager().removeTask(taskKey);
//		sendShowDownloadBroadcost(DDApp.dApp.getApplicationContext());
	}
	
	
	public static DownloadInfo getDownloadInfo(String taskKey){
		return DownloadDBManager.INSTANCE.get(taskKey);
	}
	
	/**
	 * 获取未下载完成的任务数量(即 状态不是finish)
	 */
	public static int getDownloadTaskCount(){
		List<DownloadInfo> allTask = APPDownloadManager.getManager().getAllTask();
		int i = 0;
		for (DownloadInfo downloadInfo : allTask) {
			// && downloadInfo.getState() != DownloadManager.ERROR
			if (downloadInfo.getState() != DownloadManager.FINISH) {
				i++;
			}
		}
		return i;
	}
}
