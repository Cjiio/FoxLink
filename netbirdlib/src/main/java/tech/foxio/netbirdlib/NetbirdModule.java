package tech.foxio.netbirdlib;

import static tech.foxio.netbirdlib.tool.VPNService.VPN_REQUEST_CODE;

import android.ConnectionListener;
import android.URLOpener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.VpnService;
import android.os.IBinder;
import android.util.Log;

import tech.foxio.netbirdlib.tool.ServiceStateListener;
import tech.foxio.netbirdlib.tool.VPNService;

public class NetbirdModule {
    public static final String LOG_TAG = "netbirdlib";
    public final ServiceConnection serviceIPC;
    private VPNService.MyLocalBinder mBinder;
    ServiceStateListener serviceStateListener;
    ConnectionListener connectionListener;
    URLOpener urlOpener;
    private static NetbirdModule instance;
    private final Context context;

    public static synchronized void Init(Context context) {
        if (instance == null) {
            instance = new NetbirdModule(context);
        }
    }

    public static void Destroy() {
        if (instance != null) {
            instance.unbindFromServiceOnDestroy();
            instance = null;
        }
    }

    public NetbirdModule(Context context) {
        this.context = context.getApplicationContext();
        urlOpener = new MyURLOpener();
        serviceIPC = new MyServiceConnection();
        serviceStateListener = new MyServiceStateListener();
        connectionListener = new MyConnectionListener();
        mBinder = new VPNService().new MyLocalBinder();
    }

    public static void startService() {
        Log.d(LOG_TAG, "try to start service");
        if (instance == null) {
            throw new IllegalStateException("NetbirdModule has not been initialized.");
        }
        Intent intent = new Intent(instance.context, VPNService.class);
        instance.context.startService(intent);
        instance.bindToService();
    }

    private void bindToService() {
        Log.d(LOG_TAG, "try to bind the service");
        context.bindService(new Intent(context, VPNService.class), serviceIPC, Context.BIND_ABOVE_CLIENT);
    }

    public void unbindFromServiceOnDestroy() {
        Log.d(LOG_TAG, "unbindFromServiceOnDestroy");
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
            instance.mBinder.stopEngine();
        } else {
            instance.mBinder.runEngine(instance.urlOpener);
        }
    }

    public static boolean hasVpnPermission(Activity activity) {
        Intent intentPrepare = VpnService.prepare(activity);
        if (intentPrepare != null) {
            Log.d(LOG_TAG, "open vpn permission dialog");
            activity.startActivityForResult(intentPrepare, VPN_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void unbindFromServiceAfterCancel() {
        Log.d(LOG_TAG, "unbindFromServiceAfterCancel");
        if (mBinder == null) {
            return;
        }
        unBindFromService();
        mBinder = null;
    }

    private void unBindFromService() {
        Log.d(LOG_TAG, "unBindFromService");
        mBinder.removeConnectionStateListener();
        mBinder.removeServiceStateListener(serviceStateListener);
        context.unbindService(serviceIPC);
    }

    private static class MyServiceStateListener implements ServiceStateListener {

        @Override
        public void onStarted() {
            Log.d(LOG_TAG, "onStarted");

        }

        @Override
        public void onStopped() {
            Log.d(LOG_TAG, "STATE: ServiceStateListener stopped");
        }

        @Override
        public void onError(String msg) {
            Log.d(LOG_TAG, "onError " + msg);
        }
    }

    private static class MyURLOpener implements URLOpener {
        @Override
        public void open(String s) {
            Log.d(LOG_TAG, "open " + s);
        }
    }

    private static class MyConnectionListener implements ConnectionListener {
        @Override
        public void onAddressChanged(String s, String s1) {
            Log.d(LOG_TAG, "onAddressChanged " + s + " " + s1);
        }

        @Override
        public void onConnected() {
            Log.d(LOG_TAG, "STATE: ConnectionListener connected");
        }

        @Override
        public void onConnecting() {
            Log.d(LOG_TAG, "STATE: ConnectionListener connecting");
        }

        @Override
        public void onDisconnected() {
            Log.d(LOG_TAG, "STATE: ConnectionListener disconnected");
        }

        @Override
        public void onDisconnecting() {
            Log.d(LOG_TAG, "STATE: ConnectionListener disconnecting");
        }

        @Override
        public void onPeersListChanged(long l) {
            Log.d(LOG_TAG, "STATE: ConnectionListener onPeersListChanged " + l);
        }
    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(LOG_TAG, "on service connected");
            mBinder = (VPNService.MyLocalBinder) service;
            mBinder.setConnectionStateListener(connectionListener);
            mBinder.addServiceStateListener(serviceStateListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(LOG_TAG, "onServiceDisconnected");
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
