package editor.after.light.ultralight.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.other.Defines;
import editor.after.light.ultralight.util.MonitoredActivity;
import editor.after.light.ultralight.util.Util;
import editor.after.light.ultralight.view.TintOnTouchImageView;


/**
 * Created by tranhoaison on 5/14/15.
 */
public class CropActivity extends MonitoredActivity implements View.OnClickListener {
    public static final String IMAGE_PATH = "filePath";
    private CropImageView cropImageView;
    private TintOnTouchImageView timgvBack;
    private TintOnTouchImageView timgvDone;
    private TintOnTouchImageView timgvRotateLeft;
    private TintOnTouchImageView timgvRotateRight;
    private TintOnTouchImageView timgvFlipH;
    private TintOnTouchImageView timgvFlipV;

    private Button btn_rate_free;
    private Button btn_rate_1x1;
    private Button btn_rate_3x2;
    private Button btn_rate_4x3;
    private Button btn_rate_4x6;
    private Button btn_rate_5x7;
    private Button btn_rate_8x10;
    private Button btn_rate_16x9;
    private Bitmap bitmap;
    private Bitmap croppedImage;
    private final Handler mHandler = new Handler();
    private String mImagePath;
    private Uri mSaveUri = null;
    private ContentResolver mContentResolver;
    private Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContentResolver = getContentResolver();

        DisplayMetrics widthMetrics = new DisplayMetrics();
        MainActivity.getContext().getWindowManager().getDefaultDisplay().getMetrics(widthMetrics);
        int width = widthMetrics.widthPixels;

        DisplayMetrics heightMetrics = new DisplayMetrics();
        MainActivity.getContext().getWindowManager().getDefaultDisplay().getMetrics(heightMetrics);
        int height = heightMetrics.heightPixels;

        float aspectRatio = (float) width/height;


        timgvBack = (TintOnTouchImageView) findViewById(R.id.timgv_back);
        timgvBack.setOnClickListener(this);
        timgvDone = (TintOnTouchImageView)findViewById(R.id.timgv_done);
        timgvDone.setOnClickListener(this);
        timgvRotateLeft = (TintOnTouchImageView)findViewById(R.id.timgv_rotate_left);
        timgvRotateLeft.setOnClickListener(this);
        timgvRotateRight = (TintOnTouchImageView)findViewById(R.id.timgv_rotate_right);
        timgvRotateRight.setOnClickListener(this);
        timgvFlipH = (TintOnTouchImageView)findViewById(R.id.timgv_flip_h);
        timgvFlipH.setOnClickListener(this);
        timgvFlipV = (TintOnTouchImageView)findViewById(R.id.timgv_flip_v);
        timgvFlipV.setOnClickListener(this);

        btn_rate_free = (Button) findViewById(R.id.btn_rate_free);
        btn_rate_1x1 = (Button)findViewById(R.id.btn_rate_1x1);
        btn_rate_3x2 = (Button)findViewById(R.id.btn_rate_3x2);
        btn_rate_4x3 = (Button)findViewById(R.id.btn_rate_4x3);
        btn_rate_4x6 = (Button)findViewById(R.id.btn_rate_4x6);
        btn_rate_5x7 = (Button)findViewById(R.id.btn_rate_5x7);
        btn_rate_8x10 = (Button)findViewById(R.id.btn_rate_8x10);
        btn_rate_16x9 = (Button)findViewById(R.id.btn_rate_16x9);

        btn_rate_free.setOnClickListener(this);
        btn_rate_1x1.setOnClickListener(this);
        btn_rate_3x2.setOnClickListener(this);
        btn_rate_4x3.setOnClickListener(this);
        btn_rate_4x6.setOnClickListener(this);
        btn_rate_5x7.setOnClickListener(this);
        btn_rate_8x10.setOnClickListener(this);
        btn_rate_16x9.setOnClickListener(this);

        cropImageView = (CropImageView)findViewById(R.id.cropImageView);
        mImagePath = getIntent().getExtras().getString(IMAGE_PATH);
        mSaveUri = Uri.fromFile(new File(mImagePath));
        //bitmap = BitmapFactory.decodeFile(mImagePath);
        bitmap = decodeFile(new File(mImagePath));

        cropImageView.setImageBitmap(bitmap);
        cropImageView.setGuidelines(2);

        cropImageView.setFixedAspectRatio(true);
        if (bitmap.getWidth() > bitmap.getHeight()) {
            cropImageView.setAspectRatio(height, width);
        }
        else
        {
            cropImageView.setAspectRatio(width, height);
        }

        Typeface typeFace = Typeface.createFromAsset(getAssets(), Defines.FONT_REGULAR);
        TextView tv_Title = (TextView)findViewById(R.id.tv_Title);
        tv_Title.setTypeface(typeFace);
        btn_rate_free.setTypeface(typeFace);
        btn_rate_1x1.setTypeface(typeFace);
        btn_rate_3x2.setTypeface(typeFace);
        btn_rate_4x3.setTypeface(typeFace);
        btn_rate_4x6.setTypeface(typeFace);
        btn_rate_5x7.setTypeface(typeFace);
        btn_rate_8x10.setTypeface(typeFace);
        btn_rate_16x9.setTypeface(typeFace);

        cropImageView.setFixedAspectRatio(false);
        btn_rate_free.setSelected(true);
        btn_rate_1x1.setSelected(false);
        btn_rate_3x2.setSelected(false);
        btn_rate_4x3.setSelected(false);
        btn_rate_4x6.setSelected(false);
        btn_rate_5x7.setSelected(false);
        btn_rate_8x10.setSelected(false);
        btn_rate_16x9.setSelected(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

    }

    public static Bitmap decodeFile(File f)
    {
        Bitmap b = null;
        try
        {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            try
            {
                BitmapFactory.decodeStream(fis, null, o);
            }
            finally
            {
                fis.close();
            }

            // In Samsung Galaxy S3, typically max memory is 64mb
            // Camera max resolution is 3264 x 2448, times 4 to get Bitmap memory of 30.5mb for one bitmap
            // If we use scale of 2, resolution will be halved, 1632 x 1224 and x 4 to get Bitmap memory of 7.62mb
            // We try use 25% memory which equals to 16mb maximum for one bitmap
            long maxMemory = Runtime.getRuntime().maxMemory();
            int maxMemoryForImage = (int) (maxMemory / 100 * 20);

            // Refer to
            // http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html
            // A full screen GridView filled with images on a device with
            // 800x480 resolution would use around 1.5MB (800*480*4 bytes)
            // When bitmap option's inSampleSize doubled, pixel height and
            // weight both reduce in half
            int scale = 1;
            while ((o.outWidth / scale) * (o.outHeight / scale) * 4 > maxMemoryForImage)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            try
            {
                b = BitmapFactory.decodeStream(fis, null, o2);
            }
            finally
            {
                fis.close();
            }
        }
        catch (IOException e)
        {
        }
        return b;
    }

    public Bitmap flip_h(Bitmap src)
    {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        return dst;
    }

    public Bitmap flip_v(Bitmap src)
    {
        Matrix m = new Matrix();
        m.preScale(1, -1);
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        return dst;
    }

    public Bitmap rotateBitmapZoom(Bitmap bmOrg, float degree, float zoom)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        float newHeight = bmOrg.getHeight() * zoom;
        float newWidth  = bmOrg.getWidth() / 100 * (100.0f / bmOrg.getHeight() * newHeight);
        return Bitmap.createBitmap(bmOrg, 0, 0, (int)newWidth, (int)newHeight, matrix, true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timgv_rotate_left:
                bitmap = rotateBitmapZoom(bitmap, -90.0f, 1);
                cropImageView.setImageBitmap(bitmap);
                break;
            case R.id.timgv_rotate_right:
                bitmap = rotateBitmapZoom(bitmap, 90.0f, 1);
                cropImageView.setImageBitmap(bitmap);
                break;
            case R.id.timgv_flip_h:
                bitmap = flip_h(bitmap);
                cropImageView.setImageBitmap(bitmap);
                break;
            case R.id.timgv_flip_v:
                bitmap = flip_v(bitmap);
                cropImageView.setImageBitmap(bitmap);
                break;
            case R.id.btn_rate_free:
                cropImageView.setFixedAspectRatio(false);
                btn_rate_free.setSelected(true);
                btn_rate_1x1.setSelected(false);
                btn_rate_3x2.setSelected(false);
                btn_rate_4x3.setSelected(false);
                btn_rate_4x6.setSelected(false);
                btn_rate_5x7.setSelected(false);
                btn_rate_8x10.setSelected(false);
                btn_rate_16x9.setSelected(false);
                return;

            case R.id.btn_rate_1x1:
                cropImageView.setFixedAspectRatio(true);
                cropImageView.setAspectRatio(10, 10);
                btn_rate_free.setSelected(false);
                btn_rate_1x1.setSelected(true);
                btn_rate_3x2.setSelected(false);
                btn_rate_4x3.setSelected(false);
                btn_rate_4x6.setSelected(false);
                btn_rate_5x7.setSelected(false);
                btn_rate_8x10.setSelected(false);
                btn_rate_16x9.setSelected(false);
                return;

            case R.id.btn_rate_3x2:
                cropImageView.setFixedAspectRatio(true);
                cropImageView.setAspectRatio(30, 20);
                btn_rate_free.setSelected(false);
                btn_rate_1x1.setSelected(false);
                btn_rate_3x2.setSelected(true);
                btn_rate_4x3.setSelected(false);
                btn_rate_4x6.setSelected(false);
                btn_rate_5x7.setSelected(false);
                btn_rate_8x10.setSelected(false);
                btn_rate_16x9.setSelected(false);
                return;

            case R.id.btn_rate_4x3:
                cropImageView.setFixedAspectRatio(true);
                cropImageView.setAspectRatio(40, 30);
                btn_rate_free.setSelected(false);
                btn_rate_1x1.setSelected(false);
                btn_rate_3x2.setSelected(false);
                btn_rate_4x3.setSelected(true);
                btn_rate_4x6.setSelected(false);
                btn_rate_5x7.setSelected(false);
                btn_rate_8x10.setSelected(false);
                btn_rate_16x9.setSelected(false);
                return;

            case R.id.btn_rate_4x6:
                cropImageView.setFixedAspectRatio(true);
                cropImageView.setAspectRatio(40, 60);
                btn_rate_free.setSelected(false);
                btn_rate_1x1.setSelected(false);
                btn_rate_3x2.setSelected(false);
                btn_rate_4x3.setSelected(false);
                btn_rate_4x6.setSelected(true);
                btn_rate_5x7.setSelected(false);
                btn_rate_8x10.setSelected(false);
                btn_rate_16x9.setSelected(false);
                return;

            case R.id.btn_rate_5x7:
                cropImageView.setFixedAspectRatio(true);
                cropImageView.setAspectRatio(50, 70);
                btn_rate_free.setSelected(false);
                btn_rate_1x1.setSelected(false);
                btn_rate_3x2.setSelected(false);
                btn_rate_4x3.setSelected(false);
                btn_rate_4x6.setSelected(false);
                btn_rate_5x7.setSelected(true);
                btn_rate_8x10.setSelected(false);
                btn_rate_16x9.setSelected(false);
                return;

            case R.id.btn_rate_8x10:
                cropImageView.setFixedAspectRatio(true);
                cropImageView.setAspectRatio(8, 10);
                btn_rate_free.setSelected(false);
                btn_rate_1x1.setSelected(false);
                btn_rate_3x2.setSelected(false);
                btn_rate_4x3.setSelected(false);
                btn_rate_4x6.setSelected(false);
                btn_rate_5x7.setSelected(false);
                btn_rate_8x10.setSelected(true);
                btn_rate_16x9.setSelected(false);
                return;

            case R.id.btn_rate_16x9:
                cropImageView.setFixedAspectRatio(true);
                cropImageView.setAspectRatio(160, 90);
                btn_rate_free.setSelected(false);
                btn_rate_1x1.setSelected(false);
                btn_rate_3x2.setSelected(false);
                btn_rate_4x3.setSelected(false);
                btn_rate_4x6.setSelected(false);
                btn_rate_5x7.setSelected(false);
                btn_rate_8x10.setSelected(false);
                btn_rate_16x9.setSelected(true);
                return;

            case R.id.timgv_back:
                Bundle extras = new Bundle();
                Intent intent = new Intent(mSaveUri.toString());
                intent.putExtras(extras);
                intent.putExtra(IMAGE_PATH, "");
                setResult(RESULT_OK, intent);
//                bitmap.recycle();
//                croppedImage.recycle();
                finish();
                return;
            case R.id.timgv_done:
                RectF rf = cropImageView.getActualCropRect();
                croppedImage = Bitmap.createBitmap(bitmap, (int)rf.left, (int)rf.top, (int)(rf.right - rf.left), (int)(rf.bottom-rf.top));
                //croppedImage = cropImageView.getCroppedImage();
                Util.startBackgroundJob(this, null, getString(R.string.importing),
                        new Runnable() {
                            public void run() {
                                saveOutput(croppedImage);
                                finish();
                            }
                        }, mHandler);
                break;

            default:
                break;
        }
    }

    private void saveOutput(Bitmap croppedImage)
    {
        if (mSaveUri != null)
        {
            OutputStream outputStream = null;
            try
            {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null)
                {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    if (croppedImage != null) {
                        croppedImage.recycle();
                        croppedImage = null;
                    }
                }
            }
            catch (IOException ex)
            {
                setResult(RESULT_CANCELED);
                finish();
                return;
            }
            finally
            {
                Util.closeSilently(outputStream);
            }

            Bundle extras = new Bundle();
            Intent intent = new Intent(mSaveUri.toString());
            intent.putExtras(extras);
            intent.putExtra(IMAGE_PATH, mImagePath);
            setResult(RESULT_OK, intent);
        }
        else
        {
        }
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        if (croppedImage != null) {
            croppedImage.recycle();
            croppedImage = null;
        }
        finish();
    }
}
