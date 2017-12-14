package editor.after.light.ultralight.task;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import editor.after.light.ultralight.activity.EditActivity;
import editor.after.light.ultralight.model.Database;
import editor.after.light.ultralight.model.Filter;
import editor.after.light.ultralight.model.FilterCategory;
import editor.after.light.ultralight.util.BitmapUtils;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;

/**
 * Created by st on 11/10/16.
 */

public class GetFilterThumbnailTask extends AsyncTask<Void, Void, FilterCategory> {
    private Bitmap mBitmap;
    private Context mContext;
    private Activity mActivity;
    private Uri mUri;
    private FilterCategory mFilterCategory;
    private LinkedHashMap<String, Filter> mFilterHashMap;

    public GetFilterThumbnailTask(Context context, Activity activity, Uri uri, FilterCategory filterCategory, LinkedHashMap<String, Filter> filterHashMap) {
        mContext = context;
        mActivity = activity;
        mUri = uri;
        mFilterCategory = filterCategory;
        mFilterHashMap = filterHashMap;
    }

    protected void onPreExecute() {

    }

    protected FilterCategory doInBackground(Void... params) {
        mBitmap = BitmapUtils.getBitmapFromUri(mContext, mUri);
        Bitmap thumb = BitmapUtils.resize(mBitmap, 128, 128);

        for (int i = 0; i < mFilterCategory.getFilterList().size(); i++) {
            Filter filter = mFilterCategory.getFilterList().get(i);
            GPUImage thumbView = new GPUImage(mContext);
            InputStream ips = null;
            GPUImageToneCurveFilter toneCurveFilter = new GPUImageToneCurveFilter();
            try {
                String key = "filters/" + mFilterCategory.getFilterCategoryId() + "/" + filter.getFilterId() + ".acv";
                ips = mContext.getAssets().open(key);
                toneCurveFilter.setFromCurveFileInputStream(ips);
                /*
                PointF[] mRgbCompositeControlPoints;
                PointF[] mRedControlPoints;
                PointF[] mGreenControlPoints;
                PointF[] mBlueControlPoints;
                PointF[] defaultCurvePoints = new PointF[]{new PointF(0.0f, 0.0f), new PointF(0.5f, 0.5f), new PointF(0.1f, 0.1f)};
                mRgbCompositeControlPoints = defaultCurvePoints;
                mRedControlPoints = defaultCurvePoints;
                mGreenControlPoints = defaultCurvePoints;
                mBlueControlPoints = defaultCurvePoints;
                toneCurveFilter.setRgbCompositeControlPoints(mRgbCompositeControlPoints);
                */
                thumbView.setFilter(toneCurveFilter);
                Bitmap bitmap = thumbView.getBitmapWithFilterApplied(thumb);
                filter.setKey(key);
                filter.setIconImage(bitmap);
                mFilterHashMap.put(key, filter);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        mFilterCategory.setBHasLoadedAllFilterIcon(true);
        return mFilterCategory;
    }

    protected void onPostExecute(FilterCategory filterCategory) {
        ((EditActivity)mActivity).showFilterThumbsWithFilterCategory(mFilterCategory);
    }
}
