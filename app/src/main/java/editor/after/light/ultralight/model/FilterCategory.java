package editor.after.light.ultralight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by st on 11/9/16.
 */

public class FilterCategory {
    @SerializedName("FilterCategoryName")
    private String mFilterCategoryName;
    @SerializedName("FilterCategoryId")
    private String mFilterCategoryId;
    @SerializedName("Filter")
    private List<Filter> mFilterList = new ArrayList<Filter>();

    public String getFilterCategoryName() {
        return mFilterCategoryName;
    }
    public void setFilterCategoryName(String FilterCategoryName) {
        mFilterCategoryName = FilterCategoryName;
    }

    public String getFilterCategoryId() {
        return mFilterCategoryId;
    }
    public void setFilterCategoryId(String FilterCategoryId) {
        mFilterCategoryId = FilterCategoryId;
    }

    public List<Filter> getFilterList() {
        return mFilterList;
    }
    public void setFilterList(List<Filter> FilterList) {
        this.mFilterList = FilterList;
    }

    private boolean mBHasLoadedAllFilterIcon = false;
    public boolean getBHasLoadedAllFilterIcon() {return mBHasLoadedAllFilterIcon;}
    public void setBHasLoadedAllFilterIcon(boolean bHasLoadedAllFilterIcon) {
        mBHasLoadedAllFilterIcon = bHasLoadedAllFilterIcon;
    }

    private boolean mBChoosed = false;
    public boolean getBChoosed() {
        return mBChoosed;
    }
    public void setBChoosed(boolean isChoosed) {
        mBChoosed = isChoosed;
    }
}
