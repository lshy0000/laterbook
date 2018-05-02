package com.chuangfeigu.tools.app;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.chuangfeigu.tools.common.DownloadUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fro-soft on 2017-9-21.
 * /系统下载查询福这类
 */
public class OnReceiveDown extends BroadcastReceiver {
    private int uuid = 0x95468431;
    public final static String PROGRESS = "com.fro.tools.download.PROGRESS";
    public final static String ID = "com.fro.tools.download.ID";
    public final static String progressvalue = "progressvalue";
    public final static int STOP = 2;
    public final static int START = 1;
    public final static int PAUSE = 3;
    private MThread mtheard;
    public static final java.lang.String Downloadid = "Downloadid";
    public static final java.lang.String Downloadcommand = "Downloadcommand";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            DownloadUtils.installAPK(context, 0);
        }
        if (intent.getExtras() == null || intent.getExtras().getLong(Downloadid, uuid) == uuid) {
            Log.e("down", "未知的下载任务");
            return;
        }
        //开启下载任务查询线程。
        long downloadid = intent.getExtras().getLong(Downloadid, uuid);
        int command = intent.getExtras().getInt(Downloadcommand, -1);
        MThread.getInstance().deal(downloadid, command);
    }


    public static class MThread {
        private MThread() {
        }

        private static MThread instance;

        public static MThread getInstance() {
            if (instance == null) {
                instance = new MThread();
            }
            return instance;
        }

        Map<Integer, GThread> downloadids = new HashMap<>();

        public void deal(long downloadid, int command) {
            if (command == START && !downloadids.containsKey(downloadid)) {
                GThread a = new GThread(downloadid);
                a.start();
            } else if (downloadids.containsKey(downloadid)) {
                GThread a = downloadids.get(downloadid);
                a.setCommand(command);
            }
        }
    }

    public static class GThread extends Thread {
        long download;
        int command;
        private boolean isfinish;

        public void setIsfinish(boolean isfinish) {
            this.isfinish = isfinish;
        }

        public GThread(long download) {
            this.download = download;
        }

        public void setCommand(int command) {
            this.command = command;
        }

        @Override
        public void run() {
            super.run();
            while (command != STOP&&!isfinish) {
                try {
                    getProgress(download);
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        private void getProgress(long downloadId) {
            DownloadManager.Query query = new DownloadManager.Query();
            //通过下载的id查找
            query.setFilterById(downloadId);
            Cursor c = ((DownloadManager) App.getApp().getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                int p = Double.valueOf((c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) * 100.0 / c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)))).intValue();
                switch (status) {
                    //下载暂停
                    case DownloadManager.STATUS_PAUSED:
                        break;
                    //下载延迟
                    case DownloadManager.STATUS_PENDING:
                        break;
                    //正在下载
                    case DownloadManager.STATUS_RUNNING:
                        Intent pick = new Intent(PROGRESS);
                        pick.putExtra(progressvalue, p);
                        App.getApp().sendBroadcast(pick);
                        break;
                    //下载完成
                    case DownloadManager.STATUS_SUCCESSFUL:
                        //下载完成安装APK
//                        DownloadUtils.installAPK(App.getApp(), downloadId);
                        setIsfinish(true);
                        Intent pick2 = new Intent(PROGRESS);
                        pick2.putExtra(progressvalue, 100);
                        App.getApp().sendBroadcast(pick2);
                        break;
                    //下载失败
                    case DownloadManager.STATUS_FAILED:
                        Intent pick3 = new Intent(PROGRESS);
                        pick3.putExtra(progressvalue, -1);
//                        App.getApp().sendBroadcast(pick3);
//                        Toast.makeText(App.getApp(), "下载失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            c.close();
        }

    }
}
