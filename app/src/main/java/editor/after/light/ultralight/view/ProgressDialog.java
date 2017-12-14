package editor.after.light.ultralight.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import editor.after.light.ultralight.R;


public class ProgressDialog extends Dialog {
    private AnimationDrawable mFrameAnimation;
    private ImageView mImageProgressView;
    private TextView mTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterContentView();
    }

    public void setContentView(View view) {
        super.setContentView(view);
        afterContentView();
    }

    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterContentView();
    }

    private void afterContentView() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = -1;
        getWindow().setAttributes(lp);
        this.mImageProgressView = (ImageView) findViewById(R.id.progress);
        this.mTextView = (TextView) findViewById(R.id.message);
        this.mFrameAnimation = (AnimationDrawable) this.mImageProgressView.getBackground();
    }

    public ProgressDialog(Context context) {
        super(context, R.style.BaseDialog);
        setContentView((int) R.layout.dialog_progress);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, R.style.BaseDialog);
        setContentView((int) R.layout.dialog_progress);
    }

    public ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView((int) R.layout.dialog_progress);
    }

    public void show() {
        if (this.mFrameAnimation != null) {
            this.mFrameAnimation.start();
        }
        super.show();
    }

    public void hide() {
        if (this.mFrameAnimation != null) {
            this.mFrameAnimation.stop();
        }
        super.hide();
    }

    public void dismiss() {
        if (this.mFrameAnimation != null) {
            this.mFrameAnimation.stop();
        }
        super.dismiss();
    }

    public void setMessage(String title) {
        if (this.mTextView != null) {
            this.mTextView.setText(title);
        }
    }
}
