package tech.foxio.netbirdlib;

import android.ConnectionListener;
import android.URLOpener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import tech.foxio.netbirdlib.tool.ServiceStateListener;
import tech.foxio.netbirdlib.tool.VPNService;

public class NetbirdModule {
    public static final String LOG_TAG = "netbirdlib";
    ConnectionListener connectionListener;
    private VPNService.MyLocalBinder mBinder;
    public final ServiceConnection serviceIPC;
    ServiceStateListener serviceStateListener;
    URLOpener urlOpener;
    Activity activity;

    public NetbirdModule(Context activity) {
        this.activity = (Activity) activity;
        urlOpener = new MyURLOpener();
        serviceIPC = new MyServiceConnection();
        serviceStateListener = new MyServiceStateListener();
        connectionListener = new MyConnectionListener();
        mBinder = new VPNService().new MyLocalBinder();
    }

    public void startService() {
        Log.d(LOG_TAG, "try to start service");
        Intent intent = new Intent(activity, VPNService.class);
        activity.startService(intent);
        bindToService();
    }

    private void bindToService() {
        Log.d(LOG_TAG, "try to bind the service");
        activity.bindService(new Intent(activity, VPNService.class), serviceIPC, Context.BIND_ABOVE_CLIENT);
    }

    public void unbindFromServiceOnDestroy() {
        Log.d(LOG_TAG, "unbindFromServiceOnDestroy");
        if (mBinder == null){
            return;
        }
        unBindFromService();
        mBinder = null;
    }

    public void switchConnect(Boolean z) {
        if (!z){
            mBinder.stopEngine();
        }else if (mBinder.hasVpnPermission(activity)){
            mBinder.runEngine(urlOpener);
        }
    }

    public void unbindFromServiceAfterCancel() {
        Log.d(LOG_TAG, "unbindFromServiceAfterCancel");
        if (mBinder == null){
            return;
        }
        unBindFromService();
        mBinder = null;
    }

    private void unBindFromService() {
        Log.d(LOG_TAG, "unBindFromService");
        mBinder.removeConnectionStateListener();
        mBinder.removeServiceStateListener(serviceStateListener);
        activity.unbindService(serviceIPC);
    }

    private class MyServiceStateListener implements ServiceStateListener {

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

    private class MyURLOpener implements URLOpener {
        @Override
        public void open(String s) {
            Log.d(LOG_TAG, "open " + s);
        }
    }

    private class MyConnectionListener implements ConnectionListener {
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
