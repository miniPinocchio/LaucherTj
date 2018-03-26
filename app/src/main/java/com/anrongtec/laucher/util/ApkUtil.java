package com.anrongtec.laucher.util;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import com.anrongtec.laucher.bean.AppModel;
import com.anrongtec.laucher.db.LiteOrmDBUtil;
import com.anrongtec.laucher.widget.CustomToast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author dongtianhao
 * @ClassName: ApkUtil
 * @Description: 工具类
 * @date 2016年7月8日 下午3:29:56
 */
public class ApkUtil extends Utils {

    private static final String appsLauncher[] = {
            "com.xdja.uaac",
            "com.nq.mdm",
            "com.xdja.mam",
            "com.xdja.safeclient",
            "com.huawei.camera",
            "com.android.gallery3d",
            "com.android.settings",
            "com.huawei.hidisk",
            "com.android.browser",
            "com.android.soundrecorder",
            "com.android.contacts",
            "com.android.mms",
            "com.huawei.systemmanager",
            "com.example.android.notepad",
            "com.autonavi.minimap",
            "com.sina.weibo",
            "com.android.deskclock",
            "com.android.hwmirror",
            "cn.wps.moffice_eng",
            //中兴
            "zte.com.cn.filer",
            "com.zte.camera",
            //新虹伟
            "com.mediatek.filemanager"

    };

    /**
     * 获取非系统应用
     *
     * @return
     */
    public static List<AppModel> getAppsFromSystem(Context context) {

        List<AppModel> apps = new ArrayList<>();
        AppModel tempApp = null;

        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 是否是系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    && !packageInfo.packageName.equals("com.anrongtec.laucher")
                    && !packageInfo.packageName.equals("com.ansec.kernel")
                    ) {
                LogUtil.d(packageInfo.packageName + "\n");
                // 符合包名,且不为应用包名
                tempApp = new AppModel();
                tempApp.setAppName(
                        packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                tempApp.setLocalIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
                tempApp.setLocalVersionName(packageInfo.versionName);
                tempApp.setLocalVersionCode(packageInfo.versionCode);
                tempApp.setAppPackName(packageInfo.packageName);
                tempApp.setSize(new File(packageInfo.applicationInfo.sourceDir).length());
                tempApp.setFirstInstallTime(packageInfo.firstInstallTime);
                tempApp.setLastUpdateTime(packageInfo.lastUpdateTime);
                tempApp.setSystem(false);
                apps.add(tempApp);// 如果非系统应用，则添加至appList
            }
        }
        return apps;
    }


    /**
     * 获取系统应用
     *
     * @return
     */
    public static List<AppModel> getAppsFromSystems(Context context) {

        List<AppModel> apps = new ArrayList<>();
        AppModel tempApp = null;

        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < appsLauncher.length; i++) {
            for (PackageInfo packageInfo : packages) {
                // 是否是系统应用
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    if (appsLauncher[i].equals(packageInfo.packageName)) {
                        // 符合包名,且不为应用包名
                        LogUtil.d("系统应用：" + packageInfo.packageName);
                        tempApp = new AppModel();
                        tempApp.setAppName(
                                packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                        tempApp.setLocalIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
                        tempApp.setLocalVersionName(packageInfo.versionName);
                        tempApp.setLocalVersionCode(packageInfo.versionCode);
                        tempApp.setAppPackName(packageInfo.packageName);
                        tempApp.setSize(new File(packageInfo.applicationInfo.sourceDir).length());
                        tempApp.setFirstInstallTime(packageInfo.firstInstallTime);
                        tempApp.setLastUpdateTime(packageInfo.lastUpdateTime);
                        tempApp.setSystem(true);
                        apps.add(tempApp);
                    }
                    // 如果系统应用，则添加至appList
                }
            }
        }
        return apps;
    }

    /**
     * 获取数据库应用
     *
     * @return
     */
    public static List<AppModel> getAppsFromLiteOrm(Context context, boolean isSys) {
        List<AppModel> apps = new ArrayList<>();
        List<AppModel> isSystem = LiteOrmDBUtil.getQueryByWhere(AppModel.class, "isSystem", new Object[]{isSys});
        PackageManager packageManager = context.getPackageManager();
        if (isSystem != null && isSystem.size() > 0) {
            for (AppModel appModel : isSystem) {
                try {
                    Drawable drawable = packageManager.getApplicationInfo(appModel.getAppPackName(), 0).loadIcon(packageManager);
                    appModel.setLocalIcon(drawable);
                } catch (NameNotFoundException e) {
                    LogUtil.d("异常" + e.toString());
                    e.printStackTrace();
                }
                // 如果非系统应用，则添加至appList
                apps.add(appModel);
            }
        }
        return apps;
    }

    /**
     * 打开指定包名的app
     *
     * @param context
     * @param packageName
     */
    @SuppressLint("NewApi")
    public static void openAPP(Context context, String packageName) {
        // PackageManager manager = context.getPackageManager();
        // Intent app = manager.getLaunchIntentForPackage(packageName);
        // context.startActivity(app);
        openPackage(context.getApplicationContext(), packageName);
    }

    public static void startActivity(Context context, String packageName) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);

        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        }
    }


    /**
     * 获取非系统应用信息列表
     *
     * @param context
     * @return
     */
    public static ArrayList<String> getAppsFromPhone(Context context) {
        if (context == null) {
            return new ArrayList<>();
        }
        PackageManager pm = context.getPackageManager();
        ArrayList<String> appList = new ArrayList<>();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
//              // 获取该应用安装包的Intent，用于启动该应用
//              info.appIntent = pm.getLaunchIntentForPackage(packageInfo.packageName);
                appList.add(packageInfo.packageName);
            }
        }
        return appList;
    }

    /**
     * 打开app
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean openPackage(Context context, String packageName) {
        Context pkgContext = getPackageContext(context.getApplicationContext(), packageName);
        Intent intent = getAppOpenIntentByPackageName(context, packageName);
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent);
            return true;
        }
        return false;
    }


    /**
     * 判断手机是否已经安装了,指定包名和版本号的应用
     *
     * @return
     */
    public static boolean isInstalled(Context context, String pkg, int versionCode) {
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(pkg, 0);
            if (isAvailable(context, pkg) && packageInfo.versionCode == versionCode) {
                return true;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 检查手机上是否安装了指定包名的软件
     */
    public static boolean isAvailable(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    //

    private static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        // MainActivity完整名
        String mainAct = null;
        // 根据包名寻找MainActivity
        PackageManager pkgMag = context.getApplicationContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant") List<ResolveInfo> list = pkgMag.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }


    private static Context getPackageContext(Context context, String packageName) {
        Context pkgContext = null;
        if (context.getApplicationContext().getPackageName().equals(packageName)) {
            pkgContext = context.getApplicationContext();
        } else {
            // 创建第三方应用的上下文环境
            try {
                pkgContext = context.createPackageContext(packageName,
                        Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pkgContext;
    }

    /**
     * 获取应用信息
     *
     * @param context
     * @param pkg
     * @return
     */
    public static AppModel getAppInfoByPkg(Context context, String pkg) {
        AppModel app = new AppModel();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkg, 0);
            app.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            app.setLocalIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));// 图标
            app.setLocalVersionName(packageInfo.versionName);// 本地版本名
            app.setLocalVersionCode(packageInfo.versionCode);// 本地版本号
            app.setAppPackName(packageInfo.packageName);// 包名
            app.setSize(new File(packageInfo.applicationInfo.sourceDir).length());// 本地大小
            app.setFirstInstallTime(packageInfo.firstInstallTime);// 第一次安装时间
            app.setLastUpdateTime(packageInfo.lastUpdateTime);// 最后一次升级时间
            return app;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return app;
    }

    /**
     * 获取当前的UTC时间
     *
     * @return
     */
    public static String getCurrentUTCTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    /**
     * date转为UTC date
     *
     * @param date
     * @return
     */
    public static Date trans2UTC(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.setTime(date);

        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

    /**
     * 安装一个apk文件
     */
    public static void install(Context context, File uriFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        // ToastShow.shortMessage("install");
    }

    /**
     * 静默安装. 失败则用普通安装
     * <p>
     * 中科创达设备才能用静默.
     */
    public static void installSilent(Context context, File uriFile) {
        // Uri uri = Uri.parse("file://" + uriFile.getPath() + "/" +
        // uriFile.getName());
        try {
            Uri uri = Uri.fromFile(uriFile);
            CustomToast.INSTANCE.showToast(context, "开始安装: " + uriFile.getName());
            Intent installintent = new Intent("android.intent.action.SECURE_INSTALL_PACKAGE");
            installintent.addCategory(Intent.CATEGORY_DEFAULT);
            installintent.setData(uri);
            installintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(installintent);
        } catch (Exception e) {
            e.printStackTrace();
            install(context, uriFile);
        }
    }


}
