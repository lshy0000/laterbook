package com.chuangfeigu.tools.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.chuangfeigu.tools.common.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Nullable;

/**
 * 类功能描述：</br>
 * 自定义Service后台下载app——跳出DownloadManager系统下载之坑 </br>
 * 修改人：   yuyahao
 *
 * @version 1.0 </p> 修改时间：</br> 修改备注：</br>
 */
public class DownService extends Service {
    public static final String TAG = "ServiceDownLoadApp";
    public static final String ACTION = "me.shenfan.UPDATE_APP";
    public static final String STATUS = "status";
    public static final String PROGRESS = "progress";
    public static boolean DEBUG = true;

    //下载大小通知频率
    public static final int UPDATE_NUMBER_SIZE = 1;
    public static final int DEFAULT_RES_ID = -1;

    public static final int UPDATE_PROGRESS_STATUS = 0;
    public static final int UPDATE_ERROR_STATUS = -1;
    public static final int UPDATE_SUCCESS_STATUS = 1;

    //params
    private static final String URL = "downloadUrl";
    private static final String ICO_RES_ID = "icoResId";
    private static final String ICO_SMALL_RES_ID = "icoSmallResId";
    private static final String UPDATE_PROGRESS = "updateProgress";
    private static final String STORE_DIR = "storeDir";
    private static final String DOWNLOAD_NOTIFICATION_FLAG = "downloadNotificationFlag";
    private static final String DOWNLOAD_SUCCESS_NOTIFICATION_FLAG = "downloadSuccessNotificationFlag";
    private static final String DOWNLOAD_ERROR_NOTIFICATION_FLAG = "downloadErrorNotificationFlag";
    private static final String IS_SEND_BROADCAST = "isSendBroadcast";


    private String downloadUrl;
    private int icoResId;             //default app ico
    private int icoSmallResId;
    private int updateProgress;   //update notification progress when it add number
    private static String storeDir;          //default sdcard/Android/package/update
    private int downloadNotificationFlag;
    private int downloadSuccessNotificationFlag;
    private int downloadErrorNotificationFlag;
    private boolean isSendBroadcast;

    private UpdateProgressListener updateProgressListener;
    private LocalBinder localBinder = new LocalBinder();

    /**
     * Class used for the client Binder.
     */
    public class LocalBinder extends Binder {
        /**
         * set update progress call back
         *
         * @param listener
         */
        public void setUpdateProgressListener(UpdateProgressListener listener) {
            DownService.this.setUpdateProgressListener(listener);
        }
    }


    private boolean startDownload;//开始下载
    private int lastProgressNumber;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private int notifyId;
    private String appName;
    private LocalBroadcastManager localBroadcastManager;
    private Intent localIntent;
    private DownloadApk downloadApkTask;

    /**
     * whether debug
     */
    public static void debug() {
        DEBUG = true;
    }

    /**
     * 点击通知栏去进行安装
     *
     * @param path
     * @return
     */
    private static Intent installIntent(String path) {
        Uri uri = Uri.fromFile(new File(path));
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        return installIntent;
    }

    /**
     * 通过浏览器进行下载
     *
     * @param downloadUrl
     * @return
     */
    private static Intent webLauncher(String downloadUrl) {
        Uri download = Uri.parse(downloadUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, download);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 通过URL获取要下载的apk的前缀没弄过
     *
     * @param downloadUrl
     * @return
     */
    private static String getSaveFileName(String downloadUrl) {
        if (downloadUrl == null || StringUtils.isEmpty(downloadUrl)) {
            return "noName.apk";
        }
        return downloadUrl.substring(downloadUrl.lastIndexOf("/"));
    }

    /**
     * 来设置要下载apk储存的文件夹
     *
     * @param service
     * @return
     */
    private static File getDownloadDir(DownService service) {
        File downloadDir = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (service.storeDir != null) {
                downloadDir = new File(Environment.getExternalStorageDirectory(), service.storeDir);
            } else {
                storeDir = service.getExternalCacheDir().getAbsolutePath();
                downloadDir = new File(service.getExternalCacheDir(), "update");
            }
        } else {
            downloadDir = new File(service.getCacheDir(), "update");
        }
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        return downloadDir;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appName = getApplicationName();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!startDownload && intent != null) {
            startDownload = true;
            downloadUrl = intent.getStringExtra(URL);
            icoResId = intent.getIntExtra(ICO_RES_ID, DEFAULT_RES_ID);
            icoSmallResId = intent.getIntExtra(ICO_SMALL_RES_ID, DEFAULT_RES_ID);
            storeDir = intent.getStringExtra(STORE_DIR);
            updateProgress = intent.getIntExtra(UPDATE_PROGRESS, UPDATE_NUMBER_SIZE);
            downloadNotificationFlag = intent.getIntExtra(DOWNLOAD_NOTIFICATION_FLAG, 0);
            downloadErrorNotificationFlag = intent.getIntExtra(DOWNLOAD_ERROR_NOTIFICATION_FLAG, 0);
            downloadSuccessNotificationFlag = intent.getIntExtra(DOWNLOAD_SUCCESS_NOTIFICATION_FLAG, 0);
            isSendBroadcast = intent.getBooleanExtra(IS_SEND_BROADCAST, false);
            if (DEBUG) {
                Log.e(TAG, "downloadUrl: " + downloadUrl);
                Log.e(TAG, "icoResId: " + icoResId);
                Log.e(TAG, "icoSmallResId: " + icoSmallResId);
                Log.e(TAG, "storeDir: " + storeDir);
                Log.e(TAG, "updateProgress: " + updateProgress);
                Log.e(TAG, "downloadNotificationFlag: " + downloadNotificationFlag);
                Log.e(TAG, "downloadErrorNotificationFlag: " + downloadErrorNotificationFlag);
                Log.e(TAG, "downloadSuccessNotificationFlag: " + downloadSuccessNotificationFlag);
                Log.e(TAG, "isSendBroadcast: " + isSendBroadcast);
            }
            notifyId = startId;
            buildNotification();
            buildBroadcast();
            downloadApkTask = new DownloadApk(this);
            downloadApkTask.execute(downloadUrl);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(@Nullable Intent intent) {
        return localBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    public void setUpdateProgressListener(UpdateProgressListener updateProgressListener) {
        this.updateProgressListener = updateProgressListener;
    }

    @Override
    public void onDestroy() {
        if (downloadApkTask != null) {
            downloadApkTask.cancel(true);
        }

        if (updateProgressListener != null) {
            updateProgressListener = null;
        }
        localIntent = null;
        builder = null;

        super.onDestroy();
    }

    /**
     * 获取当前的应用名
     *
     * @return
     */
    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    private void buildBroadcast() {
        if (!isSendBroadcast) {
            return;
        }
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localIntent = new Intent(ACTION);
    }

    /**
     * 发送广播
     *
     * @param status
     * @param progress
     */
    private void sendLocalBroadcast(int status, int progress) {
        if (!isSendBroadcast || localIntent == null) {
            return;
        }
        localIntent.putExtra(STATUS, status);
        localIntent.putExtra(PROGRESS, progress);
        localBroadcastManager.sendBroadcast(localIntent);
    }

    /**
     * 环形通知安
     */
    private void buildNotification() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("download start")
                .setWhen(System.currentTimeMillis())
                .setProgress(100, 1, false)
                .setSmallIcon(icoSmallResId)
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), icoResId))
                .setDefaults(downloadNotificationFlag);

        manager.notify(notifyId, builder.build());
    }

    /**
     * 开始 下载
     */
    private void start() {
        builder.setContentTitle(appName);
        builder.setContentText("start");
        manager.notify(notifyId, builder.build());
        sendLocalBroadcast(UPDATE_PROGRESS_STATUS, 1);
        if (updateProgressListener != null) {
            updateProgressListener.start();
        }
    }

    /**
     * 通知进度条，进度条
     * 大小范围（1~100）
     */
    private void update(int progress) {
        if (progress - lastProgressNumber > updateProgress) {
            lastProgressNumber = progress;
            builder.setProgress(100, progress, false);
            builder.setContentText(progress + "%");
            manager.notify(notifyId, builder.build());
            sendLocalBroadcast(UPDATE_PROGRESS_STATUS, progress);
            if (updateProgressListener != null) {
                updateProgressListener.update(progress);
            }
        }
    }

    /**
     * 下载成功的回调
     *
     * @param path
     */
    private void success(String path) {

        builder.setProgress(0, 0, false);
        builder.setContentText("success");
        manager.cancel(0);
        if (checkFileIsExists(path)) {
            Intent i = installIntent(path);
            PendingIntent intent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent)
                    .setAutoCancel(true)//用户点击就自动消失
                    .setDefaults(downloadSuccessNotificationFlag);
            Notification n = builder.build();
            n.contentIntent = intent;
            manager.notify(notifyId, n);
            if (updateProgressListener != null) {
                updateProgressListener.success();
            }
            startActivity(i);
            IntentFilter filter = new IntentFilter();
        } else {
            deleteFilesByDirectory();
        }
        stopSelf();
    }

    private boolean checkFileIsExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 清除本地文件
     */
    public static void deleteFilesByDirectory() {
        new File(storeDir).deleteOnExit();
    }

    /**
     * 下载失败通知浏览器下载回调
     */
    private void error() {
        Intent i = webLauncher(downloadUrl);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentText("failed");
        builder.setContentIntent(intent);
        builder.setProgress(0, 0, false);
        builder.setDefaults(downloadErrorNotificationFlag);
        Notification n = builder.build();
        n.contentIntent = intent;
        manager.notify(notifyId, n);
        sendLocalBroadcast(UPDATE_ERROR_STATUS, -1);
        if (updateProgressListener != null) {
            updateProgressListener.error();
        }
        stopSelf();
    }

    /**
     * 下载异步任务
     */
    private static class DownloadApk extends AsyncTask<String, Integer, String> {

        private WeakReference<DownService> DownServiceWeakReference;

        public DownloadApk(DownService service) {
            DownServiceWeakReference = new WeakReference<>(service);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DownService service = DownServiceWeakReference.get();
            if (service != null) {
                service.start();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //注意，这里现在之前先进行清空文件，防止因检查到已经有存在的文件而无法 进行下载
            deleteFilesByDirectory();
            final String downloadUrl = params[0];

            final File file = new File(DownService.getDownloadDir(DownServiceWeakReference.get()),
                    DownService.getSaveFileName(downloadUrl));
            if (DEBUG) {
                Log.e(TAG, "download url is " + downloadUrl);
                Log.e(TAG, "download apk cache at " + file.getAbsolutePath());
            }
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            HttpURLConnection httpConnection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            int updateTotalSize = 0;
            java.net.URL url;
            try {
                url = new URL(downloadUrl);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setConnectTimeout(5000);
                httpConnection.setReadTimeout(5000);

                if (DEBUG) {
                    Log.e(TAG, "download status code: " + httpConnection.getResponseCode());
                }

                if (httpConnection.getResponseCode() != 200) {
                    return null;
                }

                updateTotalSize = httpConnection.getContentLength();

                if (file.exists()) {
                    if (updateTotalSize == file.length()) {
                        // 下载完成
                        return file.getAbsolutePath();
                    } else {
                        file.delete();
                    }
                }
                file.createNewFile();
                is = httpConnection.getInputStream();
                fos = new FileOutputStream(file, false);
                byte buffer[] = new byte[4096];

                int readSize = 0;
                int currentSize = 0;

                while ((readSize = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, readSize);
                    currentSize += readSize;
                    publishProgress((currentSize * 100 / updateTotalSize));
                }
                // download success
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return file.getAbsolutePath();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (DEBUG) {
                Log.e(TAG, "current progress is " + values[0]);
            }
            DownService service = DownServiceWeakReference.get();
            if (service != null) {
                service.update(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DownService service = DownServiceWeakReference.get();
            if (service != null) {
                if (s != null) {
                    service.success(s);
                } else {
                    service.error();
                }
            }
        }
    }


    /**
     * a builder class helper use DownService
     * 仿AlertDialogDownService的构造器
     */
    public static class Builder {

        private String downloadUrl;
        private int icoResId = DEFAULT_RES_ID;             //default app ico
        private int icoSmallResId = DEFAULT_RES_ID;
        private int updateProgress = UPDATE_NUMBER_SIZE;   //update notification progress when it add number
        private String storeDir;          //default sdcard/Android/package/update
        private int downloadNotificationFlag;
        private int downloadSuccessNotificationFlag;
        private int downloadErrorNotificationFlag;
        private boolean isSendBroadcast;

        protected Builder(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public static Builder create(String downloadUrl) {
            if (downloadUrl == null) {
                throw new NullPointerException("downloadUrl == null");
            }
            return new Builder(downloadUrl);
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public int getIcoResId() {
            return icoResId;
        }

        public Builder setIcoResId(int icoResId) {
            this.icoResId = icoResId;
            return this;
        }

        public int getIcoSmallResId() {
            return icoSmallResId;
        }

        public Builder setIcoSmallResId(int icoSmallResId) {
            this.icoSmallResId = icoSmallResId;
            return this;
        }

        public int getUpdateProgress() {
            return updateProgress;
        }

        public Builder setUpdateProgress(int updateProgress) {
            if (updateProgress < 1) {
                throw new IllegalArgumentException("updateProgress < 1");
            }
            this.updateProgress = updateProgress;
            return this;
        }

        public String getStoreDir() {
            return storeDir;
        }

        public Builder setStoreDir(String storeDir) {
            this.storeDir = storeDir;
            return this;
        }

        public int getDownloadNotificationFlag() {
            return downloadNotificationFlag;
        }

        public Builder setDownloadNotificationFlag(int downloadNotificationFlag) {
            this.downloadNotificationFlag = downloadNotificationFlag;
            return this;
        }

        public int getDownloadSuccessNotificationFlag() {
            return downloadSuccessNotificationFlag;
        }

        public Builder setDownloadSuccessNotificationFlag(int downloadSuccessNotificationFlag) {
            this.downloadSuccessNotificationFlag = downloadSuccessNotificationFlag;
            return this;
        }

        public int getDownloadErrorNotificationFlag() {
            return downloadErrorNotificationFlag;
        }

        public Builder setDownloadErrorNotificationFlag(int downloadErrorNotificationFlag) {
            this.downloadErrorNotificationFlag = downloadErrorNotificationFlag;
            return this;
        }

        public boolean isSendBroadcast() {
            return isSendBroadcast;
        }

        public Builder setIsSendBroadcast(boolean isSendBroadcast) {
            this.isSendBroadcast = isSendBroadcast;
            return this;
        }

        public Builder build(Context context) {
            if (context == null) {
                throw new NullPointerException("context == null");
            }
            Intent intent = new Intent();
            intent.setClass(context, DownService.class);
            intent.putExtra(URL, downloadUrl);

            if (icoResId == DEFAULT_RES_ID) {
                icoResId = getIcon(context);
            }

            if (icoSmallResId == DEFAULT_RES_ID) {
                icoSmallResId = icoResId;
            }
            intent.putExtra(ICO_RES_ID, icoResId);
            intent.putExtra(STORE_DIR, storeDir);
            intent.putExtra(ICO_SMALL_RES_ID, icoSmallResId);
            intent.putExtra(UPDATE_PROGRESS, updateProgress);
            intent.putExtra(DOWNLOAD_NOTIFICATION_FLAG, downloadNotificationFlag);
            intent.putExtra(DOWNLOAD_SUCCESS_NOTIFICATION_FLAG, downloadSuccessNotificationFlag);
            intent.putExtra(DOWNLOAD_ERROR_NOTIFICATION_FLAG, downloadErrorNotificationFlag);
            intent.putExtra(IS_SEND_BROADCAST, isSendBroadcast);
            context.startService(intent);

            return this;
        }

        /**
         * 得到系当前应用的相对应的图标
         *
         * @param context
         * @return
         */
        private int getIcon(Context context) {

            final PackageManager packageManager = context.getPackageManager();
            ApplicationInfo appInfo = null;
            try {
                appInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (appInfo != null) {
                return appInfo.icon;
            }
            return 0;
        }
    }

    public static interface UpdateProgressListener {
        public void start();

        public void success();

        public void error();

        public void update(int progress);
    }



   /*  UpdateService.Builder.create(URL)
                .setStoreDir("update/flag")
                .setDownloadSuccessNotificationFlag(Notification.DEFAULT_ALL)
                .setDownloadErrorNotificationFlag(Notification.DEFAULT_ALL)
                .build(this);
   LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
 IntentFilter filter = new IntentFilter();  filter.addAction(ACTION);
  myBroadcastReciver = new MyBroadcastReciver();
  localBroadcastManager.registerReceiver(myBroadcastReciver, filter);

 Intent intent = new Intent();
 intent.setAction(SaleLeftFragment.ACTION);
 intent.putExtra(TAG, data);
 LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
1).LocalBroadcastManager注册广播只能通过代码注册的方式。
  2).LocalBroadcastManager注册广播后，一定要记得取消监听。
 3).重点的重点，使用LocalBroadcastManager注册的广播，您在发送广播的时候务必使用LocalBroadcastManager.sendBroadcast(intent);否则您接收不到广播*/

}