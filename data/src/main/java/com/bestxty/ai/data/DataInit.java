package com.bestxty.ai.data;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.bestxty.ai.data.net.components.ApplicationComponent;
import com.bestxty.ai.data.net.components.DaggerApplicationComponent;
import com.bestxty.ai.data.net.modules.ApplicationModule;
import com.chuangfeigu.tools.app.ActivityUtil;
import com.chuangfeigu.tools.app.App;
import com.chuangfeigu.tools.app.T;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by lshy on 2018-3-8.
 */


@Interceptor(priority = 1)
public class DataInit implements IInterceptor {

    private static final int EXCELVERSION = 2;
    static Application context;
    private static ApplicationComponent applicationComponent;


    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        inits(context);
    }

    private static void inits(Context contextc) {
        Realm.init(contextc);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("myRealmFile")
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        /**
         * 注入@Singleton组件
         */
        context = ((Application) contextc);

    }


    public static ApplicationComponent getApplicationComponent() {
        if (context == null) {//0417bug记录，发现route初始化异常，拦截器均未初始化，导致空指针异常，拦截器无法初始化后产生逻辑错误，可以尝试手动初始化route，暂时不解决，
            //出现场景在app启动后，长时间后台运行过程中被清理掉内存，在从前台恢复页面时，
            context = App.getApp();
        }
        if (context == null) {
            T.showShort("route init please wait");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            context = App.getApp();
        }
        if (context == null) {
            T.showShort("App 自修复失败，route 尚未初始化，进程退出");
            ActivityUtil.getInstance().finishAll();
            System.exit(0);
        }
        if (applicationComponent == null) {
            applicationComponent = BindApplicationComponent(context);
        }

        return applicationComponent;
    }

    private static ApplicationComponent BindApplicationComponent(Application context) {


        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(context))
                .build();
    }
}

