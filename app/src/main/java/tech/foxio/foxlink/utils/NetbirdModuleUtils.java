package tech.foxio.foxlink.utils;


import android.Android;
import android.ConnectionListener;
import android.ErrListener;
import android.PeerInfoArray;
import android.Preferences;
import android.SSOListener;
import android.URLOpener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.VpnService;
import android.os.IBinder;

import com.safframework.log.L;

import tech.foxio.foxlink.tool.DeviceName;
import tech.foxio.foxlink.tool.ServiceStateListener;
import tech.foxio.foxlink.tool.VPNService;

public class NetbirdModuleUtils {
    public static final String LOG_TAG = "netbirdlib";
    public static ServiceConnection serviceIPC;
    public static VPNService.MyLocalBinder mBinder;
    public static ServiceStateListener serviceStateListener;
    public static ConnectionListener connectionListener;
    public static URLOpener urlOpener;
    public static NetbirdModuleUtils instance;
    public Context context;

    public NetbirdModuleUtils(Activity activity) {
        L.d(LOG_TAG, "init");
        this.context = activity.getApplicationContext();
        urlOpener = new MyURLOpener();
        serviceIPC = new MyServiceConnection();
        mBinder = new VPNService().new MyLocalBinder();
    }

    public static void Destroy() {
        L.d(LOG_TAG, "destroy");
        if (instance != null) {
            instance.unbindFromServiceOnDestroy();
            instance = null;
        }
    }

    public static synchronized void Init(Activity activity) {
        if (instance == null) {
            instance = new NetbirdModuleUtils(activity);
        }
    }

    public static void setServiceStateListener(ServiceStateListener serviceStateListener) {
        NetbirdModuleUtils.serviceStateListener = serviceStateListener;
    }

    public static void setConnectionListener(ConnectionListener connectionListener) {
        NetbirdModuleUtils.connectionListener = connectionListener;
    }

    public static void setPreShareKey(String preShareKey) {
        Preferences preferences = new Preferences(tech.foxio.foxlink.tool.Preferences.configFile(instance.context));
        preferences.setPreSharedKey(preShareKey);
        try {
            preferences.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String inUsePreShareKey() {
        Preferences preferences = new Preferences(tech.foxio.foxlink.tool.Preferences.configFile(instance.context));
        String preShareKey;
        try {
            preShareKey = preferences.getPreSharedKey();
            if (!preShareKey.trim().isEmpty()) {
                return preShareKey;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    //检查服务器
    public static void CheckServer(
            String url,
            SSOListener ssoListener
    ) {
        try {
            Android.newAuth(tech.foxio.foxlink.tool.Preferences.configFile(instance.context), url)
                    .saveConfigIfSSOSupported(ssoListener);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //更改服务器
    public static void ChangeServer(
            String url,
            String key,
            ErrListener errListener
    ) {
        try {
            Android.newAuth(tech.foxio.foxlink.tool.Preferences.configFile(instance.context), url)
                    .loginWithSetupKeyAndSaveConfig(errListener, key, DeviceName.getDeviceName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PeerInfoArray getPeers() {
        return mBinder.peersInfo();
    }

    public static void startService() {
        L.d(LOG_TAG, "try to start service");
        if (instance == null) {
            throw new IllegalStateException("NetbirdModule has not been initialized.");
        }
        Intent intent = new Intent(instance.context, VPNService.class);
        intent.setAction(VpnService.SERVICE_INTERFACE);
        instance.context.startService(intent);
        instance.bindToService();
    }

    private void bindToService() {
        L.d(LOG_TAG, "try to bind the service");
        context.bindService(new Intent(context, VPNService.class), serviceIPC, Context.BIND_ABOVE_CLIENT);
    }

    public void unbindFromServiceOnDestroy() {
        L.d(LOG_TAG, "unbindFromServiceOnDestroy");
        if (mBinder == null) {
            return;
        }
        unBindFromService();
        mBinder = null;
    }

    public static void switchConnect(Boolean z) {
        if (instance == null) {
            throw new IllegalStateException("NetbirdModule has not been initialized.");
        }
        if (!z) {
            mBinder.stopEngine();
        } else {
            mBinder.runEngine(urlOpener);
        }
    }

    public static boolean hasVpnPermission(Activity activity) {
        return mBinder.hasVpnPermission(activity);
    }

    public void unbindFromServiceAfterCancel() {
        L.d(LOG_TAG, "unbindFromServiceAfterCancel");
        if (mBinder == null) {
            return;
        }
        unBindFromService();
        mBinder = null;
    }

    private void unBindFromService() {
        L.d(LOG_TAG, "unBindFromService");
        mBinder.removeConnectionStateListener();
        mBinder.removeServiceStateListener(serviceStateListener);
        context.unbindService(serviceIPC);
    }

    private static class MyURLOpener implements URLOpener {
        @Override
        public void open(String s) {
            L.d(LOG_TAG, "open " + s);
        }
    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            L.d(LOG_TAG, "on service connected");
            mBinder = (VPNService.MyLocalBinder) service;
            mBinder.setConnectionStateListener(connectionListener);
            mBinder.addServiceStateListener(serviceStateListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            L.d(LOG_TAG, "onServiceDisconnected");
        }

        @Override
        public void onBindingDied(ComponentName name) {
            ServiceConnection.super.onBindingDied(name);
        }

        @Override
        public void onNullBinding(ComponentName name) {
            ServiceConnection.super.onNullBinding(name);
        }
    }
}
