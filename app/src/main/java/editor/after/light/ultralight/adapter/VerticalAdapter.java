package editor.after.light.ultralight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.activity.MainActivity;
import editor.after.light.ultralight.model.TextureCategory;

/**
 * Created by st on 11/8/16.
 */

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

    private List<TextureCategory> listVertical;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgvBackground;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            imgvBackground = (ImageView) view.findViewById(R.id.imgv_background);
        }
    }

    public VerticalAdapter(List<TextureCategory> verticalList) {
        this.listVertical = verticalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TextureCategory textureCategory = listVertical.get(position);
        //holder.imgvBackground.setImageDrawable(MainActivity.getContext().getResources().getDrawable(textureCategory.mDrawableId));
        holder.tvTitle.setText(textureCategory.getTextureCategoryName());
        holder.imgvBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,holder.txtView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVertical.size();
    }
}