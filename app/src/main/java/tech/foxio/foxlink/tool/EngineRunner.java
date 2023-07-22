package tech.foxio.foxlink.tool;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import android.Android;
import android.Client;
import android.ConnectionListener;
import android.PeerInfoArray;
import android.URLOpener;

import tech.foxio.foxlink.BuildConfig;

class EngineRunner {

    private static final String LOGTAG = "EngineRunner";
    private final RouteNotifier routeNotifier;
    private boolean engineIsRunning = false;
    Set<ServiceStateListener> serviceStateListeners = new HashSet<>();
    private final Client goClient;

    public EngineRunner(VPNService vpnService) {
        routeNotifier = new RouteNotifier(vpnService);
        IFace iFace = new IFace(vpnService);
        goClient = Android.newClient(
                Preferences.configFile(vpnService),
                DeviceName.getDeviceName(),
                iFace,
                new IFaceDiscover(),
                routeNotifier);

        if (BuildConfig.DEBUG) {
            goClient.setTraceLogLevel();
        }
    }

    public synchronized void run(URLOpener urlOpener) {
        Log.d(LOGTAG, "run engine");
        if (engineIsRunning) {
            return;
        }

        engineIsRunning = true;
        Runnable r = () -> {
            try {
                notifyServiceStateListeners(true);
                goClient.run(urlOpener);
            } catch (Exception e) {
                Log.e(LOGTAG, "goClient error", e);
                notifyError(e);
            } finally {
                engineIsRunning = false;
                notifyServiceStateListeners(false);
            }
            Log.e(LOGTAG, "service stopped");

        };
        new Thread(r).start();
    }

    public synchronized boolean isRunning() {
        return engineIsRunning;
    }

    public synchronized void setConnectionListener(ConnectionListener listener) {
        goClient.setConnectionListener(listener);
    }

    public synchronized void removeStatusListener() {
        goClient.removeConnectionListener();
    }

    public synchronized void addServiceStateListener(ServiceStateListener serviceStateListener) {
        if (engineIsRunning) {
            serviceStateListener.onStarted();
        } else {
            serviceStateListener.onStopped();
        }
        serviceStateListeners.add(serviceStateListener);
    }

    public synchronized void removeServiceStateListener(ServiceStateListener serviceStateListener) {
        serviceStateListeners.remove(serviceStateListener);
    }

    public synchronized void stop() {
        goClient.stop();
    }

    public PeerInfoArray peersInfo() {
        return goClient.peersList();
    }

    private synchronized void notifyError(Exception e) {
        for (ServiceStateListener s : serviceStateListeners) {
            s.onError(e.getMessage());
        }
    }

    private synchronized void notifyServiceStateListeners(boolean engineIsRunning) {
        for (ServiceStateListener s : serviceStateListeners) {
            if (engineIsRunning) {
                s.onStarted();
            } else {
                s.onStopped();
            }
        }
    }
}
