package editor.after.light.ultralight.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import editor.after.light.ultralight.R;

/**
 * Created by st on 10/18/16.
 */

public class CancelDialog extends BaseAlertDialog {
    private TextView mMessageView;
    private Button mBtnCancel;
    private Button mBtnOk;
    private TextView mTitleView;

    public CancelDialog(Context context) {
        super(context);
        init();
    }

    public CancelDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public CancelDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setContentView((int) R.layout.dialog_cancel);
        this.mTitleView = (TextView) findViewById(R.id.tv_title);
        this.mMessageView = (TextView) findViewById(R.id.tv_message);
        this.mBtnCancel = (Button) findViewById(R.id.button_cancel);
        this.mBtnOk = (Button) findViewById(R.id.button_ok);
    }

    public void noTitle() {
        this.mTitleView.setVisibility(View.GONE);
        findViewById(R.id.stroke_title_bottom).setVisibility(View.GONE);
    }

    public void setTitle(int resId) {
        setTitle(getContext().getResources().getString(resId));
    }

    public void setTitle(CharSequence title) {
        this.mTitleView.setText(title);
    }

    public void setMessage(String message) {
        this.mMessageView.setText(message);
    }

    public void setNegativeButton(String text, OnClickListener listener) {
        this.mBtnCancel.setText(text);
        setNegativeButton(this.mBtnCancel, listener);
    }

    public void setPositiveButton(String text, OnClickListener listener) {
        this.mBtnOk.setText(text);
        setPositiveButton(this.mBtnOk, listener);
    }
}

