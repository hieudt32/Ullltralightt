package editor.after.light.ultralight.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.adapter.GridViewAdapter;
import editor.after.light.ultralight.model.GridItemModel;
import editor.after.light.ultralight.other.Defines;


/**
 * Created by tranhoaison on 4/21/15.
 */
public class GalleryActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout ll_back;
    private LinearLayout ll_delete;
    private LinearLayout ll_done;
    private LinearLayout ll_edit;
    private TextView tv_Edit;
    private RelativeLayout rl_tool_bar;
    private ArrayList<GridItemModel> listData;
    private boolean mIsSelectMode = false;
    private GridView grid;
    private GridViewAdapter adapter;
    private File file;
    private int i;
    private Bitmap bmp;
    private ImageView imv;

    public void setContext(Context context)
    {
        mContext = context;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), Defines.FONT_REGULAR);
        TextView tv_Title = (TextView)findViewById(R.id.tv_Title);
        tv_Title.setTypeface(typeFace);
        TextView tv_Done = (TextView)findViewById(R.id.tv_Done);
        tv_Done.setTypeface(typeFace);
        TextView tv_Delete = (TextView)findViewById(R.id.tv_Delete);
        tv_Delete.setTypeface(typeFace);
        tv_Edit = (TextView)findViewById(R.id.tv_edit);
        tv_Edit.setTypeface(typeFace);
        tv_Edit.setText("EDIT");


        ll_back = (LinearLayout)findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        ll_delete = (LinearLayout)findViewById(R.id.ll_delete);
        ll_delete.setOnClickListener(this);
        ll_done = (LinearLayout)findViewById(R.id.ll_done);
        ll_done.setOnClickListener(this);
        ll_edit = (LinearLayout)findViewById(R.id.ll_edit);
        ll_edit.setOnClickListener(this);


        rl_tool_bar = (RelativeLayout)findViewById(R.id.rl_tool_bar);
        rl_tool_bar.setVisibility(View.GONE);

        ll_done.setVisibility(View.INVISIBLE);
        ll_delete.setVisibility(View.INVISIBLE);

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + "Bodytune");
            file.mkdirs();
        }

        listData = new ArrayList<GridItemModel>();
        if (file.isDirectory())
        {
            File[] listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++)
            {
                GridItemModel item = new GridItemModel();
                if (listFile[i].getAbsolutePath().endsWith("jpg")) {
                    item.setPath(listFile[i].getAbsolutePath());
                    listData.add(item);
                }
            }
        }

        grid = (GridView) findViewById(R.id.gv_image);
        adapter = new GridViewAdapter(this, listData, this);
        grid.setAdapter(adapter);
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!mIsSelectMode)
                {
                    mIsSelectMode = true;
                    ll_done.setVisibility(View.VISIBLE);
                    ll_delete.setVisibility(View.VISIBLE);
                    adapter.setMode(mIsSelectMode);
                    listData.get(position).setSelected(!listData.get(position).isSelected());
                    adapter.notifyDataSetInvalidated();
                    rl_tool_bar.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                i = position;
                if (mIsSelectMode)
                {
                    listData.get(i).setSelected(!listData.get(i).isSelected());
                    adapter.notifyDataSetInvalidated();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.getContext(), ShareActivity.class);
                    intent.putExtra("img_path", listData.get(i).getPath());
                    intent.putExtra("parent_activity", "1");
                    startActivity(intent);
                }
            }
        });

//        AdView adView = (AdView) this.findViewById(R.id.adsView);
//
//        if (Device.getInstance(this).isActivated())
//        {
//            adView.setVisibility(View.GONE);
//        }
//        else
//        {
//            AdRequest adRequest = new AdRequest.Builder().build();
//            adView.loadAd(adRequest);
//        }
    }

//    @Override
//    public void onBackPressed() {
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_edit:
                mIsSelectMode = !mIsSelectMode;
                if (mIsSelectMode) {
                    ll_done.setVisibility(View.VISIBLE);
                    ll_delete.setVisibility(View.VISIBLE);
                    adapter.setMode(mIsSelectMode);
                    adapter.notifyDataSetInvalidated();
                    rl_tool_bar.setVisibility(View.VISIBLE);
                    tv_Edit.setText("CANCEL");
                }
                else
                {
                    ll_done.setVisibility(View.INVISIBLE);
                    ll_delete.setVisibility(View.INVISIBLE);
                    adapter.setMode(mIsSelectMode);
                    adapter.notifyDataSetInvalidated();
                    rl_tool_bar.setVisibility(View.GONE);
                    tv_Edit.setText("EDIT");
                }
                break;

            case R.id.ll_delete:
                for (int i = 0; i < listData.size(); i++){
                    if (listData.get(i).isSelected()){
                        File file = new File(listData.get(i).getPath());
                        file.delete();
                        listData.remove(i);
                        MainActivity.refreshGallery(file, MainActivity.getContext());
                        i--;
                    }
                }
                mIsSelectMode = false;
                ll_done.setVisibility(View.INVISIBLE);
                ll_delete.setVisibility(View.INVISIBLE);
                adapter.setMode(mIsSelectMode);
                adapter.notifyDataSetInvalidated();
                rl_tool_bar.setVisibility(View.GONE);
                tv_Edit.setText("EDIT");

                break;

            case R.id.ll_done:
                mIsSelectMode = false;
                ll_done.setVisibility(View.INVISIBLE);
                ll_delete.setVisibility(View.INVISIBLE);
                adapter.setMode(mIsSelectMode);
                adapter.notifyDataSetInvalidated();
                rl_tool_bar.setVisibility(View.GONE);
                tv_Edit.setText("EDIT");
                break;

            default:
                break;
        }
    }
}
