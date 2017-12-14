package editor.after.light.ultralight.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.other.Defines;
import editor.after.light.ultralight.util.MarshMallowPermisson;
import editor.after.light.ultralight.view.RatingDialog;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int SELECT_PICTURE = 0x1;
    public static final int REQUEST_CAMERA = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;

    private ImageView imgvBackground;
    private LinearLayout llCollage;
    private LinearLayout llSetting;

    private ImageView imgvCamera;
    private ImageView imgvAlbum;
    private ImageView imgvCollage;
    private TextView tvCamera;
    private TextView tvAlbum;
    private TextView tvCollage;

    public File mFileTemp;
    private static MainActivity mContext;
    private String mKey;
    private int intCountStart;

    MarshMallowPermisson marshMallowPermisson = new MarshMallowPermisson(this);

    public static MainActivity getContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        mFileTemp = new File(this.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        imgvBackground = (ImageView) findViewById(R.id.imgv_background);
        llCollage = (LinearLayout) findViewById(R.id.ll_collage);
        llCollage.setOnClickListener(this);
        llSetting = (LinearLayout) findViewById(R.id.ll_setting);
        llSetting.setOnClickListener(this);
        tvCamera = (TextView) findViewById(R.id.tv_camera);
        tvAlbum = (TextView) findViewById(R.id.tv_album);
        tvCollage = (TextView) findViewById(R.id.tv_collage);

        imgvCamera = (ImageView) findViewById(R.id.imgv_camera);
        imgvCamera.setOnClickListener(this);
        imgvAlbum = (ImageView) findViewById(R.id.imgv_album);
        imgvAlbum.setOnClickListener(this);
        imgvCollage = (ImageView) findViewById(R.id.imgv_collage);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), Defines.FONT_REGULAR);
        tvCamera.setTypeface(typeFace);
        tvAlbum.setTypeface(typeFace);
        tvCollage.setTypeface(typeFace);

//        mKey = MainActivity.getMatL(MainActivity.getMatM(),
//                MainActivity.getMatH(), MainActivity.getMat1(),
//                MainActivity.getMatA(), MainActivity.getMatE(), MainActivity.getMatAI(),
//                MainActivity.getMatAB(), MainActivity.getMatEN());

        intCountStart = PreferenceManager.getDefaultSharedPreferences(mContext).getInt(Defines.COUNT_START_PREF, 0);

        if (!marshMallowPermisson.checkPermissionForExternalStorage()) {
            marshMallowPermisson.requestPermissionForExternalStorage();
        }

        File appDir = getFileTem();
        File childfile[] = appDir.listFiles();
        if (childfile != null) {
            if (childfile.length > 0) {
                showRateDialog();
                llCollage.setOnClickListener(this);
                imgvCollage.setImageDrawable(getResources().getDrawable(R.drawable.ic_collage_1));
                tvCollage.setTextColor(Color.WHITE);
            } else {
                llCollage.setOnClickListener(null);
                imgvCollage.setImageDrawable(getResources().getDrawable(R.drawable.ic_collage_1_deactive));
                tvCollage.setTextColor(Color.GRAY);
            }
        } else {
            llCollage.setOnClickListener(null);
            imgvCollage.setImageDrawable(getResources().getDrawable(R.drawable.ic_collage_1_deactive));
            tvCollage.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
        String fileName = String.format("demo/img%d.jpg",randInt(0, 2));
        Bitmap bmTmp = getBitmapFromAsset(mContext, fileName);
        imgvBackground.setScaleType(ImageView.ScaleType.FIT_XY);
        imgvBackground.setImageBitmap(bmTmp);
        */
    }

    protected void onStart() {
        super.onStart();
    }

    public void showRateDialog() {
        int ratePref = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext()).getInt(Defines.RATE_PREF, 0);
        android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext()).edit();

        if (ratePref == 0 && (intCountStart == 0 || intCountStart > 3)) {
            RatingDialog ratingDialog = new RatingDialog(this);
            ratingDialog.showAfter(1);
            intCountStart = 1;
        }

        intCountStart++;
        editor.putInt(Defines.COUNT_START_PREF, intCountStart);
        editor.commit();
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        Bitmap bitmap = null;
        try {
            inputStream = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            return null;
        }
        return bitmap;
    }

    public static void refreshGallery(File file, Context context) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        scanIntent.setData(contentUri);
        context.sendBroadcast(scanIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_camera:
                openCamera();
                break;
            case R.id.imgv_album:
                openAlbum();
                break;
            case R.id.ll_collage:
                Intent intentGallery = new Intent(getBaseContext(), GalleryActivity.class);
                startActivity(intentGallery);
                break;
            case R.id.ll_setting:
                //Intent intentSetting = new Intent(getBaseContext(), SettingActivity.class);
                //startActivity(intentSetting);
                break;
        }
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tmpFile = new File(getFileTem(), "captureImage.png");
        Uri uriImage = Uri.fromFile(tmpFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        try {
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void openAlbum() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MainActivity.SELECT_PICTURE:
//                try {
//                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
//                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
//                    copyStream(inputStream, fileOutputStream);
//                    fileOutputStream.close();
//                    inputStream.close();
//                    startCropImage();
//                } catch (Exception e) {
//                }
                Intent intent = new Intent(this, EditActivity.class);
                Uri x = data.getData();
                String x1 = x.toString();
                Uri x2 = Uri.parse(x1);
                intent.setData(data.getData());
                startActivity(intent);
                break;

            case MainActivity.REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        File tmpFile = new File(getFileTem(), "captureImage.png");
                        InputStream inputStream = new FileInputStream(tmpFile);
                        FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                        copyStream(inputStream, fileOutputStream);
                        fileOutputStream.close();
                        inputStream.close();
                        tmpFile.delete();
                        startCropImage();
                    } catch (Exception e) {
                    }
                }
                break;

            case MainActivity.REQUEST_CODE_CROP_IMAGE:
                String path = data.getStringExtra(CropActivity.IMAGE_PATH);
                //if (path == null)
                if (path.length() == 0) {
                    return;
                } else {
                    Intent editActivity = new Intent(this, EditActivity.class);
                    editActivity.putExtra(EditActivity.IMAGE_PATH, mFileTemp.getAbsolutePath());
                    startActivity(editActivity);
                }
                break;

            default:
                break;
        }
    }

    private File getFileTem() {
        File fileTem = null;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            fileTem = new File(android.os.Environment.getExternalStorageDirectory(), "SKEW");
        } else {
            fileTem = getCacheDir();
        }

        if (!fileTem.exists()) {
            fileTem.mkdirs();
        }
        return fileTem;
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private void startCropImage() {
        Intent intent = new Intent(this, CropActivity.class);
        intent.putExtra(CropActivity.IMAGE_PATH, mFileTemp.getPath());
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

//    public String getSF() { return MainActivity.decryptIt(MainActivity.getSF(), mKey);}
//    public String getSV() { return MainActivity.decryptIt(MainActivity.getSV(), mKey);}

    public static String encryptIt(String value, String cryptoPass) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes("UTF8");
            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            String encrypedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
            return encrypedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    }

    ;

    public static String decryptIt(String value, String cryptoPass) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encrypedPwdBytes = Base64.decode(value, Base64.DEFAULT);
            // cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));

            String decrypedValue = new String(decrypedValueBytes);
            return decrypedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    }

//    static {
//        System.loadLibrary("openGL");
//    }
//
//
    public static native String getMat1();
    public static native String getMatA();
    public static native String getMatAB();
    public static native String getMatAI();
    public static native String getMatE();
    public static native String getMatEN();
    public static native String getMatH();
    public static native String getMatL(String s0, String s1, String s2, String s3, String s4, String s5, String s6, String s7);
    public static native String getMatM();
    public static native String getASS();
    public static native String getEVS();
    public static native String getEFS();

}