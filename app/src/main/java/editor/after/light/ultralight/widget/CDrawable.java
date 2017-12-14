package editor.after.light.ultralight.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by tranhoaison on 4/10/15.
 */
public class CDrawable extends Drawable
{
    private Bitmap mBitmap;
    private float mFWidth;
    private float mFHeight;
    private final Paint mPaint = new Paint(6);
    private final Rect mRect = new Rect();

    public CDrawable()
    {
    }

    public Bitmap getBimap()
    {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        mBitmap = bitmap;
        mFWidth = mBitmap.getWidth();
        mFHeight = mBitmap.getHeight();
    }

    public void draw(Canvas canvas)
    {
        if (mBitmap == null)
        {
            return;
        }
        float f = getBounds().width();
        float f1 = getBounds().height();
        float f2 = f / mFWidth;
        float f3 = f1 / mFHeight;
        int i;
        int k;
        int l;
        int i1;
        int j1;
        int k1;
        if (f2 <= f3)
        {
            f2 = f3;
        }
        i = (int)(f2 * mFWidth);
        k = (int)(f2 * mFHeight);
        l = (int)((f - (float)i) / 2.0F);
        i1 = (int)((f + (float)i) / 2.0F);
        j1 = (int)((f1 - (float)k) / 2.0F);
        k1 = (int)((f1 + (float)k) / 2.0F);
        mRect.set(l, j1, i1, k1);
        canvas.drawBitmap(mBitmap, null, mRect, mPaint);
    }

    public int getOpacity()
    {
        return 0;
    }

    public void setAlpha(int i)
    {
        if (i != mPaint.getAlpha())
        {
            mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorfilter)
    {
        mPaint.setColorFilter(colorfilter);
        invalidateSelf();
    }
}
