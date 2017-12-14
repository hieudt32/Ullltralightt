package editor.after.light.ultralight.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by st on 11/10/16.
 */

public class Blend {
    @SerializedName("BlendName")
    private String mBlendName;
    public String getBlendName() {
        return mBlendName;
    }
    public void setBlendName(String BlendName) {
        mBlendName = BlendName;
    }

//    @SerializedName("BlendId")
//    private String mBlendId;
//    public String getBlendId()
//    {
//        return mBlendId;
//    }
//    public void setBlendId(String BlendId)
//    {
//        mBlendId = BlendId;
//    }
//
//    private String mBlendCategoryId;
//    public String getBlendCategoryId()
//    {
//        return mBlendCategoryId;
//    }
//    public void setBlendCategoryId(String BlendCategoryId) {
//        mBlendCategoryId = BlendCategoryId;
//    }

    private Bitmap mIconImage;
    public Bitmap getIconImage()
    {
        return mIconImage;
    }
    public void setIconImage(Bitmap iconImage) {
        mIconImage = iconImage;
    }

    private GPUImageFilter mGPUImageFilter;
    public GPUImageFilter getGPUImageFilter()
    {
        return mGPUImageFilter;
    }
    public void setGPUImageFilter(GPUImageFilter gpuImageFilter) {
        mGPUImageFilter = gpuImageFilter;
    }

//    private String mKey;
//    public String getKey()
//    {
//        return mKey;
//    }
//    public void setKey(String key) {
//        mKey = key;
//    }

    private boolean mBChoosed = false;
    public boolean getBChoosed() {
        return mBChoosed;
    }
    public void setBChoosed(boolean isChoosed) {
        mBChoosed = isChoosed;
    }
}
