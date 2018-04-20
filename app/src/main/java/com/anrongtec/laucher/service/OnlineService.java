package com.anrongtec.laucher.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.message.TodoEkongMessage;
import com.anrongtec.laucher.bean.message.TodoMessage;
import com.anrongtec.laucher.db.LiteOrmDBUtil;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.ui.activity.EkongFocusActivity;
import com.anrongtec.laucher.ui.activity.MessageActivity;
import com.anrongtec.laucher.ui.activity.TodoActivity;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.util.NetUtil;

import org.ddpush.im.util.StringUtil;
import org.ddpush.im.v1.client.appuser.Message;
import org.ddpush.im.v1.client.appuser.TCPClientBase;

import java.io.IOException;

/**
 * @author huiliu
 */
@SuppressLint("Registered")
public class OnlineService extends Service {
    protected PendingIntent tickPendIntent;
    private Intent intent;
    //    private PowerManager.WakeLock wakeLock;
    private MyTcpClient myTcpClient;
    private AlarmManager alarmMgr;
    private Notification n;
    private int IDENTIFY_LENGTH = 18;

    public class MyTcpClient extends TCPClientBase {

        private int MESSAGE_TYPE;

        public MyTcpClient(byte[] uuid, int appid, String serverAddr,
                           int serverPort) throws Exception {
            super(uuid, appid, serverAddr, serverPort, 10);
        }

        @Override
        public boolean hasNetworkConnection() {
            return NetUtil.isNetworkAvailable(OnlineService.this);
        }

        @Override
        public void trySystemSleep() {
            tryReleaseWakeLock();
        }

        @Override
        public void onPushMessage(Message message) {
            if (message == null) {
                return;
            }
            if (message.getData() == null || message.getData().length == 0) {
                return;
            }
            // 0x20 自定义推送信息
            MESSAGE_TYPE = 32;
            if (message.getCmd() == MESSAGE_TYPE) {
                String str = null;
                try {
                    str = new String(message.getData(), 5,
                            message.getContentLength(), "UTF-8");
                    TodoMessage message1 = GsonUtil.parseJsonWithGson(str, TodoMessage.class);
                    int type = message1.getType();
                    LocalBroadcastManager manager = null;
                    String title = "";
                     switch (type) {
                        case 96:
                            title = "警辅核采";
                            ring(99);
                            TodoEkongMessage message2 = new TodoEkongMessage();
                            message2.setId(message1.getId());
                            message2.setType(96);
                            long insert96 = LiteOrmDBUtil.insert(message2);
                            notifyEkongUser(Integer.parseInt(message1.getId()), title, message1.getTitle(), message1);
                            intent = new Intent();
                            intent.setAction(Constant.NOTICE_EKONG_ACTION);
                            manager = LocalBroadcastManager.getInstance(OnlineService.this);
                            manager.sendBroadcast(intent);
                            break;
                        case 97:
                            title = "天地e控";
                            ring(99);
                            TodoEkongMessage message3 = new TodoEkongMessage();
                            message3.setId(message1.getId());
                            message3.setType(97);
                            long insert97 = LiteOrmDBUtil.insert(message3);
                            notifyEkongUser(Integer.parseInt(message1.getId()), title, message1.getTitle(), message1);
                            intent = new Intent();
                            intent.setAction(Constant.NOTICE_EKONG_ACTION);
                            manager = LocalBroadcastManager.getInstance(OnlineService.this);
                            manager.sendBroadcast(intent);
                            break;
                        case 98:
                            long insert98 = LiteOrmDBUtil.insert(message1);
                            title = "购票信息";
                            ring(99);
                            notifyFocusUser(Integer.parseInt(message1.getId()), title, message1.getTitle(), message1);
                            intent = new Intent();
                            intent.setAction(Constant.NOTICE_FOCUS_ACTION);
                            manager = LocalBroadcastManager.getInstance(OnlineService.this);
                            manager.sendBroadcast(intent);
                            break;
                        case 99:
                            long insert99 = LiteOrmDBUtil.insert(message1);
                            title = "进站信息";
                            ring(99);
                            notifyFocusUser(Integer.parseInt(message1.getId()), title, message1.getTitle(), message1);
                            intent = new Intent();
                            intent.setAction(Constant.NOTICE_FOCUS_ACTION);
                            manager = LocalBroadcastManager.getInstance(OnlineService.this);
                            manager.sendBroadcast(intent);
                            break;
                        case 89:
                            ring(89);
                            notify110User(89, title, message1.getTitle(), message1);
                            break;
                        case 77:
                            ring(89);
                            notify110User(Integer.parseInt(message1.getId()), title, message1.getTitle(), message1);
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            ring(100);
                            long insert1 = LiteOrmDBUtil.insert(message1);
                            notifyUser(1, message1.getTitle(), message1.getContent(), message1);
                            intent = new Intent();
                            intent.setAction(Constant.NOTICE_ACTION);
                            manager = LocalBroadcastManager.getInstance(OnlineService.this);
                            manager.sendBroadcast(intent);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            setPkgsInfo();
        }

    }

    public OnlineService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        this.setTickAlarm();
//        PowerManager pm = (PowerManager) this
//                .getSystemService(Context.POWER_SERVICE);
//        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                "OnlineService");
        resetClient();
    }

    @Override
    public int onStartCommand(Intent param, int flags, int startId) {
        if (param == null) {
            return START_STICKY;
        }
        String cmd = param.getStringExtra("CMD");
        if (cmd == null) {
            cmd = "";
        }
//        if ("TICK".equals(cmd)) {
//            if (wakeLock != null && !wakeLock.isHeld()) {
//                wakeLock.acquire();
//            }
//        }
//        if ("RESET".equals(cmd)) {
//            if (wakeLock != null && !wakeLock.isHeld()) {
//                wakeLock.acquire();
//            }
//            resetClient();
//        }
//        if ("TOAST".equals(cmd)) {
//            String text = param.getStringExtra("TEXT");
//            if (text != null && text.trim().length() != 0) {
//                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//            }
//        }

        setPkgsInfo();

        return START_STICKY;
    }

    protected void setPkgsInfo() {
        if (this.myTcpClient == null) {
            return;
        }
        long sent = myTcpClient.getSentPackets();
        long received = myTcpClient.getReceivedPackets();
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext(), Constant.SP_NAME);
//        sharedPreferencesUtil.setData(Constant.SEND_PKGS, String.valueOf(sent));
//        sharedPreferencesUtil.setData(Constant.RECEIVE_PKGS, String.valueOf(received));
    }

    /***
     * 重置连接
     */
    protected void resetClient() {
        if (this.myTcpClient != null) {
            try {
                myTcpClient.stop();
            } catch (Exception e) {
            }
        }
        try {
            String identifier = UserService.getNewUserInfo(this).getIdentifier();
            if (identifier.length() >= IDENTIFY_LENGTH) {
                identifier = identifier.substring(2, 18);
            }
            myTcpClient = new MyTcpClient(
                    StringUtil.String16ToByteArray(identifier), 1,
                    "20.3.2.47", 9966);
            myTcpClient.setHeartbeatInterval(50);
            myTcpClient.start();
            LogUtil.d("启动成功 service");
        } catch (Exception e) {
            Log.d("Push：", "操作失败：" + e.getMessage());
        }
    }

    protected void tryReleaseWakeLock() {
//        if (wakeLock != null && wakeLock.isHeld()) {
//            wakeLock.release();
//        }
    }

    protected void setTickAlarm() {
        if (alarmMgr == null) {
            alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        Intent intent = new Intent(this, TickAlarmReceiver.class);
        int requestCode = 0;
        tickPendIntent = PendingIntent.getBroadcast(this, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 小米2s的MIUI操作系统，目前最短广播间隔为5分钟，少于5分钟的alarm会等到5分钟再触发！2014-04-28
        long triggerAtTime = System.currentTimeMillis();
        int interval = 300 * 1000;
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, interval,
                tickPendIntent);
    }

    public void notifyFocusUser(int id, String title, String content, TodoMessage msg) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentInfo(com.anrongtec.laucher.util.StringUtil.stampToTime(msg.getTime()));
        builder.setContentText(content);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.icon_laucher);
        builder.setTicker("新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
        intent.putExtra(Constant.PUSH_DATA, msg.getType());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public void notifyEkongUser(int id, String title, String content, TodoMessage msg) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentInfo(com.anrongtec.laucher.util.StringUtil.stampToTime(msg.getTime()));
        builder.setContentText(content);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.icon_laucher);
        builder.setTicker("新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent(getApplicationContext(), EkongFocusActivity.class);
        intent.putExtra(Constant.PUSH_DATA, msg.getType());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public void notify110User(int id, String title, String content, TodoMessage msg) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentInfo(com.anrongtec.laucher.util.StringUtil.stampToTime(msg.getTime()));
        builder.setContentText(content);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.icon_laucher);
        builder.setTicker("新警情");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.krx.ydjw", "cloud.policehomeapp.views.ChoiceActivity");
        intent.setComponent(cn);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }


    public void notifyUser(int id, String title, String content, TodoMessage msg) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentInfo("补充内容");
        builder.setContentText(content);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.icon_laucher);
        builder.setTicker("新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
        intent.putExtra(Constant.PUSH_DATA, msg);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    protected void cancelNotifyRunning() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.tryReleaseWakeLock();
        try {
            if (alarmMgr != null) {
                alarmMgr.cancel(tickPendIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.myTcpClient != null) {
            try {
                myTcpClient.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 播放系统铃声
     *
     * @return
     * @throws Exception
     * @throws IOException
     */
    private MediaPlayer ring(int type) throws Exception {
        AssetManager assetManager;
        MediaPlayer player = null;
        try {
            player = new MediaPlayer();
            assetManager = getAssets();
            AssetFileDescriptor fileDescriptor = null;
            switch (type) {
                case 99:
                case 100:
                    fileDescriptor = assetManager.openFd("09.mp3");
                    break;
                case 89:
                    fileDescriptor = assetManager.openFd("has_msg.mp3");
                default:
                    break;
            }
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
                    fileDescriptor.getStartOffset());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }
}
