package com.chuangfeigu.tools.common;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.chuangfeigu.tools.app.App;

import java.io.File;

/**
 * 系统下载
 */
public class DownloadUtils {

    public static final String PROGRESS = "com.fro.tools.download.PROGRESS";
    public static final String FINISH = "com.fro.tools.download.FINISH";

    //下载器
    private DownloadManager downloadManager;
    //上下文
    private Context mContext;
    //下载的ID
    private long downloadId;
    public static Uri uri;
    public static String filname;

    public static DownloadUtils from(Context context) {
        DownloadUtils re = new DownloadUtils();
        re.mContext = context;
        return re;
    }

    int getDownstatue(Context context, int downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
        if (c.moveToFirst()) {
            return c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

        }
        return DownloadManager.STATUS_FAILED;
    }

    //下载apk
    public long downloadAPK(Context context, String url, String name, String title, String description) {

        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//下载完成后通知栏任然可见
        request.setTitle(title);
        request.setDescription(description);
        request.setVisibleInDownloadsUi(true);
        try {

            filname = context.getExternalCacheDir().getAbsolutePath() + "/download/" + name;
            try {
                File file = new File(filname);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
            }
            uri = Uri.fromFile(new File(filname));
            request.setDestinationUri(uri);
            //设置下载的路径
//            request.setDestinationInExternalPublicDir("download", name);
        } catch (Exception e) {
            //不支持外部存储，使用浏览器更新
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri apk_url = Uri.parse(url);
            intent.setData(apk_url);
            context.startActivity(intent);//打开浏览器
            return 0;
        }
        //获取DownloadManager
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);
        return downloadId;
    }

//    //广播监听下载的各个状态
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            checkStatus(context);
//        }
//    };
//
//    public static int[] getBytesAndStatus(Context context, long downloadId) {
//        int[] bytesAndStatus = new int[]{-1, -1, 0};
//        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
//        Cursor c = null;
//        try {
//            c = downloadManager.query(query);
//            if (c != null && c.moveToFirst()) {
//                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
//                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
//                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
//            }
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//        }
//        return bytesAndStatus;
//    }

    //检查下载状态
    private void checkStatus(Context context) {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    installAPK(mContext, downloadId);
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        c.close();
    }

    //下载到本地后执行安装
    public static void installAPK(Context context, long downloadId) {
        //获取下载文件的Uri
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = Uri.fromFile(new File(filname));
        if (downloadFileUri != null && uri != null) {
            System.out.print(downloadFileUri.getPath().toString() + "real" + uri.getPath().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data;
            // 判断版本大于等于7.0u
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
                data = FileProvider.getUriForFile(context, App.getMetaData(context,"provider"), new File(filname));
                // 给目标应用一个临时授权
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                data = downloadFileUri;
            }
            intent.setDataAndType(data, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}
