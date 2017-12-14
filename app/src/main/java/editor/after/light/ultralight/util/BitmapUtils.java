package editor.after.light.ultralight.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import editor.after.light.ultralight.other.Defines;

public class BitmapUtils {
    private static String bitmapFileName;
    public static String rootDir;
    private static String saveDir;

    public static Bitmap getBitmapFromAsset(String type, Context context) {
        InputStream istr = null;
        try {
            istr = context.getAssets().open(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    public static Bitmap getBitmapFromUri(Context context, Uri imageUri) {
        Bitmap bitmap = null;
        try {
            bitmap = Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void saveTemporaryBitmap(Context context, Bitmap bitmap) {
        Exception e;
        Throwable th;
        BufferedOutputStream bos = null;
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                rootDir = context.getFilesDir().getAbsolutePath();
            }
            if (!TextUtils.isEmpty(rootDir)) {
                saveDir = rootDir + File.separator + Defines.FILE_PATH + File.separator + Defines.THUMB_PATH;
                File dir = new File(saveDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                String tmpFilePath = saveDir + File.separator + fileName;
                setBitmapFileName(fileName);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, 100, stream);
                Bitmap smallBitmap = getThumbnail(stream.toByteArray(), 300, 300);
                stream.close();
                BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(tmpFilePath));
                try {
                    smallBitmap.compress(CompressFormat.JPEG, 100, bos2);
                    smallBitmap.recycle();
                    bos2.close();
                    MediaScannerConnection.scanFile(context, new String[]{tmpFilePath}, new String[]{"image/jpg"}, null);
                    bos = bos2;
                } catch (Exception e2) {
                    bos = bos2;
                    try {
                        e2.printStackTrace();
                        if (bos != null) {
                            try {
                                bos.close();
                            } catch (Exception e3) {
                                return;
                            }
                        }
                    } catch (Throwable th1) {
                        throw th1;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e5) {
                }
            }
        } catch (Exception e6) {
            e6.printStackTrace();
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getBitmapThumb(Bitmap bitmap, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    public static Bitmap getBitmapThumb(Context context) {
        saveDir = rootDir + File.separator + Defines.FILE_PATH + File.separator + Defines.THUMB_PATH;
        Bitmap bitmap = null;
        try {
            bitmap = Media.getBitmap(context.getContentResolver(), Uri.fromFile(new File(new File(saveDir), getBitmapFileName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void saveBitmapThumbs(int i, String[] fileNames, Context context, String type, String subType, ArrayList<String> fileList) {
        Exception e;
        Throwable th;
        BufferedOutputStream bos = null;
        try {
            if (!TextUtils.isEmpty(rootDir)) {
                String finalSaveDir = rootDir + File.separator + Defines.FILE_PATH + File.separator + type + File.separator + subType;
                File dir = new File(finalSaveDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                String tmpFilePath = finalSaveDir + File.separator + fileName;
                fileNames[i] = fileName;
                fileList.add(fileName);
                Bitmap filteredBitmap = getFilteredBitmap(context, i, getBitmapThumb(context), type, subType);
                BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(tmpFilePath));
                try {
                    filteredBitmap.compress(CompressFormat.JPEG, 70, bos2);
                    filteredBitmap.recycle();
                    MediaScannerConnection.scanFile(context, new String[]{tmpFilePath}, new String[]{"image/jpg"}, null);
                    bos = bos2;
                } catch (Exception e2) {
                    e = e2;
                    bos = bos2;
                    try {
                        e.printStackTrace();
                        if (bos != null) {
                            try {
                                bos.close();
                            } catch (Exception e3) {
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                } catch (Throwable th3) {
                    throw th3;
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e5) {
                }
            }
        } catch (Exception e6) {
            e6.printStackTrace();
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void saveFinalBitmap(Bitmap bitmap, Context context) {
        Exception e;
        Throwable th;
        BufferedOutputStream bos = null;
        try {
            String rootDir;
            if (Environment.getExternalStorageState().equals("mounted")) {
                rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                rootDir = context.getFilesDir().getAbsolutePath();
            }
            if (!TextUtils.isEmpty(rootDir)) {
                String saveDir = rootDir + File.separator + Defines.FINAL_FILE_PATH;
                File dir = new File(saveDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String tmpFilePath = saveDir + File.separator + System.currentTimeMillis() + ".jpg";
                File tmpFile = new File(tmpFilePath);
                BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(tmpFilePath));
                try {
                    bitmap.compress(CompressFormat.JPEG, 100, bos2);
                    bitmap.recycle();
                    MediaScannerConnection.scanFile(context, new String[]{tmpFilePath}, new String[]{"image/jpg"}, null);
                    bos = bos2;
                } catch (Exception e2) {
                    e = e2;
                    bos = bos2;
                    try {
                        e.printStackTrace();
                        if (bos != null) {
                            try {
                                bos.close();
                            } catch (Exception e3) {
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                } catch (Throwable th3) {
                    throw th3;
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e5) {
                }
            }
        } catch (Exception e6) {
            e6.printStackTrace();
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getThumbnail(byte[] bytes, int height, int width) {
        Options onlyBoundsOptions = new Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;
        onlyBoundsOptions.inPreferredConfig = Config.ARGB_8888;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, onlyBoundsOptions);
        if (onlyBoundsOptions.outWidth == -1 || onlyBoundsOptions.outHeight == -1) {
            return null;
        }
        int originalSize = onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = originalSize > height ? (double) (originalSize / width) : 1.0d;
        Options bitmapOptions = new Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Config.ARGB_8888;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bitmapOptions);
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) {
            return 1;
        }
        return k;
    }

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return path.delete();
    }

    private static Bitmap getFilteredBitmap(Context context, int i, Bitmap bitmap, String type, String subType) {
        return null;
    }

    private static void setBitmapFileName(String bitmapFileName) {
        bitmapFileName = bitmapFileName;
    }

    private static String getBitmapFileName() {
        return bitmapFileName;
    }

    @TargetApi(17)
    public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        blurScript.setRadius(25.0f);
        blurScript.setRadius(25.0f);
        blurScript.setRadius(25.0f);
        blurScript.setRadius(25.0f);
        blurScript.setRadius(25.0f);
        blurScript.setRadius(25.0f);
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        allOut.copyTo(outBitmap);
        rs.destroy();
        return outBitmap;
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight <= 0 || maxWidth <= 0) {
            return image;
        }
        float ratioBitmap = ((float) image.getWidth()) / ((float) image.getHeight());
        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (((float) maxWidth) / ((float) maxHeight) > 1.0f) {
            finalWidth = (int) (((float) maxHeight) * ratioBitmap);
        } else {
            finalHeight = (int) (((float) maxWidth) / ratioBitmap);
        }
        return Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
    }
}
