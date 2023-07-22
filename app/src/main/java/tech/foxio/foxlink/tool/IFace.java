package tech.foxio.foxlink.tool;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.system.OsConstants;
import android.util.Log;

import java.util.LinkedList;

import android.TunAdapter;

import tech.foxio.foxlink.tool.wg.BackendException;
import tech.foxio.foxlink.tool.wg.InetNetwork;

class IFace implements TunAdapter {

    private static final String LOGTAG = "IFace";
    private final VPNService tunService;

    public IFace(VPNService tunService) {
        this.tunService = tunService;
    }

    @Override
    public long configureInterface(String address, long mtu, String dns, String routesString) throws Exception {
        LinkedList<Route> routes = toRoutes(routesString);

        InetNetwork addr = InetNetwork.parse(address);
        try {
            return createTun(addr.getAddress().getHostAddress(), addr.getMask(), (int) mtu, dns, routes);
        }catch (Exception e) {
            Log.e(LOGTAG, "failed to create tunnel", e);
            throw e;
        }
    }


    private int createTun(String ip, int prefixLength, int mtu, String dns, LinkedList<Route> routes) throws Exception {
        VpnService.Builder builder = tunService.getBuilder();
        builder.addAddress(ip, prefixLength);
        builder.allowFamily(OsConstants.AF_INET);
        builder.allowFamily(OsConstants.AF_INET6);
        builder.setMtu(mtu);
        if(dns != null && !dns.isEmpty()) {
            builder.addDnsServer(dns);
            Log.d(LOGTAG, "add DNS server: "+dns);
        }

        for (Route r : routes) {
            builder.addRoute(r.addr, r.prefixLength);
            Log.d(LOGTAG, "add route: "+r.addr+"/"+r.prefixLength);
        }

        disallowApp(builder,  "com.google.android.projection.gearhead");
        disallowApp(builder, "com.google.android.apps.chromecast.app");
        disallowApp(builder, "com.google.android.apps.messaging");
        disallowApp(builder, "com.google.stadia.android");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            builder.setMetered(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tunService.setUnderlyingNetworks(null);
        }

        builder.setBlocking(true);
        try (final ParcelFileDescriptor tun = builder.establish()) {
            if (tun == null) {
                throw new BackendException(BackendException.Reason.TUN_CREATION_ERROR);
            }
            return tun.detachFd();
        }
    }

    private void disallowApp(VpnService.Builder builder, String packageName) {
        try {
            builder.addDisallowedApplication(packageName);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void updateAddr(String s) throws Exception {
    }

    private LinkedList<Route> toRoutes(String routesString) {
        LinkedList<Route> routesList = new LinkedList<>();
        if(routesString == null) {
            return routesList;
        }
        if(routesString.isEmpty()) {
            return routesList;
        }
        String[] routes = routesString.split(";");
        for(String route : routes) {
            try {
                Route r = new Route(route);
                routesList.add(r);
            } catch (Exception e) {
                Log.e(LOGTAG, "invalid route: "+ route);
            }
        }
        return routesList;
    }
}