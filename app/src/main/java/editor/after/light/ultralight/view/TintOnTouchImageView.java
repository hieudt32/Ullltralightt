package editor.after.light.ultralight.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TintOnTouchImageView extends AppCompatImageView {

    public TintOnTouchImageView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        if (motionevent.getAction() != 0) {
            if (motionevent.getAction() == 1) {
                setColorFilter(null);
            }
        } else {
            setColorFilter(Color.argb(150, 0, 0, 0), android.graphics.PorterDuff.Mode.SRC_ATOP);
        }
        return super.onTouchEvent(motionevent);
    }
}
