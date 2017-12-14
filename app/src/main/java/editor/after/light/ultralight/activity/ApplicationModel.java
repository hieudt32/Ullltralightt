package editor.after.light.ultralight.activity;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.util.ArrayList;

/**
 * Created by tranhoaison on 4/1/15.
 */
public class ApplicationModel extends Application
{
    public int avgColorThreshold;
    public int brightness;
    public int checkedScaleItem;
    public ArrayList fontList;
    public String imageName;
    public Bitmap newBitmap;
    public int originalBitmapHeight;
    public int originalBitmapWidth;
    public String saveDirectory;
    public Uri selectedImage;
    public int startingTextSize;
    public int targetScale;
    public ArrayList wordList;
    public int rotation;
    public boolean skipHardReset;

    public ApplicationModel()
    {
        saveDirectory = (new StringBuilder()).append(Environment.getExternalStorageDirectory()).append("/Ultralight").toString();
        checkedScaleItem = -1;
    }

    public void init()
    {
        fontList = new ArrayList();
        wordList = new ArrayList();
        hardReset();
    }

    public void softReset() {
        skipHardReset = true;
        newBitmap = null;
    }

    public void hardReset()
    {
        /*
        avgColorThreshold = 80;
        brightness = 0;
        newBitmap = null;
        rotation = 0;
        startingTextSize = 50;
        targetScale = 300;
        */
        avgColorThreshold = 80;
        brightness = 100;
        newBitmap = null;
        rotation = 0;
        startingTextSize = 150;
        targetScale = 2000;
    }
}
