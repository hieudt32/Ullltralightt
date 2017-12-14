package editor.after.light.ultralight.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import editor.after.light.ultralight.activity.EditActivity;
import editor.after.light.ultralight.util.BitmapUtils;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;

/**
 * Created by st on 11/10/16.
 */

public class ApplyFilterTask extends AsyncTask<Void, Void, Bitmap> {
    private EditActivity mActivity;
    private Context mContext;
    private Uri mUri;
    private String mKey;
    private GPUImageFilter mFilter;
    private LinkedHashMap<String, GPUImageFilter> mFilterLinkedHashMap;
    private ArrayList<GPUImageFilter> mArrFilter;

    public ApplyFilterTask(Context context, EditActivity activity, String key, GPUImageFilter filter, Uri uri, LinkedHashMap<String, GPUImageFilter> filterLinkedHashMap) {
        mContext = context;
        mActivity = activity;
        mKey = key;
        mFilter = filter;
        mUri = uri;
        mFilterLinkedHashMap = filterLinkedHashMap;
    }

    protected void onPreExecute() {
        mArrFilter = new ArrayList();
    }

    protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap = BitmapUtils.getBitmapFromUri(mContext, mUri);
        GPUImage thumbView = new GPUImage(mContext);
        if (!mKey.equals("none")) {
            mFilterLinkedHashMap.put(mKey, this.mFilter);
        }
        for (String key : mFilterLinkedHashMap.keySet()) {
            mArrFilter.add(mFilterLinkedHashMap.get(key));
        }
        thumbView.setFilter(new GPUImageFilterGroup(mArrFilter));
        return thumbView.getBitmapWithFilterApplied(bitmap);
    }

    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mActivity.finishLoadImage(bitmap);
        deleteCacheFolder();
    }

    private void deleteCacheFolder() {
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "Cache");
        if (dir.exists() && dir.isDirectory()) {
            String[] children = dir.list();
            for (String file : children) {
                new File(dir, file).delete();
            }
        }
    }
}