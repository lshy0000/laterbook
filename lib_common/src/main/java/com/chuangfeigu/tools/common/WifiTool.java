package com.chuangfeigu.tools.common;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * wifi基本操作工具类
 */
public class WifiTool {
    // 上下文Context对象
    private Context mContext;
    // WifiManager对象

    public WifiTool(Context mContext) {
        this.mContext = mContext;
        mWifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    /**
     * 判断手机是否连接在Wifi上
     */
    public static boolean isConnectWifi(Context context) {
        // 获取ConnectivityManager对象
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        // 获取连接的方式为wifi
        NetworkInfo.State wifi = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();

        if (info != null && info.isAvailable() && wifi == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取当前手机所连接的wifi信息
     */
    public WifiInfo getCurrentWifiInfo() {
        return mWifiManager.getConnectionInfo();
    }

    /**
     * 添加一个网络并连接
     * 传入参数：WIFI发生配置类WifiConfiguration
     */
    public boolean addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        return mWifiManager.enableNetwork(wcgID, true);
    }

    /**
     * 搜索附近的热点信息，并返回所有热点为信息的SSID集合数据
     */
    public List<String> getScanSSIDsResult() {
        // 扫描的热点数据
        List<ScanResult> resultList;
        // 开始扫描热点
        mWifiManager.startScan();
        resultList = mWifiManager.getScanResults();
        ArrayList<String> ssids = new ArrayList<String>();
        if (resultList != null) {
            for (ScanResult scan : resultList) {
                ssids.add(scan.SSID);// 遍历数据，取得ssid数据集
            }
        }
        return ssids;
    }

    /**
     * 得到手机搜索到的ssid集合，从中判断出设备的ssid（dssid）
     */
    public List<String> accordSsid(String condition) {
        List<String> s = getScanSSIDsResult();
        List<String> result = new ArrayList<String>();
        for (String str : s) {
            if (checkDssid(str, condition)) {
                result.add(str);
            }
        }
        return result;
    }

    /**
     * 检测指定ssid是不是匹配的ssid，目前支持GBELL，TOP,后续可添加。
     *
     * @param ssid
     * @return
     */
    private boolean checkDssid(String ssid, String condition) {
        if (!TextUtils.isEmpty(ssid) && !TextUtils.isEmpty(condition)) {
            //这里条件根据自己的需求来判断，我这里就是随便写的一个条件
            if (ssid.length() > 8 && (ssid.substring(0, 8).equals(condition))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 连接wifi
     * 参数：wifi的ssid及wifi的密码
     */
    public boolean connectWifiTest(final String ssid, final String pwd) {
        boolean isSuccess = false;
        boolean flag = false;
        mWifiManager.disconnect();
        boolean addSucess = addNetwork(CreateWifiInfo(ssid, pwd, 3));
        if (addSucess) {
            while (!flag && !isSuccess) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                String currSSID = getCurrentWifiInfo().getSSID();
                if (currSSID != null)
                    currSSID = currSSID.replace("\"", "");
                int currIp = getCurrentWifiInfo().getIpAddress();
                if (currSSID != null && currSSID.equals(ssid) && currIp != 0) {
                    //这里还需要做优化处理，增强结果判断
                    isSuccess = true;
                } else {
                    flag = true;
                }
            }
        }
        return isSuccess;

    }

    /**
     * 创建WifiConfiguration对象
     * 分为三种情况：1没有密码;2用wep加密;3用wpa加密
     *
     * @param SSID
     * @param Password
     * @param Type
     * @return
     */
    public WifiConfiguration CreateWifiInfo(String SSID, String Password,
                                            int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == 1) // WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2) // WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) // WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager
                .getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    public WifiManager mWifiManager;

    private WifiInfo mWifiInfo;

    private List<ScanResult> mWifiList;

    private List<WifiConfiguration> mWificonfiguration;

    private WifiManager.WifiLock mWifiLock;


    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    public int checkState() {
        return mWifiManager.getWifiState();
    }

    public void acquireWifiLoc() {
        mWifiLock.acquire();
    }

    public void releaseWifiLock() {
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    public void createWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("test");
    }

    public List<WifiConfiguration> getConfigurations() {
        return mWificonfiguration;
    }

    public Boolean connectConfiguration(int index) {

//        if(index > mWificonfiguration.size()) {
//            return;
//        }
        mWifiManager.enableNetwork(index, true);
        mWifiManager.saveConfiguration();
        mWifiManager.reconnect();

        return true;
    }

    public void startScan() {
        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();
        mWificonfiguration = mWifiManager.getConfiguredNetworks();
    }

    public List<ScanResult> getmWifiList() {
        return mWifiList;
    }

    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index_" + String.valueOf(i + 1) + ":");
            stringBuilder.append(mWifiList.get(i).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }

    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    public String getSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
    }

    public int getIpAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();

    }

    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    public boolean addNetWork(WifiConfiguration wifiConfiguration) {
        int wcgID = mWifiManager.addNetwork(wifiConfiguration);
        Log.e("wcgID", wcgID + "true");
        mWifiManager.enableNetwork(wcgID, true);
        mWifiManager.saveConfiguration();
        mWifiManager.reconnect();
        return true;

    }

    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    public WifiConfiguration createWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedGroupCiphers.clear();
        configuration.allowedKeyManagement.clear();
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedProtocols.clear();
        configuration.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.isExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        switch (Type) {
            case 1:
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
//                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//                configuration.status = WifiConfiguration.Status.ENABLED;
                break;
            case 2:
                configuration.hiddenSSID = false;
                configuration.wepKeys[0] = "\"" + Password + "\"";
                configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

                break;
            case 3:

                configuration.preSharedKey = "\"" + Password + "\"";
                configuration.hiddenSSID = false;
                // configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                configuration.status = WifiConfiguration.Status.ENABLED;
                break;
        }
        return configuration;
    }

    private WifiConfiguration isExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig :
                existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }

        }
        return null;
    }

    @Deprecated
    public static void staticip(Context context, String staticip, String staticway, String staticmask, String staticnds, String staticdns2) {
        ContentResolver cr = context.getContentResolver();
        Settings.System.putInt(cr, Settings.System.WIFI_USE_STATIC_IP, 1);
        Settings.System.putString(cr, Settings.System.WIFI_STATIC_IP, staticip);
        Settings.System.putString(cr, Settings.System.WIFI_STATIC_GATEWAY, staticway);
        Settings.System.putString(cr, Settings.System.WIFI_STATIC_NETMASK, staticmask);
        Settings.System.putString(cr, Settings.System.WIFI_STATIC_DNS1, staticnds);
        Settings.System.putString(cr, Settings.System.WIFI_STATIC_DNS2, staticdns2);
    }

    /*  设置ip地址类型 assign: STATIC/DHCP 静态/动态
    */
    private static void setIpAssignment(String assign, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if (Build.VERSION.SDK_INT >= 21) {
            Object ipConfiguration = wifiConf.getClass()
                    .getMethod("getIpConfiguration").invoke(wifiConf);
            setEnumField(ipConfiguration, assign, "ipAssignment");
        }
    }

    //  设置ip地址
    private static void setIpAddress(InetAddress addr, int prefixLength, WifiConfiguration wificonf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException {
        Object linkProperties = getField(wificonf, "linkProperties");
        if (linkProperties == null) {
            return;
        }
        Class<?> laClass = Class.forName("android.net.LinkAddress");
        Constructor<?> laConstructor = laClass.getConstructor(new Class[]{
                InetAddress.class, int.class
        });
        Object linkAddress = laConstructor.newInstance(addr, prefixLength);
        ArrayList<Object> mLinkAddresses = (ArrayList<Object>) getDeclaredField(linkProperties, "mLinkAddresses");
        mLinkAddresses.clear();
        mLinkAddresses.add(linkAddress);

    }

    @SuppressWarnings("unchecked")
//  设置网关
    private static void setGateway(InetAddress gateway, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Object linkProperties = getField(wifiConf, "linkProperties");
        if (linkProperties == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            //android4.x版本
            Class<?> routeInfoClass = Class.forName("android.net.RouteInfo");
            Constructor<?> routeInfoConstructor = routeInfoClass.getConstructor(new Class[]{InetAddress.class});
            Object routeInfo = routeInfoConstructor.newInstance(gateway);
            ArrayList<Object> mRoutes = (ArrayList<Object>) getDeclaredField(linkProperties, "mRoutes");
            mRoutes.clear();
            mRoutes.add(routeInfo);
        }
        /*else
        {
            //android 3.x版本
            ArrayList<InetAddress> mGateways=(ArrayList<InetAddress>)getDeclaredField(linkProperties,"mGateways");
            mGateways.clear();
            mGateways.add(gateway);
        }*/
    }

    @SuppressWarnings("unchecked")
    //设置域名解析服务器
    private static void setDNS(InetAddress dns, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        Object linkProperties = getField(wifiConf, "linkProperties");
        if (linkProperties == null) {
            return;
        }
        ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(linkProperties, "mDnses");
        //清除原有Dns设置（如果只想增加，不想清除，词句可省略）
        mDnses.clear();
        //增加新的DNS
        mDnses.add(dns);
    }

    private static Object getField(Object obj, String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        Field f = obj.getClass().getField(name);
        Object out = f.get(obj);
        return out;
    }

    private static Object getDeclaredField(Object obj, String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(name);
        f.setAccessible(true);
        Object out = f.get(obj);
        return out;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void setEnumField(Object obj, String value, String name) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        Field f = obj.getClass().getField(name);
        f.set(obj, Enum.valueOf((Class<Enum>) f.getType(), value));
    }
    //以上是android3.x以上设置静态ip地址的方法

    //下面是调用方法

    /**
     * 子网掩码255.255.255.0
     * @param context
     * @param staticip
     * @param staticway
     * @param staticdns
     */
//    public static void setIpWithTfiStaticIp(final Context context, final String staticip, final String staticway, final String staticdns) {
//       T.showToast("正在固定ip："+ staticip+"\n如果需要权限，请点击运行\n请先连接wifi，并对该热点设置自动连接");
//        BackgroundExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                WifiConfiguration wifiConfig = null;
//                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
//                if(!isConnectWifi(context)){
//                    wifiManager.setWifiEnabled(true);
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(!isConnectWifi(context)){
//                    T.showToast("failed");
//                    return;
//                }
//                List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
//                for (WifiConfiguration conf : configuredNetworks) {
//                    if (conf.networkId == connectionInfo.getNetworkId()) {
//                        wifiConfig = conf;
//                        break;
//                    }
//                }
//        /*if(android.os.Build.VERSION.SDK_INT<11)
//        {
//            //如果是android2.x版本的话
//            ContentResolver ctRes=this.getContentResolver();
//            Settings.System.putInt(ctRes, Settings.System.WIFI_USE_STATIC_IP,1);
//            Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_IP,"192.168.0.202");
//            Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_NETMASK,"255.255.255.0");
//            Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_GATEWAY,"192.168.0.1");
//            Settings.System.putString(ctRes,Settings.System.WIFI_STATIC_DNS1,"192.168.0.1");
//            Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_DNS2,"61.134.1.9");
//        }
//        else
//        {*/
//                //如果是andoir3.x版本及以上的话
//                try {
//                    setIpAssignment("STATIC", wifiConfig);
//                    setIpAddress(InetAddress.getByName(staticip), 24, wifiConfig);
//                    setGateway(InetAddress.getByName(staticway), wifiConfig);
//                    setDNS(InetAddress.getByName(staticdns), wifiConfig);
//                    //apply the setting
//                    wifiManager.updateNetwork(wifiConfig);
//                    wifiManager.saveConfiguration();
//                    wifiManager.reconnect();
//                    wifiManager.setWifiEnabled(false);
//                    Thread.sleep(2000);
//                    wifiManager.setWifiEnabled(true);
//                    System.out.println("静态ip设置成功");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.out.println("静态ip设置失败");
//                }
//
//            }
//        });
//
//    }
}