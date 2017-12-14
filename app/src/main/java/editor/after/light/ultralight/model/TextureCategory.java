package editor.after.light.ultralight.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by st on 11/9/16.
 */

public class TextureCategory {
    @SerializedName("TextureCategoryName")
    private String mTextureCategoryName;
    @SerializedName("TextureCategoryId")
    private String mTextureCategoryId;
    @SerializedName("Texture")
    private List<Texture> mTextureList = new ArrayList<Texture>();

    public String getTextureCategoryName() {
        return mTextureCategoryName;
    }
    public void setTextureCategoryName(String textureCategoryName) {
        mTextureCategoryName = textureCategoryName;
    }

    public String getTextureCategoryId() {
        return mTextureCategoryId;
    }
    public void setTextureCategoryId(String textureCategoryId) {
        mTextureCategoryId = textureCategoryId;
    }

    public List<Texture> getTextureList() {
        return mTextureList;
    }
    public void setTextureList(List<Texture> textureList) {
        this.mTextureList = textureList;
    }


    private boolean mBChoosed = false;
    public boolean getBChoosed() {
        return mBChoosed;
    }
    public void setBChoosed(boolean isChoosed) {
        mBChoosed = isChoosed;
    }
}
