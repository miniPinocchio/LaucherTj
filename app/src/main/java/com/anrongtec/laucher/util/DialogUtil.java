package com.anrongtec.laucher.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.util.dialog.SpotsDialog;

/**
 * 各种dialog
 *
 * @author dongtianhao
 */
public class DialogUtil {

    /***
     * 创建单选对话框
     *
     */
    public static Dialog createSingleSelectionDialog(Context context, String title, String[] datas,
                                                     DialogInterface.OnClickListener linister) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        } else {
            builder.setTitle("温馨提示");
        }
        builder.setItems(datas, linister);
        Dialog dialog = builder.create();
        builder.show();
        return dialog;
    }

    /**
     * 确定框
     */
    public static Dialog createNoticeDialog(Context context, String title, String message,
                                            DialogInterface.OnClickListener linister) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton("重试", linister);
        Dialog dialog = builder.create();
        builder.setCancelable(false);
        builder.show();
        return dialog;

    }

    /**
     * 选择框
     */
    public static Dialog createSelectDialog(Context context, String title, String message,
                                            DialogInterface.OnClickListener listener1,
                                            DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton("重试", listener1);
        builder.setTitle(title).setMessage(message).setNegativeButton("游客模式", listener);
        Dialog dialog = builder.create();
        builder.setCancelable(false);
        builder.show();
        return dialog;

    }


    /**
     * 确定框
     */
    public static Dialog createNoticeDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("解决方法").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        builder.show();
        dialog.setCancelable(true);
        return dialog;

    }


    /**
     * 提示框
     */
    public static Dialog createTipDialog(Context context, String title, String message,
                                         DialogInterface.OnClickListener linister,
                                         DialogInterface.OnClickListener onCancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton("确定", linister)
                .setNegativeButton("取消", onCancelListener);
        Dialog dialog = builder.create();
        builder.show();
        return dialog;

    }

    /**
     * 自定义布局的dialog
     *
     * @param context
     * @param title
     * @param icon
     * @param v       自定义布局
     * @param
     * @return
     */
    public static Dialog createViewDialog(Context context, String title,
                                          int icon, View v, boolean showPositiveButton, DialogInterface.OnClickListener positiveLinister,
                                          boolean showNegativeButton, DialogInterface.OnClickListener negativeLinister) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(title);
        if (icon != 0) {
            builder.setIcon(icon);
        }
        if (showNegativeButton) {
            builder.setNegativeButton(context.getString(R.string.cancel), negativeLinister);
        }
        if (showPositiveButton) {
            builder.setPositiveButton(context.getString(R.string.sure), positiveLinister);
        }
        builder.setCancelable(false);
        builder.setView(v);
        Dialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG);
        dialog.show();
        return dialog;

    }


    /**
     * 加载框
     */
    public static Dialog loadingDialog(Context context,String message) {
        AlertDialog dialog = new SpotsDialog.Builder().setContext(context).setMessage(message).build();
        dialog.show();
        return dialog;

    }

}
