package editor.after.light.ultralight.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by st on 11/10/16.
 */

public class Filter {
    @SerializedName("FilterName")
    private String mFilterName;

    public String getFilterName() {
        return mFilterName;
    }

    public void setFilterName(String filterName) {
        mFilterName = filterName;
    }

    @SerializedName("FilterId")
    private String mFilterId;

    public String getFilterId() {
        return mFilterId;
    }

    public void setFilterId(String filterId) {
        mFilterId = filterId;
    }

    private String mFilterCategoryId;

    public String getFilterCategoryId() {
        return mFilterCategoryId;
    }

    public void setFilterCategoryId(String filterCategoryId) {
        mFilterCategoryId = filterCategoryId;
    }

    private Bitmap mIconImage;

    public Bitmap getIconImage() {
        return mIconImage;
    }

    public void setIconImage(Bitmap iconImage) {
        mIconImage = iconImage;
    }

    private String mKey;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    private boolean mBChoosed = false;

    public boolean getBChoosed() {
        return mBChoosed;
    }

    public void setBChoosed(boolean isChoosed) {
        mBChoosed = isChoosed;
    }
}
