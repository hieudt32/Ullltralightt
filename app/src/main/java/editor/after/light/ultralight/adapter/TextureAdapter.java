package editor.after.light.ultralight.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.List;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.activity.EditActivity;
import editor.after.light.ultralight.activity.MainActivity;
import editor.after.light.ultralight.model.Texture;
import editor.after.light.ultralight.model.TextureCategory;
import editor.after.light.ultralight.other.Defines;

/**
 * Created by st on 11/8/16.
 */

public class TextureAdapter extends RecyclerView.Adapter<TextureAdapter.MyViewHolder> {
    private Activity mActivity;
    private TextureCategory mTextureCategory;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgvBackground;
        public RelativeLayout rlContainer;

        public MyViewHolder(View view) {
            super(view);
            rlContainer = (RelativeLayout) view.findViewById(R.id.rl_container);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            Typeface typeFace = Typeface.createFromAsset(MainActivity.getContext().getAssets(), Defines.FONT_REGULAR);
            tvTitle.setTypeface(typeFace);
            imgvBackground = (ImageView) view.findViewById(R.id.imgv_background);
        }
    }

    public TextureAdapter(Activity activity, TextureCategory textureCategory) {
        mActivity = activity;
        mTextureCategory = textureCategory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.texture_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Texture texture = mTextureCategory.getTextureList().get(position);
        if (texture.getBChoosed())
        {
            holder.rlContainer.setBackgroundColor(MainActivity.getContext().getResources().getColor(R.color.colorBackground1));
            holder.imgvBackground.setBackgroundResource(R.drawable.square_path_item_active);
            holder.tvTitle.setTextColor(MainActivity.getContext().getResources().getColor(R.color.text_color_active));
        }
        else
        {
            holder.rlContainer.setBackgroundColor(MainActivity.getContext().getResources().getColor(R.color.colorBackground2));
            holder.imgvBackground.setBackgroundResource(R.drawable.square_path_item_deactive);
            holder.tvTitle.setTextColor(MainActivity.getContext().getResources().getColor(R.color.text_color_deactive));
        }

        texture.setTextureCategoryId(mTextureCategory.getTextureCategoryId());
        String s = "file:///android_asset/textures/" + mTextureCategory.getTextureCategoryId() + "/" + texture.getTextureName() +".jpg";
        Glide.with(mActivity).load(Uri.parse(s)).centerCrop()
                .crossFade().thumbnail(1)
                .into(holder.imgvBackground);
        Glide.with(mActivity).load(s).into(holder.imgvBackground);
        holder.tvTitle.setText(texture.getTextureName());
        holder.imgvBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditActivity)mActivity).onSelectedTexture(texture, mTextureCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTextureCategory.getTextureList().size();
    }
}