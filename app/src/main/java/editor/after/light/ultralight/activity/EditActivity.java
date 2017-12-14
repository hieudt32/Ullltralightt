package editor.after.light.ultralight.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.adapter.BlendAdapter;
import editor.after.light.ultralight.adapter.FilterAdapter;
import editor.after.light.ultralight.adapter.FilterCategoryAdapter;
import editor.after.light.ultralight.adapter.TextureAdapter;
import editor.after.light.ultralight.adapter.TextureCategoryAdapter;
import editor.after.light.ultralight.adapter.VerticalAdapter;
import editor.after.light.ultralight.model.Blend;
import editor.after.light.ultralight.model.Database;
import editor.after.light.ultralight.model.Filter;
import editor.after.light.ultralight.model.FilterCategory;
import editor.after.light.ultralight.model.Texture;
import editor.after.light.ultralight.model.TextureCategory;
import editor.after.light.ultralight.task.ApplyFilterTask;
import editor.after.light.ultralight.task.GetBlendThumbnailTask;
import editor.after.light.ultralight.task.GetFilterThumbnailTask;
import editor.after.light.ultralight.task.LoadImageTask;
import editor.after.light.ultralight.util.BitmapUtils;
import editor.after.light.ultralight.view.ProgressDialog;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageScreenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * Created by st on 11/8/16.
 */

public class EditActivity extends Activity implements View.OnClickListener {
    public static final String IMAGE_PATH = "filePath";
    private String mImagePath;
    private Uri mImageUri;
    private Bitmap mBitmap;
    private Database mDatabase;
    private LinkedHashMap<String, GPUImageFilter> mFilterLinkedHashMap;

    private LinkedHashMap<String, Filter> mFilterHashMap;

    private RelativeLayout rlImageHolder;
    private GPUImageView mGPUImageView;
    private ImageView mImgvDisplay;

    private RelativeLayout rlPanelContainer;
    private RelativeLayout rlPanelTexture;
    private RelativeLayout rlPanelFilter;
    private RelativeLayout rlPanelBlend;
    private RelativeLayout rlPanelEffect;
    private Button btnTexture;
    private Button btnFilter;
    private Button btnBlend;
    private Button btnEffect;
    private int mSelectedIndexMenu; //0: Texture, 1: Filter, 2: Blend, 3: Effect

    private RecyclerView rcvTextureCategory;
    private TextureCategoryAdapter adapterTextureCategory;
    private RecyclerView rcvTexture;
    private TextureAdapter adapterTexture;

    private RecyclerView rcvFilterCategory;
    private FilterCategoryAdapter adapterFilterCategory;
    private RecyclerView rcvFilter;
    private FilterAdapter adapterFilter;

    private RecyclerView rcvBlend;
    private BlendAdapter adapterBlend;

    private Texture mSelectedTexture;
    private Filter mSelectedFilter;
    private Blend mSelectedBlend;

    private ProgressDialog mProgressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initUI();
        loadImage(mImageUri);
        //mBitmap = BitmapUtils.getBitmapFromUri(this, mImageUri);
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void initUI() {
        mFilterLinkedHashMap = new LinkedHashMap();
        mFilterHashMap = new LinkedHashMap();

        mImageUri = getIntent().getData();
        mDatabase = Database.getInstance();
        InputStream ips = getResources().openRawResource(R.raw.data);
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(ips);
        mDatabase = gson.fromJson(reader, Database.class);

        rlImageHolder = (RelativeLayout) findViewById(R.id.rl_image_holder);
        rlImageHolder.setDrawingCacheEnabled(true);
        mGPUImageView = (GPUImageView) findViewById(R.id.imgv_gpuimageview);
        mImgvDisplay = (ImageView) findViewById(R.id.imgv_display);

        rlPanelTexture = (RelativeLayout) findViewById(R.id.rl_panel_texture);
        rlPanelFilter = (RelativeLayout) findViewById(R.id.rl_panel_filter);
        rlPanelBlend = (RelativeLayout) findViewById(R.id.rl_panel_blend);
        rlPanelEffect = (RelativeLayout) findViewById(R.id.rl_panel_effect);

        btnTexture = (Button) findViewById(R.id.btn_texture);
        btnFilter = (Button) findViewById(R.id.btn_filter);
        btnBlend = (Button) findViewById(R.id.btn_blend);
        btnEffect = (Button) findViewById(R.id.btn_effect);

        btnTexture.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        btnBlend.setOnClickListener(this);
        btnEffect.setOnClickListener(this);

        //TextureCategory
        rcvTextureCategory = (RecyclerView) findViewById(R.id.rcv_texture_category);
        rcvTextureCategory.setHasFixedSize(true);
        LinearLayoutManager llmTextureCategory = new LinearLayoutManager(this);
        llmTextureCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvTextureCategory.setLayoutManager(llmTextureCategory);
        adapterTextureCategory = new TextureCategoryAdapter(this, mDatabase.getTextureCategoryList());
        rcvTextureCategory.setAdapter(adapterTextureCategory);

        //Texture
        rcvTexture = (RecyclerView) findViewById(R.id.rcv_texture);
        rcvTexture.setHasFixedSize(true);
        LinearLayoutManager llmTexture = new LinearLayoutManager(this);
        llmTexture.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvTexture.setLayoutManager(llmTexture);

        //FilterCategory
        rcvFilterCategory = (RecyclerView) findViewById(R.id.rcv_filter_category);
        rcvFilterCategory.setHasFixedSize(true);
        LinearLayoutManager llmFilterCategory = new LinearLayoutManager(this);
        llmFilterCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvFilterCategory.setLayoutManager(llmFilterCategory);
        adapterFilterCategory = new FilterCategoryAdapter(this, mDatabase.getFilterCategoryList());
        rcvFilterCategory.setAdapter(adapterFilterCategory);

        //Filter
        rcvFilter = (RecyclerView) findViewById(R.id.rcv_filter);
        rcvFilter.setHasFixedSize(true);
        LinearLayoutManager llmFilter = new LinearLayoutManager(this);
        llmFilter.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvFilter.setLayoutManager(llmFilter);

        //Blend
        rcvBlend = (RecyclerView) findViewById(R.id.rcv_blend);
        rcvBlend.setHasFixedSize(true);
        LinearLayoutManager llmBlend = new LinearLayoutManager(this);
        llmBlend.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvBlend.setLayoutManager(llmBlend);

        mSelectedIndexMenu = 0;
        onMenuIndex(mSelectedIndexMenu);
    }

    public void loadImage(Uri uri) {
        openProgressDialog("Loading...");
        new LoadImageTask(this, this, uri).execute(new Void[0]);
    }

    public void finishLoadImage(Bitmap bitmap) {
        closeProgressDialog();
        float fRatio = (float) bitmap.getWidth() / ((float) bitmap.getHeight());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        if (bitmap.getWidth() > 1080.0f) {
            bitmap = BitmapUtils.getThumbnail(byteArray, 1920, 1080);
        }
        mGPUImageView.setRatio(fRatio);
        mGPUImageView.setImage(bitmap);
        //mGPUImageView.setVisibility(View.INVISIBLE);
        mBitmap = bitmap;
        mImgvDisplay.setImageBitmap(mBitmap);
    }

    private void openProgressDialog(String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void closeProgressDialog() {
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    public void onSelectedTextureCategory(TextureCategory textureCategory) {
        for (int i = 0; i < mDatabase.getTextureCategoryList().size(); i++) {
            TextureCategory tmpTextureCategory = mDatabase.getTextureCategoryList().get(i);
            if (tmpTextureCategory.getTextureCategoryId() == textureCategory.getTextureCategoryId()) {
                tmpTextureCategory.setBChoosed(true);
            } else {
                tmpTextureCategory.setBChoosed(false);
            }
        }
        adapterTextureCategory.notifyDataSetChanged();

        adapterTexture = new TextureAdapter(this, textureCategory);
        rcvTexture.setAdapter(adapterTexture);
    }

    public GPUImageFilter onSelectedTexture(Texture texture, TextureCategory textureCategory) {
        mSelectedTexture = texture;
        for (int i = 0; i < textureCategory.getTextureList().size(); i++) {
            Texture tmpTexture = textureCategory.getTextureList().get(i);
            if (tmpTexture.getTextureName() == texture.getTextureName()) {
                tmpTexture.setBChoosed(true);
            } else {
                tmpTexture.setBChoosed(false);
            }
        }
        adapterTexture.notifyDataSetChanged();

        GPUImageOverlayBlendFilter filter = new GPUImageOverlayBlendFilter();
        try {
            String s = "textures/" + texture.getTextureCategoryId() + "/" + texture.getTextureName() + ".jpg";
            filter.setBitmap(BitmapFactory.decodeStream(MainActivity.getContext().getAssets().open(s)));
            initializeFilters("texture", filter);
            return filter;
        } catch (IOException e2) {
            return null;
        }
    }

    public void onSelectedFilterCategory(FilterCategory filterCategory) {
        for (int i = 0; i < mDatabase.getFilterCategoryList().size(); i++) {
            FilterCategory tmpFilterCategory = mDatabase.getFilterCategoryList().get(i);
            if (tmpFilterCategory.getFilterCategoryId() == filterCategory.getFilterCategoryId()) {
                tmpFilterCategory.setBChoosed(true);
            } else {
                tmpFilterCategory.setBChoosed(false);
            }
        }
        adapterFilterCategory.notifyDataSetChanged();

        if (filterCategory.getBHasLoadedAllFilterIcon()) {
            showFilterList(filterCategory);
        } else {
            openProgressDialog("Loading filter...");
            new GetFilterThumbnailTask(MainActivity.getContext(), this, mImageUri, filterCategory, mFilterHashMap).execute(new Void[0]);
        }
    }

    public void showFilterThumbsWithFilterCategory(FilterCategory filterCategory) {
        closeProgressDialog();
        showFilterList(filterCategory);
    }

    public void showFilterList(FilterCategory filterCategory) {
        adapterFilter = new FilterAdapter(this, filterCategory);
        rcvFilter.setAdapter(adapterFilter);
    }

    public void onSelectedFilter(Filter filter, FilterCategory filterCategory) {
        mSelectedFilter = filter;
        for (int i = 0; i < filterCategory.getFilterList().size(); i++) {
            Filter tmpFilter = filterCategory.getFilterList().get(i);
            if (tmpFilter.getFilterId() == filter.getFilterId()) {
                tmpFilter.setBChoosed(true);
            } else {
                tmpFilter.setBChoosed(false);
            }
        }
        adapterFilter.notifyDataSetChanged();

        InputStream ips = null;
        GPUImageToneCurveFilter toneCurveFilter = new GPUImageToneCurveFilter();
        try {
            ips = MainActivity.getContext().getAssets().open(filter.getKey());
            toneCurveFilter.setFromCurveFileInputStream(ips);
            initializeFilters("filter", toneCurveFilter);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void showBlendThumbnails() {
        openProgressDialog("Loading...");
        new GetBlendThumbnailTask(this, this, mImageUri, mSelectedTexture, mDatabase.getBlendList()).execute(new Void[0]);
    }

    public void finishShowBlendThumbs(ArrayList<Bitmap> bitmaps) {
        closeProgressDialog();
        adapterBlend = new BlendAdapter(this, mDatabase.getBlendList());
        rcvBlend.setAdapter(adapterBlend);
    }

    public void onSelectedBlend(Blend blend) {
        initializeFilters("texture", blend.getGPUImageFilter());
    }

    public void initializeFilters(String key, GPUImageFilter filter) {
        openProgressDialog("Applying...");
        new ApplyFilterTask(this, this, key, filter, mImageUri, mFilterLinkedHashMap).execute(new Void[0]);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_texture:
                mSelectedIndexMenu = 0;
                onMenuIndex(mSelectedIndexMenu);
                break;
            case R.id.btn_filter:
                mSelectedIndexMenu = 1;
                onMenuIndex(mSelectedIndexMenu);
                break;
            case R.id.btn_blend:
                mSelectedIndexMenu = 2;
                onMenuIndex(mSelectedIndexMenu);
                showBlendThumbnails();
                break;
            case R.id.btn_effect:
                mSelectedIndexMenu = 3;
                onMenuIndex(mSelectedIndexMenu);
                break;
        }
    }

    public void onMenuIndex(int index) {
        TransitionSet setShow = new TransitionSet().addTransition(new Slide(Gravity.RIGHT)).setInterpolator(new LinearOutSlowInInterpolator());
        TransitionSet setHide = new TransitionSet().addTransition(new Scale(0.7f)).addTransition(new Fade()).setInterpolator(new FastOutLinearInInterpolator());
        if (index == 0) {
            btnTexture.setActivated(true);
            btnFilter.setActivated(false);
            btnBlend.setActivated(false);
            btnEffect.setActivated(false);

            TransitionManager.beginDelayedTransition(rlPanelTexture, setShow);
            rlPanelTexture.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(rlPanelFilter, setHide);
            rlPanelFilter.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelBlend, setHide);
            rlPanelBlend.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelEffect, setHide);
            rlPanelEffect.setVisibility(View.GONE);
        } else if (index == 1) {
            btnTexture.setActivated(false);
            btnFilter.setActivated(true);
            btnBlend.setActivated(false);
            btnEffect.setActivated(false);

            TransitionManager.beginDelayedTransition(rlPanelTexture, setShow);
            rlPanelTexture.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelFilter, setHide);
            rlPanelFilter.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(rlPanelBlend, setHide);
            rlPanelBlend.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelEffect, setHide);
            rlPanelEffect.setVisibility(View.GONE);
        } else if (index == 2) {
            btnTexture.setActivated(false);
            btnFilter.setActivated(false);
            btnBlend.setActivated(true);
            btnEffect.setActivated(false);

            TransitionManager.beginDelayedTransition(rlPanelTexture, setShow);
            rlPanelTexture.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelFilter, setHide);
            rlPanelFilter.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelBlend, setHide);
            rlPanelBlend.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(rlPanelEffect, setHide);
            rlPanelEffect.setVisibility(View.GONE);
        } else {
            btnTexture.setActivated(false);
            btnFilter.setActivated(false);
            btnBlend.setActivated(false);
            btnEffect.setActivated(true);

            TransitionManager.beginDelayedTransition(rlPanelTexture, setShow);
            rlPanelTexture.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelFilter, setHide);
            rlPanelFilter.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelBlend, setHide);
            rlPanelBlend.setVisibility(View.GONE);
            TransitionManager.beginDelayedTransition(rlPanelEffect, setHide);
            rlPanelEffect.setVisibility(View.VISIBLE);
        }
    }
}
