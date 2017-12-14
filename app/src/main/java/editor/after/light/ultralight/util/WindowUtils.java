package editor.after.light.ultralight.util;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class WindowUtils {
    public static void hideStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= 16) {
            View decorView = window.getDecorView();
            if (decorView == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                decorView.setSystemUiVisibility(5380);
                return;
            } else if (Build.VERSION.SDK_INT >= 16) {
                decorView.setSystemUiVisibility(1284);
                return;
            } else {
                return;
            }
        }
        window.setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
    }

    public static void makeWindowTransparent(Window window) {
        window.setBackgroundDrawable(new ColorDrawable(0));
    }

    public static void setGravityBottomAndMatchWidth(Window window) {
        LayoutParams wlp = window.getAttributes();
        wlp.gravity = 80;
        wlp.width = -1;
        window.setAttributes(wlp);
    }
}
