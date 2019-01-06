package com.yl.core.component.appupdate.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;


import com.yl.core.component.appupdate.config.UpdateConfiguration;
import com.yl.core.component.appupdate.manager.DownloadManager;
import com.yl.core.component.appupdate.service.DownloadService;

import java.io.File;

/**
 * Create by zm on 2018/10/30
 */

public final class NotificationUtil {

    /**
     * 构建一个消息
     *
     * @param context 上下文
     * @param icon    图标id
     * @param title   标题
     * @param content 内容
     */
    private static NotificationCompat.Builder builderNotification(Context context, int icon, String title, String content) {
        String channelId = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelId = getNotificationChannelId();
        }
        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .setContentText(content)
                //不能删除
                .setAutoCancel(false)
                //正在交互（如播放音乐）
                .setOngoing(true);
    }

    /**
     * 显示刚开始下载的通知
     *
     * @param context 上下文
     * @param icon    图标
     * @param title   标题
     * @param content 内容
     */
    public static void showNotification(Context context, int icon, String title, String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            afterO(manager);
        }
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                .setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(DownloadManager.getInstance().getConfiguration().getNotifyId(), builder.build());
    }

    /**
     * 显示正在下载的通知
     *
     * @param context 上下文
     * @param icon    图标
     * @param title   标题
     * @param content 内容
     */
    public static void showProgressNotification(Context context, int icon, String title, String content,
                                                int max, int progress) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                .setProgress(max, progress, false);
        manager.notify(DownloadManager.getInstance().getConfiguration().getNotifyId(), builder.build());
    }

    /**
     * 显示下载完成的通知,点击进行安装
     *
     * @param context     上下文
     * @param icon        图标
     * @param title       标题
     * @param content     内容
     * @param authorities Android N 授权
     * @param apk         安装包
     */
    public static void showDoneNotification(Context context, int icon, String title, String content,
                                            String authorities, File apk) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //不知道为什么需要先取消之前的进度通知，才能显示完成的通知。
        manager.cancel(DownloadManager.getInstance().getConfiguration().getNotifyId());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authorities, apk);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apk);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                .setContentIntent(pi);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(DownloadManager.getInstance().getConfiguration().getNotifyId(), notification);
    }

    /**
     * 显示下载错误的通知,点击继续下载
     *
     * @param context 上下文
     * @param icon    图标
     * @param title   标题
     * @param content 内容
     */
    public static void showErrorNotification(Context context, int icon, String title, String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            afterO(manager);
        }
        Intent intent = new Intent(context, DownloadService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                .setAutoCancel(true)
                .setOngoing(false)
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(DownloadManager.getInstance().getConfiguration().getNotifyId(), builder.build());
    }

    /**
     * 获取通知栏开关状态
     *
     * @return true |false
     */
    public static boolean notificationEnable(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        return notificationManagerCompat.areNotificationsEnabled();
    }

    /**
     * 适配 Android O 通知渠道
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void afterO(NotificationManager manager) {
        NotificationChannel channel;
        UpdateConfiguration config = DownloadManager.getInstance().getConfiguration();
        if (config != null) {
            channel = config.getNotificationChannel();
        } else {
            //用户没有设置
            channel = null;
        }
        //如果用户没有设置
        if (channel == null) {
            //IMPORTANCE_LOW：默认关闭声音与震动、IMPORTANCE_DEFAULT：开启声音与震动
            channel = new NotificationChannel(Constant.DEFAULT_CHANNEL_ID, Constant.DEFAULT_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW);
            //是否在桌面icon右上角展示小圆点
            channel.enableLights(true);
            //是否在久按桌面图标时显示此渠道的通知
            channel.setShowBadge(true);
            //在Android O 上更新进度 不震动
//            channel.enableVibration(true);
        }
        manager.createNotificationChannel(channel);
    }

    /**
     * 获取设置的通知渠道id
     *
     * @return 如果没有设置则使用默认的 'appUpdate'
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getNotificationChannelId() {
        NotificationChannel channel = DownloadManager.getInstance().getConfiguration().getNotificationChannel();
        if (channel == null) {
            return Constant.DEFAULT_CHANNEL_ID;
        }
        String channelId = channel.getId();
        if (TextUtils.isEmpty(channelId)) {
            return Constant.DEFAULT_CHANNEL_ID;
        }
        return channelId;
    }
}
