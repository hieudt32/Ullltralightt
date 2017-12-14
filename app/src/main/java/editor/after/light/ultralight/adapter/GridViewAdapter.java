package editor.after.light.ultralight.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.model.GridItemModel;


public class GridViewAdapter extends BaseAdapter {
    // Declare variables
    private Activity activity;
    //private String[] path;
    private ArrayList<GridItemModel> listData;
    private Context context;
    private static LayoutInflater inflater = null;
    private boolean mIsSelectMode = false;
//    private ImageLoader imageLoader;
//    private ImageLoaderConfiguration configuration;
//    private DisplayImageOptions options;

    public GridViewAdapter(Activity a, ArrayList data, Context context) {
        activity = a;
        this.context = context;
        this.listData = data;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        configuration = new ImageLoaderConfiguration.Builder(context)
//                .threadPoolSize(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
//        options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
//                .bitmapConfig(Bitmap.Config.RGB_565).build();
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(configuration);
    }

    public void setMode(boolean isSelectMode){
        this.mIsSelectMode = isSelectMode;
    }

    public int getCount() {
        return listData.size();
    }

    public Object getItem(int position) {

        return listData.get(position);
    }

    public long getItemId(int position) {

        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final GridItemModel item = listData.get(position);
        final ViewHolder holder;
        if (vi == null) {
            vi = inflater.inflate(R.layout.gridview_item, null);
            holder = new ViewHolder(vi);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder)vi.getTag();
        }
        //imageLoader.displayImage("file:///" + item.getPath(), holder.image, options);
        Glide.with(context).load(Uri.fromFile(new File(item.getPath()))).centerCrop()
                .crossFade().thumbnail(1)
                .into(holder.image);

        if (mIsSelectMode){
            holder.cb.setVisibility(View.VISIBLE);
            if(item.isSelected()){
                holder.cb.setImageResource(R.drawable.ic_check);
                holder.over.setVisibility(View.GONE);
            } else {
                holder.cb.setImageResource(0);
                holder.over.setVisibility(View.VISIBLE);
            }
        } else {
            holder.cb.setVisibility(View.GONE);
            holder.over.setVisibility(View.GONE);
        }
        return vi;
    }

    static class ViewHolder{
        ImageView image;
        ImageView cb;
        View over;
        public ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.image_in);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            cb = (ImageView) view.findViewById(R.id.cb);
            over = view.findViewById(R.id.overlay);
        }
    }
}
