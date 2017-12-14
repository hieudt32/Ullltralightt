package editor.after.light.ultralight.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.activity.EditActivity;
import editor.after.light.ultralight.activity.MainActivity;
import editor.after.light.ultralight.model.TextureCategory;
import editor.after.light.ultralight.other.Defines;

/**
 * Created by st on 11/8/16.
 */

public class TextureCategoryAdapter extends RecyclerView.Adapter<TextureCategoryAdapter.MyViewHolder> {
    private Activity mActivity;
    private List<TextureCategory> mTextureCategoryList;

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

    public TextureCategoryAdapter(Activity activity, List<TextureCategory> textureCategoryList) {
        mActivity = activity;
        mTextureCategoryList = textureCategoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.texture_category_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TextureCategory textureCategory = mTextureCategoryList.get(position);
        if (textureCategory.getBChoosed())
        {
            holder.imgvBackground.setBackgroundResource(R.drawable.rounded_corner_active);
        }
        else
        {
            holder.imgvBackground.setBackgroundResource(R.drawable.rounded_corner);
        }

        holder.tvTitle.setText(textureCategory.getTextureCategoryName());
        holder.imgvBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditActivity)mActivity).onSelectedTextureCategory(textureCategory);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTextureCategoryList.size();
    }
}