package editor.after.light.ultralight.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;

import editor.after.light.ultralight.R;
import editor.after.light.ultralight.other.Defines;
import editor.after.light.ultralight.other.Device;
import editor.after.light.ultralight.view.RatingDialog;


public class ShareActivity extends Activity implements OnClickListener {
	public static final String PARENT_ACT = "parent_activity";

	private RelativeLayout rlNav;
	private LinearLayout llBack;
	private LinearLayout llShareContainer;
	private LinearLayout llShareFacebook;
	private LinearLayout llShareTwitter;
	private LinearLayout llShareInstagram;
	private LinearLayout llShareMore;
	private ImageView imgvPhoto;
	private String strTempFile;
	private RelativeLayout rellRoot;

	int						intHeaderWidth;
	int						intHeaderHeight;
	int						intFooterWidth;
	int						intFooterHeight;

	private LinearLayout llBannerAds;
	private DisplayMetrics dpMetrics;
	private AdView adView;
	private ImageView imgvBack;
	private int intFlagParentActivity;

	private int intCountStart;

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_share);
		intFlagParentActivity = Integer.parseInt(getIntent().getExtras().getString(PARENT_ACT));
		getWidgets();

		// Get uri from intent
		strTempFile = getIntent().getStringExtra("img_path");
		if (strTempFile != null) {
			//strTempFile = GalleryActivity.FOLDER_PATH + strTempFile;
			Log.i("URI", strTempFile);
			File file = new File(strTempFile);
			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(strTempFile);

				if (bitmap != null) {
					resize(bitmap);
				}
			}
		}

		AdView adView = (AdView) this.findViewById(R.id.adsView);

		if (Device.getInstance(this).isActivated())
		{
			adView.setVisibility(View.GONE);
		}
		else
		{
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
		}

		intCountStart = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext()).getInt(Defines.COUNT_START_PREF, 0);
		showRateDialog();
	}

	@Override
	protected void onResume() {
		super.onResume();
//		if (!Device.getInstance(StartActivity.getContext()).isActivated()) {
//			showFbBannerAd();
//		}

//		if (checkNetworkState(this)) {
//			llBannerAds.setVisibility(View.VISIBLE);
//			showFbBannerAd();
//		} else {
//			llBannerAds.setVisibility(View.GONE);
//		}
	}

	public void showRateDialog()
	{
		int ratePref = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext()).getInt(Defines.RATE_PREF, 0);
		android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext()).edit();

		if (ratePref == 0 && (intCountStart == 0 || intCountStart > 3))
		{
			RatingDialog ratingDialog = new RatingDialog(this);
			ratingDialog.showAfter(1);
			intCountStart = 1;
		}

		intCountStart++;

		editor.putInt(Defines.COUNT_START_PREF, intCountStart);
		editor.commit();
	}

	public boolean checkNetworkState(Activity activity) {
		ConnectivityManager cm =
				(ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
	}

	private void getWidgets() {
		rlNav = (RelativeLayout) findViewById(R.id.rl_nav);
		llBack = (LinearLayout) findViewById(R.id.ll_back);
		llBack.setOnClickListener(this);
		imgvBack = (ImageView) findViewById(R.id.imgv_back);
		if (intFlagParentActivity == 0)
		{
			imgvBack.setImageResource(R.drawable.ic_home);
		}
		else
		{
			imgvBack.setImageResource(R.drawable.ic_back_normal);
		}

		Typeface typeFace = Typeface.createFromAsset(getAssets(), Defines.FONT_REGULAR);
		TextView tv_Title = (TextView)findViewById(R.id.tv_title);
		tv_Title.setTypeface(typeFace);
		TextView tv_Facebook = (TextView)findViewById(R.id.tv_Facebook);
		tv_Facebook.setTypeface(typeFace);
		TextView tv_Twitter = (TextView)findViewById(R.id.tv_Twitter);
		tv_Twitter.setTypeface(typeFace);
		TextView tv_Instagram = (TextView)findViewById(R.id.tv_Instagram);
		tv_Instagram.setTypeface(typeFace);
		TextView tv_More = (TextView)findViewById(R.id.tv_More);
		tv_More.setTypeface(typeFace);

		rellRoot = (RelativeLayout) findViewById(R.id.rellRoot);
		llShareContainer = (LinearLayout) findViewById(R.id.ll_share_container);
		llShareFacebook = (LinearLayout) findViewById(R.id.ll_share_facebook);
		llShareTwitter = (LinearLayout) findViewById(R.id.ll_share_twitter);
		llShareInstagram = (LinearLayout) findViewById(R.id.ll_share_instagram);
		llShareMore = (LinearLayout) findViewById(R.id.ll_share_more);
		llShareFacebook.setOnClickListener(this);
		llShareTwitter.setOnClickListener(this);
		llShareInstagram.setOnClickListener(this);
		llShareMore.setOnClickListener(this);
		imgvPhoto = (ImageView) findViewById(R.id.imgvPhoto);
	}


	/*
	private void showFbBannerAd() {
		dpMetrics = getResources().getDisplayMetrics();
		if (dpMetrics.widthPixels > 1200) {
			adView = new AdView(this, Defines.BANNER_ID, AdSize.BANNER_HEIGHT_90);
		}
		else {
			adView = new AdView(this, Defines.BANNER_ID, AdSize.BANNER_HEIGHT_50);
		}
		llBannerAds.addView(adView);
		//AdSettings.addTestDevice("2b249683fbcd80a63366ba26725a89d8");
		adView.setAdListener(new AdListener() {
			@Override
			public void onError(Ad ad, AdError adError) {
				llBannerAds.setVisibility(View.GONE);
			}

			@Override
			public void onAdLoaded(Ad ad) {}

			@Override
			public void onAdClicked(Ad ad) {}
		});
		adView.loadAd();
	}
	*/

	private void resize(final Bitmap bitmap) {
		rellRoot.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@SuppressLint("NewApi")
					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						rellRoot.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						intHeaderWidth = rlNav.getWidth();
						intHeaderHeight = rlNav.getHeight();
						Log.i("URI", intHeaderHeight + "");
						intFooterHeight = llShareContainer.getHeight();
						intFooterWidth = llShareContainer.getWidth();
						Log.i("URI", intFooterHeight + "");
						DisplayMetrics displaymetrics = new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(
								displaymetrics);
						int width = displaymetrics.widthPixels;
						int height = displaymetrics.heightPixels;
						int h = height - intFooterHeight - intHeaderHeight;
						int bw = bitmap.getWidth();
						int bh = bitmap.getHeight();
						Log.i("URI", "height: " + h);
						Log.i("URI", width + ":" + height);
						int temp;
						if (width > h) {
							temp = h;
						} else {
							temp = width;
						}
						int temp2;
						if (bw > bh) {
							temp2 = bh;
						} else {
							temp2 = bw;
						}
						if (temp2 < temp) {
							imgvPhoto.getLayoutParams().width = temp2;
							imgvPhoto.getLayoutParams().height = temp2;
						} else {
							imgvPhoto.getLayoutParams().width = temp;
							imgvPhoto.getLayoutParams().height = temp;
						}
						imgvPhoto.setImageBitmap(bitmap);
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_back:
				if (intFlagParentActivity == 0) {
					doHomeClicked();
				}else
				{
					doBackClicked();
				}
				break;
			case R.id.ll_share_facebook:
				doShareFacebook();
				break;
			case R.id.ll_share_instagram:
				doShareInstagram();
				break;
			case R.id.ll_share_twitter:
				doShareTwitter();
				break;
			//case R.id.linlEmail:
				//doShareEmail();
				//break;
			case R.id.ll_share_more:
				doShareMore();
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (intFlagParentActivity == 0) {
			doHomeClicked();
		}else
		{
			doBackClicked();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void doBackClicked() {
		finish();
	}

	private void doHomeClicked() {
		File fileTmp = new File(getFileTem(), "tmp.png");
		fileTmp.delete();
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private void doShareFacebook() {
		postImage("com.facebook.katana", strTempFile);
	}

	private void doShareInstagram() {
		postImage("com.instagram.android", strTempFile);
	}

	private void doShareTwitter() {
		postImage("com.twitter.android", strTempFile);
	}

	private void doShareEmail() {
		postImage("com.google.android.gm", strTempFile);
	}

	private void doShareMore() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/plain");
		try {
			intent.putExtra(Intent.EXTRA_STREAM, Uri
					.parse(MediaStore.Images.Media.insertImage(
							getContentResolver(), strTempFile,
							"Posted by InstaDownload", "Share happy !")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("NOTICE", "CAN'T SEND");
		}
		
		intent.setType("image/jpg");
		startActivity(Intent.createChooser(intent, "Share via"));
	}

	private void postImage(String uri, String imagePath) {
		Intent intent = getPackageManager().getLaunchIntentForPackage(uri);
		if (intent != null) {
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.setPackage(uri);
			try {
				shareIntent.putExtra(Intent.EXTRA_STREAM, Uri
						.parse(MediaStore.Images.Media.insertImage(
								getContentResolver(), imagePath,
								"Posted by InstaDownload", "Share happy !")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("NOTICE", "CAN'T SEND");
			}
			shareIntent.setType("image/jpg");
			startActivity(shareIntent);
		} else {
			intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setData(Uri.parse("market://details?id=" + uri));
			startActivity(intent);
		}
	}
	
	private File getFileTem()
	{
		File fileTem = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			fileTem = new File(Environment.getExternalStorageDirectory(),"PRISM2");
	    }
		else
		{
	        fileTem = getCacheDir();
	    }
	        
        if(!fileTem.exists())
        {
        	fileTem.mkdirs();
        }
        return fileTem;
	}
}
