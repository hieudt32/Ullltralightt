package editor.after.light.ultralight.util;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import editor.after.light.ultralight.view.CommonDialog;


public class HideStatusBarActivity extends AppCompatActivity implements CommonDialog.IDismissHandler {
    protected void hideStatusBar() {
        WindowUtils.hideStatusBar(getWindow());
    }

    public boolean isForResult() {
        return getCallingActivity() != null;
    }

    protected void onCreate(@Nullable Bundle bundle) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(bundle);
    }

    public void onDialogDismiss() {
        hideStatusBar();
    }

    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }
}
