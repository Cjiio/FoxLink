package tech.foxio.foxlink.tool;

import android.Android;
import android.content.Context;

public class Preferences {

   public  static android.Preferences newPreferences(Context context) {
       return Android.newPreferences(configFile(context));
   }

   public static String configFile(Context context){
       return context.getFilesDir().getPath() + "/foxlink.cfg";
   }
}
