package editor.after.light.ultralight.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.activity.MainActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
/**
 * Created by st on 11/9/16.
 */

public class Database {
    private static Database self;
    public static Database getInstance() {
        if (self == null) {
            self = new Database();
        }
        return self;
    }

    @SerializedName("TextureCategory")
    private List<TextureCategory> mTextureCategoryList = new ArrayList<TextureCategory>();
    public List<TextureCategory> getTextureCategoryList() {
        return mTextureCategoryList;
    }
    public void setTextureCategoryList(List<TextureCategory> textureCategoryList) {
        mTextureCategoryList = textureCategoryList;
    }

    @SerializedName("FilterCategory")
    private List<FilterCategory> mFilterCategoryList = new ArrayList<FilterCategory>();
    public List<FilterCategory> getFilterCategoryList() {
        return mFilterCategoryList;
    }
    public void setFilterCategoryList(List<FilterCategory> filterCategoryList) {
        mFilterCategoryList = filterCategoryList;
    }

    @SerializedName("Blend")
    private List<Blend> mBlendList = new ArrayList<Blend>();
    public List<Blend> getBlendList() {
        return mBlendList;
    }
    public void setBlendList(List<Blend> blendList) {
        mBlendList = blendList;
    }




//        if (listTextureCategory == null) {
//            listTextureCategory = new ArrayList<TextureCategory>();
//            TextureCategory t0 = new TextureCategory("Enamel", "00", R.drawable.texture_category_enamel);
//            TextureCategory t1 = new TextureCategory("Grain & Grit", "01", R.drawable.texture_category_grain_and_grit);
//            TextureCategory t2 = new TextureCategory("Grunge", "02", R.drawable.texture_category_grunge);
//            TextureCategory t3 = new TextureCategory("Hippie", "03", R.drawable.texture_category_hippie);
//            TextureCategory t4 = new TextureCategory("Landscape", "04", R.drawable.texture_category_landscape_enhance);
//            TextureCategory t5 = new TextureCategory("LightLeaks 1", "05", R.drawable.texture_category_light_leaks_style_1);
//            TextureCategory t6 = new TextureCategory("LightLeaks 2", "06", R.drawable.texture_category_light_leaks_style_2);
//            TextureCategory t7 = new TextureCategory("LightLeaks 3", "07", R.drawable.texture_category_light_leaks_style_3);
//            TextureCategory t8 = new TextureCategory("Radiant Light", "08", R.drawable.texture_category_radiant_light);
//            TextureCategory t9 = new TextureCategory("Urban Rogue", "09", R.drawable.texture_category_urban_rogue);
//            TextureCategory t10 = new TextureCategory("Vignettes", "10", R.drawable.texture_category_vignettes);
//            TextureCategory t11 = new TextureCategory("Vintage Retro", "11", R.drawable.texture_category_vintage_retro);
//            listTextureCategory.add(t0);
//            listTextureCategory.add(t1);
//            listTextureCategory.add(t2);
//            listTextureCategory.add(t3);
//            listTextureCategory.add(t4);
//            listTextureCategory.add(t5);
//            listTextureCategory.add(t6);
//            listTextureCategory.add(t7);
//            listTextureCategory.add(t8);
//            listTextureCategory.add(t9);
//            listTextureCategory.add(t10);
//            listTextureCategory.add(t11);
//        }
//        return listTextureCategory;
}
