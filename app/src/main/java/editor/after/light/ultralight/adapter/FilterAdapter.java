package editor.after.light.ultralight.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.activity.EditActivity;
import editor.after.light.ultralight.activity.MainActivity;
import editor.after.light.ultralight.model.Filter;
import editor.after.light.ultralight.model.FilterCategory;
import editor.after.light.ultralight.other.Defines;

/**
 * Created by st on 11/10/16.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    private Activity mActivity;
    FilterCategory mFilterCategory;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgvBackground;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            Typeface typeFace = Typeface.createFromAsset(MainActivity.getContext().getAssets(), Defines.FONT_REGULAR);
            tvTitle.setTypeface(typeFace);
            imgvBackground = (ImageView) view.findViewById(R.id.imgv_background);
        }
    }

    public FilterAdapter(Activity activity, FilterCategory filterCategory) {
        mActivity = activity;
        mFilterCategory = filterCategory;
    }

    @Override
    public FilterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item_view, parent, false);
        return new FilterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FilterAdapter.MyViewHolder holder, final int position) {
        final Filter filter = mFilterCategory.getFilterList().get(position);
        if (filter.getBChoosed()) {
            holder.imgvBackground.setActivated(true);
            holder.tvTitle.setTextColor(MainActivity.getContext().getResources().getColor(R.color.text_color_active));
        } else {
            holder.imgvBackground.setActivated(false);
            holder.tvTitle.setTextColor(MainActivity.getContext().getResources().getColor(R.color.text_color_deactive));
        }
        holder.tvTitle.setText(filter.getFilterName());
        holder.imgvBackground.setImageBitmap(filter.getIconImage());
        holder.imgvBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditActivity) mActivity).onSelectedFilter(filter, mFilterCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilterCategory.getFilterList().size();
    }
}