package editor.after.light.ultralight.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tranhoaison on 4/22/15.
 */

public class Device {

    private static Device self;
    /**
     * API >= 11 (3.0)
     */
    public static final boolean isHONEYCOMB = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
    /**
     * API >= 16 (4.1)
     */
    public static final boolean isJELLYBEAN = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN;
    /**
     * API >= 19 (4.4)
     */
    public static final boolean isKITKAT = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT;

    private boolean isActivated = false;
    private String deviceId = null;


    public static Device getInstance(Activity activity) {
        if (self == null) {
            self = new Device(activity);
        }
        return self;
    }

    @SuppressLint("DefaultLocale")
    public Device(Activity activity) {
        SharedPreferences sp = activity.getPreferences(Context.MODE_PRIVATE);
        isActivated = sp.getBoolean(Defines.KEY_ACTIVE, false);
    }

    public boolean isActivated() {
        return isActivated;
//        return true;
    }

    public void setActivated(Activity activity, boolean isActivated) {
        this.isActivated = isActivated;
        SharedPreferences.Editor spe = activity.getPreferences(Context.MODE_PRIVATE).edit();
        spe.putBoolean(Defines.KEY_ACTIVE, isActivated);
        spe.commit();
    }
}
