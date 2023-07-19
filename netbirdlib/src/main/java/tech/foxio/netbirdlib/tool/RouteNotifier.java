package tech.foxio.netbirdlib.tool;

import android.RouteListener;
import android.content.Context;
import android.content.Intent;

public class RouteNotifier implements RouteListener {

    public static final String action = "action.ROUTE_CHANGED";
    private final Context context;

    RouteNotifier(Context context) {
        this.context = context;
    }

    @Override
    public void onNewRouteSetting() {
        sendBroadcast();
    }

    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.sendBroadcast(intent);
    }
}