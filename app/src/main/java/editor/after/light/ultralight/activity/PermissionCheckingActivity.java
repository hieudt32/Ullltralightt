package editor.after.light.ultralight.activity;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import editor.after.light.ultralight.util.HideStatusBarActivity;

public class PermissionCheckingActivity extends HideStatusBarActivity {
    public static final int PERMISSION_REQUEST_CODE = 42;
    private Runnable mPendingRunnable;

    @TargetApi(16)
    protected void checkReadWriteRightAndExecute(Runnable runnable) {
        checkRightsAndExecute(runnable, "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    protected void checkRightsAndExecute(@NonNull Runnable r, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] arr$ = permissions;
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                if (hasPermission(arr$[i$])) {
                    i$++;
                } else {
                    this.mPendingRunnable = r;
                    requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                    return;
                }
            }
        }
        runOnUiThread(r);
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected boolean hasPermission(String str) {
        return checkSelfPermission(str) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length != 0) {
            switch (requestCode) {
                case PERMISSION_REQUEST_CODE /*42*/:
                    boolean granted = true;
                    for (int res : grantResults) {
                        if (res != 0) {
                            granted = false;
                            if (granted && this.mPendingRunnable != null) {
                                runOnUiThread(this.mPendingRunnable);
                                return;
                            }
                        }
                    }
                    if (granted) {
                    }
                default:
            }
        }
    }
}
