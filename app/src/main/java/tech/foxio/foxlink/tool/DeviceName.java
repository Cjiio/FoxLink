package tech.foxio.foxlink.tool;

import android.os.Build;

public class DeviceName {
   public static String getDeviceName() {
      return Build.PRODUCT;
   }

}
