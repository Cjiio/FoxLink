package tech.foxio.foxlink.tool;

public interface ServiceStateListener {
    void onStarted();
    void onStopped();
    void onError(String msg);

}
