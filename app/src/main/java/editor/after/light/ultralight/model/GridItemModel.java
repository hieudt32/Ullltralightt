package editor.after.light.ultralight.model;

/**
 * Created by mac on 3/31/15.
 */
public class GridItemModel {
    private String path;
    private boolean isSelected = false;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
