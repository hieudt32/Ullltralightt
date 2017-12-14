package editor.after.light.ultralight.task;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import editor.after.light.ultralight.activity.EditActivity;
import editor.after.light.ultralight.activity.MainActivity;
import editor.after.light.ultralight.model.Blend;
import editor.after.light.ultralight.model.Filter;
import editor.after.light.ultralight.model.Texture;
import editor.after.light.ultralight.util.BitmapUtils;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDarkenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDivideBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHardLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageScreenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSoftLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;

/**
 * Created by st on 11/10/16.
 */

public class GetBlendThumbnailTask extends AsyncTask<Void, Void, ArrayList<Bitmap>> {
    private Bitmap mBitmap;
    private Context mContext;
    private Activity mActivity;
    private Uri mUri;
    private Texture mTexture;
    private List<Blend> mBlendList;

    public GetBlendThumbnailTask(Context context, Activity activity, Uri uri, Texture texture, List<Blend> blendList) {
        mContext = context;
        mActivity = activity;
        mUri = uri;
        mTexture = texture;
        mBlendList = blendList;
    }

    protected void onPreExecute() {

    }

    protected ArrayList<Bitmap> doInBackground(Void... params) {
        ArrayList<Bitmap> bitmapArrayList = new ArrayList();
        mBitmap = BitmapUtils.getBitmapFromUri(mContext, mUri);
        String s = "textures/" + mTexture.getTextureCategoryId() + "/" + mTexture.getTextureName() + ".jpg";
        try {
            Bitmap textureThumbnail = BitmapFactory.decodeStream(MainActivity.getContext().getAssets().open(s));
            Bitmap thumb = BitmapUtils.resize(mBitmap, 128, 128);
            for (int i = 0; i < mBlendList.size(); i++) {
                Blend tmpBlend = mBlendList.get(i);
                GPUImage thumbView = new GPUImage(mContext);
                tmpBlend.setGPUImageFilter(getBlendFilter(textureThumbnail, i));
                thumbView.setFilter(tmpBlend.getGPUImageFilter());
                Bitmap bitmap = thumbView.getBitmapWithFilterApplied(thumb);
                tmpBlend.setIconImage(bitmap);
                bitmapArrayList.add(bitmap);
            }
            return bitmapArrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
        ((EditActivity)mActivity).finishShowBlendThumbs(bitmaps);
    }

    public static GPUImageFilter getBlendFilter(Bitmap bitmap, int position) {
        switch (position) {
            case 0:
                GPUImageDifferenceBlendFilter differenceBlendFilter = new GPUImageDifferenceBlendFilter();
                differenceBlendFilter.setBitmap(bitmap);
                return differenceBlendFilter;
            case 1:
                GPUImageColorBurnBlendFilter colorBurnBlendFilter = new GPUImageColorBurnBlendFilter();
                colorBurnBlendFilter.setBitmap(bitmap);
                return colorBurnBlendFilter;
            case 2:
                GPUImageColorDodgeBlendFilter colorDodgeBlendFilter = new GPUImageColorDodgeBlendFilter();
                colorDodgeBlendFilter.setBitmap(bitmap);
                return colorDodgeBlendFilter;
            case 3:
                GPUImageDarkenBlendFilter darkenBlendFilter = new GPUImageDarkenBlendFilter();
                darkenBlendFilter.setBitmap(bitmap);
                return darkenBlendFilter;
            case 4:
                GPUImageDissolveBlendFilter dissolveBlendFilter = new GPUImageDissolveBlendFilter();
                dissolveBlendFilter.setBitmap(bitmap);
                return dissolveBlendFilter;
            case 5:
                GPUImageLightenBlendFilter lightenBlendFilter = new GPUImageLightenBlendFilter();
                lightenBlendFilter.setBitmap(bitmap);
                return lightenBlendFilter;
            case 6:
                GPUImageAddBlendFilter addBlendFilter = new GPUImageAddBlendFilter();
                addBlendFilter.setBitmap(bitmap);
                return addBlendFilter;
            case 7:
                GPUImageDivideBlendFilter divideBlendFilter = new GPUImageDivideBlendFilter();
                divideBlendFilter.setBitmap(bitmap);
                return divideBlendFilter;
            case 8:
                GPUImageMultiplyBlendFilter multiplyBlendFilter = new GPUImageMultiplyBlendFilter();
                multiplyBlendFilter.setBitmap(bitmap);
                return multiplyBlendFilter;
            case 9:
                GPUImageOverlayBlendFilter overlayBlendFilter = new GPUImageOverlayBlendFilter();
                overlayBlendFilter.setBitmap(bitmap);
                return overlayBlendFilter;
            case 10:
                GPUImageScreenBlendFilter screenBlendFilter = new GPUImageScreenBlendFilter();
                screenBlendFilter.setBitmap(bitmap);
                return screenBlendFilter;
            case 11:
                GPUImageAlphaBlendFilter alphaBlendFilter = new GPUImageAlphaBlendFilter();
                alphaBlendFilter.setBitmap(bitmap);
                return alphaBlendFilter;
            case 12:
                GPUImageColorBlendFilter colorBlendFilter = new GPUImageColorBlendFilter();
                colorBlendFilter.setBitmap(bitmap);
                return colorBlendFilter;
            case 13:
                GPUImageLinearBurnBlendFilter linearBurnFilter = new GPUImageLinearBurnBlendFilter();
                linearBurnFilter.setBitmap(bitmap);
                return linearBurnFilter;
            case 14:
                GPUImageSoftLightBlendFilter softLightBlendFilter = new GPUImageSoftLightBlendFilter();
                softLightBlendFilter.setBitmap(bitmap);
                return softLightBlendFilter;
            case 15:
                GPUImageChromaKeyBlendFilter chromaKeyBlendFilter = new GPUImageChromaKeyBlendFilter();
                chromaKeyBlendFilter.setBitmap(bitmap);
                return chromaKeyBlendFilter;
            case 16:
                GPUImageExclusionBlendFilter exclusionBlendFilter = new GPUImageExclusionBlendFilter();
            exclusionBlendFilter.setBitmap(bitmap);
            return exclusionBlendFilter;
            case 17:
                GPUImageHardLightBlendFilter hardLightBlendFilter = new GPUImageHardLightBlendFilter();
                hardLightBlendFilter.setBitmap(bitmap);
                return hardLightBlendFilter;
            default:
                GPUImageScreenBlendFilter screenBlendFilter1 = new GPUImageScreenBlendFilter();
                screenBlendFilter1.setBitmap(bitmap);
                return screenBlendFilter1;
        }
    }
}
