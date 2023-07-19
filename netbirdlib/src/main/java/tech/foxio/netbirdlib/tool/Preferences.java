package tech.foxio.netbirdlib.tool;

import android.content.Context;

import android.Android;

public class Preferences {

   public  static android.Preferences newPreferences(Context context) {
       return Android.newPreferences(configFile(context));
   }

   public static String configFile(Context context){
       return context.getFilesDir().getPath() + "/netbird.cfg";
   }
}
