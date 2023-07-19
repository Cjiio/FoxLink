package tech.foxio.netbirdlib.tool;

public interface ServiceStateListener {
    void onStarted();
    void onStopped();
    void onError(String msg);
}
