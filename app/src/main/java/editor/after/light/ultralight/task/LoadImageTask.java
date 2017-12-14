package editor.after.light.ultralight.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import java.io.File;

import editor.after.light.ultralight.activity.EditActivity;
import editor.after.light.ultralight.util.BitmapUtils;
import editor.after.light.ultralight.view.ProgressDialog;


public class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
    private EditActivity mActivity;
    private Context mContext;
    private Uri mUri;

    public LoadImageTask(Context context, EditActivity activity, Uri uri) {
        mContext = context;
        mActivity = activity;
        mUri = uri;
    }

    protected void onPreExecute() {

    }

    protected Bitmap doInBackground(Void... params) {
        return BitmapUtils.getBitmapFromUri(mContext, mUri);
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
