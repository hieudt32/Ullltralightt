package editor.after.light.ultralight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by st on 11/9/16.
 */

public class Texture {
    @SerializedName("TextureName")
    private String mTextureName;

    private String mTextureCategoryId;

    public String getTextureName() {
        return mTextureName;
    }
    public void setTextureName(String textureName) {
        mTextureName = textureName;
    }

    public String getTextureCategoryId()
    {
        return mTextureCategoryId;
    }
    public void setTextureCategoryId(String textureCategoryId) {
        mTextureCategoryId = textureCategoryId;
    }

    private boolean mBChoosed = false;
    public boolean getBChoosed() {
        return mBChoosed;
    }
    public void setBChoosed(boolean isChoosed) {
        mBChoosed = isChoosed;
    }
}

