package editor.after.light.ultralight.view;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager.LayoutParams;

import editor.after.light.ultralight.util.WindowUtils;


public class CommonDialog extends DialogFragment {
    private IDismissHandler mDismissHandler;

    public interface IDismissHandler {
        void onDialogDismiss();
    }

    public CommonDialog() {
        this.mDismissHandler = null;
    }

    private void afterShow() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().clearFlags(8);
        }
    }

    private void beforeShow() {
        getDialog().getWindow().setFlags(8, 8);
    }

    private void copySystemUiVisibility() {
        getDialog().getWindow().getDecorView().setSystemUiVisibility(getActivity().getWindow().getDecorView().getSystemUiVisibility());
    }

    private void hideStatusBarInDialog() {
        copySystemUiVisibility();
        beforeShow();
        getDialog().setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                CommonDialog.this.afterShow();
            }
        });
    }

    protected void makeFitBottom() {
        WindowUtils.setGravityBottomAndMatchWidth(getDialog().getWindow());
    }

    protected void makeFitScreen() {
        LayoutParams attributes = getDialog().getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        getDialog().getWindow().setAttributes(attributes);
    }

    protected void makeFitWidth() {
        LayoutParams attributes = getDialog().getWindow().getAttributes();
        attributes.width = -1;
        getDialog().getWindow().setAttributes(attributes);
    }

    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IDismissHandler) {
            this.mDismissHandler = (IDismissHandler) activity;
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setStyle(STYLE_NO_TITLE, 0);
    }

    public void onStart() {
        super.onStart();
        updateWindowParams();
    }

    public void onStop() {
        super.onStop();
        if (this.mDismissHandler != null) {
            this.mDismissHandler.onDialogDismiss();
        }
    }

    protected void updateWindowParams() {
        hideStatusBarInDialog();
        WindowUtils.makeWindowTransparent(getDialog().getWindow());
    }
}
